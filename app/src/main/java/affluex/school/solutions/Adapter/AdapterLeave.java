package affluex.school.solutions.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import affluex.school.solutions.Model.ModelLeave;
import affluex.school.solutions.R;

public class AdapterLeave extends RecyclerView.Adapter<AdapterLeave.HolderLeave>  {
    private Context context;
    private ArrayList<ModelLeave>leaveArrayList;
    private Boolean open=false;



    public interface OnStatusClick {
        void onApprove(int postion);

        void onDecline(int position);

    }

    public AdapterLeave(Context context, ArrayList<ModelLeave> leaveArrayList) {
        this.context = context;
        this.leaveArrayList = leaveArrayList;
    }



    @NonNull
    @Override
    public AdapterLeave.HolderLeave onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_leave_approval_single_row,parent,false);
        return new AdapterLeave.HolderLeave(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLeave.HolderLeave holder, int position) {
        ModelLeave modelLeave=leaveArrayList.get(position);

        holder.txt_students_name.setText(modelLeave.getStudentName()+"( "+modelLeave.getClassName()+" - "+modelLeave.getSectionName()+" )");
        if(modelLeave.getStatus().toLowerCase().equals("pending")){
            holder.ic_details.setVisibility(View.VISIBLE);
            holder.txt_status.setBackgroundColor(context.getColor(R.color.blue_900));
            holder.txt_status.setText("Pending");
        }else if(modelLeave.getStatus().toLowerCase().equals("approved")){
            holder.ic_details.setVisibility(View.GONE);
            holder.txt_status.setBackgroundColor(context.getColor(R.color.green_900));
            holder.txt_status.setText("Approved");
        }else if(modelLeave.getStatus().toLowerCase().equals("declined")){
            holder.ic_details.setVisibility(View.GONE);
            holder.txt_status.setBackgroundColor(context.getColor(R.color.red_900));
            holder.txt_status.setText("Declined");
        }
        holder.ic_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(open){
                    holder.ll_leave_approval.setVisibility(View.GONE);
                    open=false;
                    holder.ic_details.setImageResource(R.drawable.ic_drop_down);
                }else{
                    holder.ll_leave_approval.setVisibility(View.VISIBLE);
                    open=true;
                    holder.ic_details.setImageResource(R.drawable.ic_up);
                }
            }
        });
        holder.txt_from.setText(modelLeave.getFromDate());
        holder.txt_to.setText(modelLeave.getToDate());
        holder.txt_reason.setText(modelLeave.getReason());

        holder.btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("custom-action-local-broadcast");
                // on below line we are passing data to our broad cast receiver with key and value pair.
                intent.putExtra("status", "approve");
                intent.putExtra("position",holder.getAdapterPosition());
                if(TextUtils.isEmpty(holder.et_remark.getText().toString())){
                    intent.putExtra("description",",");
                }else{
                    intent.putExtra("description",holder.et_remark.getText().toString());
                }

                // on below line we are sending our broad cast with intent using broad cast manager.
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

            }
        });

        holder.btn_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("custom-action-local-broadcast");
                // on below line we are passing data to our broad cast receiver with key and value pair.
                intent.putExtra("status", "decline");
                intent.putExtra("position",holder.getAdapterPosition());
               if(TextUtils.isEmpty(holder.et_remark.getText().toString())){
                    intent.putExtra("description",",");
                }else{
                    intent.putExtra("description",holder.et_remark.getText().toString());
                }
                // on below line we are sending our broad cast with intent using broad cast manager.
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return leaveArrayList.size();
    }

    public class HolderLeave extends RecyclerView.ViewHolder {
        TextView txt_students_name,txt_from,txt_to,txt_reason,txt_status;
        ImageView ic_details;
        EditText et_remark;
        Button btn_approve,btn_decline;
        LinearLayout ll_leave_approval;
        public HolderLeave(@NonNull View itemView) {
            super(itemView);
            txt_students_name=itemView.findViewById(R.id.txt_students_name);
            txt_from=itemView.findViewById(R.id.txt_from);
            txt_to=itemView.findViewById(R.id.txt_to);
            txt_reason=itemView.findViewById(R.id.txt_reason);
            txt_status=itemView.findViewById(R.id.txt_status);
            ic_details=itemView.findViewById(R.id.ic_details);
            et_remark=itemView.findViewById(R.id.et_remark);
            btn_approve=itemView.findViewById(R.id.btn_approve);
            btn_approve=itemView.findViewById(R.id.btn_approve);
            btn_decline=itemView.findViewById(R.id.btn_decline);
            ll_leave_approval=itemView.findViewById(R.id.ll_leave_approval);
        }
    }
}

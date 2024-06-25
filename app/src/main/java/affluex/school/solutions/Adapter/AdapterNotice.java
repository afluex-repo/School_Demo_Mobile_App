package affluex.school.solutions.Adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import affluex.school.solutions.Model.NoticeDetails;
import affluex.school.solutions.R;

public class AdapterNotice extends RecyclerView.Adapter<AdapterNotice.HolderNotice> {

    private Context context;
    private List<NoticeDetails> noticeDetails;

    public AdapterNotice(Context context, List<NoticeDetails> noticeDetails) {
        this.context = context;
        this.noticeDetails = noticeDetails;
    }

    @NonNull
    @Override
    public AdapterNotice.HolderNotice onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_notice_single_row,parent,false);
        return new AdapterNotice.HolderNotice(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNotice.HolderNotice holder, int position) {
        NoticeDetails noticeDetails1=noticeDetails.get(position);
        holder.txt_date.setText(noticeDetails1.getNoticeDate());
        holder.txt_title.setText(noticeDetails1.getNoticeName());

        holder.ic_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(context);
                View mView = LayoutInflater.from(context).inflate(R.layout.dialog_view_notice,null);
                alert.setView(mView);

                TextView txt_class=mView.findViewById(R.id.txt_class);
                TextView txt_date=mView.findViewById(R.id.txt_date);
                EditText txt_homework_details=mView.findViewById(R.id.txt_homework_details);
                ImageView ic_close=mView.findViewById(R.id.ic_close);
                TextView txt_no_image=mView.findViewById(R.id.txt_no_image);
                Button btn_ok=mView.findViewById(R.id.btn_ok);
                Button btn_delete=mView.findViewById(R.id.btn_delete);
                Button btn_submit=mView.findViewById(R.id.btn_submit);
                txt_class.setText(noticeDetails1.getClassName()+"-"+noticeDetails1.getSectionName());
                txt_date.setText(noticeDetails1.getNoticeDate());
                txt_homework_details.setText(noticeDetails1.getNoticeName());
                txt_homework_details.setEnabled(false);
                btn_submit.setVisibility(View.GONE);
                btn_ok.setVisibility(View.VISIBLE);
                final android.app.AlertDialog alertDialog = alert.create();
                alertDialog.show();

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        txt_homework_details.setEnabled(true);
                        btn_ok.setVisibility(View.GONE);
                        btn_submit.setVisibility(View.VISIBLE);

                    }
                });
                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent("notice_edit");

                        intent.putExtra("notice_id",noticeDetails1.getPK_NoticeId());
                        intent.putExtra("class_id",noticeDetails1.getFK_ClassId());
                        intent.putExtra("section_id",noticeDetails1.getPK_NoticeId());
                        intent.putExtra("notice_name",txt_homework_details.getText().toString());
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        alertDialog.dismiss();


                    }
                });

                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent("notice");
                        intent.putExtra("notice_id",noticeDetails1.getPK_NoticeId());
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        alertDialog.dismiss();

                    }
                });
                ic_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return noticeDetails.size();
    }

    public class HolderNotice extends RecyclerView.ViewHolder {
        TextView txt_date,txt_title;
        ImageView ic_edit;
        public HolderNotice(@NonNull View itemView) {
            super(itemView);
            txt_date=itemView.findViewById(R.id.txt_date);
            txt_title=itemView.findViewById(R.id.txt_title);
            ic_edit=itemView.findViewById(R.id.ic_edit);
        }
    }
}

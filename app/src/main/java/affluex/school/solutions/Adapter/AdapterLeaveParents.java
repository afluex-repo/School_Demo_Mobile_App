package affluex.school.solutions.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import affluex.school.solutions.Model.ModelLeaveParents;
import affluex.school.solutions.R;

public class AdapterLeaveParents extends RecyclerView.Adapter<AdapterLeaveParents.HolderLeaveParents> {
    private Context context;
    private ArrayList<ModelLeaveParents>leaveParentsArrayList;

    public AdapterLeaveParents(Context context, ArrayList<ModelLeaveParents> leaveParentsArrayList) {
        this.context = context;
        this.leaveParentsArrayList = leaveParentsArrayList;
    }

    @NonNull
    @Override
    public AdapterLeaveParents.HolderLeaveParents onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_laeave_parents,parent,false);
        return new AdapterLeaveParents.HolderLeaveParents(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLeaveParents.HolderLeaveParents holder, int position) {
        ModelLeaveParents modelLeave=leaveParentsArrayList.get(position);

        holder.txt_students_name.setText(modelLeave.getStudentName()+"( "+modelLeave.getClassName()+" - "+modelLeave.getSectionName()+" )");
        if(modelLeave.getStatus().toLowerCase().equals("pending")){
            holder.txt_status.setBackgroundColor(context.getColor(R.color.blue_900));
            holder.txt_status.setText("Pending");
        }else if(modelLeave.getStatus().toLowerCase().equals("approved")){
            holder.txt_status.setBackgroundColor(context.getColor(R.color.green_900));
            holder.txt_status.setText("Approved");
        }else if(modelLeave.getStatus().toLowerCase().equals("declined")){
            holder.txt_status.setBackgroundColor(context.getColor(R.color.red_900));
            holder.txt_status.setText("Declined");
        }
        holder.txt_from.setText(modelLeave.getFromDate());
        holder.txt_to.setText(modelLeave.getToDate());
        holder.txt_reason.setText(modelLeave.getReason());
    }

    @Override
    public int getItemCount() {
        return leaveParentsArrayList.size();
    }

    public class HolderLeaveParents extends RecyclerView.ViewHolder {
        TextView txt_students_name,txt_from,txt_to,txt_reason,txt_status;
        public HolderLeaveParents(@NonNull View itemView) {
            super(itemView);
            txt_students_name=itemView.findViewById(R.id.txt_students_name);
            txt_from=itemView.findViewById(R.id.txt_from);
            txt_to=itemView.findViewById(R.id.txt_to);
            txt_reason=itemView.findViewById(R.id.txt_reason);
            txt_status=itemView.findViewById(R.id.txt_status);

        }
    }
}

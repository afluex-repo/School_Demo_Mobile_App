package affluex.school.solutions.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import affluex.school.solutions.Model.ModelAssignment;
import affluex.school.solutions.Model.ModelAttendance;
import affluex.school.solutions.Model.ResponseAttendnace;
import affluex.school.solutions.R;

public class AdapterAttendance extends RecyclerView.Adapter<AdapterAttendance.HolderAttendance> {
    private Context context;
    private ArrayList<ModelAttendance> attendnaceArrayList;

    public AdapterAttendance(Context context, ArrayList<ModelAttendance> attendnaceArrayList) {
        this.context = context;
        this.attendnaceArrayList = attendnaceArrayList;
    }

    @NonNull
    @Override
    public AdapterAttendance.HolderAttendance onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_attendance_list_single_row,parent,false);
        return new AdapterAttendance.HolderAttendance(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAttendance.HolderAttendance holder, int position) {
        ModelAttendance modelAttendance=attendnaceArrayList.get(position);
        holder.txt_students_name.setText(modelAttendance.getAttendanceDate());
        holder.txt_from.setText(modelAttendance.getInTime());
        holder.txt_to.setText(modelAttendance.getOutTime());
        holder.txt_reason.setText(modelAttendance.getLatitude()+" , "+modelAttendance.getLongitude());
        holder.txt_status.setText(modelAttendance.getOutLatitude()+" , "+modelAttendance.getOutLongitude());

    }

    @Override
    public int getItemCount() {
        return attendnaceArrayList.size();
    }

    public class HolderAttendance extends RecyclerView.ViewHolder {
        TextView txt_students_name,txt_from,txt_to,txt_reason,txt_status;
        public HolderAttendance(@NonNull View itemView) {
            super(itemView);
            txt_students_name=itemView.findViewById(R.id.txt_students_name);
            txt_from=itemView.findViewById(R.id.txt_from);
            txt_to=itemView.findViewById(R.id.txt_to);
            txt_reason=itemView.findViewById(R.id.txt_reason);
            txt_status=itemView.findViewById(R.id.txt_status);
        }
    }
}

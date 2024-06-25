package affluex.school.solutions.Adapter;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import affluex.school.solutions.Model.ModelStudentAttendance;
import affluex.school.solutions.R;

public class AdapterStudentsAttendance extends RecyclerView.Adapter<AdapterStudentsAttendance.HolderStudentsAttendance> {
    private Context context;
    private ArrayList<ModelStudentAttendance> studentAttendanceArrayList;

    public AdapterStudentsAttendance(Context context, ArrayList<ModelStudentAttendance> studentAttendanceArrayList) {
        this.context = context;
        this.studentAttendanceArrayList = studentAttendanceArrayList;
    }

    @NonNull
    @Override
    public AdapterStudentsAttendance.HolderStudentsAttendance onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_student_attendance_single_row,parent,false);

        return new AdapterStudentsAttendance.HolderStudentsAttendance(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterStudentsAttendance.HolderStudentsAttendance holder, int position) {

        if(position>0){
            ModelStudentAttendance modelStudentAttendance=studentAttendanceArrayList.get(position-1);
            holder.txt_sr_no.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            holder.txt_name.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            holder.txt_class.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            holder.txt_date.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            holder.txt_status.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

            holder.txt_sr_no.setText(""+(position));
            holder.txt_name.setText(modelStudentAttendance.getStudentName());
            holder.txt_class.setText(modelStudentAttendance.getClassName()+" - "+modelStudentAttendance.getSectionName());
            holder.txt_status.setText(modelStudentAttendance.getStatus());
            holder.txt_date.setText(modelStudentAttendance.getAttendanceDate());
        }else{
            holder.txt_sr_no.setTypeface(Typeface.DEFAULT_BOLD);
            holder.txt_name.setTypeface(Typeface.DEFAULT_BOLD);
            holder.txt_class.setTypeface(Typeface.DEFAULT_BOLD);
            holder.txt_date.setTypeface(Typeface.DEFAULT_BOLD);
            holder.txt_status.setTypeface(Typeface.DEFAULT_BOLD);
        }



    }

    @Override
    public int getItemCount() {
        return studentAttendanceArrayList.size()+1;
    }

    public class HolderStudentsAttendance extends RecyclerView.ViewHolder {
        TextView txt_sr_no,txt_name,txt_class,txt_date,txt_status;
        public HolderStudentsAttendance(@NonNull View itemView) {
            super(itemView);
            txt_sr_no=itemView.findViewById(R.id.txt_sr_no);
            txt_name=itemView.findViewById(R.id.txt_name);
            txt_class=itemView.findViewById(R.id.txt_class);
            txt_date=itemView.findViewById(R.id.txt_date);
            txt_status=itemView.findViewById(R.id.txt_status);
        }
    }
}

package affluex.school.solutions.Adapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import affluex.school.solutions.Model.StudentDetails;
import affluex.school.solutions.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterSelectStudent extends RecyclerView.Adapter<AdapterSelectStudent.HolderSelectStudent> {
    private Context context;
    private ArrayList<StudentDetails> studentDetails;


    public AdapterSelectStudent(Context context, ArrayList<StudentDetails> studentDetails) {
        this.context = context;
        this.studentDetails = studentDetails;
    }

    @NonNull
    @Override
    public AdapterSelectStudent.HolderSelectStudent onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_student_details_single_row,parent,false);
        return new HolderSelectStudent(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSelectStudent.HolderSelectStudent holder, int position) {
        StudentDetails studentDetails1=studentDetails.get(position);
        holder.txt_name.setText(studentDetails1.getStudentName());
        holder.txt_class.setText(studentDetails1.getClassName()+" - "+studentDetails1.getSectionName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("student_selected");

                SharedPreferences sharedPreferences=context.getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("pk_studentId",studentDetails1.getPkStudentId());
                editor.putString("pk_studentName",studentDetails1.getStudentName());
                editor.apply();
                editor.commit();
                intent.putExtra("position",holder.getAdapterPosition());
                intent.putExtra("pk_studentId",studentDetails1.getPkStudentId());
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return studentDetails.size();
    }

    public class HolderSelectStudent extends RecyclerView.ViewHolder {
        CircleImageView img_profile;
        TextView txt_class,txt_name;
        public HolderSelectStudent(@NonNull View itemView) {
            super(itemView);
            img_profile=itemView.findViewById(R.id.img_profile);
            txt_class=itemView.findViewById(R.id.txt_class);
            txt_name=itemView.findViewById(R.id.txt_name);
        }
    }
}

package affluex.school.solutions.Adapter;

import static android.os.Build.VERSION_CODES.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import affluex.school.solutions.Model.HomeWorkDetails;
import affluex.school.solutions.R;

public class AdapterParentsAssignment extends RecyclerView.Adapter<AdapterParentsAssignment.HolderParentsAssignment> {
    private Context context;
    private ArrayList<HomeWorkDetails> homeWorkDetails;

    public AdapterParentsAssignment(Context context, ArrayList<HomeWorkDetails> homeWorkDetails) {
        this.context = context;
        this.homeWorkDetails = homeWorkDetails;
    }

    @NonNull
    @Override
    public AdapterParentsAssignment.HolderParentsAssignment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(affluex.school.solutions.R.layout.layout_parents_homework_single_row,parent,false);

        return new AdapterParentsAssignment.HolderParentsAssignment(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterParentsAssignment.HolderParentsAssignment holder, int position) {
        HomeWorkDetails homeWorkDetails1=homeWorkDetails.get(position);
        holder.ic_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.txt_stu.setText(homeWorkDetails1.getStudentName());
        holder.txt_class.setText(homeWorkDetails1.getClassName()+" - "+ homeWorkDetails1.getSectionName());
        holder.txt_date.setText(homeWorkDetails1.getHomeworkDate());
        holder.txt_subject.setText(homeWorkDetails1.getSubjectName());

    }

    @Override
    public int getItemCount() {
        return homeWorkDetails.size();
    }

    public class HolderParentsAssignment extends RecyclerView.ViewHolder {

        TextView txt_stu,txt_subject,txt_class,txt_date,ic_next;
        public HolderParentsAssignment(@NonNull View itemView) {
            super(itemView);
            txt_stu=itemView.findViewById(affluex.school.solutions.R.id.txt_stu);
            txt_subject=itemView.findViewById(affluex.school.solutions.R.id.txt_subject);
            txt_class=itemView.findViewById(affluex.school.solutions.R.id.txt_class);
            txt_date=itemView.findViewById(affluex.school.solutions.R.id.txt_date);
            ic_next=itemView.findViewById(affluex.school.solutions.R.id.ic_next);
        }
    }
}

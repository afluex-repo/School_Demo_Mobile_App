package affluex.school.solutions.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import affluex.school.solutions.Model.ModelAssignment;
import affluex.school.solutions.R;

public class AdapterAssignment extends RecyclerView.Adapter<AdapterAssignment.HolderAssignment> {
    private Context context;
    private ArrayList<ModelAssignment> assignmentArrayList;


    public AdapterAssignment(Context context, ArrayList<ModelAssignment> assignmentArrayList) {
        this.context = context;
        this.assignmentArrayList = assignmentArrayList;
    }

    @NonNull
    @Override
    public AdapterAssignment.HolderAssignment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_assignment_single_row,parent,false);
        return new AdapterAssignment.HolderAssignment(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAssignment.HolderAssignment holder, int position) {
        ModelAssignment modelAssignment=assignmentArrayList.get(position);
        holder.txt_date.setText(modelAssignment.getHomeworkDate());
        holder.txt_subject.setText(modelAssignment.getSubjectId());
        holder.txt_title.setText(modelAssignment.getClassName()+" - "+modelAssignment.getSectionName());
        holder.ic_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "On Progress", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return assignmentArrayList.size();
    }

    public class HolderAssignment extends RecyclerView.ViewHolder {
        TextView txt_date,txt_title,txt_subject;
        ImageView ic_next;
        public HolderAssignment(@NonNull View itemView) {
            super(itemView);
            txt_date=itemView.findViewById(R.id.txt_date);
            txt_title=itemView.findViewById(R.id.txt_title);
            txt_subject=itemView.findViewById(R.id.txt_subject);
            ic_next=itemView.findViewById(R.id.ic_next);
        }
    }
}

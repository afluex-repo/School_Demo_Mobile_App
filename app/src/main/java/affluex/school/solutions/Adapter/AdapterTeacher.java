package affluex.school.solutions.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import affluex.school.solutions.Model.ModelTeachers;
import affluex.school.solutions.R;

public class AdapterTeacher extends RecyclerView.Adapter<AdapterTeacher.HolderTeacher> {
    private Context context;
    private ArrayList<ModelTeachers>teachersArrayList;

    public AdapterTeacher(Context context, ArrayList<ModelTeachers> teachersArrayList) {
        this.context = context;
        this.teachersArrayList = teachersArrayList;
    }

    @NonNull
    @Override
    public AdapterTeacher.HolderTeacher onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_teacher_single_row,parent,false);
        return new AdapterTeacher.HolderTeacher(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTeacher.HolderTeacher holder, int position) {
        ModelTeachers modelTeachers=teachersArrayList.get(position);
        holder.txt_designation.setText(modelTeachers.getDesignation());
        holder.txt_mobile.setText(modelTeachers.getMobile());
        holder.txt_name.setText(modelTeachers.getName());
    }

    @Override
    public int getItemCount() {
        return teachersArrayList.size();
    }

    public class HolderTeacher extends RecyclerView.ViewHolder {
        ImageView img_profile,img_next;
        TextView txt_designation,txt_mobile,txt_name;
        public HolderTeacher(@NonNull View itemView) {
            super(itemView);
            img_profile=itemView.findViewById(R.id.img_profile);
            img_next=itemView.findViewById(R.id.img_next);
            txt_designation=itemView.findViewById(R.id.txt_designation);
            txt_mobile=itemView.findViewById(R.id.txt_mobile);
            txt_name=itemView.findViewById(R.id.txt_name);
        }
    }
}

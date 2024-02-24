package affluex.school.solutions.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
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

        holder.txt_stu.setText(homeWorkDetails1.getStudentName()+" ( "+homeWorkDetails1.getClassName()+" - "+ homeWorkDetails1.getSectionName()+" )");
        holder.txt_date.setText(homeWorkDetails1.getHomeworkDate());
        holder.txt_subject.setText(homeWorkDetails1.getSubjectName());

        holder.ic_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(context);
                View mView = LayoutInflater.from(context).inflate(affluex.school.solutions.R.layout.dialog_homework_details,null);
                alert.setView(mView);

                TextView txt_class=mView.findViewById(affluex.school.solutions.R.id.txt_class);
                TextView txt_subject=mView.findViewById(R.id.txt_subject);
                TextView txt_date=mView.findViewById(R.id.txt_date);
                TextView txt_homework_details=mView.findViewById(R.id.txt_homework_details);
                ImageView img_homework=mView.findViewById(R.id.img_homework);
                ImageView ic_close=mView.findViewById(R.id.ic_close);
                TextView txt_no_image=mView.findViewById(R.id.txt_no_image);
                Button btn_ok=mView.findViewById(R.id.btn_ok);

                Button btn_delete=mView.findViewById(R.id.btn_delete);
                btn_delete.setVisibility(View.GONE);
                txt_class.setText(homeWorkDetails1.getClassName()+"-"+homeWorkDetails1.getSectionName());
                txt_subject.setText(homeWorkDetails1.getSubjectName());
                txt_date.setText(homeWorkDetails1.getHomeworkDate());
                txt_homework_details.setText(homeWorkDetails1.getHomeworkText());
                if(homeWorkDetails1.getHomeworkFile()==null ||homeWorkDetails1.getHomeworkFile().equals("") ){
                    txt_no_image.setVisibility(View.VISIBLE);
                    img_homework.setVisibility(View.GONE);
                }else{
                    txt_no_image.setVisibility(View.GONE);
                    img_homework.setVisibility(View.VISIBLE);
                }

                final android.app.AlertDialog alertDialog = alert.create();
                alertDialog.show();


                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
        return homeWorkDetails.size();
    }

    public class HolderParentsAssignment extends RecyclerView.ViewHolder {

        TextView txt_stu,txt_subject,txt_date,ic_next;
        public HolderParentsAssignment(@NonNull View itemView) {
            super(itemView);
            txt_stu=itemView.findViewById(affluex.school.solutions.R.id.txt_stu);
            txt_subject=itemView.findViewById(affluex.school.solutions.R.id.txt_subject);

            txt_date=itemView.findViewById(affluex.school.solutions.R.id.txt_date);
            ic_next=itemView.findViewById(affluex.school.solutions.R.id.ic_next);
        }
    }
}

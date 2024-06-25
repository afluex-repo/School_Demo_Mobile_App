package affluex.school.solutions.Adapter;
import static android.view.View.GONE;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import affluex.school.solutions.Model.ModelAssignment;
import affluex.school.solutions.R;
import affluex.school.solutions.app.TouchImageView;


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
        holder.txt_subject.setText(modelAssignment.getSubjectName());

        holder.txt_title.setText(modelAssignment.getClassName()+" - "+modelAssignment.getSectionName());
        holder.ic_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(context);
                View mView = LayoutInflater.from(context).inflate(R.layout.dialog_homework_details,null);
                alert.setView(mView);

                TextView txt_class=mView.findViewById(R.id.txt_class);
                TextView txt_subject=mView.findViewById(R.id.txt_subject);
                TextView txt_date=mView.findViewById(R.id.txt_date);
                TextView txt_homework_details=mView.findViewById(R.id.txt_homework_details);
                ImageView img_homework=mView.findViewById(R.id.img_homework);
                ImageView ic_close=mView.findViewById(R.id.ic_close);
                ImageView btn_close=mView.findViewById(R.id.btn_close);
                ImageView enlarged_image=mView.findViewById(R.id.enlarged_image);
                TextView txt_no_image=mView.findViewById(R.id.txt_no_image);
                LinearLayout ll_enlarged=mView.findViewById(R.id.ll_enlarged);
                Button btn_ok=mView.findViewById(R.id.btn_ok);
                Button btn_delete=mView.findViewById(R.id.btn_delete);
                txt_class.setText(modelAssignment.getClassName()+"-"+modelAssignment.getSectionName());
                txt_subject.setText(modelAssignment.getSubjectName());
                txt_date.setText(modelAssignment.getHomeworkDate());
                txt_homework_details.setText(modelAssignment.getHomeWorkHTML());
                final android.app.AlertDialog alertDialog = alert.create();
                alertDialog.show();

                ll_enlarged.setVisibility(GONE);
                if(modelAssignment.getHomeworkFile()==null ||modelAssignment.getHomeworkFile().equals("") ){
                    txt_no_image.setVisibility(View.VISIBLE);
                    img_homework.setVisibility(GONE);
                }else{
                    txt_no_image.setVisibility(GONE);
                    img_homework.setVisibility(View.VISIBLE);
                    String substring=modelAssignment.getHomeworkFile();
                    String link="https://school.afluex.com"+substring;
                    Log.e("Title123",link);


                    Picasso.get().load(link).
                            resize(400,400).centerCrop()
                            .placeholder(R.drawable.user3)
                            .into(img_homework);

                    Picasso.get().load(link).
                            resize(400,400).centerCrop()
                            .placeholder(R.drawable.user3)
                            .into(enlarged_image);

                    img_homework.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            final android.app.AlertDialog.Builder alert1 = new android.app.AlertDialog.Builder(context);
                            View mView1 = LayoutInflater.from(context).inflate(R.layout.layout_enlarged_image,null);
                            alert1.setView(mView1);

                            TouchImageView touchImageView=mView1.findViewById(R.id.image_view);
                            ImageView ic_close=mView1.findViewById(R.id.ic_close);

                            Glide.with(context).load(link)
                                    .into(touchImageView);

                            android.app.AlertDialog alertDialog1 = alert1.create();
                            alertDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            alertDialog1.show();

                            ic_close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog1.dismiss();
                                }
                            });


                        }
                    });
                    btn_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ll_enlarged.setVisibility(GONE);

                        }
                    });
                }

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent("homework");
                        intent.putExtra("homework_id",modelAssignment.getHomeWorkID());
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

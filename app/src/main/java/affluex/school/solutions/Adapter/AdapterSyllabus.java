package affluex.school.solutions.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import affluex.school.solutions.Model.ModelSyllabus;
import affluex.school.solutions.R;

public class AdapterSyllabus extends RecyclerView.Adapter<AdapterSyllabus.HolderSyllabus> {
    private Context context;
    private ArrayList<ModelSyllabus> syllabusArrayList;

    public AdapterSyllabus(Context context, ArrayList<ModelSyllabus> syllabusArrayList) {
        this.context = context;
        this.syllabusArrayList = syllabusArrayList;
    }

    @NonNull
    @Override
    public AdapterSyllabus.HolderSyllabus onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_syllabus_single_row,parent,false);
        return new AdapterSyllabus.HolderSyllabus(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSyllabus.HolderSyllabus holder, int position) {
        ModelSyllabus modelSyllabus=syllabusArrayList.get(position);
        String substring=modelSyllabus.getSyllabus();
        String link="http://demo2.afluex.com"+substring;
        Log.e("Title123",link);

        Picasso.get().load(link).
                resize(400,400).centerCrop()
                .placeholder(R.drawable.profile_round)
                .into(holder.img_syllabus);

    }

    @Override
    public int getItemCount() {
        return syllabusArrayList.size();
    }

    public class HolderSyllabus extends RecyclerView.ViewHolder {
        ImageView img_syllabus;
        public HolderSyllabus(@NonNull View itemView) {
            super(itemView);
            img_syllabus=itemView.findViewById(R.id.img_syllabus);
        }
    }
}

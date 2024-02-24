package affluex.school.solutions.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import affluex.school.solutions.Model.ModelTimeTable;
import affluex.school.solutions.R;

public class AdapterTimeTable extends RecyclerView.Adapter<AdapterTimeTable.HolderTimeTable> {
    private Context context;
    private ArrayList<ModelTimeTable> timeTableArrayList;

    public AdapterTimeTable(Context context, ArrayList<ModelTimeTable> timeTableArrayList) {
        this.context = context;
        this.timeTableArrayList = timeTableArrayList;
    }

    @NonNull
    @Override
    public AdapterTimeTable.HolderTimeTable onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_time_table_single_row,parent,false);
        return new AdapterTimeTable.HolderTimeTable(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTimeTable.HolderTimeTable holder, int position) {
        ModelTimeTable modelTimeTable=timeTableArrayList.get(position);
        holder.txt_name.setText(modelTimeTable.getStudentName());
        String substring=modelTimeTable.getTimeTable();
        String link="http://demo2.afluex.com"+substring;
        Log.e("Title123",link);

        Picasso.get().load(link).
                resize(400,400).centerCrop()
                .placeholder(R.drawable.profile_round)
                .into(holder.img_time_table);

    }

    @Override
    public int getItemCount() {
        return timeTableArrayList.size();
    }

    public class HolderTimeTable extends RecyclerView.ViewHolder {
        TextView txt_name;
        ImageView img_time_table;
        public HolderTimeTable(@NonNull View itemView) {
            super(itemView);
            txt_name=itemView.findViewById(R.id.txt_name);
            img_time_table=itemView.findViewById(R.id.img_time_table);
        }
    }
}

package affluex.school.solutions.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import affluex.school.solutions.Model.ModelClass;
import affluex.school.solutions.R;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.HolderClass> {
    private Context context;
    private ArrayList<ModelClass> classArrayList;

    public AdapterClass(Context context, ArrayList<ModelClass> classArrayList) {
        this.context = context;
        this.classArrayList = classArrayList;
    }

    @NonNull
    @Override
    public AdapterClass.HolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_class_single_row,parent,false);
        return new AdapterClass.HolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClass.HolderClass holder, int position) {
        ModelClass modelClass=classArrayList.get(position);
        holder.txt_title.setText(modelClass.getTitle());
        holder.txt_ct.setText(modelClass.getCt_name());

    }

    @Override
    public int getItemCount() {
        return classArrayList.size();
    }

    public class HolderClass extends RecyclerView.ViewHolder {
        TextView txt_title,txt_ct;
        public HolderClass(@NonNull View itemView) {

            super(itemView);
            txt_title=itemView.findViewById(R.id.txt_title);
            txt_ct=itemView.findViewById(R.id.txt_ct);
        }

    }
}

package affluex.school.solutions.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import affluex.school.solutions.Model.ModelOptions;
import affluex.school.solutions.R;

public class AdapterOptions extends RecyclerView.Adapter<AdapterOptions.HolderOptions> {
    private Context context;
    private ArrayList<ModelOptions> optionsArrayList;

    public AdapterOptions(Context context, ArrayList<ModelOptions> optionsArrayList) {
        this.context = context;
        this.optionsArrayList = optionsArrayList;
    }

    @NonNull
    @Override
    public AdapterOptions.HolderOptions onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_options_single_row,parent,false);
        return new AdapterOptions.HolderOptions(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOptions.HolderOptions holder, int position) {
        ModelOptions modelOptions=optionsArrayList.get(position);
        holder.txt_id.setText(modelOptions.getOptionId());
        holder.txt_otions.setText(modelOptions.getOptionName());

    }

    @Override
    public int getItemCount() {
        return optionsArrayList.size();
    }

    public class HolderOptions extends RecyclerView.ViewHolder {
        TextView txt_id,txt_otions;

        public HolderOptions(@NonNull View itemView) {
            super(itemView);

            txt_id=itemView.findViewById(R.id.txt_id);
            txt_otions=itemView.findViewById(R.id.txt_options);
        }
    }
}

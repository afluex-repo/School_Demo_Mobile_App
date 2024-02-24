package affluex.school.solutions.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import affluex.school.solutions.Model.ModelComplain;
import affluex.school.solutions.R;

public class AdapterComplain extends RecyclerView.Adapter<AdapterComplain.HolderComplain> {
    private Context context;
    private ArrayList<ModelComplain> complains;


    public AdapterComplain(Context context, ArrayList<ModelComplain> complains) {
        this.context = context;
        this.complains = complains;
    }

    @NonNull
    @Override
    public AdapterComplain.HolderComplain onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_complain_single_row,parent,false);
        return new AdapterComplain.HolderComplain(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterComplain.HolderComplain holder, int position) {
        ModelComplain modelComplain=complains.get(position);
        holder.txt_date.setText(modelComplain.getAddedOn());
        holder.txt_sender.setText(modelComplain.getMemberName());
        holder.txt_message.setText(modelComplain.getMessagess());
    }

    @Override
    public int getItemCount() {
        return complains.size();
    }

    public class HolderComplain extends RecyclerView.ViewHolder {
        TextView txt_message,txt_sender,txt_date;

        public HolderComplain(@NonNull View itemView) {
            super(itemView);

            txt_message=itemView.findViewById(R.id.txt_message);
            txt_sender=itemView.findViewById(R.id.txt_sender);
            txt_date=itemView.findViewById(R.id.txt_date);
        }
    }
}

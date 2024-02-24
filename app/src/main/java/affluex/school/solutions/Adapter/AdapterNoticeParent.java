package affluex.school.solutions.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import affluex.school.solutions.Model.ModelNoticeParent;
import affluex.school.solutions.R;

public class AdapterNoticeParent extends RecyclerView.Adapter<AdapterNoticeParent.HolderNoticeParent> {
    private Context context;
    private ArrayList<ModelNoticeParent> noticeParentArrayList;

    public AdapterNoticeParent(Context context, ArrayList<ModelNoticeParent> noticeParentArrayList) {
        this.context = context;
        this.noticeParentArrayList = noticeParentArrayList;
    }

    @NonNull
    @Override
    public AdapterNoticeParent.HolderNoticeParent onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_notice_parent_single_row,parent,false);
        return new AdapterNoticeParent.HolderNoticeParent(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNoticeParent.HolderNoticeParent holder, int position) {
        ModelNoticeParent modelNoticeParent=noticeParentArrayList.get(position);
        holder.txt_class.setText(modelNoticeParent.getClassName()+" - "+modelNoticeParent.getSectionName());
        holder.txt_title.setText(modelNoticeParent.getNotice());

    }

    @Override
    public int getItemCount() {
        return noticeParentArrayList.size();
    }

    public class HolderNoticeParent extends RecyclerView.ViewHolder {

        TextView txt_class,txt_title;
        public HolderNoticeParent(@NonNull View itemView) {
            super(itemView);
            txt_class=itemView.findViewById(R.id.txt_class);
            txt_title=itemView.findViewById(R.id.txt_title);
        }
    }
}

package affluex.school.solutions.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import affluex.school.solutions.Model.NoticeDetails;
import affluex.school.solutions.R;

public class AdapterNotice extends RecyclerView.Adapter<AdapterNotice.HolderNotice> {

    private Context context;
    private List<NoticeDetails> noticeDetails;

    public AdapterNotice(Context context, List<NoticeDetails> noticeDetails) {
        this.context = context;
        this.noticeDetails = noticeDetails;
    }

    @NonNull
    @Override
    public AdapterNotice.HolderNotice onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_notice_single_row,parent,false);
        return new AdapterNotice.HolderNotice(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNotice.HolderNotice holder, int position) {
        NoticeDetails noticeDetails1=noticeDetails.get(position);
        holder.txt_date.setText(noticeDetails1.getNoticeDate());
        holder.txt_title.setText(noticeDetails1.getNoticeName());

    }

    @Override
    public int getItemCount() {
        return noticeDetails.size();
    }

    public class HolderNotice extends RecyclerView.ViewHolder {
        TextView txt_date,txt_title;

        public HolderNotice(@NonNull View itemView) {
            super(itemView);
            txt_date=itemView.findViewById(R.id.txt_date);
            txt_title=itemView.findViewById(R.id.txt_title);
        }
    }
}

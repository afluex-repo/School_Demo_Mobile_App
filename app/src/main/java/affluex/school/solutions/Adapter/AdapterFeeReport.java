package affluex.school.solutions.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import affluex.school.solutions.Model.ModelFeeReport;
import affluex.school.solutions.R;

public class AdapterFeeReport extends RecyclerView.Adapter<AdapterFeeReport.HolderFeeReport> {

    private Context context;
    private ArrayList<ModelFeeReport> feeReportArrayList;


    public AdapterFeeReport(Context context, ArrayList<ModelFeeReport> feeReportArrayList) {
        this.context = context;
        this.feeReportArrayList = feeReportArrayList;
    }

    @NonNull
    @Override
    public AdapterFeeReport.HolderFeeReport onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_fee_report_single_row,parent,false);
        return new AdapterFeeReport.HolderFeeReport(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFeeReport.HolderFeeReport holder, int position) {

        ModelFeeReport modelFeeReport=feeReportArrayList.get(position);
        holder.txt_details.setText(modelFeeReport.getBankDetails());
        holder.txt_amt.setText(modelFeeReport.getAmount());
        holder.txt_date.setText(modelFeeReport.getPaymentDate());
        holder.txt_mode.setText(modelFeeReport.getPaymentMode());
        holder.txt_name.setText(modelFeeReport.getStudentName());
        holder.txt_rec_no.setText(modelFeeReport.getReceiptNo());

    }

    @Override
    public int getItemCount() {
        return feeReportArrayList.size();
    }

    public class HolderFeeReport extends RecyclerView.ViewHolder {

        TextView txt_rec_no,txt_date,txt_name,txt_amt,txt_mode,txt_details;
        public HolderFeeReport(@NonNull View itemView) {
            super(itemView);
            txt_rec_no=itemView.findViewById(R.id.txt_rec_no);
            txt_date=itemView.findViewById(R.id.txt_date);
            txt_name=itemView.findViewById(R.id.txt_name);
            txt_amt=itemView.findViewById(R.id.txt_amt);
            txt_mode=itemView.findViewById(R.id.txt_mode);
            txt_details=itemView.findViewById(R.id.txt_details);

        }
    }
}

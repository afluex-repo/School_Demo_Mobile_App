package affluex.school.solutions.Adapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import affluex.school.solutions.Model.ModelSalary;
import affluex.school.solutions.R;

public class AdapterSalary extends RecyclerView.Adapter<AdapterSalary.HolderSalary> {


    private Context context;
    private ArrayList<ModelSalary> modelSalaryArrayList;

    public AdapterSalary(Context context, ArrayList<ModelSalary> modelSalaryArrayList) {
        this.context = context;
        this.modelSalaryArrayList = modelSalaryArrayList;
    }

    @NonNull
    @Override
    public AdapterSalary.HolderSalary onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.layout_salary_single_row,parent,false);
        return new AdapterSalary.HolderSalary(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSalary.HolderSalary holder, int position) {

        if(position==0){
            holder.btn_print.setVisibility(View.GONE);
            holder.txt_heading.setVisibility(View.VISIBLE);
            holder.txt_sr_no.setText("Sr No");
            holder.et_emp_code.setText("Emp Code");
            holder.et_month.setText("Period");
            holder.txt_sr_no.setTypeface(Typeface.DEFAULT_BOLD);
            holder.et_emp_code.setTypeface(Typeface.DEFAULT_BOLD);
            holder.et_month.setTypeface(Typeface.DEFAULT_BOLD);
        }else{
            ModelSalary modelSalary=modelSalaryArrayList.get(position-1);
            holder.btn_print.setVisibility(View.VISIBLE);
            holder.txt_heading.setVisibility(View.GONE);
            holder.txt_sr_no.setText(""+(position));
            holder.et_emp_code.setText(modelSalary.getEmployeeCode());
            holder.et_month.setText(modelSalary.getMonthName()+"-"+modelSalary.getYear());
            holder.txt_sr_no.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            holder.et_emp_code.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            holder.et_month.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

            holder.btn_print.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent("site_position");
                    intent.putExtra("salary_id",modelSalary.getPkPaidSalId());
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (modelSalaryArrayList.size()+1);
    }

    public class HolderSalary extends RecyclerView.ViewHolder {

        Button btn_print;
        TextView txt_sr_no,et_emp_code,et_month,txt_heading;
        public HolderSalary(@NonNull View itemView) {
            super(itemView);
            btn_print=itemView.findViewById(R.id.btn_print);

            txt_sr_no=itemView.findViewById(R.id.txt_sr_no);
            et_emp_code=itemView.findViewById(R.id.et_emp_code);

            et_month=itemView.findViewById(R.id.et_month);
            txt_heading=itemView.findViewById(R.id.txt_heading);

        }
    }
}

package affluex.school.solutions.Fragments;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import affluex.school.solutions.Activity.DashboardSchool;
import affluex.school.solutions.Adapter.AdapterSalary;
import affluex.school.solutions.Model.ResponseHomework;
import affluex.school.solutions.Model.ResponseSalary;
import affluex.school.solutions.R;
import affluex.school.solutions.Retrofit.ApiServices;
import affluex.school.solutions.Retrofit.ServiceGenerator;
import affluex.school.solutions.databinding.FragmentTeacherSalaryBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TeacherSalaryFragment extends Fragment {
    FragmentTeacherSalaryBinding binding;
    private final Calendar myCalendar = Calendar.getInstance();
    String fromDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentTeacherSalaryBinding.inflate(inflater,container,false);
        DatePickerDialog.OnDateSetListener dateFrom = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            fromDate = sdf.format(myCalendar.getTime());
            Log.e("Date", "From:" + fromDate);
            updateLabel(binding.txtFrom);


        };
        DatePickerDialog.OnDateSetListener dateTo = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            fromDate = sdf.format(myCalendar.getTime());
            Log.e("Date", "From:" + fromDate);
            updateLabel(binding.txtTo);


        };

        binding.txtFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateFrom, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                String myFormat = "dd/MM/yyyy"; //In which you need put here
            }
        });

        binding.txtTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateTo, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                String myFormat = "dd/MM/yyyy"; //In which you need put here
            }
        });

        binding.icSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getSalaryReport(binding.txtFrom.getText().toString(),binding.txtTo.getText().toString());
            }
        });

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter("site_position"));

        getSalaryReport("","");















        return binding.getRoot();
    }

    public BroadcastReceiver mMessageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String salaryId=intent.getStringExtra("salary_id");

            Log.e("Salary_id",salaryId);

            Bundle bundle =new Bundle();
            bundle.putString("salary_id",salaryId);
            Fragment fragment=new ViewSalarySlipFragment();
            fragment.setArguments(bundle);
            ((DashboardSchool)getActivity()).switchFragmentOnDashBoard(fragment,"View Salary Slip");
        }
    };

    private void getSalaryReport(String from,String to) {

        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("pkTeacherId","");
        Log.e("JHFJHJJH",""+pkteacherId);
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkteacherId)) {
            JsonObject object = new JsonObject();
            object.addProperty("EmployeeID", Integer.parseInt(pkteacherId));
            object.addProperty("FromDate", from);
            object.addProperty("ToDate", to);
            object.addProperty("Pk_PaidSalId", "");

            Call<ResponseSalary> call=apiServices.EmployeeSalarySlipBy(object);

            call.enqueue(new Callback<ResponseSalary>() {
                @Override
                public void onResponse(Call<ResponseSalary> call, Response<ResponseSalary> response) {

                    if(response.body().getSalaryArrayList()==null){
                        Toast.makeText(getActivity(), "Unable to fetch data", Toast.LENGTH_SHORT).show();
                    }else{
                        if(response.isSuccessful() && response.body().getSalaryArrayList()!=null){
                            if(response.body().getSalaryArrayList().size()>0){
                                AdapterSalary adapterSalary=new AdapterSalary(getActivity(),response.body().getSalaryArrayList());
                                binding.rvLabourList.setAdapter(adapterSalary);
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<ResponseSalary> call, Throwable t) {

                }
            });


        }
    }
    private void updateLabel(EditText fromdateEt) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        fromdateEt.setText(sdf.format(myCalendar.getTime()));
    }

}
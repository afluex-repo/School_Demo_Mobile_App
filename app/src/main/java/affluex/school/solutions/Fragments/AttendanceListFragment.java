package affluex.school.solutions.Fragments;
import static android.content.Context.MODE_PRIVATE;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.google.gson.JsonObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import affluex.school.solutions.Adapter.AdapterAttendance;
import affluex.school.solutions.Model.ResponseAttendnace;
import affluex.school.solutions.Retrofit.ApiServices;
import affluex.school.solutions.Retrofit.ServiceGenerator;
import affluex.school.solutions.databinding.FragmentAttendanceListBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AttendanceListFragment extends Fragment {
    FragmentAttendanceListBinding binding;

    private final Calendar myCalendar = Calendar.getInstance();
    String fromDate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=affluex.school.solutions.databinding.FragmentAttendanceListBinding.inflate(inflater,container,false);
        getAttendance("","");
        initview();

        DatePickerDialog.OnDateSetListener dateFrom = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yyyy";
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
            String myFormat = "dd/MM/yyyy";
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
                getAttendance(binding.txtFrom.getText().toString(),binding.txtTo.getText().toString());
            }
        });
        return binding.getRoot();
    }

    private void initview() {

    }

    private void updateLabel(EditText fromdateEt) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        fromdateEt.setText(sdf.format(myCalendar.getTime()));
    }

    private void getAttendance(String from,String to) {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("pkTeacherId","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");

        if(!TextUtils.isEmpty(pkteacherId)) {
            JsonObject object = new JsonObject();
            object.addProperty("FK_EmpID", Integer.parseInt(pkteacherId));
            object.addProperty("FromDate", from);
            object.addProperty("ToDate", to);
            Call<ResponseAttendnace> call = apiServices.GetAttenndaceList(object);

            call.enqueue(new Callback<ResponseAttendnace>() {
                @Override
                public void onResponse(Call<ResponseAttendnace> call, Response<ResponseAttendnace> response) {
                    if(response.isSuccessful()&&response.body().getMessage().equals("Record Found.")){
                        AdapterAttendance adapterAttendance=new AdapterAttendance(getActivity(),response.body().getListAttenndace());
                        binding.rvLeave.setAdapter(adapterAttendance);

                    }
                }

                @Override
                public void onFailure(Call<ResponseAttendnace> call, Throwable t) {

                }
            });
        }
    }
}
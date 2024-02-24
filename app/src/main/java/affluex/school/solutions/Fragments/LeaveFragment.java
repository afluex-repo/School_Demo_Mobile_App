package affluex.school.solutions.Fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import affluex.school.solutions.Adapter.AdapterLeave;
import affluex.school.solutions.Adapter.AdapterLeaveParents;
import affluex.school.solutions.Adapter.AdapterSelectStudent;
import affluex.school.solutions.Model.CommonResponse;
import affluex.school.solutions.Model.ModelLeave;
import affluex.school.solutions.Model.ResponseLeave;
import affluex.school.solutions.Model.ResponseLeaveApproval;
import affluex.school.solutions.Model.ResponseLeaveParent;
import affluex.school.solutions.Model.ResponseParentDashboard;
import affluex.school.solutions.Model.StudentDetails;
import affluex.school.solutions.R;
import affluex.school.solutions.Retrofit.ApiServices;
import affluex.school.solutions.Retrofit.ServiceGenerator;
import affluex.school.solutions.common.LoggerUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LeaveFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    affluex.school.solutions.databinding.FragmentLeaveBinding binding;
    private ArrayList<String> statusList;
    private ProgressDialog progressDialog;

    private ArrayList<ModelLeave> pendingleaveArrayList;
    private ArrayList<ModelLeave> totalleaveArrayList;

    String status="Pending";

    private final Calendar myCalendar = Calendar.getInstance();
    String fromDate;


    private ArrayList<StudentDetails> studentDetails;

    private String selectedstudentId;

    public LeaveFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=affluex.school.solutions.databinding.FragmentLeaveBinding.inflate(inflater,container,false);
        binding.cardPending.setCardBackgroundColor(getContext().getColor(R.color.blue_900));
        binding.txtPending.setTextColor(getContext().getColor(R.color.white));
        binding.txtApproved.setTextColor(getContext().getColor(R.color.black));
        binding.txtDeclined.setTextColor(getContext().getColor(R.color.black));
        binding.cardApproved.setCardBackgroundColor(getContext().getColor(R.color.white));
        binding.cardDeclined.setCardBackgroundColor(getContext().getColor(R.color.white));
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        binding.llView.setVisibility(View.VISIBLE);
        binding.llAdd.setVisibility(View.GONE);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);




        studentDetails=new ArrayList<>();

        pendingleaveArrayList=new ArrayList<>();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        totalleaveArrayList=new ArrayList<>();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("custom-action-local-broadcast"));
        if (sharedPreferences.getString("userType", "").equals("Parent")) {
            binding.cardAdd.setVisibility(View.VISIBLE);
            binding.llTeacherSearch.setVisibility(View.GONE);
            binding.spinnerSelectStudent.setVisibility(View.VISIBLE);

            callLeaveListParents();

        }else{
            callPendingLeaveListApi("","");
            callTotalLeaveListApi("","");
            binding.cardAdd.setVisibility(View.GONE);
            binding.llTeacherSearch.setVisibility(View.VISIBLE);
            binding.spinnerSelectStudent.setVisibility(View.GONE);
        }


        binding.cardPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.cardPending.setCardBackgroundColor(getContext().getColor(R.color.blue_900));
                binding.txtPending.setTextColor(getContext().getColor(R.color.white));
                binding.txtApproved.setTextColor(getContext().getColor(R.color.black));
                binding.txtDeclined.setTextColor(getContext().getColor(R.color.black));
                binding.cardApproved.setCardBackgroundColor(getContext().getColor(R.color.white));
                binding.cardDeclined.setCardBackgroundColor(getContext().getColor(R.color.white));
                progressDialog.show();
                status="pending";
                callPendingLeaveListApi("","");
            }
        });
        binding.cardApproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.cardApproved.setCardBackgroundColor(getContext().getColor(R.color.blue_900));
                binding.txtApproved.setTextColor(getContext().getColor(R.color.white));
                binding.txtPending.setTextColor(getContext().getColor(R.color.black));
                binding.txtDeclined.setTextColor(getContext().getColor(R.color.black));
                binding.cardPending.setCardBackgroundColor(getContext().getColor(R.color.white));
                binding.cardDeclined.setCardBackgroundColor(getContext().getColor(R.color.white));
                status="Approved";
                progressDialog.show();
                callTotalLeaveListApi("","");


            }
        });
        binding.cardDeclined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.cardDeclined.setCardBackgroundColor(getContext().getColor(R.color.blue_900));
                binding.txtDeclined.setTextColor(getContext().getColor(R.color.white));
                binding.txtPending.setTextColor(getContext().getColor(R.color.black));
                binding.txtApproved.setTextColor(getContext().getColor(R.color.black));
                binding.cardPending.setCardBackgroundColor(getContext().getColor(R.color.white));
                binding.cardApproved.setCardBackgroundColor(getContext().getColor(R.color.white));
                status="Declined";
                progressDialog.show();
                callTotalLeaveListApi("","");


            }
        });

        callDashBoardApi();


        DatePickerDialog.OnDateSetListener dateFrom = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            fromDate = sdf.format(myCalendar.getTime());
            Log.e("Date", "From:" + fromDate);
            updateLabel(binding.etFrom);


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
            updateLabel(binding.etTo);


        };
         DatePickerDialog.OnDateSetListener dateFromAdd = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            fromDate = sdf.format(myCalendar.getTime());
            Log.e("Date", "From:" + fromDate);
            updateLabel(binding.etFromAdd);



        };

        DatePickerDialog.OnDateSetListener dateToAdd = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            fromDate = sdf.format(myCalendar.getTime());
            Log.e("Date", "From:" + fromDate);
            updateLabel(binding.etToAdd);



        };

        binding.etFromAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateFromAdd, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                String myFormat = "dd/MM/yyyy"; //In which you need put here
            }
        });

        binding.etToAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateToAdd, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                String myFormat = "dd/MM/yyyy"; //In which you need put here
            }
        });

        binding.etFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateFrom, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                String myFormat = "dd/MM/yyyy"; //In which you need put here
            }
        });

        binding.etTo.setOnClickListener(new View.OnClickListener() {
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
                if(status.equals("Pending")){
                    callPendingLeaveListApi(binding.etFrom.getText().toString(),binding.etTo.getText().toString());
                }else{
                    callTotalLeaveListApi(binding.etFrom.getText().toString(),binding.etTo.getText().toString());
                }
            }
        });

        binding.cardAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llView.setVisibility(View.GONE);
                binding.llAdd.setVisibility(View.VISIBLE);

            }
        });

        binding.spinnerAddSelectStudent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedstudentId=studentDetails.get(i).getPkStudentId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(binding.etFromAdd.getText().toString())){
                    Toast.makeText(getActivity(), "Select Leave from date", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(binding.etToAdd.getText().toString())){
                    Toast.makeText(getActivity(), "Select Leave To Date", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(binding.etReason.getText().toString())){
                    Toast.makeText(getActivity(), "Specify Reason for leave", Toast.LENGTH_SHORT).show();
                }else{
                    callAddLeaveApi();
                }
            }
        });



        return binding.getRoot();
    }

    private void callAddLeaveApi() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("Pk_ParentID","");
        JsonObject object = new JsonObject();
        object.addProperty("Fk_ParentID", Integer.parseInt(pkteacherId));
        object.addProperty("Pk_StudentId", Integer.parseInt(selectedstudentId));
        object.addProperty("FromDate", binding.etFromAdd.getText().toString());
        object.addProperty("ToDate", binding.etToAdd.getText().toString());
        object.addProperty("Reason", binding.etReason.getText().toString());


        Call<CommonResponse> call=apiServices.ApplyLeave(object);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(response.body().getErrorMessage().equals("Leave Applied Successfully")){
                    Toast.makeText(getActivity(), response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                    binding.llAdd.setVisibility(View.GONE);
                    binding.etReason.getText().clear();
                    binding.etFrom.getText().clear();
                    binding.etTo.getText().clear();
                    binding.llView.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(getActivity(), response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

            }
        });

    }


    private void callLeaveListParents() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("Pk_ParentID","");
        JsonObject object = new JsonObject();
        object.addProperty("AddedBy", Integer.parseInt(pkteacherId));

        LoggerUtil.logItem(object);
        Call<ResponseLeaveParent> call = apiServices.LeaveList(object);

        call.enqueue(new Callback<ResponseLeaveParent>() {
            @Override
            public void onResponse(Call<ResponseLeaveParent> call, Response<ResponseLeaveParent> response) {
                if(response.isSuccessful()){
                    if(response.body().getLeaveListDetailsArrayList().size()>0){

                        AdapterLeaveParents adapterLeaveParents =new AdapterLeaveParents(getActivity(),
                                response.body().getLeaveListDetailsArrayList().get(0).getLeaveParentsArrayList());
                        binding.rvLeave.setAdapter(adapterLeaveParents);


                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLeaveParent> call, Throwable t) {

            }
        });

    }

    private void updateLabel(EditText fromdateEt) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        fromdateEt.setText(sdf.format(myCalendar.getTime()));
    }


    private void callTotalLeaveListApi(String from,String to) {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("pkTeacherId","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkteacherId)) {
            JsonObject object = new JsonObject();
            object.addProperty("TeacherID", Integer.parseInt(pkteacherId));
            object.addProperty("FromDate", from);
            object.addProperty("ToDate", to);
            LoggerUtil.logItem(object);
            Call<ResponseLeave> call = apiServices.GetTotalLeave(object);
            call.enqueue(new Callback<ResponseLeave>() {
                @Override
                public void onResponse(Call<ResponseLeave> call, Response<ResponseLeave> response) {
                    progressDialog.dismiss();
                    totalleaveArrayList.clear();
//                    totalleaveArrayList=new ArrayList<>(response.body().getLeaveList());

                    if(response.body().getLeaveList()!=null){
                        for(int i=0;i<response.body().getLeaveList().size();i++){
                            Log.e("ResponseTotalLeaveList",""+(response.body().getLeaveList().get(i).getStatus().toLowerCase()));
                            if(response.body().getLeaveList().get(i).getStatus().toLowerCase().equals(status.toLowerCase())){
                                totalleaveArrayList.add(response.body().getLeaveList().get(i));
                            }
                        }
                        if(totalleaveArrayList.size()>0){
                            AdapterLeave adapterLeave=new AdapterLeave(getActivity(),totalleaveArrayList);
                            binding.rvLeave.setAdapter(adapterLeave);
                        }
                    }

                }

                @Override
                public void onFailure(Call<ResponseLeave> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void callPendingLeaveListApi(String from,String to) {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("pkTeacherId","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkteacherId)) {
            JsonObject object = new JsonObject();
            object.addProperty("TeacherID", Integer.parseInt(pkteacherId));
            object.addProperty("FromDate", from);
            object.addProperty("ToDate", to);
            LoggerUtil.logItem(object);
            Call<ResponseLeave> call = apiServices.GetPendingLeave(object);
            call.enqueue(new Callback<ResponseLeave>() {
                @Override
                public void onResponse(Call<ResponseLeave> call, Response<ResponseLeave> response) {
                    progressDialog.dismiss();
                    pendingleaveArrayList=new ArrayList<>(response.body().getLeaveList());
////                    totalleaveArrayList=new ArrayList<>(response.body().getLeaveList());
//                    if(response.body().getLeaveList()!=null) {
//                        for (int i = 0; i < response.body().getLeaveList().size(); i++) {
//                            Log.e("ResponseTotalLeaveList", "" + (response.body().getLeaveList().get(i).getStatus().toLowerCase()));
//                                pendingleaveArrayList.add(response.body().getLeaveList().get(i));
//
//                        }
//                    }
                    if(pendingleaveArrayList.size()>0){
                        AdapterLeave adapterLeave=new AdapterLeave(getActivity(),pendingleaveArrayList);
                        binding.rvLeave.setAdapter(adapterLeave);
                    }
                }

                @Override
                public void onFailure(Call<ResponseLeave> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    class SpinnerStatus extends android.widget.BaseAdapter {

        @Override
        public int getCount() {
            return statusList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inf = getLayoutInflater();
            View row = inf.inflate(R.layout.layout_of_country_row, null);
            TextView spinner_text;

            spinner_text = row.findViewById(R.id.spinner_text);
            spinner_text.setText(statusList.get(position));


            return row;
        }
    }
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        // we will receive data updates in onReceive method.
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String status = intent.getStringExtra("status");
            int position=intent.getIntExtra("position",-1);
            String description=intent.getStringExtra("description");
            Log.e("Recesjhjh",status);
            Log.e("Recesjhjh",""+position);
            
            
            if(status.equals("decline")){
                callDeclineApi(position,description);
            }else{
                callApproveApi(position,description);
            }

            // on below line we are updating the data in our text view.
        }
    };

    private void callApproveApi(int position, String description) {

        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("pkTeacherId","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkteacherId)) {
            JsonObject object = new JsonObject();
            object.addProperty("UpdatedBy", Integer.parseInt(pkteacherId));
            object.addProperty("Pk_StudentID", Integer.parseInt(totalleaveArrayList.get(position).getPkStudentID()));
            object.addProperty("description", description);
            object.addProperty("PK_StdntLeaveID", Integer.parseInt(totalleaveArrayList.get(position).getPKStdntLeaveID()));
            LoggerUtil.logItem(object);
            Call<ResponseLeaveApproval> call = apiServices.ApproveLeave(object);
            call.enqueue(new Callback<ResponseLeaveApproval>() {
                @Override
                public void onResponse(Call<ResponseLeaveApproval> call, Response<ResponseLeaveApproval> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        callPendingLeaveListApi("","");
                    }
                }

                @Override
                public void onFailure(Call<ResponseLeaveApproval> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void callDeclineApi(int position, String description) {

        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("pkTeacherId","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkteacherId)) {
            JsonObject object = new JsonObject();
            object.addProperty("UpdatedBy", Integer.parseInt(pkteacherId));
            object.addProperty("Pk_StudentID", Integer.parseInt(totalleaveArrayList.get(position).getPkStudentID()));
            object.addProperty("description", description);
            object.addProperty("PK_StdntLeaveID", Integer.parseInt(totalleaveArrayList.get(position).getPKStdntLeaveID()));
            LoggerUtil.logItem(object);
            Call<ResponseLeaveApproval> call = apiServices.DeclineLeave(object);
            call.enqueue(new Callback<ResponseLeaveApproval>() {
                @Override
                public void onResponse(Call<ResponseLeaveApproval> call, Response<ResponseLeaveApproval> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        callPendingLeaveListApi("","");
                    }
                }

                @Override
                public void onFailure(Call<ResponseLeaveApproval> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void callDashBoardApi() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkParentId=sharedPreferences.getString("Pk_ParentID","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkParentId)) {
            JsonObject object = new JsonObject();
            object.addProperty("Fk_ParentId", Integer.parseInt(pkParentId));
            LoggerUtil.logItem(object);
            Call<ResponseParentDashboard> call = apiServices.GetDashboard(object);
            call.enqueue(new Callback<ResponseParentDashboard>() {
                @Override
                public void onResponse(Call<ResponseParentDashboard> call, Response<ResponseParentDashboard> response) {
                    Log.e("RESPKLNJGGHVJHFG",""+response.body().getDashBoardList().get(0).getStudentDetails().size());
                    if(response.isSuccessful() && response.body()!=null){
                        if(response.body().getDashBoardList().size()>0){

                            if(response.body().getDashBoardList().get(0).getStudentDetails().size()>0){
                                studentDetails=new ArrayList<>(response.body().getDashBoardList().get(0).getStudentDetails());
                                SpinnerSectionAdapter spinnerSectionAdapter=new SpinnerSectionAdapter();
                                binding.spinnerSelectStudent.setAdapter(spinnerSectionAdapter);
                                binding.spinnerAddSelectStudent.setAdapter(spinnerSectionAdapter);

                            }
                        }

                    }
                }


                @Override
                public void onFailure(Call<ResponseParentDashboard> call, Throwable t) {

                }
            });
        }
    }


    class SpinnerSectionAdapter extends android.widget.BaseAdapter {

        @Override
        public int getCount() {
            return studentDetails.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inf = getLayoutInflater();
            View row = inf.inflate(R.layout.layout_of_country_row, null);
            TextView spinner_text;

            spinner_text = row.findViewById(R.id.spinner_text);
            spinner_text.setText(studentDetails.get(position).getStudentName()+"\t"+studentDetails.get(position).getClassName()
            +" - "+studentDetails.get(position).getSectionName());


            return row;
        }
    }

}
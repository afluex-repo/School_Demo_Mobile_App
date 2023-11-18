package affluex.school.solutions.Fragments;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import affluex.school.solutions.Adapter.AdapterLeave;
import affluex.school.solutions.Model.ModelLeave;
import affluex.school.solutions.Model.ResponseLeave;
import affluex.school.solutions.Model.ResponseLeaveApproval;
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

        pendingleaveArrayList=new ArrayList<>();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("userType", "").equals("Parent")) {
            binding.cardAdd.setVisibility(View.VISIBLE);

        }else{
            binding.cardAdd.setVisibility(View.GONE);
        }
        totalleaveArrayList=new ArrayList<>();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("custom-action-local-broadcast"));

        callPendingLeaveListApi();
        callTotalLeaveListApi();

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
                callPendingLeaveListApi();
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
                callTotalLeaveListApi();


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
                callTotalLeaveListApi();


            }
        });



        return binding.getRoot();
    }

    private void callTotalLeaveListApi() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("pkTeacherId","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkteacherId)) {
            JsonObject object = new JsonObject();
            object.addProperty("TeacherID", Integer.parseInt(pkteacherId));
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

    private void callPendingLeaveListApi() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("pkTeacherId","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkteacherId)) {
            JsonObject object = new JsonObject();
            object.addProperty("TeacherID", Integer.parseInt(pkteacherId));
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
                        callPendingLeaveListApi();
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
                        callPendingLeaveListApi();
                    }
                }

                @Override
                public void onFailure(Call<ResponseLeaveApproval> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
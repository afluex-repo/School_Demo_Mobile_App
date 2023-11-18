package affluex.school.solutions.Fragments;

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

import com.google.gson.JsonObject;

import java.util.ArrayList;

import affluex.school.solutions.Activity.DashboardSchool;
import affluex.school.solutions.Adapter.AdapterSelectStudent;
import affluex.school.solutions.Model.ResponseLeave;
import affluex.school.solutions.Model.ResponseParentDashboard;
import affluex.school.solutions.R;
import affluex.school.solutions.Retrofit.ApiServices;
import affluex.school.solutions.Retrofit.ServiceGenerator;
import affluex.school.solutions.common.LoggerUtil;
import affluex.school.solutions.databinding.FragmentParentHomeBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParentHome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentParentHomeBinding binding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentParentHomeBinding.inflate(inflater,container,false);
        callDashBoardApi();
        binding.cardSwitchStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llMain.setVisibility(View.GONE);
                binding.llSelectStudent.setVisibility(View.VISIBLE);
                SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("pk_studentId","");
                editor.putString("pk_studentName","");
                editor.apply();
                editor.commit();
                callDashBoardApi();
            }
        });

        binding.cardAssignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new AssignmentFragment();
                ((DashboardSchool)getActivity()).switchFragmentOnDashBoard(fragment);
            }
        });

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("student_selected"));







        return binding.getRoot();
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

                                if(response.body().getDashBoardList().get(0).getStudentDetails().size()>1){
                                    AdapterSelectStudent adapterSelectStudent=new AdapterSelectStudent(getActivity(),new ArrayList<>(response.body().getDashBoardList().get(0).getStudentDetails()));
                                    binding.rvSelectStudent.setAdapter(adapterSelectStudent);
                                    if(sharedPreferences.getString("pk_studentId","").equals("")){
                                        binding.llMain.setVisibility(View.GONE);
                                        binding.llSelectStudent.setVisibility(View.VISIBLE);
                                    }else{
                                        binding.llMain.setVisibility(View.VISIBLE);
                                        binding.llSelectStudent.setVisibility(View.GONE);
                                    }

                                }else if(response.body().getDashBoardList().get(0).getStudentDetails().size()==1){
                                    binding.llMain.setVisibility(View.VISIBLE);
                                    binding.llSelectStudent.setVisibility(View.GONE);
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    editor.putString("pk_studentId",response.body().getDashBoardList().get(0).getStudentDetails().get(0).getPkStudentId());
                                    editor.putString("pk_studentName",response.body().getDashBoardList().get(0).getStudentDetails().get(0).getStudentName());
                                    editor.apply();
                                    editor.commit();


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
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        // we will receive data updates in onReceive method.
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            binding.llMain.setVisibility(View.VISIBLE);
            binding.llSelectStudent.setVisibility(View.GONE);




            // on below line we are updating the data in our text view.
        }
    };
}
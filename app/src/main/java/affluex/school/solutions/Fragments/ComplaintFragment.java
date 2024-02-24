package affluex.school.solutions.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;

import affluex.school.solutions.Adapter.AdapterComplain;
import affluex.school.solutions.Model.CommonResponse;
import affluex.school.solutions.Model.ResponseComplain;
import affluex.school.solutions.Model.ResponseParentDashboard;
import affluex.school.solutions.R;
import affluex.school.solutions.Retrofit.ApiServices;
import affluex.school.solutions.Retrofit.ServiceGenerator;
import affluex.school.solutions.common.LoggerUtil;
import affluex.school.solutions.databinding.FragmentComplaintBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ComplaintFragment extends Fragment {


    FragmentComplaintBinding binding;

    // TODO: Rename parameter arguments, choose names that match


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentComplaintBinding.inflate(inflater,container,false);

        getComplainList();


        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(binding.etComplain.getText().toString())){
                    Toast.makeText(getActivity(), "Type your complain to submit", Toast.LENGTH_SHORT).show();
                }else{
                    callAddComplainApi();
                }
            }
        });
        return binding.getRoot();
    }

    private void getComplainList() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkParentId=sharedPreferences.getString("Pk_ParentID","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkParentId)) {
            JsonObject object = new JsonObject();
            object.addProperty("Pk_ParentID", Integer.parseInt(pkParentId));
            Log.e("jkonknjn",pkParentId);
            LoggerUtil.logItem(object);
            Call<ResponseComplain> call = apiServices.GetComplains(object);
            call.enqueue(new Callback<ResponseComplain>() {
                @Override
                public void onResponse(Call<ResponseComplain> call, Response<ResponseComplain> response) {
                    if(response.isSuccessful()){
                        if(response.body().getComplainArrayList()!=null && response.body().getComplainArrayList().size()>0){
                            AdapterComplain adapterComplain=new AdapterComplain(getActivity(),response.body().getComplainArrayList());
                            binding.rvComplain.setAdapter(adapterComplain);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseComplain> call, Throwable t) {

                }
            });
        }
    }

    private void callAddComplainApi() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkParentId=sharedPreferences.getString("Pk_ParentID","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkParentId)) {
            JsonObject object = new JsonObject();
            object.addProperty("Pk_ParentID", Integer.parseInt(pkParentId));
            object.addProperty("Complain", binding.etComplain.getText().toString());
            LoggerUtil.logItem(object);
            Call<CommonResponse> call = apiServices.Complain(object);
            call.enqueue(new Callback<CommonResponse>() {
                @Override
                public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                    if(response.body().getErrorMessage().equals("Complain Add Successfully")){
                        Toast.makeText(getActivity(), response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                        binding.etComplain.getText().clear();
                        getComplainList();
                    }else{
                        Toast.makeText(getActivity(), response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CommonResponse> call, Throwable t) {

                }
            });
        }
    }

}
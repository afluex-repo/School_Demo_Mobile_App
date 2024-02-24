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

import affluex.school.solutions.Model.CommonResponse;
import affluex.school.solutions.R;
import affluex.school.solutions.Retrofit.ApiServices;
import affluex.school.solutions.Retrofit.ServiceGenerator;
import affluex.school.solutions.common.LoggerUtil;
import affluex.school.solutions.databinding.FragmentForgotPasswordBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordFragment extends Fragment {

    FragmentForgotPasswordBinding binding;

    String userType="", activity="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentForgotPasswordBinding.inflate(inflater,container,false);

        binding.btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(binding.etOldPassword.getText().toString())){
                    Toast.makeText(getActivity(), "Enter old password", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(binding.etNewPassowrd.getText().toString())){
                    Toast.makeText(getActivity(), "Enter new password", Toast.LENGTH_SHORT).show();
                }else if(!binding.etNewPassowrd.getText().toString().equals(binding.etConfirmNewPassword.getText().toString())){
                    Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                }else{
                    callChangePasswordApi();
                }
            }
        });

        getArgumentData();
        return binding.getRoot();
    }

    private void callChangePasswordApi() {

        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("pkTeacherId","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkteacherId)) {
            JsonObject object = new JsonObject();
            object.addProperty("UpdatedBy", Integer.parseInt(pkteacherId));
            object.addProperty("NewPassword", binding.etConfirmNewPassword.getText().toString());
            object.addProperty("OldPassword", binding.etOldPassword.getText().toString());

            LoggerUtil.logItem(object);
            Call<CommonResponse> call = apiServices.ChangePassword(object);
            call.enqueue(new Callback<CommonResponse>() {
                @Override
                public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CommonResponse> call, Throwable t) {

                }
            });

        }
    }

    private void getArgumentData() {
        userType=getArguments().getString("userType");
        activity=getArguments().getString("activity");
        if(activity.equals("change")){
            binding.txtHeading.setText("Change Password");
            binding.llChangePassword.setVisibility(View.VISIBLE);
            binding.llForgotPassowrd.setVisibility(View.GONE);
            binding.llVerifyOtp.setVisibility(View.GONE);
        }else{
            binding.txtHeading.setText("Forgot Password");
            binding.llChangePassword.setVisibility(View.GONE);
            binding.llForgotPassowrd.setVisibility(View.VISIBLE);
            binding.llVerifyOtp.setVisibility(View.GONE);
        }
    }
}
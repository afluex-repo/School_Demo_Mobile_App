package affluex.school.solutions.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.google.gson.JsonObject;
import affluex.school.solutions.Model.CommonResponse;
import affluex.school.solutions.Retrofit.ApiServices;
import affluex.school.solutions.Retrofit.ServiceGenerator;
import affluex.school.solutions.common.LoggerUtil;
import affluex.school.solutions.databinding.ActivityForgotPasswordBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    ActivityForgotPasswordBinding binding;

    ApiServices apiServices;

    String userType;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiServices = ServiceGenerator.createService(ApiServices.class);
        sharedPreferences=getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        Intent intent=getIntent();
        userType=intent.getStringExtra("userType");

        binding.llLogin.setVisibility(View.VISIBLE);
        binding.llOtp.setVisibility(View.GONE);
        binding.llVerification.setVisibility(View.GONE);
        binding.btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(binding.etLogin.getText().toString())){
                    Toast.makeText(ForgotPasswordActivity.this, "Enter Login ID", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(binding.etPassword.getText().toString())){
                    Toast.makeText(ForgotPasswordActivity.this, "Enter Regeisterd Mobile", Toast.LENGTH_SHORT).show();
                }else{
                    callSendOtp();
                }
            }
        });

        binding.btnVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(binding.etVerifyOtp.getText().toString())){
                    Toast.makeText(ForgotPasswordActivity.this, "Enter the OTP received", Toast.LENGTH_SHORT).show();
                }else{
                    callGetPassword();
                }
            }
        });
    }

    private void callGetPassword() {
        JsonObject object = new JsonObject();
        object.addProperty("LoginId",binding.etLogin.getText().toString() );
        object.addProperty("MobileNo",binding.etPassword.getText().toString() );
        if(userType.equals("Teacher")){
            object.addProperty("UserType","3" );
        }else{
            object.addProperty("UserType","2" );
        }

        LoggerUtil.logItem(object);
        Call<CommonResponse> call = apiServices.GetPassword(object);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(ForgotPasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    binding.llLogin.setVisibility(View.GONE);
                    binding.llOtp.setVisibility(View.GONE);
                    binding.llVerification.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

            }
        });
    }

    private void callSendOtp() {
        JsonObject object = new JsonObject();
        object.addProperty("LoginId",binding.etLogin.getText().toString() );
        object.addProperty("MobileNo",binding.etPassword.getText().toString() );
        if(userType.equals("Teacher")){
            object.addProperty("UserType","3" );
        }else{
            object.addProperty("UserType","2" );
        }

        LoggerUtil.logItem(object);
        Call<CommonResponse> call = apiServices.GetOtp(object);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(response.isSuccessful()){


                    if(response.body().getNewStatus().equals("1")){
                        Toast.makeText(ForgotPasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        binding.llLogin.setVisibility(View.GONE);
                        binding.llOtp.setVisibility(View.VISIBLE);
                        binding.llVerification.setVisibility(View.GONE);
                    }else{
                        Toast.makeText(ForgotPasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {

            }
        });
    }
}
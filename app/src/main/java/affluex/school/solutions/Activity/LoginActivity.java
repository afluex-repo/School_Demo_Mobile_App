package affluex.school.solutions.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;

import affluex.school.solutions.Model.ModelParents;
import affluex.school.solutions.Model.ModelTeachers;
import affluex.school.solutions.R;
import affluex.school.solutions.Retrofit.ApiServices;
import affluex.school.solutions.Retrofit.ServiceGenerator;
import affluex.school.solutions.common.BaseActivity;
import affluex.school.solutions.common.LoggerUtil;
import affluex.school.solutions.common.NetworkUtils;
import affluex.school.solutions.databinding.ActivityLoginBinding;
import affluex.school.solutions.databinding.LayoutLoginRegisterBannerBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    ActivityLoginBinding binding;
//    LayoutLoginRegisterBannerBinding bannerBinding;
    String loginType="";
    ApiServices apiServices;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityLoginBinding.inflate(getLayoutInflater());



//        bannerBinding=binding.bannerLayout;

        setContentView(binding.getRoot());
        apiServices = ServiceGenerator.createService(ApiServices.class);
        sharedPreferences=getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

//        bannerBinding.txtHeading.setText("Already have an account. Login Here!");
        binding.llLogin.setVisibility(View.GONE);
        binding.llChoice.setVisibility(View.VISIBLE);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please Wait");
        binding.cardParents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.cardTeacher.setBackground(getDrawable(R.drawable.shape_card_unselected));
                binding.cardParents.setBackground(getDrawable(R.drawable.shape_card_selected));
                loginType="Parents";
                binding.llLogin.setVisibility(View.VISIBLE);
                binding.rbTeacher.setTextColor(getColor(R.color.black));
                binding.rbStudent.setTextColor(getColor(R.color.white));
            }
        });
        binding.cardTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.cardTeacher.setBackground(getDrawable(R.drawable.shape_card_selected));
                binding.cardParents.setBackground(getDrawable(R.drawable.shape_card_unselected));
                loginType="Teacher";
                binding.llLogin.setVisibility(View.VISIBLE);
                binding.rbTeacher.setTextColor(getColor(R.color.white));
                binding.rbStudent.setTextColor(getColor(R.color.black));
            }
        });
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(TextUtils.isEmpty(binding.etLogin.getText().toString())){
                   binding.etLogin.setError("Enter User Id");
               }else if(TextUtils.isEmpty(binding.etPassword.getText().toString())){
                   binding.etPassword.setError("Invalid Password");
               }else{
                   progressDialog.show();

                   if(loginType.equals("Teacher")){

                       if(NetworkUtils.getConnectivityStatus(LoginActivity.this)!=0){
                           callLoginTeacher();
                       }else{
                        showMessage("No Internet Connection");
                       }

                   }else{
                       if(NetworkUtils.getConnectivityStatus(LoginActivity.this)!=0){
                           callLoginParent();
                       }else{
                           showMessage("No Internet Connection");
                       }

                   }
               }
            }
        });


        binding.txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                intent.putExtra("userType",loginType);
                startActivity(intent);
            }
        });



    }

    private void callLoginParent() {
        progressDialog.dismiss();
        JsonObject object = new JsonObject();
        object.addProperty("LoginId",binding.etLogin.getText().toString() );
        object.addProperty("Password",binding.etPassword.getText().toString() );
        LoggerUtil.logItem(object);
        Call<ModelParents> call = apiServices.loginParents(object);
        call.enqueue(new Callback<ModelParents>() {
            @Override
            public void onResponse(Call<ModelParents> call, Response<ModelParents> response) {
                if(response.body().getStatus().equals("0")){
                    editor.putString("userType","Parent");
                    Log.e("ResponseANGHGHVGHGHCG",""+response.body().getLoginId());
                    editor.putString("userId",response.body().getLoginId());
                    editor.putString("name",response.body().getParentName());
                    editor.putString("Pk_ParentID",response.body().getPk_ParentID());

                    editor.apply();
                    editor.commit();
                    startActivity(new Intent(LoginActivity.this,DashboardSchool.class));
                }
            }

            @Override
            public void onFailure(Call<ModelParents> call, Throwable t) {

            }
        });

    }

    private void callLoginTeacher() {
        JsonObject object = new JsonObject();
        object.addProperty("LoginId",binding.etLogin.getText().toString() );
        object.addProperty("Password",binding.etPassword.getText().toString() );
        LoggerUtil.logItem(object);
        Call<ModelTeachers> call = apiServices.getLoginTeachers(object);
        call.enqueue(new Callback<ModelTeachers>() {
            @Override
            public void onResponse(Call<ModelTeachers> call, Response<ModelTeachers> response) {
                if(response.body().getStatus().equals("0")){
                    editor.putString("userType","Teacher");
                    Log.e("ResponseANGHGHVGHGHCG",""+response.body().getLoginId());
                    editor.putString("userId",response.body().getLoginId());
                    editor.putString("name",response.body().getTeacherName());
                    editor.putString("isClassTeacher",response.body().getIsClassTeacher());
                    editor.putString("fkClassId",response.body().getFk_ClassId());
                    editor.putString("fkSectionId",response.body().getFk_SectionId());
                    editor.putString("pkTeacherId",response.body().getPK_TeacherID());
                    editor.putString("imagePath",response.body().getImagePath());

                    editor.apply();
                    editor.commit();
                    startActivity(new Intent(LoginActivity.this,DashboardSchool.class));

                    
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, response.body().getErrorMessage(), Toast.LENGTH_SHORT).show();

                }
                

            }

            @Override
            public void onFailure(Call<ModelTeachers> call, Throwable t) {
                progressDialog.dismiss();
                showMessage(t.getMessage());
                Log.e("Response1234",t.getMessage());

            }
        });
        
        
    }
}
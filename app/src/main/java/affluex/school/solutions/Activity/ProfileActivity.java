package affluex.school.solutions.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import affluex.school.solutions.Model.CommonResponse;
import affluex.school.solutions.Model.ModelProfile;
import affluex.school.solutions.Model.ResponseLeave;
import affluex.school.solutions.R;
import affluex.school.solutions.Retrofit.ApiServices;
import affluex.school.solutions.Retrofit.ServiceGenerator;
import affluex.school.solutions.common.LoggerUtil;
import affluex.school.solutions.databinding.ActivityProfileBinding;
import affluex.school.solutions.databinding.ContentProfileBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    ContentProfileBinding contentProfileBinding;
    ProgressDialog progressDialog;
    String action="Edit";

    private final Calendar myCalendar = Calendar.getInstance();

    String dob,doj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Getting profile");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        contentProfileBinding=binding.contentProfile;
        contentProfileBinding.etName.setEnabled(false);
        contentProfileBinding.etFatherName.setEnabled(false);
        contentProfileBinding.txtMobile.setEnabled(false);
        contentProfileBinding.etEmail.setEnabled(false);
                    contentProfileBinding.etDob.setEnabled(false);
                    contentProfileBinding.etDoj.setEnabled(false);
        contentProfileBinding.etAddress.setEnabled(false);
        contentProfileBinding.etCity.setEnabled(false);
        contentProfileBinding.etState.setEnabled(false);
        contentProfileBinding.etPinCode.setEnabled(false);
        contentProfileBinding.etQualification.setEnabled(false);
        contentProfileBinding.etExperience.setEnabled(false);
        contentProfileBinding.etLastSchool.setEnabled(false);
        contentProfileBinding.etLastExperience.setEnabled(false);
        progressDialog.show();


        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            dob = sdf.format(myCalendar.getTime());

            contentProfileBinding.etDob.setText(sdf.format(myCalendar.getTime()));


        };

        DatePickerDialog.OnDateSetListener date1 = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            doj = sdf.format(myCalendar.getTime());
            contentProfileBinding.etDoj.setText(sdf.format(myCalendar.getTime()));


        };




        contentProfileBinding.etDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(action.equals("Save")){
                    DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
                    String myFormat = "dd/MM/yyyy"; //In which you need put here

                }
            }
        });

        contentProfileBinding.etDoj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileActivity.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                String myFormat = "dd/MM/yyyy"; //In which you need put here
            }
        });



        callProfileApi();



        contentProfileBinding.icAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(action.equals("Edit")){
                    contentProfileBinding.imgAdd.setImageResource(R.drawable.baseline_done_24);
                    action="Save";
                    contentProfileBinding.etName.setEnabled(true);
                    contentProfileBinding.etFatherName.setEnabled(true);
                    contentProfileBinding.txtMobile.setEnabled(true);
                    contentProfileBinding.etEmail.setEnabled(true);
//                    contentProfileBinding.etDob.setEnabled(true);
                    contentProfileBinding.etAddress.setEnabled(true);
                    contentProfileBinding.etCity.setEnabled(true);
                    contentProfileBinding.etState.setEnabled(true);
                    contentProfileBinding.etPinCode.setEnabled(true);
                    contentProfileBinding.etQualification.setEnabled(true);
                    contentProfileBinding.etExperience.setEnabled(true);
                    contentProfileBinding.etLastSchool.setEnabled(true);
                    contentProfileBinding.etLastExperience.setEnabled(true);
                    contentProfileBinding.etDob.setEnabled(true);
                    contentProfileBinding.etDoj.setEnabled(true);
                    contentProfileBinding.etDoj.setFocusable(false);
                    contentProfileBinding.etDob.setFocusable(false);
                }else if(action.equals("Save")){
                    progressDialog.setMessage("Please wait");
                    progressDialog.show();
                    action="Edit";
                    contentProfileBinding.imgAdd.setImageResource(R.drawable.ic_edit);
                    callUpdateProfile();

                }
            }
        });
    }

    private void callUpdateProfile() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("pkTeacherId","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkteacherId)) {
            JsonObject object = new JsonObject();


            object.addProperty("PK_TeacherID", Integer.parseInt(pkteacherId));
            object.addProperty("Name", contentProfileBinding.etName.getText().toString());
            object.addProperty("FatherName", contentProfileBinding.etFatherName.getText().toString());
            object.addProperty("Address", contentProfileBinding.etAddress.getText().toString());
            object.addProperty("PinCode", contentProfileBinding.etPinCode.getText().toString());
            object.addProperty("EmailID",contentProfileBinding.etEmail.getText().toString());
            object.addProperty("DOB", contentProfileBinding.etDob.getText().toString());
            object.addProperty("EmailID", contentProfileBinding.etEmail.getText().toString());
            object.addProperty("LastSchool", contentProfileBinding.etLastSchool.getText().toString());
            object.addProperty("LastExperience", contentProfileBinding.etLastExperience.getText().toString());
            object.addProperty("Gender", "");
            object.addProperty("Religion", "");
            object.addProperty("Category", "");
            object.addProperty("DOJ", contentProfileBinding.etDoj.getText().toString());
            object.addProperty("Qualification", contentProfileBinding.etQualification.getText().toString());
            object.addProperty("Experience", contentProfileBinding.etExperience.getText().toString());
            object.addProperty("BranchName", "");
            object.addProperty("MobileNo", contentProfileBinding.txtMobile.getText().toString());
            object.addProperty("UpdatedBy", Integer.parseInt(pkteacherId));
            LoggerUtil.logItem(object);
            Call<CommonResponse> call = apiServices.UpdateProfile(object);
            call.enqueue(new Callback<CommonResponse>() {
                @Override
                public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(ProfileActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

                        Log.e("Messageghfg",response.body().getMessage());
                        callProfileApi();
                    }
                }

                @Override
                public void onFailure(Call<CommonResponse> call, Throwable t) {

                }
            });
        }
    }

    private void callProfileApi() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("pkTeacherId","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkteacherId)) {
            JsonObject object = new JsonObject();
            object.addProperty("PK_TeacherID", Integer.parseInt(pkteacherId));
            LoggerUtil.logItem(object);
            Call<ModelProfile> call = apiServices.GetProfile(object);
            call.enqueue(new Callback<ModelProfile>() {
                @Override
                public void onResponse(Call<ModelProfile> call, Response<ModelProfile> response) {
                    if(response.isSuccessful() && response.body()!=null){
                        progressDialog.dismiss();
                        contentProfileBinding.etName.setText(response.body().getName());

                        String substring=getSharedPreferences("LoginDetails",MODE_PRIVATE).getString("imagePath","").substring(2);
                        String link="http://demo2.afluex.com"+substring;
                        Log.e("Title123",link);

                        Picasso.get().load(link).
                                resize(400,400).centerCrop()
                                .placeholder(R.drawable.profile_round)
                                .into(binding.imgProfile);
                        contentProfileBinding.etFatherName.setText(response.body().getFatherName());
                        contentProfileBinding.txtMobile.setText(response.body().getMobileNo());
                        contentProfileBinding.etEmail.setText(response.body().getEmailID());
                        contentProfileBinding.etDob.setText(response.body().getDob());
                        contentProfileBinding.etAddress.setText(response.body().getAddress());
                        contentProfileBinding.etCity.setText(response.body().getCity());
                        contentProfileBinding.etState.setText(response.body().getState());
                        contentProfileBinding.etPinCode.setText(response.body().getPinCode());
                        contentProfileBinding.etQualification.setText(response.body().getQualification());
                        contentProfileBinding.etExperience.setText(response.body().getExperience());
                        contentProfileBinding.etLastSchool.setText(response.body().getLastSchool());
                        contentProfileBinding.etLastExperience.setText(response.body().getLastExperience());
                        binding.txtLoginId.setText(getSharedPreferences("LoginDetails", Context.MODE_PRIVATE).getString("userId",""));

                        contentProfileBinding.etDoj.setText(response.body().getDoj());
                        Log.e("ResImg",response.body().getImage());
//                        if(!(response.body().getImage()==null)){
//                            Picasso.get().load(response.body().getImage()).
//                                    resize(400,400).centerCrop()
//                                    .placeholder(R.drawable.profile_round).into(binding.imgProfile);
//                        }
                    }

                }

                @Override
                public void onFailure(Call<ModelProfile> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(ProfileActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }
}
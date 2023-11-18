package affluex.school.solutions.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import affluex.school.solutions.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    String selectedUserType="";
    String selectedPage="Choice";
    Boolean verifyId=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.llChoice.setVisibility(View.VISIBLE);
        binding.llMain.setVisibility(View.GONE);
        binding.llDesignation.setVisibility(View.GONE);
        binding.llUserId.setVisibility(View.GONE);
        binding.rlButton.setVisibility(View.GONE);
        binding.llSchoolTiming.setVisibility(View.GONE);
        binding.btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.rbSchool.setChecked(false);
                binding.rbTeacher.setChecked(false);
                binding.rbStudent.setChecked(false);
                binding.llMain.setVisibility(View.GONE);
                binding.llChoice.setVisibility(View.VISIBLE);
            }
        });
        binding.rbSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.rbSchool.isChecked()){
                    binding.rbSchool.setChecked(true);
                    binding.rbStudent.setChecked(false);
                    binding.rbTeacher.setChecked(false);
                    binding.llChoice.setVisibility(View.GONE);
                    binding.llMain.setVisibility(View.VISIBLE);
                    selectedUserType="School";
                    binding.txtUserType.setText("School");
                    selectedPage="Main";
                    binding.rlButton.setVisibility(View.VISIBLE);
                    binding.cardSchoolId.setVisibility(View.GONE);

                    binding.tilName.setHint("Your School Name");

                }
            }
        });
        binding.rbStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.rbStudent.isChecked()){
                    binding.rbSchool.setChecked(false);
                    binding.rbStudent.setChecked(true);
                    binding.rbTeacher.setChecked(false);
                    binding.llChoice.setVisibility(View.GONE);
                    binding.llMain.setVisibility(View.VISIBLE);
                    selectedUserType="Student";
                    binding.txtUserType.setText("Student");
                    selectedPage="Main";
                    binding.etSchoolId.setVisibility(View.VISIBLE);
                    binding.etName.setHint("Your Name");
                    binding.rlButton.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.rbTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.rbTeacher.isChecked()){
                    binding.rbSchool.setChecked(false);
                    binding.rbStudent.setChecked(false);
                    binding.rbTeacher.setChecked(true);
                    binding.llChoice.setVisibility(View.GONE);
                    binding.llMain.setVisibility(View.VISIBLE);
                    selectedUserType="Teacher";
                    binding.txtUserType.setText("Teacher");
                    selectedPage="Main";
                    binding.etSchoolId.setVisibility(View.VISIBLE);
                    binding.etName.setHint("Your Name");
                    binding.rlButton.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedPage.equals("Main")){
                    if(selectedUserType.equals("Student") || selectedUserType.equals("Teacher")){
                        if(TextUtils.isEmpty(binding.etSchoolId.getText().toString()) || !verifyId){
                            Toast.makeText(RegisterActivity.this, "Invalid School Id", Toast.LENGTH_SHORT).show();
                        }
                    }else if(TextUtils.isEmpty(binding.etName.getText().toString())){
                        Toast.makeText(RegisterActivity.this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                    }else if(TextUtils.isEmpty(binding.etMobile.getText().toString())|| TextUtils.isEmpty(binding.pinOtp.getText().toString())){
                        Toast.makeText(RegisterActivity.this, "Invalid mobile no or OTP incorrect", Toast.LENGTH_SHORT).show();
                    }else{
                        binding.llMain.setVisibility(View.GONE);
                        if(selectedUserType.equals("School")){
                            binding.llUserId.setVisibility(View.GONE);
                            binding.llChoice.setVisibility(View.GONE);
                            binding.llMain.setVisibility(View.GONE);
                            binding.llDesignation.setVisibility(View.GONE);
                            binding.llSchoolTiming.setVisibility(View.VISIBLE);

                            selectedPage="School";
                            binding.rlButton.setVisibility(View.VISIBLE);
                            binding.btnNext.setVisibility(View.GONE);
                        }else{
                            binding.llDesignation.setVisibility(View.VISIBLE);
                            binding.llUserId.setVisibility(View.GONE);
                            binding.llChoice.setVisibility(View.GONE);
                            binding.llMain.setVisibility(View.GONE);
                            binding.llSchoolTiming.setVisibility(View.GONE);
                            selectedPage="Designation";
                            if(selectedUserType.equals("Student")){
                                binding.txtSpinnerHeading.setText("Select Class");
                            }else{
                                binding.txtSpinnerHeading.setText("Select Designation");
                            }


                        }
                    }

                }else if(selectedPage.equals("Designation")){
                    if(binding.spinnerDesignation.getSelectedItemPosition()>0){

                    }else{
                        if(selectedUserType.equals("Student")){
                            Toast.makeText(RegisterActivity.this, "Please select your class", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegisterActivity.this, "Please select your designation", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });
    }
}
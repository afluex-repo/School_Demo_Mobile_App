package affluex.school.solutions.Fragments;
import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import affluex.school.solutions.Model.ResponseSalarySlip;
import affluex.school.solutions.Retrofit.ApiServices;
import affluex.school.solutions.Retrofit.ServiceGenerator;
import affluex.school.solutions.databinding.FragmentViewSalarySlipBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewSalarySlipFragment extends Fragment {

    FragmentViewSalarySlipBinding binding;
    String salary_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentViewSalarySlipBinding.inflate(inflater, container, false);
        salary_id = getArguments().getString("salary_id");
        getSalarySlip();
        binding.btnPunchIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
            }
        });

        return binding.getRoot();
    }

    private void getSalarySlip() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkteacherId = sharedPreferences.getString("pkTeacherId", "");
        Log.e("JHFJHJJH", "" + pkteacherId);
        String fkClassId = sharedPreferences.getString("fkClassId", "");
        String fkSectionId = sharedPreferences.getString("fkSectionId", "");
        if (!TextUtils.isEmpty(pkteacherId)) {
            JsonObject object = new JsonObject();
            object.addProperty("EmployeeID", Integer.parseInt(pkteacherId));
            object.addProperty("FromDate", "");
            object.addProperty("ToDate", "");
            object.addProperty("Pk_PaidSalId", salary_id);
            Call<ResponseSalarySlip> call = apiServices.PrintSalarySlip(object);
            call.enqueue(new Callback<ResponseSalarySlip>() {
                @Override
                public void onResponse(Call<ResponseSalarySlip> call, Response<ResponseSalarySlip> response) {
                    if (response.isSuccessful()) {
                        ResponseSalarySlip modelSalary = response.body();
                        binding.txtCompanyName.setText(modelSalary.getCompanyName());
                        binding.txtAddress.setText(modelSalary.getCompanyAddress());
                        binding.txtEmail.setText(modelSalary.getEmailID());
                        binding.txtName.setText(modelSalary.getEmployeeName());
                        binding.txtCode.setText(modelSalary.getEmployeeID());
                        binding.txtFatherName.setText(modelSalary.getFatherName());
                        binding.txtPanNo.setText(modelSalary.getPanNo());
                        binding.txtAccNo.setText(modelSalary.getAccountNo());
                        binding.txtPayPeriod.setText(modelSalary.getMonthName() + "-" + modelSalary.getYear());
                        binding.txtBasic.setText(modelSalary.getBasic());
                        binding.txtEmp.setText(modelSalary.getContributionTosociety());
                        binding.txtHra.setText(modelSalary.getHra());
                        binding.txtTds.setText(modelSalary.getTds());
                        binding.txtCa.setText(modelSalary.getCa());
                        binding.txtInsurance.setText(modelSalary.getInsurance());
                        binding.txtMa.setText(modelSalary.getMa());
                        binding.txtAdvance.setText(modelSalary.getAdvance());
                        binding.txtPa.setText(modelSalary.getPa());
                        binding.txtPf.setText(modelSalary.getPf());
                        binding.txtTotalEarning.setText(modelSalary.getTotalIncome());
                        binding.txtTotalDeduction.setText(modelSalary.getTotalDeduction());
                        binding.txtNetPay.setText(modelSalary.getNetSalary());
                    }
                }
                @Override
                public void onFailure(Call<ResponseSalarySlip> call, Throwable t) {
                }
            });
        }
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            saveSalaryReport();
        }
    }
    private void saveSalaryReport() {
        StringBuilder reportData = new StringBuilder();
        reportData.append("Company Name: ").append(binding.txtCompanyName.getText().toString()).append("\n");
        reportData.append("Company Address: ").append(binding.txtAddress.getText().toString()).append("\n");
        reportData.append("Email: ").append(binding.txtEmail.getText().toString()).append("\n");
        reportData.append("Employee Name: ").append(binding.txtName.getText().toString()).append("\n");
        reportData.append("Employee ID: ").append(binding.txtCode.getText().toString()).append("\n");
        reportData.append("Father's Name: ").append(binding.txtFatherName.getText().toString()).append("\n");
        reportData.append("PAN No: ").append(binding.txtPanNo.getText().toString()).append("\n");
        reportData.append("Account No: ").append(binding.txtAccNo.getText().toString()).append("\n");
        reportData.append("Pay Period: ").append(binding.txtPayPeriod.getText().toString()).append("\n");
        reportData.append("Basic: ").append(binding.txtBasic.getText().toString()).append("\n");
        reportData.append("HRA: ").append(binding.txtHra.getText().toString()).append("\n");
        reportData.append("TDS: ").append(binding.txtTds.getText().toString()).append("\n");
        reportData.append("CA: ").append(binding.txtCa.getText().toString()).append("\n");
        reportData.append("Insurance: ").append(binding.txtInsurance.getText().toString()).append("\n");
        reportData.append("MA: ").append(binding.txtMa.getText().toString()).append("\n");
        reportData.append("Advance: ").append(binding.txtAdvance.getText().toString()).append("\n");
        reportData.append("PA: ").append(binding.txtPa.getText().toString()).append("\n");
        reportData.append("PF: ").append(binding.txtPf.getText().toString()).append("\n");
        reportData.append("Total Earning: ").append(binding.txtTotalEarning.getText().toString()).append("\n");
        reportData.append("Total Deduction: ").append(binding.txtTotalDeduction.getText().toString()).append("\n");
        reportData.append("Net Pay: ").append(binding.txtNetPay.getText().toString()).append("\n");

        File reportFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "SalaryReport.txt");

        try (FileOutputStream fos = new FileOutputStream(reportFile)) {
            fos.write(reportData.toString().getBytes());
            Toast.makeText(getContext(), "Salary report downloaded successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Failed to download salary report.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveSalaryReport();
            } else {
                Toast.makeText(getContext(), "Permission denied to write to external storage.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

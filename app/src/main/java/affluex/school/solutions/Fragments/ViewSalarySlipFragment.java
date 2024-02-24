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

import com.google.gson.JsonObject;

import affluex.school.solutions.Model.ResponseSalary;
import affluex.school.solutions.Model.ResponseSalarySlip;
import affluex.school.solutions.R;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentViewSalarySlipBinding.inflate(inflater,container,false);
        salary_id=getArguments().getString("salary_id");

        getSalarySlip();


        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void getSalarySlip() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("pkTeacherId","");
        Log.e("JHFJHJJH",""+pkteacherId);
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkteacherId)) {
            JsonObject object = new JsonObject();
            object.addProperty("EmployeeID", Integer.parseInt(pkteacherId));
            object.addProperty("FromDate", "");
            object.addProperty("ToDate", "");
            object.addProperty("Pk_PaidSalId", salary_id);
            Call<ResponseSalarySlip> call=apiServices.PrintSalarySlip(object);
            call.enqueue(new Callback<ResponseSalarySlip>() {
                @Override
                public void onResponse(Call<ResponseSalarySlip> call, Response<ResponseSalarySlip> response) {
                    if(response.isSuccessful()){
                        ResponseSalarySlip modelSalary=response.body();
                     binding.txtCompanyName.setText(modelSalary.getCompanyName());
                     binding.txtAddress.setText(modelSalary.getCompanyAddress());
                     binding.txtEmail.setText(modelSalary.getEmailID());
                     binding.txtName.setText(modelSalary.getEmployeeName());
                     binding.txtCode.setText(modelSalary.getEmployeeID());
                     binding.txtFatherName.setText(modelSalary.getFatherName());
                     binding.txtPanNo.setText(modelSalary.getPanNo());
                     binding.txtAccNo.setText(modelSalary.getAccountNo());
                     binding.txtPayPeriod.setText(modelSalary.getMonthName()+"-"+modelSalary.getYear());
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
}
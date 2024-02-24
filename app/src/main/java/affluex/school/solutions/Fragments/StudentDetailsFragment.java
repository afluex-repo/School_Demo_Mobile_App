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

import affluex.school.solutions.Model.ModelStudentDetails;
import affluex.school.solutions.Model.ResponseParentDashboard;
import affluex.school.solutions.Model.ResponseStudentDetails;
import affluex.school.solutions.R;
import affluex.school.solutions.Retrofit.ApiServices;
import affluex.school.solutions.Retrofit.ServiceGenerator;
import affluex.school.solutions.common.LoggerUtil;
import affluex.school.solutions.databinding.FragmentStudentDetailsBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StudentDetailsFragment extends Fragment {

    FragmentStudentDetailsBinding binding;


    String studentId;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentStudentDetailsBinding.inflate(inflater,container,false);
        // Inflate the layout for this fragment

        studentId=getArguments().getString("studentId");
        getStudentDetails();
        return binding.getRoot();
    }

    private void getStudentDetails() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkParentId=sharedPreferences.getString("Pk_ParentID","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkParentId)) {
            JsonObject object = new JsonObject();
            object.addProperty("Fk_ParentId", Integer.parseInt(pkParentId));
            object.addProperty("Fk_StudentId",studentId);
            Log.e("STfpknpkinasf",studentId);
            LoggerUtil.logItem(object);

            Call<ResponseStudentDetails> call = apiServices.GetDashBoardDetailsofStudent(object);

            call.enqueue(new Callback<ResponseStudentDetails>() {
                @Override
                public void onResponse(Call<ResponseStudentDetails> call, Response<ResponseStudentDetails> response) {
                    if(response.isSuccessful() && response.body().getStudentDetails()!=null){
                        if(response.body().getStudentDetails().size()>0){
                            ModelStudentDetails modelStudentDetails=response.body().getStudentDetails().get(0);
                            binding.txtClass.setText(modelStudentDetails.getClassName()+" - "+modelStudentDetails.getSectionName());
                            binding.txtMobile.setText(modelStudentDetails.getMobile());
                            binding.etAddressCorrespondance.setText(modelStudentDetails.getCorrespondenceAddress());
                            binding.etAddressPermanent.setText(modelStudentDetails.getPermanentAddress());
                            binding.etRoll.setText(modelStudentDetails.getRollNo());
                            binding.etName.setText(modelStudentDetails.getStudentName());
                            binding.etState.setText(modelStudentDetails.getState());
                            binding.etCity.setText(modelStudentDetails.getCity());
                            binding.etDob.setText(modelStudentDetails.getDateOfBirth());
                            binding.etGender.setText(modelStudentDetails.getGender());
                            binding.etFatherOccupation.setText(modelStudentDetails.getFatherOcc());
                            binding.etMotherOccupation.setText(modelStudentDetails.getMotherOcc());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseStudentDetails> call, Throwable t) {

                }
            });
        }
    }
}
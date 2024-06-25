package affluex.school.solutions.Fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.gson.JsonObject;
import affluex.school.solutions.Activity.DashboardSchool;
import affluex.school.solutions.Model.CommonResponse;
import affluex.school.solutions.Model.ModelParentsProfile;
import affluex.school.solutions.Model.ResponseParentsProfile;
import affluex.school.solutions.Retrofit.ApiServices;
import affluex.school.solutions.Retrofit.ServiceGenerator;
import affluex.school.solutions.common.LoggerUtil;
import affluex.school.solutions.databinding.FragmentParentsProfileBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ParentsProfileFragment extends Fragment {
  FragmentParentsProfileBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentParentsProfileBinding.inflate(inflater,container,false);
        getProfile();
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(binding.etName.getText().toString())){
                    Toast.makeText(getActivity(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(binding.txtMobile.getText().toString())){
                    Toast.makeText(getActivity(), "Mobile cannot be empty", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(binding.etAddress.getText().toString())){
                    Toast.makeText(getActivity(), "Address cannot be empty", Toast.LENGTH_SHORT).show();
                }else{
                    updateProfile();
                }
            }
        });
        return binding.getRoot();

    }
    private void updateProfile() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkParentId=sharedPreferences.getString("Pk_ParentID","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkParentId)) {
            JsonObject object = new JsonObject();
            object.addProperty("Pk_ParentID", Integer.parseInt(pkParentId));
            object.addProperty("Email",binding.etEmail.getText().toString() );
            object.addProperty("ParentName",binding.etName.getText().toString() );
            object.addProperty("Mobile", binding.txtMobile.getText().toString());
            object.addProperty("Address",binding.etAddress.getText().toString() );
            object.addProperty("PinCode",binding.etPincode.getText().toString() );
            object.addProperty("State",binding.etState.getText().toString() );
            object.addProperty("City",binding.etCity.getText().toString() );
            LoggerUtil.logItem(object);
            Call<CommonResponse> call=apiServices.UpdateParentProfile(object);
            call.enqueue(new Callback<CommonResponse>() {
                @Override
                public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        ((DashboardSchool)getActivity()).switchFragmentOnDashBoard(new ParentHome(),"Home");
                    }
                }
                @Override
                public void onFailure(Call<CommonResponse> call, Throwable t) {
                }
            });
        }
    }

    private void getProfile() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkParentId=sharedPreferences.getString("Pk_ParentID","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkParentId)) {
            JsonObject object = new JsonObject();
            object.addProperty("Pk_ParentID", Integer.parseInt(pkParentId));
            LoggerUtil.logItem(object);
            Call<ResponseParentsProfile> call=apiServices.ProfileDetailsParent(object);
            call.enqueue(new Callback<ResponseParentsProfile>() {
                @Override
                public void onResponse(Call<ResponseParentsProfile> call, Response<ResponseParentsProfile> response) {
                    if(response.isSuccessful() && response.body().getParentsProfileArrayList()!=null){
                        if(response.body().getParentsProfileArrayList().size()>0){
                            ModelParentsProfile modelParentsProfile=response.body().getParentsProfileArrayList().get(0);
                            binding.etName.setText(modelParentsProfile.getParentName());
                            binding.etAddress.setText(modelParentsProfile.getAddress());
                            binding.etCity.setText(modelParentsProfile.getCity());
                            binding.etState.setText(modelParentsProfile.getState());
                            binding.etEmail.setText(modelParentsProfile.getEmail());
                            binding.etPincode.setText(modelParentsProfile.getPinCode());
                        }
                    }
                }
                @Override
                public void onFailure(Call<ResponseParentsProfile> call, Throwable t) {

                }
            });
        }
    }


}
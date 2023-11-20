package affluex.school.solutions.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;

import affluex.school.solutions.Adapter.AdapterAttendance;
import affluex.school.solutions.Model.CommonResponse;
import affluex.school.solutions.Model.ResponseAttendnace;
import affluex.school.solutions.R;
import affluex.school.solutions.Retrofit.ApiServices;
import affluex.school.solutions.Retrofit.ServiceGenerator;
import affluex.school.solutions.databinding.FragmentAttendanceListBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AttendanceListFragment extends Fragment {
    FragmentAttendanceListBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=affluex.school.solutions.databinding.FragmentAttendanceListBinding.inflate(inflater,container,false);
        getAttendance();







        return binding.getRoot();
    }

    private void getAttendance() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("pkTeacherId","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");

        if(!TextUtils.isEmpty(pkteacherId)) {
            JsonObject object = new JsonObject();
            object.addProperty("FK_EmpID", Integer.parseInt(pkteacherId));
            Call<ResponseAttendnace> call = apiServices.GetAttenndaceList(object);

            call.enqueue(new Callback<ResponseAttendnace>() {
                @Override
                public void onResponse(Call<ResponseAttendnace> call, Response<ResponseAttendnace> response) {
                    if(response.isSuccessful()&&response.body().getMessage().equals("Record Found.")){
                        AdapterAttendance adapterAttendance=new AdapterAttendance(getActivity(),response.body().getListAttenndace());
                        binding.rvLeave.setAdapter(adapterAttendance);

                    }
                }

                @Override
                public void onFailure(Call<ResponseAttendnace> call, Throwable t) {

                }
            });
        }
    }
}
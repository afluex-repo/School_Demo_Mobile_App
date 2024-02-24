package affluex.school.solutions.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;

import affluex.school.solutions.Adapter.AdapterSyllabus;
import affluex.school.solutions.Model.ResponseSyllabus;
import affluex.school.solutions.Model.ResponseTimetable;
import affluex.school.solutions.R;
import affluex.school.solutions.Retrofit.ApiServices;
import affluex.school.solutions.Retrofit.ServiceGenerator;
import affluex.school.solutions.common.LoggerUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SyllabusFragment extends Fragment {

    affluex.school.solutions.databinding.FragmentSyllabusBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=affluex.school.solutions.databinding.FragmentSyllabusBinding.inflate(inflater,container,false);
        
        getSyllabus();
        return binding.getRoot();
    }

    private void getSyllabus() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkParentId=sharedPreferences.getString("Pk_ParentID","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkParentId)) {
            JsonObject object = new JsonObject();
            object.addProperty("Fk_ParentId", Integer.parseInt(pkParentId));
            LoggerUtil.logItem(object);

            Call<ResponseSyllabus> call = apiServices.Sylabus(object);

            call.enqueue(new Callback<ResponseSyllabus>() {
                @Override
                public void onResponse(Call<ResponseSyllabus> call, Response<ResponseSyllabus> response) {
                    if(response.isSuccessful()&& response.body().getModelSyllabusDetailsArrayList()!=null){
                        if(response.body().getModelSyllabusDetailsArrayList().get(0).getSyllabusArrayList().size()>0){
                            AdapterSyllabus adapterSyllabus=new AdapterSyllabus(getActivity(),response.body().getModelSyllabusDetailsArrayList().get(0).getSyllabusArrayList());
                            binding.rvTimeTable.setAdapter(adapterSyllabus);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseSyllabus> call, Throwable t) {

                }
            });

        }
    }
}
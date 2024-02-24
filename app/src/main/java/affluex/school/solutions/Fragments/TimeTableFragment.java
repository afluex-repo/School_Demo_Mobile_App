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

import affluex.school.solutions.Adapter.AdapterTimeTable;
import affluex.school.solutions.Model.ResponseTimetable;
import affluex.school.solutions.R;
import affluex.school.solutions.Retrofit.ApiServices;
import affluex.school.solutions.Retrofit.ServiceGenerator;
import affluex.school.solutions.common.LoggerUtil;
import affluex.school.solutions.databinding.FragmentTimeTableBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeTableFragment extends Fragment {

   FragmentTimeTableBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentTimeTableBinding.inflate(inflater,container,false);
        
        getTimeTable();
        return binding.getRoot();
    }

    private void getTimeTable() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkParentId=sharedPreferences.getString("Pk_ParentID","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkParentId)) {
            JsonObject object = new JsonObject();
            object.addProperty("Fk_ParentId", Integer.parseInt(pkParentId));
            LoggerUtil.logItem(object);

            Call<ResponseTimetable> call=apiServices.TimeTable(object);
            call.enqueue(new Callback<ResponseTimetable>() {
                @Override
                public void onResponse(Call<ResponseTimetable> call, Response<ResponseTimetable> response) {
                    if(response.isSuccessful() && response.body().getTimeTableDetailsArrayList()!=null){
                        if(response.body().getTimeTableDetailsArrayList().get(0).getTimeTableArrayList().size()>0){
                            AdapterTimeTable adapterTimeTable=new AdapterTimeTable(getActivity(),response.body().getTimeTableDetailsArrayList().get(0).getTimeTableArrayList());
                            binding.rvTimeTable.setAdapter(adapterTimeTable);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseTimetable> call, Throwable t) {

                }
            });
        }
    }
}
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
import affluex.school.solutions.Adapter.AdapterFeeReport;
import affluex.school.solutions.Model.REsponseFeeReport;
import affluex.school.solutions.Retrofit.ApiServices;
import affluex.school.solutions.Retrofit.ServiceGenerator;
import affluex.school.solutions.databinding.FragmentFeeBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FeeFragment extends Fragment {

   FragmentFeeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentFeeBinding.inflate(inflater,container,false);
        getFeeReport();
        return binding.getRoot();
    }

    private void getFeeReport() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkParentId=sharedPreferences.getString("Pk_ParentID","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkParentId)) {
            JsonObject object = new JsonObject();
            object.addProperty("Fk_ParentId", Integer.parseInt(pkParentId));


            Call<REsponseFeeReport> call=apiServices.FeeReport(object);

            call.enqueue(new Callback<REsponseFeeReport>() {
                @Override
                public void onResponse(Call<REsponseFeeReport> call, Response<REsponseFeeReport> response) {
                    if(response.isSuccessful()){
                        if(response.body().getFeeReportDetailsArrayList()!=null){
                            if(response.body().getFeeReportDetailsArrayList().get(0).getFeeReportArrayList()!=null
                            &&response.body().getFeeReportDetailsArrayList().get(0).getFeeReportArrayList().size()>0){
                                AdapterFeeReport adapterFeeReport=new AdapterFeeReport(getActivity(),response.body().getFeeReportDetailsArrayList().get(0).getFeeReportArrayList());
                                binding.rvFeeReport.setAdapter(adapterFeeReport);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<REsponseFeeReport> call, Throwable t) {

                }
            });

        }
    }
}
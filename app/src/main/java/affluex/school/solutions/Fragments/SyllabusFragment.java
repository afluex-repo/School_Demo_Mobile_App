package affluex.school.solutions.Fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.gson.JsonObject;
import affluex.school.solutions.Adapter.AdapterSyllabus;
import affluex.school.solutions.Model.ResponseSyllabus;
import affluex.school.solutions.Retrofit.ApiServices;
import affluex.school.solutions.Retrofit.ServiceGenerator;
import affluex.school.solutions.common.LoggerUtil;
import affluex.school.solutions.databinding.FragmentSyllabusBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SyllabusFragment extends Fragment {

    private FragmentSyllabusBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSyllabusBinding.inflate(inflater, container, false);

        setupRecyclerView();
        getSyllabus();
        return binding.getRoot();
    }

    private void setupRecyclerView() {
        binding.rvTimeTable.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void getSyllabus() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkParentId = sharedPreferences.getString("Pk_ParentID", "");
        String fkClassId = sharedPreferences.getString("fkClassId", "");
        String fkSectionId = sharedPreferences.getString("fkSectionId", "");

        if (!TextUtils.isEmpty(pkParentId)) {
            JsonObject object = new JsonObject();
            object.addProperty("Fk_ParentId", Integer.parseInt(pkParentId));
            LoggerUtil.logItem(object);

            Call<ResponseSyllabus> call = apiServices.Sylabus(object);

            call.enqueue(new Callback<ResponseSyllabus>() {
                @Override
                public void onResponse(@NonNull Call<ResponseSyllabus> call, @NonNull Response<ResponseSyllabus> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().getModelSyllabusDetailsArrayList() != null &&
                                !response.body().getModelSyllabusDetailsArrayList().isEmpty() &&
                                !response.body().getModelSyllabusDetailsArrayList().get(0).getSyllabusArrayList().isEmpty()) {
                            AdapterSyllabus adapterSyllabus = new AdapterSyllabus(
                                    getActivity(),
                                    response.body().getModelSyllabusDetailsArrayList().get(0).getSyllabusArrayList()
                            );
                            binding.rvTimeTable.setAdapter(adapterSyllabus);
                        }
                    } else {
                        Log.e("SyllabusFragment", "Response not successful or body is null");
                    }
                }

                @Override
                public void onFailure(Call<ResponseSyllabus> call, Throwable t) {
                    Log.e("Picasso", "Error loading image: " + t.getMessage());
                }

            });
        }
    }
}

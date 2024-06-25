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
import android.widget.TextView;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import affluex.school.solutions.Adapter.AdapterStudentsAttendance;
import affluex.school.solutions.Model.ResponseParentDashboard;
import affluex.school.solutions.Model.ResponseStudentAttendance;
import affluex.school.solutions.Model.StudentDetails;
import affluex.school.solutions.R;
import affluex.school.solutions.Retrofit.ApiServices;
import affluex.school.solutions.Retrofit.ServiceGenerator;
import affluex.school.solutions.common.LoggerUtil;
import affluex.school.solutions.databinding.FragmentStudentsAttendance2Binding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StudentsAttendanceFragment extends Fragment {
    FragmentStudentsAttendance2Binding binding;
    private ArrayList<StudentDetails> studentDetails;
    private String selectedStudentId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentStudentsAttendance2Binding.inflate(inflater,container,false);
        studentDetails=new ArrayList<>();
        callDashBoardApi();
        getStudentAttendance("","");
        return binding.getRoot();
    }

    private void getStudentAttendance(String classId, String sectionId) {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkParentId=sharedPreferences.getString("Pk_ParentID","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkParentId)) {
            JsonObject object = new JsonObject();
            object.addProperty("Fk_ParentId", Integer.parseInt(pkParentId));
            Log.e("REJVSGCGHD",pkParentId);
            object.addProperty("Fk_ClassID", "");
            object.addProperty("Fk_SectionID", "");
            LoggerUtil.logItem(object);
            Call<ResponseStudentAttendance> call = apiServices.StudentAttendanceFilter(object);
            call.enqueue(new Callback<ResponseStudentAttendance>() {
                @Override
                public void onResponse(Call<ResponseStudentAttendance> call, Response<ResponseStudentAttendance> response) {
                    Log.e("REJVSGCGHD",""+(response.body().getResponseStudentListArrayList()==null));
                    if(response.isSuccessful() && response.body().getResponseStudentListArrayList()!=null){
                        if(response.body().getResponseStudentListArrayList().get(0)
                                .getStudentAttendanceArrayList().size()>0){
                            AdapterStudentsAttendance adapterStudentsAttendance=new AdapterStudentsAttendance(getActivity(),
                                    response.body().getResponseStudentListArrayList().get(0)
                                            .getStudentAttendanceArrayList() );

                            binding.rvLeave.setAdapter(adapterStudentsAttendance);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseStudentAttendance> call, Throwable t) {

                }
            });
        }

    }

    private void callDashBoardApi() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkParentId=sharedPreferences.getString("Pk_ParentID","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkParentId)) {
            JsonObject object = new JsonObject();
            object.addProperty("Fk_ParentId", Integer.parseInt(pkParentId));
            LoggerUtil.logItem(object);
            Call<ResponseParentDashboard> call = apiServices.GetDashboard(object);
            call.enqueue(new Callback<ResponseParentDashboard>() {
                @Override
                public void onResponse(Call<ResponseParentDashboard> call, Response<ResponseParentDashboard> response) {
                    Log.e("RESPKLNJGGHVJHFG",""+response.body().getDashBoardList().get(0).getStudentDetails().size());
                    if(response.isSuccessful() && response.body()!=null){
                        if(response.body().getDashBoardList().size()>0){

                            if(response.body().getDashBoardList().get(0).getStudentDetails().size()>0){
                                studentDetails=new ArrayList<>(response.body().getDashBoardList().get(0).getStudentDetails());
                               studentDetails.add(0,new StudentDetails("Select Student"));
                                SpinnerSectionAdapter spinnerSectionAdapter=new SpinnerSectionAdapter();
                                binding.spinnerSelectStudent.setAdapter(spinnerSectionAdapter);

                            }
                        }

                    }
                }


                @Override
                public void onFailure(Call<ResponseParentDashboard> call, Throwable t) {

                }
            });
        }
    }


    class SpinnerSectionAdapter extends android.widget.BaseAdapter {

        @Override
        public int getCount() {
            return studentDetails.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inf = getLayoutInflater();
            View row = inf.inflate(R.layout.layout_of_country_row, null);
            TextView spinner_text;

            spinner_text = row.findViewById(R.id.spinner_text);
            if(position==0){
                spinner_text.setText(studentDetails.get(0).getStudentName());
            }else{
                spinner_text.setText(studentDetails.get(position).getStudentName()+"\t"+studentDetails.get(position).getClassName()
                        +" - "+studentDetails.get(position).getSectionName());
            }



            return row;
        }
    }
}
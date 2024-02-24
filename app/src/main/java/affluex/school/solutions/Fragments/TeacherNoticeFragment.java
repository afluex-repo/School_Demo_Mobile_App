package affluex.school.solutions.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import affluex.school.solutions.Adapter.AdapterNotice;
import affluex.school.solutions.Adapter.AdapterNoticeParent;
import affluex.school.solutions.Model.CommonResponse;
import affluex.school.solutions.Model.ModelClass;
import affluex.school.solutions.Model.ModelSection;
import affluex.school.solutions.Model.NoticeDetails;
import affluex.school.solutions.Model.ResponseClass;
import affluex.school.solutions.Model.ResponseParentNotice;
import affluex.school.solutions.Model.ResponseSection;
import affluex.school.solutions.Model.ResponseStudentAttendance;
import affluex.school.solutions.Model.lstNoticeList;
import affluex.school.solutions.R;
import affluex.school.solutions.Retrofit.ApiServices;
import affluex.school.solutions.Retrofit.ServiceGenerator;
import affluex.school.solutions.common.LoggerUtil;
import affluex.school.solutions.databinding.FragmentTeacherNoticeBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TeacherNoticeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FragmentTeacherNoticeBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String selectedClassId,selectedSectionId,selectedSubjectId,selectedClassName,selectedSectionName,selectedSubjectName,techerId;

    ArrayList<ModelClass> classArrayList;
    ArrayList<ModelSection> sectionArrayList;

    public TeacherNoticeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentTeacherNoticeBinding.inflate(inflater,container,false);

        binding.llView.setVisibility(View.VISIBLE);
        binding.llAdd.setVisibility(View.GONE);
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("userType","").equals("Parent")){
            binding.cardAdd.setVisibility(View.GONE);
            binding.btnSubmit.setVisibility(View.GONE);

            getNoticeParent();

        }else{
            binding.cardAdd.setVisibility(View.VISIBLE);
            getNoticeList();
            getClassList();
        }


        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter("notice"));

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver1,
                new IntentFilter("notice_edit"));

        binding.icAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("GHFyd","clicked");
                showAddNoticeDialog();
            }
        });
        binding.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llView.setVisibility(View.VISIBLE);
                binding.llAdd.setVisibility(View.GONE);
            }
        });
        binding.spinnerAddClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){
                    binding.spinnerAddSection.setVisibility(View.VISIBLE);
                    selectedClassId=classArrayList.get(i).getFk_ClassID();
                    selectedClassName=classArrayList.get(i).getClassName();
                    getSectionList();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spinnerAddSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){

                    selectedSectionId=sectionArrayList.get(i).getPK_SectionId();
                    selectedSectionName=sectionArrayList.get(i).getSectionName();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.ivMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.ENGLISH);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to type");


                try {

                    startActivityForResult(intent, 104);
                } catch (Exception e) {
                    Toast
                            .makeText(getActivity(), " " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(binding.etAssignment.getText().toString())){
                    Toast.makeText(getActivity(), "Notice cannot be left empty", Toast.LENGTH_SHORT).show();
                }else{
                    callAddNoticeApi();
                }
            }
        });



        return binding.getRoot();
    }

    private void getNoticeParent() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkParentId=sharedPreferences.getString("Pk_ParentID","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkParentId)) {
            JsonObject object = new JsonObject();
            object.addProperty("Fk_ParentId", Integer.parseInt(pkParentId));
            LoggerUtil.logItem(object);
            Call<ResponseParentNotice> call = apiServices.GetNoticeForParent(object);
            call.enqueue(new Callback<ResponseParentNotice>() {
                @Override
                public void onResponse(Call<ResponseParentNotice> call, Response<ResponseParentNotice> response) {


                    if(response.body().getNoticeParentArrayList()!=null){
                        if(response.body().getNoticeParentArrayList().size()>0){
                            AdapterNoticeParent adapterNoticeParent=new AdapterNoticeParent(getActivity(),response.body().getNoticeParentArrayList());
                            binding.rvNotice.setAdapter(adapterNoticeParent);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseParentNotice> call, Throwable t) {

                }
            });
        }
    }

    private void showAddNoticeDialog() {
        binding.llView.setVisibility(View.GONE);
        binding.llAdd.setVisibility(View.VISIBLE);
    }

    private void callAddNoticeApi() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("pkTeacherId","");
        String fkClassId=selectedClassId;
        String fkSectionId=selectedSectionId;
        Log.e("jkhjkh","T"+pkteacherId);
        Log.e("jkhjkh","C"+fkClassId);
        Log.e("jkhjkh","S"+fkSectionId);
        if(!TextUtils.isEmpty(pkteacherId)&&!TextUtils.isEmpty(selectedSectionId)&&!TextUtils.isEmpty(selectedClassId)){
            JsonObject object = new JsonObject();
            object.addProperty("AddedBy",Integer.parseInt(pkteacherId) );
            object.addProperty("PK_ClassID",Integer.parseInt(fkClassId) );
            object.addProperty("Notice", binding.etAssignment.getText().toString() );
            object.addProperty("PK_SectionId",Integer.parseInt(fkSectionId) );
            LoggerUtil.logItem(object);
            Call<String> call = apiServices.addNotice(object);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.body()!=null){
                        Toast.makeText(getActivity(), "Notice Added Successfully", Toast.LENGTH_SHORT).show();
                        getNoticeList();
                        binding.llAdd.setVisibility(View.GONE);
                        binding.llView.setVisibility(View.VISIBLE);

                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();


                }
            });
        }
    }

    private void getNoticeList() {
        ArrayList<NoticeDetails> noticeDetails=new ArrayList<>();
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("pkTeacherId","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");

        if(!TextUtils.isEmpty(pkteacherId)){
            JsonObject object = new JsonObject();
            object.addProperty("Pk_TeacherId",Integer.parseInt(pkteacherId) );
            LoggerUtil.logItem(object);
            Call<lstNoticeList> call = apiServices.noticeList(object);
            call.enqueue(new Callback<lstNoticeList>() {
                @Override
                public void onResponse(Call<lstNoticeList> call, Response<lstNoticeList> response) {
                    List<NoticeDetails>noticeDetailsList=new ArrayList<>(response.body().getNoticeDetailsList().get(0).getNoticeDetails());
                    AdapterNotice adapterNotice=new AdapterNotice(getActivity(),noticeDetailsList);
                    binding.rvNotice.setAdapter(adapterNotice);

                    Log.e("RESPONSEGHFY",""+noticeDetailsList.size());

                }

                @Override
                public void onFailure(Call<lstNoticeList> call, Throwable t) {

                }
            });

        }


    }
    private void getSectionList() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("pkTeacherId","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkteacherId)) {
            JsonObject object = new JsonObject();
            object.addProperty("TeacherID", Integer.parseInt(pkteacherId));
            object.addProperty("Fk_ClassID", Integer.parseInt(selectedClassId));
            LoggerUtil.logItem(object);
            Call<ResponseSection> call=apiServices.GetSectionList(object);
            call.enqueue(new Callback<ResponseSection>() {
                @Override
                public void onResponse(Call<ResponseSection> call, Response<ResponseSection> response) {
                    sectionArrayList=new ArrayList<>(response.body().getSectionArrayList());
                    sectionArrayList.add(0,new ModelSection("Select Section"));
                    SpinnerSectionAdapter spinnerSectionAdapter=new SpinnerSectionAdapter();
                    binding.spinnerAddSection.setAdapter(spinnerSectionAdapter);
                }

                @Override
                public void onFailure(Call<ResponseSection> call, Throwable t) {

                }
            });
        }
    }

    private void getClassList() {

        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("pkTeacherId","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkteacherId)) {
            JsonObject object = new JsonObject();
            object.addProperty("TeacherID", Integer.parseInt(pkteacherId));
            LoggerUtil.logItem(object);
            Call<ResponseClass> call = apiServices.GetClassList(object);
            call.enqueue(new Callback<ResponseClass>() {
                @Override
                public void onResponse(Call<ResponseClass> call, Response<ResponseClass> response) {
                    classArrayList=new ArrayList(response.body().getListClass());
                    Log.e("Response.body",""+response.body().getListClass().size());
                    Log.e("Response.body",""+classArrayList.size());
                    classArrayList.add(0,new ModelClass("Select Class"));
                    SpinnerClassAdapter spinnerClassAdapter=new SpinnerClassAdapter();
                    binding.spinnerAddClass.setAdapter(spinnerClassAdapter);


                }

                @Override
                public void onFailure(Call<ResponseClass> call, Throwable t) {

                }
            });
        }
    }

    class SpinnerClassAdapter extends android.widget.BaseAdapter {

        @Override
        public int getCount() {
            return classArrayList.size();
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
            spinner_text.setText(classArrayList.get(position).getClassName());


            return row;
        }
    }
    class SpinnerSectionAdapter extends android.widget.BaseAdapter {

        @Override
        public int getCount() {
            return sectionArrayList.size();
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
            spinner_text.setText(sectionArrayList.get(position).getSectionName());


            return row;
        }
    }

    public BroadcastReceiver mMessageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
          String noticeId=intent.getStringExtra("notice_id");
          callDeleteNotice(noticeId);



        }
    };
    public BroadcastReceiver mMessageReceiver1=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String noticeId=intent.getStringExtra("notice_id");
            String classId=intent.getStringExtra("class_id");
            String sectionId=intent.getStringExtra("section_id");
            String noticeName=intent.getStringExtra("notice_name");
            callEditNotice(noticeId,classId,sectionId,noticeName);



        }
    };

    private void callEditNotice(String noticeId, String classId, String sectionId, String noticeName) {

        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("pkTeacherId","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkteacherId)) {
            JsonObject object = new JsonObject();
            object.addProperty("UpdatedBy", Integer.parseInt(pkteacherId));
            object.addProperty("PK_NoticeId", noticeId);
            object.addProperty("Fk_ClassID", classId);
            object.addProperty("Fk_SectionID", sectionId);
            object.addProperty("NoticeName", noticeName);
            LoggerUtil.logItem(object);


            Log.e("NoticeArgms","1"+noticeId);
            Log.e("NoticeArgms","2"+classId);
            Log.e("NoticeArgms","3"+sectionId);
            Log.e("NoticeArgms","4"+noticeName);

            Call<CommonResponse> call = apiServices.UpdateNotice(object);
            call.enqueue(new Callback<CommonResponse>() {
                @Override
                public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getActivity(), "Notice Updation successful", Toast.LENGTH_SHORT).show();
                        getNoticeList();
                    }
                }

                @Override
                public void onFailure(Call<CommonResponse> call, Throwable t) {

                }
            });
        }
    }

    private void callDeleteNotice(String noticeId) {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("pkTeacherId","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkteacherId)) {
            JsonObject object = new JsonObject();
            object.addProperty("DeletedBy", Integer.parseInt(pkteacherId));
            object.addProperty("PK_NoticeId", noticeId);

            LoggerUtil.logItem(object);
            Call<CommonResponse> call = apiServices.DeleteNotice(object);
            call.enqueue(new Callback<CommonResponse>() {
                @Override
                public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getActivity(), "Notice Deletion Successful", Toast.LENGTH_SHORT).show();
                        getNoticeList();
                    }
                }

                @Override
                public void onFailure(Call<CommonResponse> call, Throwable t) {

                }
            });
        }

    }

    private void initRecyclerView() {
    }
}
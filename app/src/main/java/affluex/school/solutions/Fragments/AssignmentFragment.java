package affluex.school.solutions.Fragments;
import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.StrictMode;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.JsonObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import affluex.school.solutions.Adapter.AdapterAssignment;
import affluex.school.solutions.Adapter.AdapterParentsAssignment;
import affluex.school.solutions.Model.CommonResponse;
import affluex.school.solutions.Model.HomeWorkDetails;
import affluex.school.solutions.Model.ModelAssignment;
import affluex.school.solutions.Model.ModelClass;
import affluex.school.solutions.Model.ModelSection;
import affluex.school.solutions.Model.ModelSubject;
import affluex.school.solutions.Model.ResponseAssignmentDashboard;
import affluex.school.solutions.Model.ResponseClass;
import affluex.school.solutions.Model.ResponseHomework;
import affluex.school.solutions.Model.ResponseParentDashboard;
import affluex.school.solutions.Model.ResponseSection;
import affluex.school.solutions.Model.ResponseSubject;
import affluex.school.solutions.Model.StudentDetails;
import affluex.school.solutions.R;
import affluex.school.solutions.Retrofit.ApiServices;
import affluex.school.solutions.Retrofit.ServiceGenerator;
import affluex.school.solutions.common.LoggerUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    affluex.school.solutions.databinding.FragmentAssignmentBinding binding;
    ArrayList<ModelClass> classArrayList;
    ArrayList<ModelSection> sectionArrayList;
    ArrayList<ModelSubject> subjectArrayList;
    private final Calendar myCalendar = Calendar.getInstance();
    String selectedClassId="", selectedSectionId="", selectedSubjectId="",
            selectedClassName="", selectedSectionName="", selectedSubjectName="", techerId;

    Uri imgUri = null;
    String fromDate;
    String fileName = "";
    private ArrayList<StudentDetails> studentDetailsArrayList;

    private ArrayList<ModelAssignment> assignmentArrayList;

    public AssignmentFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = affluex.school.solutions.databinding.FragmentAssignmentBinding.inflate(inflater, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        getClassList();
        binding.spinnerAddSection.setVisibility(View.GONE);
        binding.spinnerAddSubject.setVisibility(View.GONE);
        binding.llAssignemnt.setVisibility(View.GONE);
        binding.llUpload.setVisibility(View.GONE);
        binding.llView.setVisibility(View.VISIBLE);
        binding.llAdd.setVisibility(View.GONE);
        binding.btnSubmit.setVisibility(View.GONE);
        studentDetailsArrayList=new ArrayList<>();

        binding.spinnerSection.setVisibility(View.GONE);
        binding.spinnerSubject.setVisibility(View.GONE);

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (sharedPreferences.getString("userType", "").equals("Parent")) {
                    binding.cardAdd.setVisibility(View.GONE);
                    binding.spinnerClass.setVisibility(View.GONE);
                    binding.spinnerSection.setVisibility(View.GONE);
                    binding.llTeacherSearch.setVisibility(View.GONE);
                    binding.llParentsSearch.setVisibility(View.VISIBLE);
                    callDashBoardApi();
                    getAssignmentListParents();
                    binding.swipeRefresh.setRefreshing(false);


                } else {
                    binding.cardAdd.setVisibility(View.VISIBLE);

                    binding.spinnerClass.setVisibility(View.VISIBLE);
                    binding.spinnerSection.setVisibility(View.VISIBLE);
                    binding.llTeacherSearch.setVisibility(View.VISIBLE);
                    binding.llParentsSearch.setVisibility(View.GONE);
                    binding.txtTo.getText().clear();
                    binding.txtFrom.getText().clear();
                    binding.txtFrom.setHint("From Date");
                    binding.txtTo.setHint("To Date");
                    binding.spinnerClass.setSelection(0);
                    binding.spinnerSection.setSelection(0);
                    binding.spinnerSubject.setSelection(0);
                    getAssignmentList("","","","","");
                    binding.swipeRefresh.setRefreshing(false);
                }
            }
        });

        if (sharedPreferences.getString("userType", "").equals("Parent")) {
            binding.cardAdd.setVisibility(View.GONE);
            binding.spinnerClass.setVisibility(View.GONE);
            binding.spinnerSection.setVisibility(View.GONE);
            binding.llTeacherSearch.setVisibility(View.GONE);
            binding.llParentsSearch.setVisibility(View.VISIBLE);
            callDashBoardApi();
            getAssignmentListParents();


        } else {
            binding.cardAdd.setVisibility(View.VISIBLE);
            binding.spinnerClass.setVisibility(View.VISIBLE);
            binding.spinnerSection.setVisibility(View.VISIBLE);
            binding.llTeacherSearch.setVisibility(View.VISIBLE);
            binding.llParentsSearch.setVisibility(View.GONE);
            getAssignmentList("","","","","");
        }
        binding.cardAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llView.setVisibility(View.GONE);
                binding.llAdd.setVisibility(View.VISIBLE);

            }
        });
        binding.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llView.setVisibility(View.VISIBLE);
                binding.llAdd.setVisibility(View.GONE);
            }
        });
        binding.spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    binding.spinnerSection.setVisibility(View.VISIBLE);
                    selectedClassId = classArrayList.get(i).getFk_ClassID();
                    selectedClassName = classArrayList.get(i).getClassName();
                    getSectionList();

                    if (sharedPreferences.getString("userType", "").equals("Parent")) {

                    }else{
                        getAssignmentList("","",selectedClassId,"","");
                    }

                }else{
                    binding.spinnerSection.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerAddClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    binding.spinnerAddSection.setVisibility(View.VISIBLE);
                    selectedClassId = classArrayList.get(i).getFk_ClassID();
                    selectedClassName = classArrayList.get(i).getClassName();
                    getSectionList();
                }else{
                    binding.spinnerSection.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spinnerSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    binding.spinnerSubject.setVisibility(View.VISIBLE);
                    selectedSectionId = sectionArrayList.get(i).getPK_SectionId();
                    selectedSectionName = sectionArrayList.get(i).getSectionName();
                    getSubjectList();
                    if (sharedPreferences.getString("userType", "").equals("Parent")) {

                    }else{
                        getAssignmentList("","",selectedClassId,selectedSectionId,"");
                    }
                }else {
                    binding.spinnerSubject.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerAddSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    binding.spinnerAddSubject.setVisibility(View.VISIBLE);
                    selectedSectionId = sectionArrayList.get(i).getPK_SectionId();
                    selectedSectionName = sectionArrayList.get(i).getSectionName();
                    getSubjectList();
                }else {
                    binding.spinnerSubject.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {

                    selectedSubjectId = subjectArrayList.get(i).getFk_SubjectID();
                    selectedSubjectName = subjectArrayList.get(i).getSubjectName();
                    if (sharedPreferences.getString("userType", "").equals("Parent")) {

                    }else{
                        getAssignmentList("","",selectedClassId,selectedSectionId,selectedSubjectId);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spinnerAddSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    binding.llAssignemnt.setVisibility(View.VISIBLE);
                    binding.llUpload.setVisibility(View.VISIBLE);
                    binding.btnSubmit.setVisibility(View.VISIBLE);
                    selectedSubjectId = subjectArrayList.get(i).getFk_SubjectID();
                    selectedSubjectName = subjectArrayList.get(i).getSubjectName();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        binding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Assignment details missing.", Toast.LENGTH_SHORT).show();
                permissionCheck();

            }
        });

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter("homework"));
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
                callAddAssignmentApi();
                Toast.makeText(getActivity(), "select fille", Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(binding.etAssignment.getText().toString()) && imgUri == null) {
                    Toast.makeText(getActivity(), "Assignment details missing.", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(binding.etDate.getText().toString())){
                    Toast.makeText(getActivity(), "Select Assignment Date", Toast.LENGTH_SHORT).show();
                } else {
                   callAddAssignmentApi();
                }
            }
        });
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            fromDate = sdf.format(myCalendar.getTime());
            Log.e("Date", "From:" + fromDate);
            updateLabel(binding.etDate);
        };
        DatePickerDialog.OnDateSetListener dateFrom = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            fromDate = sdf.format(myCalendar.getTime());
            Log.e("Date", "From:" + fromDate);
            updateLabel(binding.txtFrom);
        };
        DatePickerDialog.OnDateSetListener dateTo = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            fromDate = sdf.format(myCalendar.getTime());
            Log.e("Date", "From:" + fromDate);
            updateLabel(binding.txtTo);
        };
        binding.etDate.setOnClickListener((View v) -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + 86400000);
            datePickerDialog.show();
            String myFormat = "dd/MM/yyyy";
        });
        binding.txtFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateFrom, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                String myFormat = "dd/MM/yyyy";
            }
        });
        binding.txtTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateTo, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                String myFormat = "dd/MM/yyyy";
            }
        });
        binding.icSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAssignmentList(binding.txtFrom.getText().toString(),
                        binding.txtTo.getText().toString(),selectedClassId,selectedSectionId,selectedSubjectId);
            }
        });
        return binding.getRoot();

    }

    private void getAssignmentListParents() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkParentId=sharedPreferences.getString("Pk_ParentID","");

        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkParentId)) {
            JsonObject object = new JsonObject();
            object.addProperty("Fk_ParentID", Integer.parseInt(pkParentId));
            Call<ResponseAssignmentDashboard> call=apiServices.GetAssignmentParent(object);
            call.enqueue(new Callback<ResponseAssignmentDashboard>() {
                @Override
                public void onResponse(Call<ResponseAssignmentDashboard> call, Response<ResponseAssignmentDashboard> response) {
                    if(response.isSuccessful() && response.body()!=null){
                        if(response.body().getModelParentsAssignments().get(0).getHomeWorkDetailsList()!=null){
                            if(response.body().getModelParentsAssignments().get(0).getHomeWorkDetailsList().size()>0){
                                AdapterParentsAssignment adapterParentsAssignment=new AdapterParentsAssignment((Context) getActivity(), (ArrayList<HomeWorkDetails>) response.body().getModelParentsAssignments().get(0).getHomeWorkDetailsList());
                                binding.rvAssignment.setAdapter(adapterParentsAssignment);
                            }
                        }

                    }
                }
                @Override
                public void onFailure(Call<ResponseAssignmentDashboard> call, Throwable t) {

                }
            });
        }
    }

    private void updateLabel(EditText fromdateEt) {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        fromdateEt.setText(sdf.format(myCalendar.getTime()));
    }
    private void callAddAssignmentApi() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        techerId = sharedPreferences.getString("pkTeacherId", "");
        String fkClassId = selectedClassId;
        String fkSectionId = selectedSectionId;
        String fkSubjectId = selectedSubjectId;

        if (!TextUtils.isEmpty(techerId) && !TextUtils.isEmpty(selectedSectionId) && !TextUtils.isEmpty(selectedClassId)) {
            SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String imageDataString = preferences.getString("imageData", "");
            byte[] imageData = Base64.decode(imageDataString, Base64.DEFAULT);
            File imageFile = saveByteArrayToFile(imageData);
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart("AddedBy", techerId);
            builder.addFormDataPart("Fk_ClassID", fkClassId);
            builder.addFormDataPart("HomeWorkHTML", binding.etAssignment.getText().toString());
            builder.addFormDataPart("Fk_SectionID", fkSectionId);
            builder.addFormDataPart("SubjectID", fkSubjectId);
            builder.addFormDataPart("HomeworkBy", "Teacher");
            builder.addFormDataPart("HomeworkDate", binding.etDate.getText().toString());
            builder.addFormDataPart("StudentFiles", imageFile.getName(),
                    RequestBody.create(MediaType.parse("image/*"), imageFile));
            RequestBody requestBody = builder.build();

            Call<String> call = apiServices.SaveAsignment(requestBody);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getActivity(), "Assignment Added Successfully", Toast.LENGTH_SHORT).show();
                        getAssignmentList("", "", "", "", "");
                        binding.llAdd.setVisibility(View.GONE);
                        binding.llView.setVisibility(View.VISIBLE);
                        binding.spinnerAddSection.setVisibility(View.GONE);
                        binding.spinnerAddSubject.setVisibility(View.GONE);
                        binding.llAssignemnt.setVisibility(View.GONE);
                        binding.llUpload.setVisibility(View.GONE);
                        binding.spinnerClass.setSelection(0);
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getActivity(), "Failed to add assignment", Toast.LENGTH_SHORT).show();
                    Log.e("AddAssignmentFailure", t.getMessage());
                }
            });
        }
    }

    private File saveByteArrayToFile(byte[] byteArray) {
        File file = null;
        FileOutputStream fos = null;
        try {
            file = new File(getActivity().getCacheDir(), "image.jpg");
            fos = new FileOutputStream(file);
            fos.write(byteArray);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    private void getAssignmentList(String from,String to,String classId,String section,String subject) {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("pkTeacherId","");
        Log.e("JHFJHJJH",""+pkteacherId);
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkteacherId)) {
            JsonObject object = new JsonObject();
            object.addProperty("FK_TeacherID", Integer.parseInt(pkteacherId));
            object.addProperty("Fk_ClassId", classId);
            object.addProperty("Fk_SectionID", section);
            object.addProperty("SubjectID", subject);
            object.addProperty("FromDate", from);
            object.addProperty("ToDate", to);
            Log.e("Parameter","1:::"+classId);
            Log.e("Parameter","2:::"+section);
            Log.e("Parameter","3:::"+subject);
            Call<ResponseHomework> call=apiServices.GetHomeworkList(object);
            call.enqueue(new Callback<ResponseHomework>() {
                @Override
                public void onResponse(Call<ResponseHomework> call, Response<ResponseHomework> response) {

                    if (response.body().getAssignmentList()==null) {
                        Toast.makeText(getActivity(), "Unable to fetch data", Toast.LENGTH_SHORT).show();
                    }else{
                        if(response.body().getAssignmentList().size()>0){
                            assignmentArrayList=new ArrayList(response.body().getAssignmentList());
                            AdapterAssignment adapterAssignment=new AdapterAssignment(getActivity(),assignmentArrayList);
                            binding.rvAssignment.setAdapter(adapterAssignment);

                        }else{
                            binding.rvAssignment.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseHomework> call, Throwable t) {

                    Log.e("JHFJHJJH",""+t.getMessage());
                }
            });
        }
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose Image"), 100);
    }

    private void permissionCheck() {
        boolean result = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        if (result) {
            chooseImage();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 102);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 102) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooseImage();
            } else {
                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
@Override
public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
        Uri selectedImageUri = data.getData();
        byte[] imageData = getImageDataFromUri(selectedImageUri);
        saveImageData(imageData);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        binding.txtUpload.setImageBitmap(bitmap);
    }
}
    private byte[] getImageDataFromUri(Uri uri) {
        try {
            InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
            inputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveImageData(byte[] imageData) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("imageData", Base64.encodeToString(imageData, Base64.DEFAULT));
        editor.apply();
    }


    private void getSubjectList() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("pkTeacherId","");/*ye data to share preferance se get kr rhe ho usme phle se save data nhi hai esliye null aa rha hai pahle data ko save krna pdega vo api integrate kijiye phe ya check kijiye phle se ho to kaha p r hai*/
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkteacherId)) {
            JsonObject object = new JsonObject();
            object.addProperty("TeacherID", Integer.parseInt(pkteacherId));
            object.addProperty("Fk_ClassID", Integer.parseInt(selectedClassId));
            object.addProperty("Fk_SectionID", Integer.parseInt(selectedSectionId));
            LoggerUtil.logItem(object);
            Call<ResponseSubject> call=apiServices.GetSubjectList(object);
            call.enqueue(new Callback<ResponseSubject>() {
                @Override
                public void onResponse(Call<ResponseSubject> call, Response<ResponseSubject> response) {
                    subjectArrayList=new ArrayList<>(response.body().getListSection());
                    subjectArrayList.add(0,new ModelSubject("Select Subject"));
                    SpinnerSubjectAdapter spinnerSubjectAdapter=new SpinnerSubjectAdapter();
                    binding.spinnerAddSubject.setAdapter(spinnerSubjectAdapter);
                    binding.spinnerSubject.setAdapter(spinnerSubjectAdapter);
                }

                @Override
                public void onFailure(Call<ResponseSubject> call, Throwable t) {

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
            Log.e("selectedClassId",selectedClassId);
            LoggerUtil.logItem(object);
            Call<ResponseSection> call=apiServices.GetSectionList(object);
            call.enqueue(new Callback<ResponseSection>() {
                @Override
                public void onResponse(Call<ResponseSection> call, Response<ResponseSection> response) {
                    sectionArrayList=new ArrayList<>(response.body().getSectionArrayList());
                    sectionArrayList.add(0,new ModelSection("Select Section"));
                    SpinnerSectionAdapter spinnerSectionAdapter=new SpinnerSectionAdapter();
                    binding.spinnerAddSection.setAdapter(spinnerSectionAdapter);
                    binding.spinnerSection.setAdapter(spinnerSectionAdapter);
                }

                @Override
                public void onFailure(Call<ResponseSection> call, Throwable t) {

                }
            });
        }
    }
//////
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
                    binding.spinnerClass.setAdapter(spinnerClassAdapter);
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
    class SpinnerSubjectAdapter extends android.widget.BaseAdapter {

        @Override
        public int getCount() {
            return subjectArrayList.size();
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
            spinner_text.setText(subjectArrayList.get(position).getSubjectName());


            return row;
        }
    }



    class SpinnerStudentAdapter extends android.widget.BaseAdapter {

        @Override
        public int getCount() {
            return studentDetailsArrayList.size();
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
            spinner_text.setText(studentDetailsArrayList.get(position).getStudentName());


            return row;
        }
    }
    public class Post extends AsyncTask<Object,Object,Object>{



        @Override
        protected Object doInBackground(Object... objects) {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("Fk_ClassID","7")
                    .addFormDataPart("Fk_SectionID","34")
                    .addFormDataPart("SubjectID","17")
                    .addFormDataPart("HomeworkDate","16/09/2023")
                    .addFormDataPart("AddedBy","18")
                    .addFormDataPart("HomeworkBy","Teacher")
                    .addFormDataPart("HomeWorkHTML","")
                    .addFormDataPart("StudentFiles",imgUri.getPath(),
                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                    new File(imgUri.getPath())))
                    .build();
            Request request = new Request.Builder()
                    .url("http://ldemo1.afluex.com///MasterForApi/AttendanceReportBy")
                    .method("POST", body)
                    .addHeader("SponsorId", "brainstew")
                    .addHeader("MobileNo", "8787000491")
                    .addHeader("Email", "kunjan@gmail.com")
                    .addHeader("Gender", "male")
                    .addHeader("FirstName", "Kunjan")
                    .addHeader("LastName", "Sharma")
                    .addHeader("RegistrationBy", "1")
                    .addHeader("PinCode", "274407")
                    .build();
            try {
                okhttp3.Response response = client.newCall(request).execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
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
                            studentDetailsArrayList.clear();
                            studentDetailsArrayList=new ArrayList<>(response.body().getDashBoardList().get(0).getStudentDetails());
                           
                            SpinnerStudentAdapter spinnerStudentAdapter=new SpinnerStudentAdapter();
                            binding.spinnerStudent.setAdapter(spinnerStudentAdapter);

                        }

                    }
                }


                @Override
                public void onFailure(Call<ResponseParentDashboard> call, Throwable t) {

                }
            });
        }
    }


    public BroadcastReceiver mMessageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String salaryId=intent.getStringExtra("homework_id");

            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
            builder.setCancelable(false);
            builder.setTitle("Are you Sure you want to Delete this Homework?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           callDeleteHomeworkApi(salaryId);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
            builder.show();



        }
    };

    private void callDeleteHomeworkApi(String salaryId) {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("pkTeacherId","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkteacherId)) {
            JsonObject object = new JsonObject();
            object.addProperty("Pk_TeacherID", Integer.parseInt(pkteacherId));
            object.addProperty("Pk_HomeworkID", salaryId);

            Log.e("Pk_HomeworkID",salaryId);
            LoggerUtil.logItem(object);
            Call<CommonResponse> call = apiServices.DeleteHomeWork(object);
            call.enqueue(new Callback<CommonResponse>() {
                @Override
                public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getActivity(), "Homework Deletion successful.", Toast.LENGTH_SHORT).show();

                        getAssignmentList("","","","","");
                    }else{
                        Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CommonResponse> call, Throwable t) {

                }
            });

        }

    }


}
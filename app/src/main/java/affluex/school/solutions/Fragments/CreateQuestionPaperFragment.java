package affluex.school.solutions.Fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import affluex.school.solutions.Activity.DashboardSchool;
import affluex.school.solutions.ExamModel.Exam.ExamDetail;
import affluex.school.solutions.ExamModel.Exam.ExamResponse;
import affluex.school.solutions.ExamModel.Exam.Lstexamdetail;
import affluex.school.solutions.Model.ModelClass;
import affluex.school.solutions.Model.ModelSection;
import affluex.school.solutions.Model.ModelSubject;
import affluex.school.solutions.Model.ResponseClass;
import affluex.school.solutions.Model.ResponseSection;
import affluex.school.solutions.Model.ResponseSubject;
import affluex.school.solutions.Network.RetrofitInstance;
import affluex.school.solutions.R;
import affluex.school.solutions.Retrofit.ApiServices;
import affluex.school.solutions.Retrofit.ServiceGenerator;
import affluex.school.solutions.common.LoggerUtil;
import affluex.school.solutions.databinding.FragmentCreateQuestionPaperBinding;
import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class CreateQuestionPaperFragment extends Fragment {
    FragmentCreateQuestionPaperBinding binding;
    private ArrayList<String> examination;
    private ArrayList<String> Class;
    private ArrayList<String> section;
    private ArrayList<String> subject;
    ArrayList<ModelClass> classArrayList;
    ArrayList<ModelSection> sectionArrayList;
    ArrayList<ModelSubject> subjectArrayList;
    private ArrayAdapter<String> classAdapter;
    private ArrayList<String> questionsType;
    String selectedClassId="", selectedSectionId="", selectedSubjectId="",
            selectedClassName="", selectedSectionName="", selectedSubjectName="", techerId;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateQuestionPaperBinding.inflate(inflater, container, false);

       initview();
       onclicklistener();
        getExamList();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        getClassList();
        examination = new ArrayList<>();
        Class = new ArrayList<>();
        section = new ArrayList<>();
        subject = new ArrayList<>();
        questionsType = new ArrayList<>();
        populateDefaultValues();
        setUpSpinners();

        return binding.getRoot();

    }

    private void onclicklistener() {
        binding.btnAddSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddSectionDialog();
            }
        });
        binding.btnCreateQuestionPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateAndCreateQuestionPaper();
            }
        });
        // examination

        binding.spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    binding.spinnerSection.setVisibility(View.VISIBLE);
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
                }else {
                    binding.spinnerSubject.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        binding.spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >0) {
                    binding.spinnerSubject.setVisibility(View.VISIBLE);
                    selectedSectionId = subjectArrayList.get(i).getFk_SectionID();
                    selectedSubjectName = subjectArrayList.get(i).getSubjectName();
                }else {
                    binding.spinnerSubject.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void initview() {


    }


    private void populateDefaultValues() {
        examination.add("Select Exam");
        examination.add("Monthly Exam");
        examination.add("Unit Test");
        examination.add("Half Yearly Exam");
        examination.add("Annual Exam");
        examination.add("Others");

        Class.add("Select class");
        Class.add("Class 3");
        Class.add("Class 5");
        Class.add("Class 6");

        section.add("Select Section");
        section.add("A");
        section.add("B");
        section.add("C");

        subject.add("Select Subject");
        subject.add("Maths");
        subject.add("Science");
        subject.add("English");
        subject.add("Hindi");

        questionsType.add("Select Question Type");
        questionsType.add("MCQ");
        questionsType.add("One Word Answer");
        questionsType.add("True/False");
        questionsType.add("Short Answer Type");
        questionsType.add("Very Short Answer Type Question");
        questionsType.add("Long Answer Type");
        questionsType.add("Essay/Letter");
        questionsType.add("Diagram/Map");
    }

    private void setUpSpinners() {
        setUpSpinner(binding.spinnerExamination, examination);
        setUpSpinner(binding.spinnerClass, Class);
        setUpSpinner(binding.spinnerSection, section);
    }

    private void setUpSpinner(Spinner spinner, List<String> items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }
    private void showAddSectionDialog() {
        final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getActivity());
        View mView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_add_section, null);
        alert.setView(mView);

        EditText et_section_heading = mView.findViewById(R.id.et_section_heading);
        EditText et_section_marks = mView.findViewById(R.id.et_section_marks);
        Spinner spinner_questions_type = mView.findViewById(R.id.spinner_questions_type);

        ArrayAdapter<String> questionsTypeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, questionsType);
        questionsTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_questions_type.setAdapter(questionsTypeAdapter);

        final android.app.AlertDialog alertDialog = alert.create();

        Button btn_create_section = mView.findViewById(R.id.btn_create_section);
        btn_create_section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AddQuestionFragment();
                Bundle args = new Bundle();
                args.putString("Type", questionsType.get(spinner_questions_type.getSelectedItemPosition()));
                args.putInt("Position", spinner_questions_type.getSelectedItemPosition());
                fragment.setArguments(args);
                ((DashboardSchool) getActivity()).switchFragmentOnDashBoard(fragment, "Add Question");
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
    private void validateAndCreateQuestionPaper() {
        if (binding.spinnerExamination.getSelectedItemPosition() == 0) {
            Toast.makeText(getActivity(), "Select Examination", Toast.LENGTH_SHORT).show();
//        } else if (binding.spinnerClass.getSelectedItemPosition() == 0) {
//            Toast.makeText(getActivity(), "Select Class", Toast.LENGTH_SHORT).show();
//        } else if (binding.spinnerSection.getSelectedItemPosition() == 0) {
//            Toast.makeText(getActivity(), "Select Section", Toast.LENGTH_SHORT).show();
//        } else if (binding.spinnerSubject.getSelectedItemPosition() == 0) {
//            Toast.makeText(getActivity(), "Select Subject", Toast.LENGTH_SHORT).show();
//        } else if (TextUtils.isEmpty(binding.etMaxMarks.getText().toString())) {
            Toast.makeText(getActivity(), "Enter Maximum Marks", Toast.LENGTH_SHORT).show();
        } else if ((TextUtils.isEmpty(binding.etMaxHrs.getText().toString()) && TextUtils.isEmpty(binding.etMaxMinutes.getText().toString())) ||
                ("0".equals(binding.etMaxHrs.getText().toString()) && "0".equals(binding.etMaxMinutes.getText().toString()))) {
            Toast.makeText(getActivity(), "Enter the time allotted", Toast.LENGTH_SHORT).show();
        } else {
            createDatabase();
        }
    }


    private void createDatabase() {
        String timestamp = "" + System.currentTimeMillis();
        EasyDB easyDB = EasyDB.init(getActivity().getApplicationContext(), "Examination")
                .setTableName("QuestionPaper")
                .addColumn(new Column("Timestamp", new String[]{"text", "not null"}))
                .addColumn(new Column("Examination", new String[]{"text", "not null"}))
                .addColumn(new Column("Class", new String[]{"text", "not null"}))
                .addColumn(new Column("Section", new String[]{"text", "not null"}))
                .addColumn(new Column("Subject", new String[]{"text", "not null"}))
                .addColumn(new Column("MaxMarks", new String[]{"text", "not null"}))
                .addColumn(new Column("MaxHrs", new String[]{"text", "not null"}))
                .addColumn(new Column("MaxMins", new String[]{"text", "not null"}))
                .doneTableColumn();

        try {
            String maxHrs = TextUtils.isEmpty(binding.etMaxHrs.getText().toString()) ? "0" : binding.etMaxHrs.getText().toString();
            String maxMins = TextUtils.isEmpty(binding.etMaxMinutes.getText().toString()) ? "0" : binding.etMaxMinutes.getText().toString();

            easyDB.addData("Timestamp", timestamp)
                    .addData("Examination", examination.get(binding.spinnerExamination.getSelectedItemPosition()))
                  //  .addData("Class", Class.get(binding.spinnerClass.getSelectedItemPosition()))
                    //.addData("Section", section.get(binding.spinnerSection.getSelectedItemPosition()))
                    .addData("Subject", subject.get(binding.spinnerSubject.getSelectedItemPosition()))
                    .addData("MaxMarks", binding.etMaxMarks.getText().toString())
                    .addData("MaxHrs", maxHrs)
                    .addData("MaxMins", maxMins)
                    .doneDataAdding();

            binding.llDetails.setVisibility(View.GONE);
            binding.llQuestion.setVisibility(View.VISIBLE);
        } catch (SQLiteConstraintException e) {
            Log.e("Exception", e.getMessage());
        }
    }


    private void getSubjectList() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("pkTeacherId","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String Fk_SectionID=sharedPreferences.getString("fkSectionId","");
        if(!TextUtils.isEmpty(pkteacherId)) {
            JsonObject object = new JsonObject();
            object.addProperty("TeacherID", Integer.parseInt(pkteacherId));
            object.addProperty("Fk_ClassID",6);
            object.addProperty("Fk_SectionID", 31);
            LoggerUtil.logItem(object);
            Call<ResponseSubject> call=apiServices.GetSubjectList(object);
            call.enqueue(new Callback<ResponseSubject>() {
                @Override
                public void onResponse(Call<ResponseSubject> call, Response<ResponseSubject> response) {
                    subjectArrayList=new ArrayList<>(response.body().getListSection());
                    subjectArrayList.add(0,new ModelSubject("Select Subject"));
                    SpinnerSubjectAdapter spinnerSubjectAdapter=new SpinnerSubjectAdapter();
                    binding.spinnerSubject.setAdapter(spinnerSubjectAdapter);
                    binding.spinnerSubject.setAdapter(spinnerSubjectAdapter);
                }
                @Override
                public void onFailure(Call<ResponseSubject> call, Throwable t) {

                }
            });
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
                    binding.spinnerSection.setAdapter(spinnerSectionAdapter);
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
                    binding.spinnerClass.setAdapter(spinnerClassAdapter);

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


    private void getExamList(){
        ApiServices apiService = RetrofitInstance.getClient().create(ApiServices.class);

        Call<ExamResponse> call = apiService.getExamDetails();
        call.enqueue(new Callback<ExamResponse>() {
            @Override
            public void onResponse(Call<ExamResponse> call, Response<ExamResponse> response) {
                if (response.isSuccessful()) {
                    ExamResponse apiResponse = response.body();
                    if (apiResponse != null) {
                        List<String> examNames = new ArrayList<>();
                        examNames.add("Select Exam");
                        List<Lstexamdetail> examDetailsLists = apiResponse.getLstexamdetails();

                        for (Lstexamdetail examDetailsList : examDetailsLists) {
                            for (ExamDetail examDetails : examDetailsList.getExamDetails()) {
                                examNames.add(examDetails.getExamTypeName());
                            }
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, examNames);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.spinnerExamination.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ExamResponse> call, Throwable t) {
                // Handle error
                t.printStackTrace();
            }
        });
    }

    }





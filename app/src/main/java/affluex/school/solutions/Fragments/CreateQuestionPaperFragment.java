package affluex.school.solutions.Fragments;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import affluex.school.solutions.Activity.DashboardSchool;
import affluex.school.solutions.R;
import affluex.school.solutions.databinding.FragmentCreateQuestionPaperBinding;
import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class CreateQuestionPaperFragment extends Fragment {

    FragmentCreateQuestionPaperBinding binding;

    private ArrayList<String> examination;
    private ArrayList<String> Class;
    private ArrayList<String> section;
    private ArrayList<String> subject;
    private ArrayList<String> questionsType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentCreateQuestionPaperBinding.inflate(inflater, container, false);
        examination=new ArrayList<>();
        Class=new ArrayList<>();
        section=new ArrayList<>();
        subject=new ArrayList<>();
        questionsType=new ArrayList<>();

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

        ArrayAdapter<String> examinationArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,
                        examination); //selected item will look like a spinner set from XML
        examinationArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);

        binding.spinnerExamination.setAdapter(examinationArrayAdapter);
        ArrayAdapter<String> classArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,
                        Class); //selected item will look like a spinner set from XML
        classArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);

        binding.spinnerClass.setAdapter(classArrayAdapter);
         ArrayAdapter<String> sectionArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,
                        section); //selected item will look like a spinner set from XML
        sectionArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);

        binding.spinnerSection.setAdapter(sectionArrayAdapter);
         ArrayAdapter<String> subjectArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,
                        subject); //selected item will look like a spinner set from XML
        subjectArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);

        binding.spinnerSubject.setAdapter(subjectArrayAdapter);
        binding.btnAddSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddSectionDialog();
            }
        });

        binding.btnCreateQuestionPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.spinnerExamination.getSelectedItemPosition()==0){
                    Toast.makeText(getActivity(), "Select Examination", Toast.LENGTH_SHORT).show();
                }else if(binding.spinnerClass.getSelectedItemPosition()==0){
                    Toast.makeText(getActivity(), "Select Class", Toast.LENGTH_SHORT).show();
                }else if(binding.spinnerSection.getSelectedItemPosition()==0){
                    Toast.makeText(getActivity(), "Select Section", Toast.LENGTH_SHORT).show();
                }else if(binding.spinnerSubject.getSelectedItemPosition()==0){
                    Toast.makeText(getActivity(), "Select Subject", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(binding.etMaxMarks.getText().toString())){
                    Toast.makeText(getActivity(), "Enter Maximum Marks", Toast.LENGTH_SHORT).show();
                }else if((TextUtils.isEmpty(binding.etMaxHrs.getText().toString()) && TextUtils.isEmpty(binding.etMaxMinutes.getText().toString()))||
                        (binding.etMaxHrs.equals("0") && binding.etMaxMinutes.equals("0") ) ){
                    Toast.makeText(getActivity(), "Enter the time alloted ", Toast.LENGTH_SHORT).show();
                }else{

                    createDatabase();


                }
            }
        });


        return binding.getRoot();
    }

    private void showAddSectionDialog() {
        final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getActivity());
        View mView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_add_section, null);
        alert.setView(mView);
        EditText et_section_heading=mView.findViewById(R.id.et_section_heading);
        EditText et_section_marks=mView.findViewById(R.id.et_section_marks);
        Spinner spinner_questions_type=mView.findViewById(R.id.spinner_questions_type);




        ArrayAdapter<String> questionsTypeAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,
                        questionsType); //selected item will look like a spinner set from XML
        questionsTypeAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);

        spinner_questions_type.setAdapter(questionsTypeAdapter);
        final android.app.AlertDialog alertDialog = alert.create();




        Button btn_create_section=mView.findViewById(R.id.btn_create_section);
        btn_create_section.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new AddQuestionFragment();
                Bundle args = new Bundle();
                args.putString("Type",questionsType.get(spinner_questions_type.getSelectedItemPosition()));
                args.putInt("Position",spinner_questions_type.getSelectedItemPosition());
                fragment.setArguments(args);
                ((DashboardSchool)getActivity()).switchFragmentOnDashBoard(fragment,"Add Question");
                alertDialog.dismiss();

            }
        });


        alertDialog.show();

    }

    private void createDatabase() {
        String timestam=""+System.currentTimeMillis();
        EasyDB easyDB= EasyDB.init(getActivity().getApplicationContext(),"Examination")
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
            String maxHrs,maxmin;
            if(TextUtils.isEmpty(binding.etMaxHrs.getText().toString())){
                maxHrs="0";
            }else{
                maxHrs=binding.etMaxHrs.getText().toString();
            }
            if(TextUtils.isEmpty(binding.etMaxMinutes.getText().toString())){
                maxmin="0";
            }else{
                maxmin=binding.etMaxMinutes.getText().toString();
            }
            easyDB.addData("Timestamp",timestam)
                    .addData("Examination" , examination.get(binding.spinnerExamination.getSelectedItemPosition()))
                    .addData("Class" , Class.get(binding.spinnerClass.getSelectedItemPosition()))
                    .addData("Section" , section.get(binding.spinnerSection.getSelectedItemPosition()))
                    .addData("Subject" , subject.get(binding.spinnerSubject.getSelectedItemPosition()))
                    .addData("MaxMarks" , binding.etMaxMarks.getText().toString())
                    .addData("MaxHrs" , maxHrs)
                    .addData("MaxMins" , maxmin)
                    .doneDataAdding();
           binding.llDetails.setVisibility(View.GONE);
           binding.llQuestion.setVisibility(View.VISIBLE);
//            deleteFromList(adapterPosition,holder);
        }catch (SQLiteConstraintException e){
            Log.e("Exception",e.getMessage());
        }
    }
}
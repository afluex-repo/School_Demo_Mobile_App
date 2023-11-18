package affluex.school.solutions.Fragments;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import affluex.school.solutions.Adapter.AdapterClass;
import affluex.school.solutions.Model.ModelClass;
import affluex.school.solutions.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentClass#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentClass extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CardView ic_add;
    RecyclerView rv_class;
    Spinner spinner_period;
    private ArrayList<ModelClass> classArrayList;
    private ArrayList<String> periodsArrayList;

    public FragmentClass() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentClass.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentClass newInstance(String param1, String param2) {
        FragmentClass fragment = new FragmentClass();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_class, container, false);
        ic_add=view.findViewById(R.id.ic_add);
        rv_class=view.findViewById(R.id.rv_class);


        classArrayList=new ArrayList<ModelClass>();
        periodsArrayList=new ArrayList<>();
        for(int i=1;i<=10;i++){
            periodsArrayList.add(String.valueOf(i));
        }
        periodsArrayList.add(0,"Select No of Periods/day");




        classArrayList.add(new ModelClass("Class 1",8,"Ms Anita"));
        classArrayList.add(new ModelClass("Class 2",8,"Ms Shalu"));
        classArrayList.add(new ModelClass("Class 3",8,"Not Assigned"));
        classArrayList.add(new ModelClass("Class 4",8,"Ms Anita"));
        classArrayList.add(new ModelClass("Class 5",8,"Not Assigned"));
        classArrayList.add(new ModelClass("Class 6",8,"Ms Anita"));
        AdapterClass adapterClass=new AdapterClass(getActivity(),classArrayList);
        rv_class.setAdapter(adapterClass);
        ic_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                // dialog.getWindow().getAttributes().windowAnimations = R.style.DailoAnimation;
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));


                dialog.setCancelable(false);
                dialog.setContentView(R.layout.layout_add_class);
                spinner_period=dialog.findViewById(R.id.spinner_period);
               ImageView ic_close=dialog.findViewById(R.id.ic_close);
                SpinnerPeriodsAdapter spinnerPeriodsAdapter=new SpinnerPeriodsAdapter();
                spinner_period.setAdapter(spinnerPeriodsAdapter);
                ic_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();


            }
        });
        return view;
    }
    class SpinnerPeriodsAdapter extends android.widget.BaseAdapter {

        @Override
        public int getCount() {
            return periodsArrayList.size();
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
            spinner_text.setText(periodsArrayList.get(position));


            return row;
        }
    }

}
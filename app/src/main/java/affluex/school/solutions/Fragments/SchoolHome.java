package affluex.school.solutions.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import affluex.school.solutions.Activity.DashboardSchool;
import affluex.school.solutions.R;
import affluex.school.solutions.databinding.FragmentSchoolHomeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SchoolHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SchoolHome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentSchoolHomeBinding binding;
    LinearLayout ll_class;



    public SchoolHome() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static SchoolHome newInstance(String param1, String param2) {
        SchoolHome fragment = new SchoolHome();
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
        binding=FragmentSchoolHomeBinding.inflate(getLayoutInflater());
        View view= inflater.inflate(R.layout.fragment_school_home, container, false);
        ll_class=view.findViewById(R.id.ll_class);

        ll_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentClass fragment= new FragmentClass();
                ((DashboardSchool)getActivity()).switchFragmentOnDashBoard(fragment,"Class");
            }
        });

        return view;
    }
}
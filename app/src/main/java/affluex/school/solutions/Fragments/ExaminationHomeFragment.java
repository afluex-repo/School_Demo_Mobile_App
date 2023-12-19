package affluex.school.solutions.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import affluex.school.solutions.Activity.DashboardSchool;
import affluex.school.solutions.R;
import affluex.school.solutions.databinding.FragmentExaminationHomeBinding;
import affluex.school.solutions.databinding.FragmentTeacherHomeBinding;


public class ExaminationHomeFragment extends Fragment {
    FragmentExaminationHomeBinding binding;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentExaminationHomeBinding.inflate(inflater,container,false);
        binding.cardCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new CreateQuestionPaperFragment();
                ((DashboardSchool)getActivity()).switchFragmentOnDashBoard(fragment,"Create Question Paper");
            }
        });


        return binding.getRoot();
    }
}
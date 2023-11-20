package affluex.school.solutions.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import affluex.school.solutions.R;
import affluex.school.solutions.databinding.FragmentAttendanceListBinding;


public class AttendanceListFragment extends Fragment {
    FragmentAttendanceListBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=affluex.school.solutions.databinding.FragmentAttendanceListBinding.inflate(inflater,container,false);








        return binding.getRoot();
    }
}
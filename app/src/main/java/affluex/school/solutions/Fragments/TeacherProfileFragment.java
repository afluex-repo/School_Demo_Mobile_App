package affluex.school.solutions.Fragments;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import affluex.school.solutions.databinding.FragmentTeacherProfileBinding;
public class TeacherProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    FragmentTeacherProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentTeacherProfileBinding.inflate(getLayoutInflater(),container,false);

        return binding.getRoot();
    }
}
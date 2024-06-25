package affluex.school.solutions.Fragments;
import static android.app.Activity.RESULT_OK;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import affluex.school.solutions.Adapter.AdapterOptions;
import affluex.school.solutions.Model.ModelOptions;
import affluex.school.solutions.databinding.FragmentAddQuestionBinding;


public class AddQuestionFragment extends Fragment {
    FragmentAddQuestionBinding binding;
    String questionType;
    Integer position;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    ArrayList<String> result;
    private final int REQUEST_IMAGE = 400;
    Bitmap bp=null;
    private ArrayList<ModelOptions> optionsArray;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentAddQuestionBinding.inflate(inflater,container,false);
        result=new ArrayList<>();
        oncliclistener();
        binding.rlImage.setVisibility(View.VISIBLE);
        optionsArray=new ArrayList<>();
        getArgumentData();
        binding.llOptions.setVisibility(View.GONE);
        return binding.getRoot();
    }

    private void oncliclistener() {
        binding.ivMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.ENGLISH);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak to type");
                try {

                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                } catch (Exception e) {
                    Toast
                            .makeText(getActivity(), " " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
        binding.btnShowOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llOptions.setVisibility(View.VISIBLE);
            }
        });
        binding.btnAddOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(binding.etOptions.getText().toString())){
                    Toast.makeText(getActivity(), "Options field cannot be blank", Toast.LENGTH_SHORT).show();
                }else{

                    optionsArray.add(new ModelOptions(""+(optionsArray.size()+1),binding.etOptions.getText().toString()));

                    binding.etOptions.setText("");
                    initOptionsRecyclerView();

                }
            }
        });

        binding.ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permissionCheck();
            }
        });

    }

    private void initOptionsRecyclerView() {
        AdapterOptions adapterOptions=new AdapterOptions(getActivity(),optionsArray);
        binding.rvOptions.setAdapter(adapterOptions);
    }

    private void getArgumentData() {
        questionType=getArguments().getString("Type");
        position=getArguments().getInt("Position");

    }

    private void permissionCheck() {
        Dexter.withActivity(getActivity())
                .withPermissions(android.Manifest.permission.CAMERA,
                        android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
//                        Log.e("Denied",""+report.getDeniedPermissionResponses().get(0).getPermissionName());
                        if (report.areAllPermissionsGranted()) {
                            Log.e("Camera123","Permission Granted");
                            launchCameraIntentForTax();
                        }
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Grant Permission");
        builder.setMessage("This App Requires permission");
        builder.setPositiveButton("Goto settings", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        getActivity().startActivityForResult(intent, 101);
    }

    private void launchCameraIntentForTax() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                binding.etQuestion.setText(Objects.requireNonNull(result).get(0));
            }
        } else if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Log.e("CAMERA", "NJNJ");
            Bundle extras = data.getExtras();
            if (extras != null && extras.containsKey("data")) {
                bp = (Bitmap) extras.get("data");
                if (bp != null) {
                    binding.ivImg.setImageBitmap(bp);
                } else {
                    Log.e("CAMERA", "Bitmap is null");
                }
            } else {
                Log.e("CAMERA", "No image data in extras");
            }
        }
    }

}
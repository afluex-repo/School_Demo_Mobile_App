package affluex.school.solutions.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import affluex.school.solutions.Activity.DashboardSchool;
import affluex.school.solutions.Model.CommonResponse;
import affluex.school.solutions.Model.ResponseLeave;
import affluex.school.solutions.Retrofit.ApiServices;
import affluex.school.solutions.Retrofit.ServiceGenerator;
import affluex.school.solutions.common.LoggerUtil;
import affluex.school.solutions.databinding.FragmentTeacherHomeBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherHome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentTeacherHomeBinding binding;
    String lastActivity="",lastActivityDate="";
    String currentDate=null;
    SharedPreferences.Editor editor;
    private static final int Location_Request_code = 100;
    private final int REQUEST_IMAGE = 400;
    private String[] locationPermissions;
    double latitude=0.0,longitude=0.0;
    private LocationManager locationManager;
    FusedLocationProviderClient fusedLocationProviderClient;
    Bitmap bp=null;

    public TeacherHome() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentTeacherHomeBinding.inflate(inflater,container,false);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("TeacherLogin", MODE_PRIVATE);
        editor=sharedPreferences.edit();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
            lastActivity=sharedPreferences.getString("lastActivity","");
            lastActivityDate=sharedPreferences.getString("lastActivityDate","");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            currentDate = new android.icu.text.SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(new Date());
        }
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        Log.e("lastActivity",lastActivity);
        Log.e("lastActivity",lastActivityDate);
            if(lastActivity.equals("")||lastActivityDate.equals("")||!lastActivityDate.equals(currentDate)||lastActivity.equals("out")){
                binding.btnPunchIn.setVisibility(View.VISIBLE);
                binding.btnPunchout.setVisibility(View.GONE);

                binding.llPunch.setVisibility(View.VISIBLE);
                binding.llMain.setVisibility(View.GONE);
            }else{
                binding.btnPunchout.setVisibility(View.VISIBLE);
                binding.btnPunchIn.setVisibility(View.GONE);
                binding.llPunch.setVisibility(View.GONE);
                binding.llMain.setVisibility(View.VISIBLE);
            }

            binding.btnCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//
                    permissionCheck();
                }
            });
        binding.btnPunchout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("lastActivity","out");
                editor.putString("lastActivityDate",currentDate);
                editor.apply();
                editor.commit();
                binding.llMain.setVisibility(View.GONE);
                binding.llPunch.setVisibility(View.VISIBLE);
                binding.btnPunchIn.setVisibility(View.VISIBLE);
            }
        });
        binding.cardNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new TeacherNoticeFragment();
                ((DashboardSchool)getActivity()).switchFragmentOnDashBoard(fragment);

            }
        });
        binding.cardAssignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new AssignmentFragment();
                ((DashboardSchool)getActivity()).switchFragmentOnDashBoard(fragment);
            }
        });

        binding.cardStudentsLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new LeaveFragment();
                ((DashboardSchool)getActivity()).switchFragmentOnDashBoard(fragment);
            }
        });

        binding.btnPunchIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(bp==null){
                    Toast.makeText(getActivity(), "Upload Selfie to Punch in", Toast.LENGTH_SHORT).show();
                }else{
                    detectLocation();

                }

            }
        });


        return binding.getRoot();
    }

    private void saveAttendance() {
        ApiServices apiServices = ServiceGenerator.createService(ApiServices.class);
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("LoginDetails", MODE_PRIVATE);
        String pkteacherId=sharedPreferences.getString("pkTeacherId","");
        String fkClassId=sharedPreferences.getString("fkClassId","");
        String fkSectionId=sharedPreferences.getString("fkSectionId","");
        String currentDate1 = null;
        String currentTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            currentDate1 = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(new Date());
            currentTime = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(new Date());
        }
        if(!TextUtils.isEmpty(pkteacherId)) {
            JsonObject object = new JsonObject();
            object.addProperty("AddedBy", Integer.parseInt(pkteacherId));
            object.addProperty("InTime", currentTime);
            object.addProperty("AttendanceDate", currentDate1);
            object.addProperty("EmployeeID", Integer.parseInt(pkteacherId));
            object.addProperty("UploadFile", "");
            object.addProperty("LatiTude", latitude);
            object.addProperty("LongiTude", longitude);

            Log.e("TeacherId",sharedPreferences.getString("pkTeacherId",""));
            Log.e("TeacherId","2:: "+currentTime);
            Log.e("TeacherId","3:: "+currentDate1);
            Log.e("TeacherId","4:: "+latitude);
            Log.e("TeacherId","5:: "+longitude);
            LoggerUtil.logItem(object);
            Call<CommonResponse> call = apiServices.SaveAttendance(object);
            String finalCurrentTime = currentTime;
            call.enqueue(new Callback<CommonResponse>() {
                @Override
                public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                    if(response.isSuccessful()){

                        if(response.body().getMessage().equals("   Punching Successfully !")){
                            Toast.makeText(getActivity(), "Welcome "+
                                    getActivity().
                                            getSharedPreferences("LoginDetails",MODE_PRIVATE)
                                            .getString("name","")+" Your Punch In Time is "+ finalCurrentTime, Toast.LENGTH_SHORT).show();
                            editor.putString("lastActivity","in");
                            editor.putString("lastActivityDate",currentDate);
                            editor.apply();
                            editor.commit();
                            binding.llMain.setVisibility(View.VISIBLE);
                            binding.llPunch.setVisibility(View.GONE);
                            binding.btnPunchout.setVisibility(View.VISIBLE);
                        }else{
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }


                        Log.e("TeacherId","5:: "+response.body());

                    }
                }

                @Override
                public void onFailure(Call<CommonResponse> call, Throwable t) {

                }
            });
        }
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
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(MediaStore.Images.Media.TITLE, "Temp_Image title");
//        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Temp_ Image Description");
//        image_uri1 = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("CAMERA123",""+resultCode);
        Log.e("CAMERA123",""+(data==null));

        if (requestCode == REQUEST_IMAGE && resultCode == -1 && data != null) {
            Bundle extras = data.getExtras();
            Log.e("CAMERA","NJNJ");
            bp = (Bitmap) data.getExtras().get("data");
            binding.imgAttendance.setImageBitmap(bp);
            binding.txtAttendance.setText("Change Picture");







        }
    }

    @SuppressLint("MissingPermission")
    private void detectLocation() {

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
            return;
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            {
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        try{
                            Location location = task.getResult();
                            if (location != null) {

                                try {
                                    Geocoder geocoder = new Geocoder(getActivity(), Locale.ENGLISH);
                                    List<Address> addresses = geocoder.getFromLocation(
                                            location.getLatitude(), location.getLongitude(), 1


                                    );

                                    latitude = addresses.get(0).getLatitude();
                                    longitude = addresses.get(0).getLongitude();
//                                    Toast.makeText(getActivity(), "Your Lat/Long:::"+latitude+","+longitude, Toast.LENGTH_LONG).show();
                                    Log.e("AVGHCGHJGFHC",""+latitude);
                                    Log.e("AVGHCGHJGFHC",""+longitude);
                                    saveAttendance();






//                                if(latitude>0 && longitude>0){
//                                    fusedLocationProviderClient.removeLocationUpdates((LocationListener) locationManager);
//                                }
//                                    if (siteId > 0) {
//
//                                        String email = userId + "@yopmail.com";
//                                        Log.e("email", "" + email);
//                                        firebaseAuth.signInWithEmailAndPassword(email, userPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                                            @Override
//                                            public void onSuccess(AuthResult authResult) {
//                                                Log.e("GetSiteName", "SignIn");
//
////                                countDownTimer.cancel();
//                                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
//                                                ref.child(hruid).child("Industry").child("Construction").child("Site").child(String.valueOf(siteId)).addListenerForSingleValueEvent(new ValueEventListener() {
//                                                    @Override
//                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                                        String currentDate = null;
//                                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                                                            currentDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(new Date());
//                                                        }
//                                                        if(snapshot.child("date").getValue(String.class)!=null){
//                                                            if(snapshot.child("date").getValue(String.class).equals(currentDate)){
//                                                                getBlockStatus(siteId);
//                                                            }else{
//                                                                HashMap<String,Object> hashMap=new HashMap();
//                                                                hashMap.put("online",false);
//                                                                hashMap.put("skilled",0);
//                                                                hashMap.put("unskilled",0);
//                                                                hashMap.put("SkilledTime","");
//                                                                hashMap.put("UnskilledTime","");
//                                                                hashMap.put("memberOnline",0);
//                                                                hashMap.put("picActivity",false);
//                                                                hashMap.put("picId","");
//                                                                hashMap.put("picTime","");
//                                                                hashMap.put("picDate","");
//                                                                hashMap.put("picLink","");
//                                                                hashMap.put("picRemark","");
//                                                                hashMap.put("picLatitude", 0);
//                                                                hashMap.put("picLongitude", 0);
//                                                                hashMap.put("memberOnline",0);
//                                                                ref.child(String.valueOf(siteId)).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                                            @Override
//                                                                            public void onSuccess(Void unused) {
//                                                                                getBlockStatus(siteId);
//                                                                            }
//                                                                        })
//                                                                        .addOnFailureListener(new OnFailureListener() {
//                                                                            @Override
//                                                                            public void onFailure(@NonNull Exception e) {
//
//                                                                            }
//                                                                        });
//                                                            }
//
//                                                        }else{
//                                                            getBlockStatus(siteId);
//                                                        }
//
//                                                    }
//
//                                                    @Override
//                                                    public void onCancelled(@NonNull DatabaseError error) {
//
//                                                    }
//                                                });
////                                            getBlockStatus(siteId);
//
//                                            }
//                                        }).addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                Log.e("ProgressDialog", "5");
//                                                progressDialog.dismiss();
//                                                Toast.makeText(LoginPic.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//
//                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();

                                }

                            } else {

                                Toast.makeText(getActivity() ,"Unable to detect Your Location", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){


                        }



                    }
                });
            }
        } else {

            Toast.makeText(getActivity(), "Your Location is Turned Off.", Toast.LENGTH_SHORT).show();
            detectLocation();
        }
    }
    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(getActivity(), locationPermissions, Location_Request_code);

    }

}
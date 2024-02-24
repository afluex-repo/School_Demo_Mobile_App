package affluex.school.solutions.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


import affluex.school.solutions.Activity.DashboardSchool;
import affluex.school.solutions.Activity.LoginActivity;
import affluex.school.solutions.BuildConfig;
import affluex.school.solutions.Model.CommonResponse;
import affluex.school.solutions.Model.ResponseLeave;
import affluex.school.solutions.R;
import affluex.school.solutions.Retrofit.ApiServices;
import affluex.school.solutions.Retrofit.RealPathUtil;
import affluex.school.solutions.Retrofit.ServiceGenerator;
import affluex.school.solutions.common.LoggerUtil;
import affluex.school.solutions.common.Utils;
import affluex.school.solutions.databinding.FragmentTeacherHomeBinding;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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
    String fileName = "";
    String attendance="";

    File file;
    Uri fileUri;

    ProgressDialog progressDialog;


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
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
            lastActivity=sharedPreferences.getString("lastActivity","");
            lastActivityDate=sharedPreferences.getString("lastActivityDate","");


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            currentDate = new android.icu.text.SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).format(new Date());
        }

        int adb = Settings.Secure.getInt(getActivity().getContentResolver(),
                Settings.Global.DEVELOPMENT_SETTINGS_ENABLED , 0);

        int timesettings=android.provider.Settings.Global.getInt(getActivity().getContentResolver(), android.provider.Settings.Global.AUTO_TIME, 0);

        Log.e("DeveloperOptions",String.valueOf(adb));
        Log.e("DeveloperOptions",String.valueOf(timesettings));
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        Log.e("lastActivity",lastActivity);
        Log.e("lastActivity",lastActivityDate);
            if(lastActivity.equals("")||lastActivityDate.equals("")||!lastActivityDate.equals(currentDate)||lastActivity.equals("out")){
                binding.btnPunchIn.setVisibility(View.VISIBLE);
                binding.btnPunchout.setVisibility(View.GONE);

                ((DashboardSchool)getActivity()).setToolbar("Welcome Teacher","Punch In to Start Your Day");

                binding.llPunch.setVisibility(View.VISIBLE);
                binding.llMain.setVisibility(View.GONE);
            }else{
                binding.btnPunchout.setVisibility(View.VISIBLE);
                binding.btnPunchIn.setVisibility(View.GONE);
                binding.llPunch.setVisibility(View.GONE);
                binding.llMain.setVisibility(View.VISIBLE);
                ((DashboardSchool)getActivity()).setToolbar("You are punched in","Have a nice day");
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
                int adb = Settings.Secure.getInt(getActivity().getContentResolver(),
                        Settings.Global.DEVELOPMENT_SETTINGS_ENABLED , 0);

//                    if(adb==1){
//                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
//                        builder.setCancelable(false);
//                        builder.setTitle("Developer Options Enabled")
//                                .setMessage("You need to disable Developer Options to Continue")
//                                .setCancelable(true)
//                                .setPositiveButton("Developer Options Setting", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
//                                    }
//                                })
//                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                });
//                        builder.show();
//                    }else

                if(timesettings==0){
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
                    builder.setCancelable(false);
                    builder.setTitle("Phone time and date not match")
                            .setMessage("You need to enable Automatic Time and Date to Continue")
                            .setCancelable(true)
                            .setPositiveButton("Setting", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(Settings.ACTION_DATE_SETTINGS));
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.show();
                }else{
                    attendance="Out";
                    detectLocation();
                }


            }
        });

        binding.cardSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new TeacherSalaryFragment();
                ((DashboardSchool)getActivity()).switchFragmentOnDashBoard(fragment,"Salary Report");
            }
        });


        binding.cardNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new TeacherNoticeFragment();
                ((DashboardSchool)getActivity()).switchFragmentOnDashBoard(fragment,"Notice");

            }
        });
        binding.cardExamination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new ExaminationHomeFragment();
                ((DashboardSchool)getActivity()).switchFragmentOnDashBoard(fragment,"Examination");

            }
        });

        binding.cardAttendanceListTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new AttendanceListFragment();
                ((DashboardSchool)getActivity()).switchFragmentOnDashBoard(fragment,"Attendance");
            }
        });
        binding.cardAssignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new AssignmentFragment();
                ((DashboardSchool)getActivity()).switchFragmentOnDashBoard(fragment,"Homework");
            }
        });

        binding.cardStudentsLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new LeaveFragment();
                ((DashboardSchool)getActivity()).switchFragmentOnDashBoard(fragment,"Leave");
            }
        });

        binding.btnPunchIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(bp==null){
                    Toast.makeText(getActivity(), "Upload Selfie to Punch in", Toast.LENGTH_SHORT).show();
                }else{

                    if (Build.VERSION.SDK_INT >= 30){
                        if (!Environment.isExternalStorageManager()){
                            Intent getpermission = new Intent();
                            getpermission.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                            startActivity(getpermission);
                        }
                        else{

                            String path = Environment.getExternalStorageDirectory().toString();
                            OutputStream fOut = null;
                            Integer counter = 0;
                            file = new File(path, "Pictures/"+System.currentTimeMillis()+".jpg"); // the File to save , append increasing numeric counter to prevent files from getting overwritten.

                            if(!file.exists()){
                                if (!file.getParentFile().mkdirs()) {
                                    Log.e("Monitoring", "Oops! Failed create Monitoring directory");
                                    file.getParentFile().mkdir();
                                    try {
                                        file.createNewFile();
                                    } catch (IOException e) {
                                        Log.e("KPMKOFNFKLN",e.getMessage());
                                        throw new RuntimeException(e);
                                    }

                                }
                            }
                            try {
                                fOut = new FileOutputStream(file);
                                Bitmap pictureBitmap = bp; // obtaining the Bitmap
                                pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
                                fOut.flush(); // Not really required
                                fOut.close();

                                MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
                                fileName=file.getAbsolutePath();

                                Log.e("imahejbdjkb",""+fileName);
                            } catch (FileNotFoundException e) {
                                Log.e("KPMKOFNFKLN",e.getMessage());
                                throw new RuntimeException(e);

                            } catch (IOException e) {
                                Log.e("KPMKOFNFKLN",e.getMessage());
                                throw new RuntimeException(e);
                            }

                            // do not forget to close the stream


                            int adb = Settings.Secure.getInt(getActivity().getContentResolver(),
                                    Settings.Global.DEVELOPMENT_SETTINGS_ENABLED , 0);
                            int timesettings=android.provider.Settings.Global.getInt(getActivity().getContentResolver(), android.provider.Settings.Global.AUTO_TIME, 0);
                            if(timesettings==0){
                                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
                                builder.setCancelable(false);
                                builder.setTitle("Phone time and date not match")
                                        .setMessage("You need to enable Automatic Time and Date to Continue")
                                        .setCancelable(true)
                                        .setPositiveButton("Setting", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(new Intent(Settings.ACTION_DATE_SETTINGS));
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
                            else{
                                attendance="In";

                                progressDialog.setMessage("Logging You In");
                                progressDialog.show();

                                detectLocation();
                            }
                        }
                    }


//                    String path = Environment.getExternalStorageDirectory().toString();
//                    OutputStream fOut = null;
//                    Integer counter = 0;
//                    file = new File(path, "Pictures"+System.currentTimeMillis()+".jpg"); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
//
//                    if(!file.exists()){
//                        if (!file.mkdirs()) {
//                            Log.e("Monitoring", "Oops! Failed create Monitoring directory");
//                            file.getParentFile().mkdir();
//                            try {
//                                file.createNewFile();
//                            } catch (IOException e) {
//                                Log.e("KPMKOFNFKLN",e.getMessage());
//                                throw new RuntimeException(e);
//                            }
//
//                        }
//                    }  Log.e("FileNAMEJNJL",fileName);



//                    try {
//                        fOut = new FileOutputStream(file);
//                        Bitmap pictureBitmap = bp; // obtaining the Bitmap
//                        pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
//                        fOut.flush(); // Not really required
//                        fOut.close();
//
//                        MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
//                        fileName=file.getAbsolutePath();
//
//                        Log.e("imahejbdjkb",""+fileName);
//                    } catch (FileNotFoundException e) {
//                        throw new RuntimeException(e);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//
//                    // do not forget to close the stream
//
//
//                    int adb = Settings.Secure.getInt(getActivity().getContentResolver(),
//                            Settings.Global.DEVELOPMENT_SETTINGS_ENABLED , 0);
//                    int timesettings=android.provider.Settings.Global.getInt(getActivity().getContentResolver(), android.provider.Settings.Global.AUTO_TIME, 0);
//                    if(adb==1){
//                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
//                        builder.setCancelable(false);
//                        builder.setTitle("Developer Options Enabled")
//                                .setMessage("You need to disable Developer Options to Continue")
//                                .setCancelable(true)
//                                .setPositiveButton("Developer Options Setting", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));
//                                    }
//                                })
//                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                });
//                        builder.show();
//                    }else




                }


            }
        });


        return binding.getRoot();
    }

    private void savePunchOutAttendance() {
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
            object.addProperty("OutTime", currentTime);
            object.addProperty("AttendanceDate", currentDate1);
            object.addProperty("EmployeeID", Integer.parseInt(pkteacherId));
            object.addProperty("OutLongitude", longitude);
            object.addProperty("OutLatiTude", latitude);
            Call<CommonResponse> call = apiServices.SavePunchOutAttendance(object);
            String finalCurrentTime = currentTime;
            call.enqueue(new Callback<CommonResponse>() {
                @Override
                public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                    if(response.isSuccessful()){

                        if(response.body().getMessage().equals("   PunchOut Successfully !")){
                            final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getActivity());
                            View mView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_punch_successful, null);
                            alert.setView(mView);
                            TextView txt_message=mView.findViewById(R.id.txt_message);
                            TextView txt_date=mView.findViewById(R.id.txt_date);
                            TextView txt_time=mView.findViewById(R.id.txt_time);
                            TextView txt_latitude=mView.findViewById(R.id.txt_latitude);
                            TextView txt_longitude=mView.findViewById(R.id.txt_longitude);
                            TextView txt_address=mView.findViewById(R.id.txt_address);
                            Button btn_start=mView.findViewById(R.id.btn_start);
                            btn_start.setText("End Your Day");
                            txt_time.setText(response.body().getPunchOutTime());

                            txt_date.setText(response.body().getPunchOutDate());
                            txt_latitude.setText(""+latitude);
                            txt_longitude.setText(""+longitude);
                            txt_message.setText("Your Punch Out Successful was!!");
                            try {
                                Geocoder geocoder = new Geocoder(getActivity(), Locale.ENGLISH);
                                List<Address> addresses = geocoder.getFromLocation(
                                        latitude, longitude, 1


                                );
                                txt_address.setText(addresses.get(0).getAddressLine(0));


//                                    Toast.makeText(getActivity(), "Your Lat/Long:::"+latitude+","+longitude, Toast.LENGTH_LONG).show();
                                Log.e("AVGHCGHJGFHC",""+latitude);
                                Log.e("AVGHCGHJGFHC",""+longitude);







                            } catch (IOException e) {
                                e.printStackTrace();
                                txt_address.setText("-");

                            }

                            final android.app.AlertDialog alertDialog = alert.create();
                            alertDialog.show();



                            btn_start.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    editor.putString("lastActivity","out");
                                    editor.putString("lastActivityDate",currentDate);
                                    editor.apply();
                                    editor.commit();
                                    binding.llMain.setVisibility(View.GONE);
                                    binding.llPunch.setVisibility(View.VISIBLE);
                                    binding.btnPunchIn.setVisibility(View.VISIBLE);
                                    alertDialog.dismiss();
                                }
                            });

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
            currentTime = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH).format(new Date());
        }
        if(!TextUtils.isEmpty(pkteacherId)) {
            JsonObject object = new JsonObject();
            object.addProperty("AddedBy", Integer.parseInt(pkteacherId));
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

            String finalCurrentTime = currentTime;



            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("AddedBy", String.valueOf(Integer.parseInt(pkteacherId)))
                    .addFormDataPart("EmployeeID",String.valueOf(Integer.parseInt(pkteacherId)))
                    .addFormDataPart("LatiTude",""+latitude)
                    .addFormDataPart("LongiTude", String.valueOf(longitude))
                    .addFormDataPart("TeacherPhoto",fileName,
                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                    new File(fileName)))
                    .build();

            Call<CommonResponse> call = apiServices.SaveAttendance(body);
//            Request request = new Request.Builder()
//                    .url("http://demo2.afluex.com/MasterForApi/SaveHomework")
//                    .method("POST", body)
//                    .build();
            call.enqueue(new Callback<CommonResponse>() {
                @Override
                public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                    Log.e("Respomkng",""+response.body().getMessage());
                    if(response.body() != null){
                        progressDialog.dismiss();

                        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                        View mView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_punch_successful, null);
                        alert.setView(mView);
                        TextView txt_message=mView.findViewById(R.id.txt_message);
                        TextView txt_date=mView.findViewById(R.id.txt_date);
                        TextView txt_time=mView.findViewById(R.id.txt_time);
                        TextView txt_latitude=mView.findViewById(R.id.txt_latitude);
                        TextView txt_longitude=mView.findViewById(R.id.txt_longitude);
                        TextView txt_address=mView.findViewById(R.id.txt_address);
                        Button btn_start=mView.findViewById(R.id.btn_start);
                        txt_time.setText(response.body().getPunchInTime());

                        editor.putString("lastActivity","in");
                        editor.putString("lastActivityDate",currentDate);
                        editor.apply();
                        editor.commit();

                        txt_date.setText(response.body().getPunchInDate());
                        txt_latitude.setText(""+latitude);
                        txt_longitude.setText(""+longitude);
                        txt_message.setText("Your Punch In Successful!! Have a wonderful day!!");
                        try {
                            Geocoder geocoder = new Geocoder(getActivity(), Locale.ENGLISH);
                            List<Address> addresses = geocoder.getFromLocation(
                                    latitude, longitude, 1


                            );
                            txt_address.setText(addresses.get(0).getAddressLine(0));


//                                    Toast.makeText(getActivity(), "Your Lat/Long:::"+latitude+","+longitude, Toast.LENGTH_LONG).show();
                            Log.e("AVGHCGHJGFHC",""+latitude);
                            Log.e("AVGHCGHJGFHC",""+longitude);







                        } catch (IOException e) {
                            e.printStackTrace();
                            txt_address.setText("-");

                        }

                        final AlertDialog alertDialog = alert.create();
                        alertDialog.show();



                        btn_start.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                binding.llMain.setVisibility(View.VISIBLE);
                                binding.llPunch.setVisibility(View.GONE);
                                binding.btnPunchout.setVisibility(View.VISIBLE);

                                ((DashboardSchool)getActivity()).setToolbar("You are punched in","Have a nice day");
                                alertDialog.dismiss();
                            }
                        });

                    }
                }

                @Override
                public void onFailure(Call<CommonResponse> call, Throwable t) {
                    Log.e("jhkbhujvjhv",t.getMessage());
                }
            });


//            call.enqueue(new Callback<CommonResponse>() {
//                @Override
//                public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
//                    if(response.isSuccessful()){
//
//                        if(response.body().getMessage().equals("   Punching Successfully !")){
//                            progressDialog.dismiss();
//
//                            final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getActivity());
//                            View mView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_punch_successful, null);
//                            alert.setView(mView);
//                            TextView txt_message=mView.findViewById(R.id.txt_message);
//                            TextView txt_date=mView.findViewById(R.id.txt_date);
//                            TextView txt_time=mView.findViewById(R.id.txt_time);
//                            TextView txt_latitude=mView.findViewById(R.id.txt_latitude);
//                            TextView txt_longitude=mView.findViewById(R.id.txt_longitude);
//                            TextView txt_address=mView.findViewById(R.id.txt_address);
//                            Button btn_start=mView.findViewById(R.id.btn_start);
//                            txt_time.setText(response.body().getPunchInTime());
//
//                            editor.putString("lastActivity","in");
//                            editor.putString("lastActivityDate",currentDate);
//                            editor.apply();
//                            editor.commit();
//
//                            txt_date.setText(response.body().getPunchInDate());
//                            txt_latitude.setText(""+latitude);
//                            txt_longitude.setText(""+longitude);
//                            txt_message.setText("Your Punch In Successful!! Have a wonderful day!!");
//                            try {
//                                Geocoder geocoder = new Geocoder(getActivity(), Locale.ENGLISH);
//                                List<Address> addresses = geocoder.getFromLocation(
//                                        latitude, longitude, 1
//
//
//                                );
//                                txt_address.setText(addresses.get(0).getAddressLine(0));
//
//
////                                    Toast.makeText(getActivity(), "Your Lat/Long:::"+latitude+","+longitude, Toast.LENGTH_LONG).show();
//                                Log.e("AVGHCGHJGFHC",""+latitude);
//                                Log.e("AVGHCGHJGFHC",""+longitude);
//
//
//
//
//
//
//
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                                txt_address.setText("-");
//
//                            }
//
//                            final android.app.AlertDialog alertDialog = alert.create();
//                            alertDialog.show();
//
//
//
//                            btn_start.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//
//                                    binding.llMain.setVisibility(View.VISIBLE);
//                                    binding.llPunch.setVisibility(View.GONE);
//                                    binding.btnPunchout.setVisibility(View.VISIBLE);
//
//                                    ((DashboardSchool)getActivity()).setToolbar("You are punched in","Have a nice day");
//                                    alertDialog.dismiss();
//                                }
//                            });
//
//                        }else{
//                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//
//
//                        Log.e("TeacherId","5:: "+response.body());
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<CommonResponse> call, Throwable t) {
//
//                }
//            });
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
                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                            StrictMode.setVmPolicy(builder.build());
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
//        fileUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
//        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//        file = new File(getActivity().getExternalCacheDir(),
//                String.valueOf(System.currentTimeMillis()) + ".jpg");
//        fileUri = Uri.fromFile(file);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("CAMERA123456",""+resultCode);
        Log.e("CAMERA123456",""+(data==null));

        if (requestCode == REQUEST_IMAGE && resultCode == -1 && data != null) {
            Bundle extras = data.getExtras();



            bp = (Bitmap) data.getExtras().get("data");
            binding.imgAttendance.setImageBitmap(bp);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bp.compress(Bitmap.CompressFormat.JPEG, 100, bytes);


//            String[] projection = { MediaStore.Images.Media.DATA };
//            Cursor cursor = getActivity().managedQuery(
//                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                    projection, null, null, null);
//            int column_index_data = cursor
//                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//
//
//
//            fileName = fileUri.getPath();



            Log.e("CAMERA","NJNJ");


            binding.txtAttendance.setText("Change Picture");










        }
    }

    @SuppressLint("MissingPermission")
    private void detectLocation() {
        int adb = Settings.Secure.getInt(getActivity().getContentResolver(),
                Settings.Global.DEVELOPMENT_SETTINGS_ENABLED , 0);

        int timesettings=android.provider.Settings.Global.getInt(getActivity().getContentResolver(), android.provider.Settings.Global.AUTO_TIME, 0);

        Log.e("DeveloperOptions",String.valueOf(adb));
        Log.e("DeveloperOptions",String.valueOf(timesettings));

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
                        float[] results = new float[1];
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
                                    if(attendance.equals("In")){
                                        saveAttendance();
//                                        Location.distanceBetween(Utils.officeLatitude,Utils.officeLongitude,latitude,longitude,results);
//                                        float distance=results[0];
//                                        if(distance>1000){
//                                            progressDialog.dismiss();
//                                            Toast.makeText(getActivity(), "You are "+distance+" m away from Office Location. Can't Punch In.", Toast.LENGTH_SHORT).show();
//                                        }else{
//
//                                        }


                                    }else{
                                        savePunchOutAttendance();
//                                        Location.distanceBetween(Utils.officeLatitude,Utils.officeLongitude,latitude,longitude,results);
//                                        float distance=results[0];
//                                        if(distance>1000){
//                                            Toast.makeText(getActivity(), "You are "+distance+" m away from Office Location. Can't Punch Out.", Toast.LENGTH_SHORT).show();
//                                        }else{
//
//                                        }

                                    }









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


    @Override
    public void onResume() {
        super.onResume();

        Log.e("HRESUIME","RESHUIOG");
        ((DashboardSchool)getActivity()).setTitle("Home");
    }
}
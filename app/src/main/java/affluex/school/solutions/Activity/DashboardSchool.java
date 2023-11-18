package affluex.school.solutions.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import affluex.school.solutions.Fragments.ParentHome;
import affluex.school.solutions.Fragments.SchoolHome;
import affluex.school.solutions.Fragments.TeacherHome;
import affluex.school.solutions.Fragments.TeacherProfileFragment;
import affluex.school.solutions.R;
import affluex.school.solutions.databinding.ActivityDashboardSchoolBinding;
import affluex.school.solutions.databinding.CommonHomeSupportToolbarBinding;
import affluex.school.solutions.databinding.CustomToolBarBinding;
import affluex.school.solutions.databinding.DrawaberNavigationViewLayoutBinding;
import affluex.school.solutions.databinding.LayoutTopToolbarBinding;

public class DashboardSchool extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
    ActivityDashboardSchoolBinding binding;


    CustomToolBarBinding topToolbarBinding;
    private static final int Location_Request_code = 100;
    private final int REQUEST_IMAGE = 400;
    private String[] locationPermissions;
    double latitude=0.0,longitude=0.0;
    private LocationManager locationManager;
    FusedLocationProviderClient fusedLocationProviderClient;



    DrawaberNavigationViewLayoutBinding drawerBinding;

    public ActionBarDrawerToggle drawerToggle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDashboardSchoolBinding.inflate(getLayoutInflater());
        topToolbarBinding=binding.toolbar;

        drawerBinding=binding.layoutNavigation;

        locationPermissions = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION};
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        setContentView(binding.getRoot());
        Fragment fragment=null;
        Log.e("Title",""+getSharedPreferences("LoginDetails",MODE_PRIVATE).getString("name",""));
        if(getSharedPreferences("LoginDetails",MODE_PRIVATE).getString("userType","").equals("Teacher")){
             fragment=new TeacherHome();
             topToolbarBinding.tvTHometitle.setText(getSharedPreferences("LoginDetails",MODE_PRIVATE).getString("name",""));
             String substring=getSharedPreferences("LoginDetails",MODE_PRIVATE).getString("imagePath","").substring(2);
             String link="http://demo2.afluex.com"+substring;
             Log.e("Title123",link);

            Picasso.get().load(link).
                    resize(400,400).centerCrop()
                    .placeholder(R.drawable.profile_round)
                    .into(topToolbarBinding.imgProfile);

//            Glide.with(this).load(link).into(topToolbarBinding.imgProfile);

            if(getSharedPreferences("LoginDetails",MODE_PRIVATE).getString("isClassTeacher","").equals("0")){
                topToolbarBinding.tvClassTitle.setVisibility(View.GONE);
            }else{
                topToolbarBinding.tvClassTitle.setVisibility(View.VISIBLE);
                topToolbarBinding.tvClassTitle.setText(getSharedPreferences("LoginDetails",MODE_PRIVATE).getString("className","")+" - "
                        +getSharedPreferences("LoginDetails",MODE_PRIVATE).getString("sectionName",""));
            }


        }else{
            fragment=new ParentHome();
        }

        topToolbarBinding.toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        drawerToggle = new ActionBarDrawerToggle (this, binding.drawerLayout,topToolbarBinding.toolbar,R.string.open,R.string.close);

        binding.drawerLayout.addDrawerListener(drawerToggle);

        drawerToggle.setDrawerIndicatorEnabled(false);
        binding.navigationView.setNavigationItemSelectedListener(this);


        drawerBinding.liLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(DashboardSchool.this,LoginActivity.class));
                finish();
            }
        });




        drawerToggle.syncState();
        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        setSupportActionBar(topToolbarBinding.toolbar);
        topToolbarBinding.ivBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getSharedPreferences("LoginDetails",MODE_PRIVATE).getString("userType","").equals("Teacher")){
                    startActivity(new Intent(DashboardSchool.this,ProfileActivity.class));
                }else{

                }
            }
        });
        topToolbarBinding.menuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("MENUUUCLICK","CLICKED");
                checkDrawerOpen();

            }
        });

        topToolbarBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSupportFragmentManager().getBackStackEntryCount()>0){
                    onBackPressed();
                }else{
                    binding.drawerLayout.addDrawerListener(drawerToggle);
                    drawerToggle.setDrawerIndicatorEnabled(true);
                    drawerToggle.syncState();
                    binding.drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });


        switchFragmentOnDashBoard(fragment);
    }
    public void switchFragmentOnDashBoard(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void checkDrawerOpen() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("AVGHCGHJGFHC",""+resultCode);
        if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Log.e("AVGHCGHJGFHC","NJNJ");
            detectLocation();







        }
    }
    @SuppressLint("MissingPermission")
    private void detectLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                                    Geocoder geocoder = new Geocoder(DashboardSchool.this, Locale.ENGLISH);
                                    List<Address> addresses = geocoder.getFromLocation(
                                            location.getLatitude(), location.getLongitude(), 1


                                    );

                                    latitude = addresses.get(0).getLatitude();
                                    longitude = addresses.get(0).getLongitude();
                                    Toast.makeText(DashboardSchool.this, "Your Lat/Long:::"+latitude+","+longitude, Toast.LENGTH_LONG).show();
                                    Log.e("AVGHCGHJGFHC",""+latitude);
                                    Log.e("AVGHCGHJGFHC",""+longitude);






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

                                Toast.makeText(DashboardSchool.this ,"Unable to detect Your Location", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){


                        }



                    }
                });
            }
        } else {

            Toast.makeText(this, "Your Location is Turned Off.", Toast.LENGTH_SHORT).show();
            detectLocation();
        }
    }
    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, locationPermissions, Location_Request_code);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
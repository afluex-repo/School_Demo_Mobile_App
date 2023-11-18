package affluex.school.solutions.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import affluex.school.solutions.R;
import affluex.school.solutions.databinding.SplashActivityBinding;

public class SplashActivity extends AppCompatActivity {
    SplashActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=SplashActivityBinding.inflate(getLayoutInflater());
        setContentView(R.layout.splash_activity);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                } finally {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!getSharedPreferences("LoginDetails",MODE_PRIVATE).getString("userType","").equals("")){
                                startActivity(new Intent(SplashActivity.this,DashboardSchool.class));
                                finish();
                            }else{
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                finish();
                            }

                        }
                    });




                }
            }
        };

        timer.start();
    }
}
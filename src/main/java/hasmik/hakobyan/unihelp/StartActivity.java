package hasmik.hakobyan.unihelp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;



public class StartActivity extends AppCompatActivity {

    int current_page = 1;
    MaterialButton button_next1;
    TextView button_skip;
    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        boolean isFinished = sharedPref.getBoolean("onboarding_done", false);

        if (isFinished) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }


        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        button_next1 = findViewById(R.id.button_next);
        button_skip = findViewById(R.id.button_skip);

        Intent i = new Intent(this, LoginActivity.class);

        button_next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_skip.setVisibility(View.VISIBLE);
                switch (current_page) {
                    case 1:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragmentContainer, new OnBoardingFragment2())
                                .addToBackStack(null)
                                .commit();
                        current_page = 2;
                        break;
                    case 2:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragmentContainer, new OnBoardingFragment3())
                                .addToBackStack(null)
                                .commit();
                        current_page = 3;
                        break;
                    case 3:
                        sharedPref.edit().putBoolean("onboarding_done", true).apply();
                        startActivity(i);
                        finish();
                        break;
                }
            }
        });

        button_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPref.edit().putBoolean("onboarding_done", true).apply();
                startActivity(i);
                finish();
            }
        });

    }
}
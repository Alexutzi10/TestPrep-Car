package com.example.cartest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cartest.model.Car;
import com.example.cartest.network.AsyncTaskRunner;
import com.example.cartest.network.Callback;
import com.example.cartest.network.CarParser;
import com.example.cartest.network.HttpManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {
    private static final String api = "https://api.npoint.io/e6e75ce93e54db938689";
    public static final String BRAND = "brand";
    public static final String SHAREDPREFERENCES = "com.example.cartest.sharedpreferences";
    private FloatingActionButton fab;
    private TextView tv;
    private Spinner spinner;
    private EditText et;
    private Button bttn;
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
    private SharedPreferences sharedPreferences;
    private List<Car> cars = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getApplicationContext() == null) {
            return;
        }
        sharedPreferences = getApplicationContext().getSharedPreferences(SHAREDPREFERENCES, MODE_PRIVATE);

        initComponents();
        loadDefaultValues();

        fab.setOnClickListener(v -> {
            Callable<String> httpManager = new HttpManager(api);
            Callback<String> httpManagerCallback = getMainThreadOperation();
            asyncTaskRunner.executeAsync(httpManager, httpManagerCallback);


        });

        bttn.setOnClickListener(click -> {
            String brand = et.getText() != null && !et.getText().toString().trim().isEmpty() ? et.getText().toString() : "";

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(BRAND, brand);
            editor.apply();
        });
    }

    private void loadDefaultValues() {
        String brand = sharedPreferences.getString(BRAND, "");
        et.setText(brand);
    }

    private Callback<String> getMainThreadOperation() {
        return result -> {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            List<Car> parsedCars = CarParser.fromJson(result);
            cars.addAll(parsedCars);
            Log.i("ParsedList", parsedCars.toString());
        };
    }

    private void initComponents() {
        fab = findViewById(R.id.surugiu_george_alexandru_fab);
        tv = findViewById(R.id.surugiu_george_alexandru_tv);
        spinner = findViewById(R.id.surugiu_george_alexandru_spinner);
        et = findViewById(R.id.surugiu_george_alexandru_et);
        bttn = findViewById(R.id.surugiu_george_alexandru_bttn);
    }
}
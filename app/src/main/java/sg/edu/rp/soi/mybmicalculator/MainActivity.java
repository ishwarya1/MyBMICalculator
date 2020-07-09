package sg.edu.rp.soi.mybmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText etWeight, etHeight;
    Button btnCal, btnReset;
    TextView tvLastCal, tvLastBMI,tvOutcome;
    float weight,height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        btnCal = findViewById(R.id.btn);
        btnReset = findViewById(R.id.btn2);
        tvLastCal = findViewById(R.id.tv);
        tvLastBMI = findViewById(R.id.tv2);
        tvOutcome = findViewById(R.id.tvOutcome);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWeight.setText(" ");
                etHeight.setText(" ");
            }
        });
        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weight = Float.parseFloat(etWeight.getText().toString());
                height = Float.parseFloat(etHeight.getText().toString());
                Float BMI = weight/(height*height);
                Calendar now = Calendar.getInstance();
                String dateTime = now.get(Calendar.DAY_OF_MONTH)+ "/"+
                        now.get(Calendar.MONTH+1)+ "/" + now.get(Calendar.YEAR) +
                        now.get(Calendar.HOUR_OF_DAY)+ ":" + now.get(Calendar.MINUTE);
                if(BMI<18.5){
                    tvOutcome.setText("You are underweight");
                }
                else if(BMI>= 18.5 && BMI<=24.9){
                    tvOutcome.setText("Your BMI is normal");
                }
                else  if(BMI>=25 && BMI<=29.9){
                    tvOutcome.setText("You are overweight");
                }
                else {
                    tvOutcome.setText("You are obese");
                }
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.putString("Last Calculate Date :",dateTime);
                prefEdit.putFloat("Last Calculate BMI: ",BMI);
                prefEdit.commit();
                tvLastBMI.setText(BMI+"");
                tvLastCal.setText(dateTime);
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        String lastDateTime = tvLastCal.getText().toString();
        Float lastBMI = Float.parseFloat(tvLastCal.getText().toString());
        String lastOutcome = tvOutcome.getText().toString();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("Last Calculate Date :",lastDateTime);
        prefEdit.putFloat("Last Calculate BMI: ",lastBMI);
        prefEdit.putString("Last Outcome",lastOutcome);
        prefEdit.commit();
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String msgLastDate = prefs.getString("Date","");
        float msgLastBMI = prefs.getFloat("Last BMI",0);
        String msgLastOutcome = prefs.getString("Last Outcome","");
        tvLastBMI.setText("Last Calculate BMI : " + msgLastBMI + "");
        tvLastCal.setText("Last Calculate Date: " + msgLastDate);
        tvOutcome.setText("Last Outcome: " + msgLastOutcome);
    }
}
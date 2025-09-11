package com.example.gasolinecalc_cen4360_christophervotoe;

import android.os.Bundle;
import android.os.DropBoxManager;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText distanceInput, costInput, mpgInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        //int modifier = 0;

        distanceInput = findViewById(R.id.distanceNumber);
        costInput     = findViewById(R.id.CPGNumber);
        mpgInput      = findViewById(R.id.HighwayMPGNumber);
        Log.d("distance",""+distanceInput);

        Button calcBtn = findViewById(R.id.Calculate_Button);
        calcBtn.setOnClickListener(v -> printValue());

    }

    private void printValue() {
        String dist = distanceInput.getText().toString().trim();
        String cost = costInput.getText().toString().trim();
        String mpg  = mpgInput.getText().toString().trim();

        Log.d("INPUTS", "distance=" + dist + ", cost=" + cost + ", mpg=" + mpg);
        Toast.makeText(this, "distance=" + dist + ", cost=" + cost + ", mpg=" + mpg,
                Toast.LENGTH_SHORT).show();
    }

    private int calcModifierValue(){
        int modifier = 0;
        CheckBox agresiveDriver = findViewById(R.id.AG_Checkbox);

        RadioGroup AC = findViewById(R.id.acRadioGroup);
        Spinner roadType = findViewById(R.id.RoadType_DropBox);
        String roadTypeValue = roadType.getSelectedItem().toString();
        int selectedChoice = AC.getCheckedRadioButtonId();


        if(agresiveDriver.isChecked())
        {
            switch (roadTypeValue) {
                case "Highway":
                    modifier+=15;
                    break;
                case "City":
                    modifier+=25;
                    break;
                case "Mixed":
                    modifier+=20;
                    break;
            }
        }
        else
        {
            //Driver is not aggressive
            switch (roadTypeValue) {
                case "Highway":
                    modifier+=0;
                    break;
                case "City":
                    modifier+=15;
                    break;
                case "Mixed":
                    modifier+=10;
                    break;
            }
        }

        if(selectedChoice == R.id.Yes)
        {
            modifier+=15;
        }

        return modifier;
    }




}

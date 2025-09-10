package com.example.gasolinecalc_cen4360_christophervotoe;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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


        distanceInput = findViewById(R.id.distanceNumber);
        costInput     = findViewById(R.id.CPGNumber);
        mpgInput      = findViewById(R.id.HighwayMPGNumber);
        Log.d("distance",""+distanceInput);

        Button calcBtn = findViewById(R.id.Calculate_Button);
        calcBtn.setOnClickListener(v -> printValue());

    }


    private void grabInputedNumbers() {
        try {
            int distance = Integer.parseInt(distanceInput.getText().toString().trim());
            double costPerGallon = Double.parseDouble(costInput.getText().toString().trim());
            int highwayMPG = Integer.parseInt(mpgInput.getText().toString().trim());
            Log.d("INPUTS", "distance=" + distance + " cost=" + costPerGallon + " mpg=" + highwayMPG);
        } catch (NumberFormatException e) {
            Log.d("Input Error","Numbers entered incorrectly");
        }
    }
    private void printValue() {
        String dist = distanceInput.getText().toString().trim();
        String cost = costInput.getText().toString().trim();
        String mpg  = mpgInput.getText().toString().trim();

        Log.d("INPUTS", "distance=" + dist + ", cost=" + cost + ", mpg=" + mpg);
        Toast.makeText(this, "distance=" + dist + ", cost=" + cost + ", mpg=" + mpg,
                Toast.LENGTH_SHORT).show();
    }

}

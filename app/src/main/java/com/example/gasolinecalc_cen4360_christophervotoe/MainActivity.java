package com.example.gasolinecalc_cen4360_christophervotoe;

import android.os.Bundle;
import android.os.DropBoxManager;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
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



        // How we get the avgSpeed bar to work correctly
        SeekBar seekBar = findViewById(R.id.AvgSpeed_SeekBar);
        TextView speedTextView = findViewById(R.id.displayAvgSpeedSliderNumber);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int actualValue = 35 + (progress * 5);
                speedTextView.setText("" + actualValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


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
        CheckBox aggressiveDriver = findViewById(R.id.AG_Checkbox);
        SeekBar avgSpeed = findViewById(R.id.AvgSpeed_SeekBar);
        RadioGroup AC = findViewById(R.id.acRadioGroup);
        Spinner roadType = findViewById(R.id.RoadType_DropBox);
        String roadTypeValue = roadType.getSelectedItem().toString();
        int selectedChoice = AC.getCheckedRadioButtonId();


        if(aggressiveDriver.isChecked())
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

    private float calcFinalMPG(int modifier,EditText initialMPG)
    {
        float finalMPG = 0;
        String initialMPGString =initialMPG.toString().trim();
        int initialMPGInt = Integer.parseInt(initialMPGString);
        finalMPG = initialMPGInt * ((float) (100 - modifier) / 100);
        return finalMPG;
    }

    private float calcGallons(EditText distance, float finalMPG) {
        String distanceString = distance.toString().trim();
        int distanceInt = Integer.parseInt(distanceString);
        return (distanceInt/finalMPG);
    }

    private float calcRoundTripCost(float gallon, TextView costPerGallon)
    {
        String costPerGallonString = costPerGallon.toString().trim();
        float costPerGallonFloat = Float.parseFloat(costPerGallonString);
        return ((gallon * costPerGallonFloat)*2);
    }


}

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

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private EditText distanceInput, costInput, mpgInput;
    private TextView resultText;

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
        calcBtn.setOnClickListener(v -> calculateAndDisplay());

    }

//    private void printValue() {
//        String dist = distanceInput.getText().toString().trim();
//        String cost = costInput.getText().toString().trim();
//        String mpg  = mpgInput.getText().toString().trim();
//
//        Log.d("INPUTS", "distance=" + dist + ", cost=" + cost + ", mpg=" + mpg);
//        Toast.makeText(this, "distance=" + dist + ", cost=" + cost + ", mpg=" + mpg,
//                Toast.LENGTH_SHORT).show();
//    }

    private void calculateAndDisplay() {

        int modifier = calcModifierValue();

        // 2) Final MPG
        float finalMPG = calcFinalMPG(modifier, mpgInput);
        if (finalMPG <= 0f) {
            Toast.makeText(this, "Enter a valid Highway MPG", Toast.LENGTH_SHORT).show();
            resultText.setText("—");
            return;
        }

        // 3) Gallons for the entered distance (we’ll double if one-way)
        float gallons = calcGallons(distanceInput, finalMPG);
        if (gallons <= 0f) {
            Toast.makeText(this, "Enter a valid distance", Toast.LENGTH_SHORT).show();
            resultText.setText("—");
            return;
        }

        // 4) Cost = gallons * $/gal
        float cost = calcRoundTripCost(gallons, costInput); // costInput is EditText
        TextView displayText = findViewById(R.id.TotalSpent_Text);

        String costStr = String.format("$%.2f", cost);
        displayText.append(" " + costStr);
    }

    private int calcModifierValue(){
        int modifier = 0;
        CheckBox aggressiveDriver = findViewById(R.id.AG_Checkbox);
        SeekBar avgSpeed = findViewById(R.id.AvgSpeed_SeekBar);
        RadioGroup AC = findViewById(R.id.acRadioGroup);
        Spinner roadType = findViewById(R.id.RoadType_DropBox);
        String roadTypeValue = roadType.getSelectedItem().toString();
        int selectedChoice = AC.getCheckedRadioButtonId();

        //If aggressive or not, how it affects depending on road type
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
        //If AC is on
        if(selectedChoice == R.id.Yes)
        {
            modifier+=15;
        }

        // Checks the speed of the car and how much over it is
        int progress = avgSpeed.getProgress();
        int speed = 35 + (progress*5);
        if(speed>50)
        {
            int speedOver = (speed-50) / 5;
            modifier += speedOver * 5;

        }
        return modifier;
    }

    private float calcFinalMPG(int modifier,EditText initialMPG)
    {
        float finalMPG = 0;
        String initialMPGString =initialMPG.getText().toString().trim();
        int initialMPGInt = Integer.parseInt(initialMPGString);
        finalMPG = initialMPGInt * ((float) (100 - modifier) / 100);
        return finalMPG;
    }

    private float calcGallons(EditText distance, float finalMPG) {
        String distanceString = distance.getText().toString().trim();
        int distanceInt = Integer.parseInt(distanceString);
        return (distanceInt/finalMPG);
    }

    private float calcRoundTripCost(float gallon, EditText costPerGallon)
    {
        String costPerGallonString = costPerGallon.getText().toString().trim();
        float costPerGallonFloat = Float.parseFloat(costPerGallonString);
        return ((gallon * costPerGallonFloat)*2);
    }


}

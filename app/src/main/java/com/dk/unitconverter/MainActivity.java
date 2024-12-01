package com.dk.unitconverter;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etInputValue;
    private Spinner spinnerUnitType, spinnerFromUnit, spinnerToUnit;
    private TextView tvResult;
    private Button btnConvert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInputValue = findViewById(R.id.InputValue);
        spinnerUnitType = findViewById(R.id.spinnerUnitType);
        spinnerFromUnit = findViewById(R.id.spinnerFromUnit);
        spinnerToUnit = findViewById(R.id.spinnerToUnit);
        tvResult = findViewById(R.id.Result);
        btnConvert = findViewById(R.id.btnConvert);

        // Set up spinners
        String[] unitTypes = {"Length", "Weight", "Temperature"};
        ArrayAdapter<String> unitTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, unitTypes);
        unitTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnitType.setAdapter(unitTypeAdapter);

        // Set listeners for the spinners
        spinnerUnitType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedUnitType = unitTypes[position];
                updateUnitOptions(selectedUnitType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        btnConvert.setOnClickListener(v -> performConversion());
    }

    private void updateUnitOptions(String unitType) {
        String[] units = {};
        if (unitType.equals("Length")) {
            units = new String[] {"Meter", "Kilometer", "Centimeter", "Inch"};
        } else if (unitType.equals("Weight")) {
            units = new String[] {"Gram", "Kilogram", "Pound", "Ounce"};
        } else if (unitType.equals("Temperature")) {
            units = new String[] {"Celsius", "Fahrenheit", "Kelvin"};
        }

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromUnit.setAdapter(unitAdapter);
        spinnerToUnit.setAdapter(unitAdapter);
    }

    private void performConversion() {
        // Get the values from the inputs
        String inputText = etInputValue.getText().toString();
        if (inputText.isEmpty()) {
            tvResult.setText("Please enter a value to convert.");
            return;
        }

        double inputValue = Double.parseDouble(inputText);
        int fromUnitPosition = spinnerFromUnit.getSelectedItemPosition();
        int toUnitPosition = spinnerToUnit.getSelectedItemPosition();
        String unitType = spinnerUnitType.getSelectedItem().toString();

        double result = 0.0;

        // Conversion logic based on unit type
        if (unitType.equals("Length")) {
            result = convertLength(fromUnitPosition, toUnitPosition, inputValue);
        } else if (unitType.equals("Weight")) {
            result = convertWeight(fromUnitPosition, toUnitPosition, inputValue);
        } else if (unitType.equals("Temperature")) {
            result = convertTemperature(fromUnitPosition, toUnitPosition, inputValue);
        }

        // Show the result
        tvResult.setText("Result: " + result);
    }

    // Length conversion (Meter, Kilometer, Centimeter, Inch)
    private double convertLength(int fromUnit, int toUnit, double value) {
        // Convert input to meters first, then convert to the desired unit
        double meters = value;

        if (fromUnit == 1) { // Kilometer
            meters = value * 1000;
        } else if (fromUnit == 2) { // Centimeter
            meters = value / 100;
        } else if (fromUnit == 3) { // Inch
            meters = value * 0.0254;
        }

        // Convert meters to the target unit
        if (toUnit == 0) { // Meter
            return meters;
        } else if (toUnit == 1) { // Kilometer
            return meters / 1000;
        } else if (toUnit == 2) { // Centimeter
            return meters * 100;
        } else if (toUnit == 3) { // Inch
            return meters / 0.0254;
        }
        return value;
    }

    // Weight conversion (Gram, Kilogram, Pound, Ounce)
    private double convertWeight(int fromUnit, int toUnit, double value) {
        double grams = value;

        // Convert to grams first
        if (fromUnit == 1) { // Kilogram
            grams = value * 1000;
        } else if (fromUnit == 2) { // Pound
            grams = value * 453.592;
        } else if (fromUnit == 3) { // Ounce
            grams = value * 28.3495;
        }

        // Convert grams to the target unit
        if (toUnit == 0) { // Gram
            return grams;
        } else if (toUnit == 1) { // Kilogram
            return grams / 1000;
        } else if (toUnit == 2) { // Pound
            return grams / 453.592;
        } else if (toUnit == 3) { // Ounce
            return grams / 28.3495;
        }
        return value;
    }

    // Temperature conversion (Celsius, Fahrenheit, Kelvin)
    private double convertTemperature(int fromUnit, int toUnit, double value) {
        double celsius = value;

        // Convert to Celsius first
        if (fromUnit == 1) { // Fahrenheit
            celsius = (value - 32) * 5 / 9;
        } else if (fromUnit == 2) { // Kelvin
            celsius = value - 273.15;
        }

        // Convert Celsius to the target unit
        if (toUnit == 0) { // Celsius
            return celsius;
        } else if (toUnit == 1) { // Fahrenheit
            return (celsius * 9 / 5) + 32;
        } else if (toUnit == 2) { // Kelvin
            return celsius + 273.15;
        }
        return value;
    }
}

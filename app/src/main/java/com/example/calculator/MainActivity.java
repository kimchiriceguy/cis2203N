package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tvDisplay, tvHistory;
    private ScrollView scrollViewHistory;

    private double firstNum = 0.0;
    private String currentOp = "";
    private boolean isNewInput = true;

    private String calculationHistory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        tvDisplay = findViewById(R.id.tvDisplay);
        tvHistory = findViewById(R.id.tvHistory);
        scrollViewHistory = findViewById(R.id.scrollViewHistory);

        // Buttons
        Button btn0 = findViewById(R.id.btn0);
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btn4 = findViewById(R.id.btn4);
        Button btn5 = findViewById(R.id.btn5);
        Button btn6 = findViewById(R.id.btn6);
        Button btn7 = findViewById(R.id.btn7);
        Button btn8 = findViewById(R.id.btn8);
        Button btn9 = findViewById(R.id.btn9);

        Button btnPlus = findViewById(R.id.btnPlus);
        Button btnSub = findViewById(R.id.btnSub);
        Button btnMul = findViewById(R.id.btnMul);
        Button btnDiv = findViewById(R.id.btnDiv);
        Button btnEqual = findViewById(R.id.btnEqual);
        Button btnAC = findViewById(R.id.btnAC);
        Button btnDot = findViewById(R.id.btnDot);
        Button btnPercent = findViewById(R.id.btnPercent);
        Button btnPlusMinus = findViewById(R.id.btnPlusMinus);
        Button customOperator = findViewById(R.id.CustomOperator);

        if (savedInstanceState != null) {
            firstNum = savedInstanceState.getDouble("firstNum", 0.0);
            currentOp = savedInstanceState.getString("currentOp", "");
            isNewInput = savedInstanceState.getBoolean("isNewInput", true);
            tvDisplay.setText(savedInstanceState.getString("displayText", "0"));
            calculationHistory = savedInstanceState.getString("calculationHistory", "");
            tvHistory.setText(calculationHistory);
        }

        // Number buttons
        View.OnClickListener numberClick = v -> onNumberClicked(((Button) v).getText().toString());

        btn0.setOnClickListener(numberClick);
        btn1.setOnClickListener(numberClick);
        btn2.setOnClickListener(numberClick);
        btn3.setOnClickListener(numberClick);
        btn4.setOnClickListener(numberClick);
        btn5.setOnClickListener(numberClick);
        btn6.setOnClickListener(numberClick);
        btn7.setOnClickListener(numberClick);
        btn8.setOnClickListener(numberClick);
        btn9.setOnClickListener(numberClick);

        // Operators
        btnPlus.setOnClickListener(v -> prepareOperator("+"));
        btnSub.setOnClickListener(v -> prepareOperator("-"));
        btnMul.setOnClickListener(v -> prepareOperator("x"));
        btnDiv.setOnClickListener(v -> prepareOperator("÷"));

        btnEqual.setOnClickListener(v -> {
            performCalculation();
            currentOp = "";
        });

        btnAC.setOnClickListener(v -> resetCalculator());

        btnDot.setOnClickListener(v -> appendDot());

        btnPercent.setOnClickListener(v -> {
            double value = parseDisplay();
            updateUI(value / 100, false);
        });

        btnPlusMinus.setOnClickListener(v -> {
            double value = parseDisplay();
            updateUI(value * -1, false);
        });

        customOperator.setOnClickListener(v -> {
            double value = parseDisplay();
            appendHistory(value + " × MyIDNumber", String.valueOf(value * 0.37));
            updateUI(value * 0.37, false);
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("firstNum", firstNum);
        outState.putString("currentOp", currentOp);
        outState.putBoolean("isNewInput", isNewInput);
        outState.putString("displayText", tvDisplay.getText().toString());
        outState.putString("calculationHistory", calculationHistory);
    }

    private double parseDisplay() {
        try {
            return Double.parseDouble(tvDisplay.getText().toString());
        } catch (Exception e) {
            return 0.0;
        }
    }

    private void appendHistory(String expression, String result) {
        calculationHistory += expression + " = " + result + "\n";
        tvHistory.setText(calculationHistory);

        scrollViewHistory.post(() ->
                scrollViewHistory.fullScroll(ScrollView.FOCUS_DOWN));
    }

    private void appendDot() {
        String currentText = tvDisplay.getText().toString();
        int digitCount = currentText.replaceAll("[^0-9]", "").length();

        if (!currentText.contains(".") && digitCount < 7) {
            tvDisplay.append(".");
            isNewInput = false;
        }
    }

    private void resetCalculator() {
        tvDisplay.setText("0");
        firstNum = 0.0;
        currentOp = "";
        isNewInput = true;
        calculationHistory = "";
        tvHistory.setText("");
    }

    private void prepareOperator(String op) {
        if (!currentOp.isEmpty() && !isNewInput) {
            performCalculation();
        }
        firstNum = parseDisplay();
        currentOp = op;
        isNewInput = true;
    }

    private void performCalculation() {
        String secondNumStr = tvDisplay.getText().toString();
        if (currentOp.isEmpty() || secondNumStr.isEmpty()) return;

        double secondNum = parseDisplay();
        double result = 0.0;
        boolean hasError = false;

        switch (currentOp) {
            case "+":
                result = firstNum + secondNum;
                break;
            case "-":
                result = firstNum - secondNum;
                break;
            case "x":
                result = firstNum * secondNum;
                break;
            case "÷":
                if (secondNum == 0.0) hasError = true;
                else result = firstNum / secondNum;
                break;
        }

        String formattedResult = (result % 1 == 0)
                ? String.valueOf((long) result)
                : String.format(Locale.US, "%.4f", result);

        appendHistory(firstNum + " " + currentOp + " " + secondNum, formattedResult);
        updateUI(result, hasError);
    }

    private void updateUI(double result, boolean hasError) {
        if (hasError) {
            tvDisplay.setText("Cannot divide by zero!");
            firstNum = 0.0;
        } else {
            String formatted = (result % 1 == 0)
                    ? String.valueOf((long) result)
                    : String.valueOf(Double.parseDouble(String.format(Locale.US, "%.4f", result)));

            tvDisplay.setText(formatted);
            firstNum = result;
        }
        isNewInput = true;
    }

    private void onNumberClicked(String number) {
        String currentText = tvDisplay.getText().toString();

        if (isNewInput || currentText.equals("Error")) {
            tvDisplay.setText(number);
            isNewInput = false;
        } else {
            int digitCount = currentText.replaceAll("[^0-9]", "").length();
            if (digitCount < 7) {
                tvDisplay.append(number);
            }
        }
    }
}

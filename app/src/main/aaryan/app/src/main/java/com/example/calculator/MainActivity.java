package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText display;
    private String currentNumber = "";
    private String operation = "";
    private double firstNumber = 0;
    private boolean isNewOperation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        // Number buttons
        setupNumberButton(R.id.btn0, "0");
        setupNumberButton(R.id.btn1, "1");
        setupNumberButton(R.id.btn2, "2");
        setupNumberButton(R.id.btn3, "3");
        setupNumberButton(R.id.btn4, "4");
        setupNumberButton(R.id.btn5, "5");
        setupNumberButton(R.id.btn6, "6");
        setupNumberButton(R.id.btn7, "7");
        setupNumberButton(R.id.btn8, "8");
        setupNumberButton(R.id.btn9, "9");

        // Operation buttons
        setupOperationButton(R.id.btnPlus, "+");
        setupOperationButton(R.id.btnMinus, "-");
        setupOperationButton(R.id.btnMultiply, "*");
        setupOperationButton(R.id.btnDivide, "/");

        // Clear button
        Button btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(v -> {
            currentNumber = "";
            operation = "";
            firstNumber = 0;
            display.setText("");
            isNewOperation = true;
        });

        // Equals button
        Button btnEquals = findViewById(R.id.btnEquals);
        btnEquals.setOnClickListener(v -> calculate());
    }

    private void setupNumberButton(int buttonId, String number) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> {
            if (isNewOperation) {
                display.setText("");
                isNewOperation = false;
            }
            currentNumber += number;
            display.setText(currentNumber);
        });
    }

    private void setupOperationButton(int buttonId, String op) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> {
            if (!currentNumber.isEmpty()) {
                firstNumber = Double.parseDouble(currentNumber);
                operation = op;
                currentNumber = "";
                display.setText("");
            }
        });
    }

    private void calculate() {
        if (!currentNumber.isEmpty() && !operation.isEmpty()) {
            double secondNumber = Double.parseDouble(currentNumber);
            double result = 0;

            switch (operation) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "*":
                    result = firstNumber * secondNumber;
                    break;
                case "/":
                    if (secondNumber != 0) {
                        result = firstNumber / secondNumber;
                    } else {
                        display.setText("Error");
                        return;
                    }
                    break;
            }

            display.setText(String.valueOf(result));
            currentNumber = String.valueOf(result);
            operation = "";
            isNewOperation = true;
        }
    }
} 
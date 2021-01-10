package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    var result: TextView? = null
    var number: EditText? = null
    var operation: TextView? = null
    var operand: Double? = null
    var lastOperation = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        result = findViewById(R.id.resultField);
        number = findViewById(R.id.numberField);
        operation = findViewById(R.id.operationField);
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("OPERATION", lastOperation)
        if (operand != null) outState.putDouble("OPERAND", operand!!)
        super.onSaveInstanceState(outState)
    }

    // получение ранее сохраненного состояния
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        lastOperation = savedInstanceState.getString("OPERATION")!!
        operand = savedInstanceState.getDouble("OPERAND")
        result!!.text = operand.toString()
        operation!!.text = lastOperation
    }

    // обработка нажатия на числовую кнопку
    fun onNumberClick(view: View) {
        val button: Button = view as Button
        number!!.append(button.getText())
        if (lastOperation == "=" && operand != null) {
            operand = null
        }
    }

    // обработка нажатия на кнопку операции
    fun onOperationClick(view: View) {
        val button: Button = view as Button
        val op: String = button.getText().toString()
        var number = number!!.text.toString()
        // если введенно что-нибудь
        if (number.length > 0) {
            number = number.replace(',', '.')
            try {
                calculate(java.lang.Double.valueOf(number), op)
            } catch (ex: NumberFormatException) {
                this.number!!.setText("")
            }
        }
        lastOperation = op
        operation!!.text = lastOperation
    }

    private fun calculate(number: Double, operation: String) {

        if (operand == null) {
            operand = number
        } else {
            if (lastOperation == "=") {
                lastOperation = operation
            }
            when (lastOperation) {
                "=" -> operand = number
                "/" -> if (number == 0.0) {
                    operand = 0.0
                } else {
                    this.operand = this.operand!! / number;
                }
                "*" -> this.operand = this.operand!! * number;

                "+" -> this.operand = this.operand!! + number;

                "-" -> this.operand = this.operand!! - number;

            }
        }
        result!!.text = operand.toString().replace('.', ',')
        this.number!!.setText("")
    }
}
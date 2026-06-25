package com.theophilus.t_scientificcalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.*
import android.view.View

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private lateinit var history: TextView

    private var currentInput = ""
    private val calculationHistory = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.txtDisplay)
        history = findViewById(R.id.txtHistory)

        setupNumbers()
        setupOperators()
        setupScientificFunctions()
        setupEquals()
    }

    private fun setupNumbers() {

        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        for (id in numberButtons) {
            findViewById<Button>(id).setOnClickListener {
                currentInput += (it as Button).text.toString()
                display.text = currentInput
            }
        }

        findViewById<Button>(R.id.btnDot).setOnClickListener {
            currentInput += "."
            display.text = currentInput
        }

        findViewById<Button>(R.id.btnOpenBracket).setOnClickListener {
            currentInput += "("
            display.text = currentInput
        }

        findViewById<Button>(R.id.btnCloseBracket).setOnClickListener {
            currentInput += ")"
            display.text = currentInput
        }

        findViewById<Button>(R.id.btnPi).setOnClickListener {
            currentInput += Math.PI.toString()
            display.text = currentInput
        }

        findViewById<Button>(R.id.btnE).setOnClickListener {
            currentInput += Math.E.toString()
            display.text = currentInput
        }
    }

    private fun setupOperators() {

        findViewById<Button>(R.id.btnPlus).setOnClickListener {
            currentInput += "+"
            display.text = currentInput
        }

        findViewById<Button>(R.id.btnMinus).setOnClickListener {
            currentInput += "-"
            display.text = currentInput
        }

        findViewById<Button>(R.id.btnMultiply).setOnClickListener {
            currentInput += "*"
            display.text = currentInput
        }

        findViewById<Button>(R.id.btnDivide).setOnClickListener {
            currentInput += "/"
            display.text = currentInput
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            currentInput = ""
            display.text = "0"

            history.visibility = View.GONE
        }

        findViewById<Button>(R.id.btnHistory).setOnClickListener {

            if (history.visibility == TextView.VISIBLE) {
                history.visibility = TextView.GONE
            } else {
                history.visibility = TextView.VISIBLE
                history.text = calculationHistory.joinToString("\n")
            }
        }
    }

    private fun setupScientificFunctions() {

        findViewById<Button>(R.id.btnSin).setOnClickListener {
            calculateSingleInput("sin") {
                sin(Math.toRadians(it))
            }
        }

        findViewById<Button>(R.id.btnCos).setOnClickListener {
            calculateSingleInput("cos") {
                cos(Math.toRadians(it))
            }
        }

        findViewById<Button>(R.id.btnTan).setOnClickListener {
            calculateSingleInput("tan") {
                tan(Math.toRadians(it))
            }
        }

        findViewById<Button>(R.id.btnLog).setOnClickListener {
            calculateSingleInput("log") {
                log10(it)
            }
        }

        findViewById<Button>(R.id.btnLn).setOnClickListener {
            calculateSingleInput("ln") {
                ln(it)
            }
        }

        findViewById<Button>(R.id.btnRoot).setOnClickListener {
            calculateSingleInput("√") {
                sqrt(it)
            }
        }

        findViewById<Button>(R.id.btnSquare).setOnClickListener {
            calculateSingleInput("x²") {
                it * it
            }
        }

        findViewById<Button>(R.id.btnFactorial).setOnClickListener {
            try {
                val n = currentInput.toInt()
                var result = 1L

                for (i in 1..n) {
                    result *= i
                }

                saveResult("$n!", result.toDouble())
            } catch (e: Exception) {
                display.text = "Error"
            }
        }

        findViewById<Button>(R.id.btnPower).setOnClickListener {
            currentInput += "^"
            display.text = currentInput
        }

        findViewById<Button>(R.id.btnNpr).setOnClickListener {
            display.text = "nPr later"
        }

        findViewById<Button>(R.id.btnNcr).setOnClickListener {
            display.text = "nCr later"
        }
    }

    private fun setupEquals() {

        findViewById<Button>(R.id.btnEquals).setOnClickListener {

            try {

                val expression = ExpressionBuilder(currentInput).build()

                val result = expression.evaluate()

                saveResult(currentInput, result)

            } catch (e: Exception) {

                display.text = "Error"

            }
        }
    }

    private fun calculateSingleInput(
        operationName: String,
        operation: (Double) -> Double
    ) {
        try {
            val value = currentInput.toDouble()
            val result = operation(value)

            val expression = "$operationName($value)"
            calculationHistory.add("$expression = $result")

            history.text = calculationHistory.joinToString("\n")

            display.text = result.toString()
            currentInput = result.toString()

        } catch (e: Exception) {
            display.text = "Error"
        }
    }

    private fun saveResult(expression: String, result: Double) {

        calculationHistory.add("$expression = $result")

        history.text = calculationHistory.joinToString("\n")

        display.text = result.toString()

        currentInput = result.toString()
    }
}
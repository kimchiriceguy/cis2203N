package com.example.calculator

import android.os.Bundle
import android.os.PersistableBundle
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.ScrollView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var firstNum: Double = 0.0
    private var currentOp: String = ""
    private var isNewInput: Boolean = true

//    bonus thingy
    private var calculationHistory: String = ""

    private fun appendHistory(expression: String, result: String) {
        calculationHistory = "$calculationHistory$expression = $result\n"
        binding.tvHistory.text = calculationHistory
        // Auto-scroll to bottom
        binding.scrollViewHistory.post {
            binding.scrollViewHistory.fullScroll(ScrollView.FOCUS_DOWN)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savedInstanceState?.let {
            firstNum = it.getDouble("firstNum", 0.0)
            currentOp = it.getString("currentOp", "") ?: ""
            isNewInput = it.getBoolean("isNewInput", true)
            binding.tvDisplay.text = it.getString("displayText", "0")
            calculationHistory = it.getString("calculationHistory", "") ?: ""
            binding.tvHistory.text = calculationHistory
        }

        setupClickListeners()



    }
//save state
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("firstNum", firstNum)
        outState.putString("currentOp", currentOp)
        outState.putBoolean("isNewInput", isNewInput)
        outState.putString("displayText", binding.tvDisplay.text.toString())

    outState.putString("calculationHistory", calculationHistory)

    }
    private fun setupClickListeners() {
        val numButtons = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3, binding.btn4,
            binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9
        )

        numButtons.forEach { btn ->
            btn.setOnClickListener {
                animatButton(it)
                onNumberClicked(btn.text.toString())
            }
        }

        binding.apply{
            btnPlus.setOnClickListener { animatButton(it); prepareOperator("+") }
            btnSub.setOnClickListener { animatButton(it); prepareOperator("-") }
            btnMul.setOnClickListener { animatButton(it); prepareOperator("x") }
            btnDiv.setOnClickListener { animatButton(it); prepareOperator("÷") }
        }

        binding.apply {
            btnEqual.setOnClickListener {
                animatButton(it)
                performCaculation()
                currentOp = ""
            }

            btnAC.setOnClickListener {
                animatButton(it)
                resetCalculator()
            }
            btnDot.setOnClickListener {
                animatButton(it)
                appendDot()
            }

            btnPercent.setOnClickListener {
                animatButton(it)
                val value = binding.tvDisplay.text.toString().toDoubleOrNull() ?: 0.0
                updateUI(value / 100, false)
            }

            btnPlusMinus.setOnClickListener {
                animatButton(it)
                val value = binding.tvDisplay.text.toString().toDoubleOrNull() ?: 0.0
                updateUI(value * 100, false)
            }
            //    so i should have a thing that doesa  thing that does a thing/**/
//19800037
            CustomOperator.setOnClickListener{
                animatButton(it)
                val value = binding.tvDisplay.text.toString().toDoubleOrNull() ?: 0.0
                appendHistory("$value × MyIDNumber", (value * 0.37).toString())
                updateUI(value * 0.37, false)
            }
        }


    }

   private fun appendDot() {
       val currentText = binding.tvDisplay.text.toString()
       val digitCount = currentText.filter { it.isDigit()}.length

       if (!currentText.contains(".") && digitCount <7) {
           binding.tvDisplay.append(".")
           isNewInput = false
       }
    }

    private fun resetCalculator() {
        binding.tvDisplay.text = "0"
       firstNum = 0.0
       currentOp = ""
       isNewInput = true
        calculationHistory = ""
        binding.tvHistory.text = ""
    }

    private fun prepareOperator(op: String) {
       if (currentOp.isNotEmpty() && !isNewInput) {
           performCaculation()
       }
       firstNum = binding.tvDisplay.text.toString().toDoubleOrNull() ?:0.0
       currentOp = op
       isNewInput = true
   }

    private fun performCaculation() {
        val secondNumString = binding.tvDisplay.text.toString()
        if (currentOp.isEmpty() || secondNumString.isEmpty()) return

        val secondNum = secondNumString.toDoubleOrNull() ?: 0.0
        var result = 0.0
        var hasError = false

        when(currentOp) {
            "+" -> result = firstNum + secondNum
            "-" -> result = firstNum - secondNum
            "x" -> result = firstNum * secondNum
            "÷" -> {
                if(secondNum == 0.0) hasError = true
                else result = firstNum / secondNum
            }

        }
        updateUI(result, hasError)


//history
        val formattedResult = if (result % 1 == 0.0) result.toLong().toString()
        else String.format(Locale.US, "%.4f", result)
        appendHistory("$firstNum $currentOp $secondNum", formattedResult)
        updateUI(result, hasError)
    }

    private fun updateUI(result: Double, hasError: Boolean) {
        if(hasError) {
            binding.tvDisplay.text = "Cannot divide by zero!"
            firstNum = 0.0
        }else{
            val formatted = if (result % 1 == 0.0) {
                result.toLong().toString()
            }else{
                String.format(Locale.US, "%.4f", result).toDouble().toString()
            }
            binding.tvDisplay.text = formatted
            firstNum = result
        }
        isNewInput = true
    }

    private fun onNumberClicked(number: String) {
        val currentText = binding.tvDisplay.text.toString()

        if (isNewInput || currentText == "Error"){
            binding.tvDisplay.text = number
            isNewInput = false

        }else {
            val digitCount = currentText.filter { it.isDigit()}.length
            if(digitCount < 7) {
                binding.tvDisplay.append(number)
            }else {
                binding.tvDisplay.performHapticFeedback(HapticFeedbackConstants.REJECT)
            }
        }
    }


    private fun animatButton(view: View) {
        view.animate()
            .scaleX(0.9f)
            .scaleY(0.9f)
            .setDuration(50)
            .withEndAction {
                view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(50).start()
            }
            .start()


    }



}



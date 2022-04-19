package com.example.tipcalculator

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonCalculate.setOnClickListener{ calculateTip() }
    }

    private fun calculateTip() {
        val inputCostText = binding.edittextBillAmount.text.toString()
        val inputCost = inputCostText.toDoubleOrNull()
        if(inputCost == null || inputCost < 0.0)
        {
            Toast.makeText(this,"Please enter a valid bill amount", Toast.LENGTH_SHORT).show()
            displayResults(0.00, 0.00)
            return
        }
        val selectedTip = when(binding.radioTipOptions.checkedRadioButtonId) {
            R.id.radiob_guilty-> 0.25
            R.id.radiob_excellent -> 0.2
            R.id.radiob_good -> 0.15
            R.id.radiob_bad -> 0.1
            else -> 0.0
        }

        var tip = inputCost * selectedTip
        var total = inputCost + tip
        if (binding.switchRound.isChecked) {
            total = kotlin.math.ceil(total)
            tip = total - inputCost
        }

        displayResults(tip, total)
    }

    private fun displayResults(tip: Double, total: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        val formattedTotal = NumberFormat.getCurrencyInstance().format(total)
        binding.textResultNumber.text = formattedTip.toString()
        binding.textTotalNumber.text = formattedTotal.toString()
    }
}
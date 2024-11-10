package com.example.metricapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var inputValue: EditText
    private lateinit var inputUnit: Spinner
    private lateinit var outputUnit: Spinner
    private lateinit var convertButton: Button
    private lateinit var outputValue: TextView

    private val units = arrayOf("Milimeter", "Sentimeter", "Meter", "Kilometer")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputValue = findViewById(R.id.inputValue)
        inputUnit = findViewById(R.id.inputUnit)
        outputUnit = findViewById(R.id.outputUnit)
        convertButton = findViewById(R.id.convertButton)
        outputValue = findViewById(R.id.outputValue)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, units)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        inputUnit.adapter = adapter
        outputUnit.adapter = adapter

        convertButton.setOnClickListener { convert() }
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    private fun convert() {
        val value = inputValue.text.toString().toDoubleOrNull()
        if (value == null) {
            outputValue.text = "Invalid input"
            return
        }

        val inputUnitIndex = inputUnit.selectedItemPosition
        val outputUnitIndex = outputUnit.selectedItemPosition

        val valueInMeters = when (inputUnitIndex) {
            0 -> value / 1000 // Milimeter to Meter
            1 -> value / 100 // Sentimeter to Meter
            2 -> value // Meter
            3 -> value * 1000 // Kilometer to Meter
            else -> value
        }

        val convertedValue = when (outputUnitIndex) {
            0 -> valueInMeters * 1000 // Meter to Milimeter
            1 -> valueInMeters * 100 // Meter to Sentimeter
            2 -> valueInMeters // Meter to Meter
            3 -> valueInMeters / 1000 // Meter to Kilometer
            else -> valueInMeters
        }

        outputValue.text = String.format("%.2f", convertedValue)
    }
}
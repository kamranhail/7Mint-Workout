package com.example.excersiceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.excersiceapp.databinding.ActivityBmiBinding
import com.example.excersiceapp.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.math.RoundingMode

class ActivityBMI : AppCompatActivity() {
    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW" // Metric Unit View
        private const val US_UNITS_VIEW = "US_UNIT_VIEW" // US Unit View
    }
    private var currentVisibleView: String =
        METRIC_UNITS_VIEW
    private  var binding : ActivityBmiBinding? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.tbBMIactivity)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }


        binding?.tbBMIactivity?.setNavigationOnClickListener {

            onBackPressed()

        }

        makeVisibleMetricUnitsView()
        // END
        //
        // START
        // Radio Group change listener is set to the radio group which is added in XML.
        //we use _ for the first value because we don't need it
        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->

            // Here if the checkId is METRIC UNITS view then make the view visible else US UNITS view.
            if (checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUsUnitsView()
            }



            binding?.btnCalculateUnits?.setOnClickListener {
               calculateUnits()

            }
        }
    }

    private fun calculateUnits(){

        if (currentVisibleView == METRIC_UNITS_VIEW) {
            // The values are validated.
            if (ValidateMetricUint()) {

                // The height value is converted to float value and divided by 100 to convert it to meter.
                val heightValue: Float = binding?.etMetericUnitheight?.text.toString().toFloat() / 100

                // The weight value is converted to float value
                val weightValue: Float = binding?.etMetericUnitweight?.text.toString().toFloat()

                // BMI value is calculated in METRIC UNITS using the height and weight value.
                val bmi = weightValue / (heightValue * heightValue)

                displayBMIResult(bmi)
            } else {
                Toast.makeText(
                    this@ActivityBMI,
                    "Please enter valid values.",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        } else {

            // The values are validated.
            if (validateUsUnits()) {

                val usUnitHeightValueFeet: String =
                    binding?.etusUnitfeet?.text.toString() // Height Feet value entered in EditText component.
                val usUnitHeightValueInch: String =
                    binding?.etusUnitinch?.text.toString() // Height Inch value entered in EditText component.
                val usUnitWeightValue: Float = binding?.etMetericUnitweight?.text.toString()
                    .toFloat() // Weight value entered in EditText component.

                // Here the Height Feet and Inch values are merged and multiplied by 12 for converting it to inches.
                val heightValue =
                    usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12

                // This is the Formula for US UNITS result.
                // Reference Link : https://www.cdc.gov/healthyweight/assessing/bmi/childrens_bmi/childrens_bmi_formula.html
                val bmi = 703 * (usUnitWeightValue / (heightValue * heightValue))

                displayBMIResult(bmi) // Displaying the result into UI
            } else {
                Toast.makeText(
                    this@ActivityBMI,
                    "Please enter valid values.",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }



        private fun makeVisibleMetricUnitsView() {
            currentVisibleView = METRIC_UNITS_VIEW // Current View is updated here.

            binding?.llymtric?.visibility = View.VISIBLE // METRIC  Weight UNITS VIEW is Visible
            binding?.llyusunits?.visibility = View.GONE // make weight view Gone.


            binding?.etusUnitinch?.text!!.clear() // height value is cleared if it is added.
            binding?.etMetericUnitheight?.text!!.clear() // weight value is cleared if it is added.
            binding?.etMetericUnitweight?.text!!.clear() // height value is cleared if it is added.
            binding?.etusUnitfeet?.text!!.clear()
            binding?.llDiplayBMIResult?.visibility = View.INVISIBLE
        }
        // END

        //
        // START
        private fun makeVisibleUsUnitsView() {
            currentVisibleView = US_UNITS_VIEW // Current View is updated here.
            binding?.llymtric?.visibility = View.GONE // METRIC  Weight UNITS VIEW is Visible
            binding?.llyusunits?.visibility = View.VISIBLE // make weight view Gone.

            binding?.etusUnitinch?.text!!.clear() // height value is cleared if it is added.
            binding?.etMetericUnitheight?.text!!.clear() // weight value is cleared if it is added.
            binding?.etMetericUnitweight?.text!!.clear() // height value is cleared if it is added.
            binding?.etusUnitfeet?.text!!.clear()
            binding?.llDiplayBMIResult?.visibility = View.INVISIBLE
        }
    private fun displayBMIResult(bmi : Float)
    {

        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        //Use to set the result layout visible
        binding?.llDiplayBMIResult?.visibility = View.VISIBLE

        // This is used to round the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2,
            RoundingMode.HALF_EVEN).toString()

        binding?.tvBMIValue?.text = bmiValue // Value is set to TextView
        binding?.tvBMIType?.text = bmiLabel // Label is set to TextView
        binding?.tvBMIDescription?.text = bmiDescription // Description is set to TextView

    }
    private  fun ValidateMetricUint() : Boolean
 {
var isValid =true
     if(binding?.etMetericUnitweight?.text.toString().isEmpty())
     {
         isValid=false
     }
     if(binding?.etMetericUnitheight?.text.toString().isEmpty())
     {
         isValid=false
     }

return  isValid
 }
    private fun validateUsUnits(): Boolean {
        var isValid = true

        when {
            binding?.etMetericUnitweight?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etusUnitfeet?.text.toString().isEmpty() -> {
                isValid = false
            }
            binding?.etusUnitinch?.text.toString().isEmpty() -> {
                isValid = false
            }
        }

        return isValid
    }
}





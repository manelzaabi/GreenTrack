package tn.esprit.formation.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.formtaion.R
import tn.esprit.formtaion.model.ConsomationResponse
import tn.esprit.formtaion.model.TotalResponse


import java.text.DecimalFormat

class DomesticFragment : Fragment() {

    private lateinit var editTextElectricityConsumption: EditText
    private lateinit var editTextGasConsumption: EditText
    private lateinit var btnCalculateDomestic: Button
    private lateinit var textViewDomesticResultLabel: TextView
    private lateinit var textViewDomesticResult: TextView
    private lateinit var textViewTotalCarbon: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_domestic, container, false)

        // Initialize views
        editTextElectricityConsumption = view.findViewById(R.id.editTextElectricityConsumption)
        editTextGasConsumption = view.findViewById(R.id.editTextGasConsumption)
        btnCalculateDomestic = view.findViewById(R.id.btnCalculateDomestic)
        textViewDomesticResultLabel = view.findViewById(R.id.textViewDomesticResultLabel)
        textViewDomesticResult = view.findViewById(R.id.textViewDomesticResult)
        textViewTotalCarbon = view.findViewById(R.id.textViewTotalCarbon)

        // Add a click listener to the button
        btnCalculateDomestic.setOnClickListener {
            calculateAndDisplayResult()
            editTextElectricityConsumption.setText("")
            editTextGasConsumption.setText("")
            //textViewtotal.text = Aff().toString()  // Uncomment if needed
        }

        fetchAndDisplayTotalCarbon()  // Move this line here

        return view
    }

    private fun calculateAndDisplayResult() {
        // Get values from input fields
        val electricityConsumption =
            editTextElectricityConsumption.text.toString().toDoubleOrNull() ?: 0.0
        val gasConsumption = editTextGasConsumption.text.toString().toDoubleOrNull() ?: 0.0

        // Calculate carbon footprint
        val carbonFootprint = calculateCarbonFootprint(electricityConsumption, gasConsumption)

        // Display the result in the result field
        val formattedResult = DecimalFormat("#.##").format(carbonFootprint)
        textViewDomesticResult.text = "$formattedResult kg CO2"

        // Call the API to add the calculated value
        doAdd(carbonFootprint, requireContext())

        // Refresh the total carbon after the calculation
        fetchAndDisplayTotalCarbon()
    }

    private fun calculateCarbonFootprint(
        electricityConsumption: Double,
        gasConsumption: Double
    ): Double {
        // Constants for emission factors (arbitrary values for illustration)
        val electricityEmissionFactor = 0.5
        val gasEmissionFactor = 1.8

        // Calculate carbon footprint
        val carbonFootprintElectricity = electricityConsumption * electricityEmissionFactor
        val carbonFootprintGas = gasConsumption * gasEmissionFactor

        // Total carbon footprint
        return carbonFootprintElectricity + carbonFootprintGas
    }

    private fun fetchAndDisplayTotalCarbon() {
        // Call the API to get the total carbon
        ApiService.consommationService.getTotalCarbon()
            .enqueue(object : Callback<TotalResponse> {
                override fun onResponse(
                    call: Call<TotalResponse>,
                    response: Response<TotalResponse>
                ) {
                    if (response.isSuccessful) {
                        val totalCarbon = response.body()?.total
                        if (totalCarbon != null) {
                            textViewTotalCarbon.text = "Total du carbone : $totalCarbon kg CO2"
                        } else {
                            Log.d("RESPONSE ERROR", "Total carbon is null")
                        }
                    } else {
                        // Handle errors here
                        Log.d("HTTP ERROR", "status code is " + response.code())
                    }
                }

                override fun onFailure(call: Call<TotalResponse>, t: Throwable) {
                    // Handle connection errors here
                    Log.d("FAIL", "fail server $t")
                }
            })
    }

    private fun doAdd(valeur: Double, context: Context) {
        val gson = Gson()

        ApiService.consommationService.add(
            ConsommationService.ConsommationBody(
                "Domestique",
                valeur
            )
        ).enqueue(
            object : Callback<ConsomationResponse> {
                override fun onResponse(
                    call: Call<ConsomationResponse>,
                    response: Response<ConsomationResponse>
                ) {
                    if (response.isSuccessful) {
                        val token = gson.toJson(response.body())
                        val jsonObject = JSONTokener(token).nextValue() as JSONObject
                        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.d("HTTP ERROR", "status code is " + response.code())
                        Toast.makeText(
                            context,
                            "Please Check Your Information",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(
                    call: Call<ConsomationResponse>,
                    t: Throwable
                ) {
                    Log.d("FAIL", "fail server $t")
                    Toast.makeText(context, "Connection error", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}

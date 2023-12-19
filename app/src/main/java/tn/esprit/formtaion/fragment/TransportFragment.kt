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

class TransportFragment : Fragment() {

    private lateinit var editTextTransport: EditText
    private lateinit var btnCalculateTransport: Button
    private lateinit var textViewTransportResultLabel: TextView
    private lateinit var textViewTransportResult: TextView
    private lateinit var textViewTotalCarbon: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tansport, container, false)

        // Initialize the views
        editTextTransport = view.findViewById(R.id.editTextTransport)
        btnCalculateTransport = view.findViewById(R.id.btnCalculateTransport)
        textViewTransportResultLabel = view.findViewById(R.id.textViewTransportResultLabel)
        textViewTransportResult = view.findViewById(R.id.textViewTransportResult)
        textViewTotalCarbon = view.findViewById(R.id.textViewTotalCarbon)

        // Add a click listener to the button to trigger the calculation
        btnCalculateTransport.setOnClickListener {
            calculateAndDisplayResult()
            editTextTransport.setText("")
        }
        fetchAndDisplayTotalCarbon()
        return view
    }

    private fun calculateAndDisplayResult() {
        // Get values from input fields
        val distance = editTextTransport.text.toString().toDoubleOrNull() ?: 0.0

        // Call the carbon footprint calculation function
        val carbonFootprint = calculateCarbonFootprint(distance)

        // Display the result in the result field
        val formattedResult = DecimalFormat("#.##").format(carbonFootprint)
        textViewTransportResult.text = "$formattedResult kg CO2"

        // Call the API to add the calculated value
        doAdd(carbonFootprint, requireContext())
        fetchAndDisplayTotalCarbon()
    }

    private fun calculateCarbonFootprint(distance: Double): Double {
        // Constants for emission factors (arbitrary values for illustration)
        val distanceFactor = 0.5

        // Calculate carbon footprint
        return distance * distanceFactor
    }

    private fun doAdd(value: Double, context: Context) {
        val gson = Gson()

        ApiService.consommationService.add(
            ConsommationService.ConsommationBody(
                "transport",
                value
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
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()

                    } else {
                        Log.d("HTTP ERROR", "status code is " + response.code())
                        Toast.makeText(context, "Please Check Your Information", Toast.LENGTH_SHORT).show()
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

    private fun fetchAndDisplayTotalCarbon() {
        // Call the API to get the total carbon
        ApiService.consommationService.getTotalCarbon().enqueue(object : Callback<TotalResponse> {
            override fun onResponse(call: Call<TotalResponse>, response: Response<TotalResponse>) {
                if (response.isSuccessful) {
                    val totalCarbon = response.body()?.total
                    // Display the total carbon in your TextView

                    textViewTotalCarbon.text = "Total du carbone : $totalCarbon kg CO2"
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
}



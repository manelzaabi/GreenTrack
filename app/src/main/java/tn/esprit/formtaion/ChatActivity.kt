package tn.esprit.formtaion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import tn.esprit.formtaion.R
import java.io.IOException

class ChatActivity :  AppCompatActivity() {
    private val client = OkHttpClient()
    lateinit var txtResponse: TextView
    lateinit var idTVQuestion: TextView
    lateinit var etQuestion: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        etQuestion = findViewById(R.id.etQuestion)
        idTVQuestion = findViewById(R.id.idTVQuestion)
        txtResponse = findViewById(R.id.txtResponse)

        etQuestion.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                txtResponse.text = "Please wait.."
                val question = etQuestion.text.toString().trim()
                Toast.makeText(this, question, Toast.LENGTH_SHORT).show()
                if (question.isNotEmpty()) {
                    getResponse(question) { response ->
                        runOnUiThread {
                            txtResponse.text = response
                        }
                    }
                }
                return@setOnEditorActionListener true
            }
            false
        }
    }

    fun getResponse(question: String, callback: (String) -> Unit) {
        idTVQuestion.text = question
        etQuestion.setText("")

        val apiKey = "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"
        val url = "https://api.openai.com/v1/chat/completions"

        val requestBody = """
            {
                "prompt": "$question",
                "max_tokens": 500,
                "temperature": 0
            }
        """.trimIndent()

        val request = Request.Builder()
            .url(url)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $apiKey")
            .post(requestBody.toRequestBody("application/json".toMediaTypeOrNull()))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", "API failed", e)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val body = response.body?.string()
                    if (body != null) {
                        Log.v("data", body)
                        val jsonObject = JSONObject(body)

                        if (jsonObject.has("choices")) {
                            val jsonArray: JSONArray = jsonObject.getJSONArray("choices")

                            if (jsonArray.length() > 0) {
                                val textResult = jsonArray.getJSONObject(0).getString("text")
                                callback(textResult)
                            } else {
                                Log.e("error", "Empty array for 'choices'")
                            }
                        } else {
                            Log.e("error", "Key 'choices' not found in JSON")
                        }
                    } else {
                        Log.v("data", "empty")
                    }
                } catch (e: Exception) {
                    Log.e("error", "Exception", e)
                }
            }
        })
    }
}
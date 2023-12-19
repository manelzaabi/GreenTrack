package tn.esprit.formtaion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val scanningLayout = findViewById<LinearLayout>(R.id.id_scanning)
        scanningLayout.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }

        val ecoPointLayout = findViewById<LinearLayout>(R.id.id_eco_point)
        ecoPointLayout.setOnClickListener {
            val intent = Intent(this, FourthActivity::class.java)
            startActivity(intent)
        }

//        val mapLayout = findViewById<LinearLayout>(R.id.id_map)
//        mapLayout.setOnClickListener {
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//        }


        val chatLayout = findViewById<LinearLayout>(R.id.id_chat)
        chatLayout.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
    }
}

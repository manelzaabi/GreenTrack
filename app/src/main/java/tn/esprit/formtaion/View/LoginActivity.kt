package tn.esprit.formtaion.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import tn.esprit.formtaion.R
import tn.esprit.formtaion.databinding.ActivityRegisterBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Récupérer la référence à votre ConstraintLayout
        val allerRegisterConstraintLayout = findViewById<ConstraintLayout>(R.id.allerRegister)

        // Ajouter un OnClickListener pour démarrer l'ActivityRegister
        allerRegisterConstraintLayout.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            //startActivity(intent)
        }
    }
}
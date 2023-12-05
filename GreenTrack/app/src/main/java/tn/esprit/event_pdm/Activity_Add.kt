package tn.esprit.event_pdm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.widget.DatePicker

class Activity_Add : AppCompatActivity() {


    private var collector = ""

    private lateinit var titletxt: EditText
    private lateinit var desctxt: EditText
    private lateinit var loctxt : EditText
    private lateinit var UserComment: EditText
    private lateinit var SubmitSave: Button
    private lateinit var Freebtn: RadioButton
    private lateinit var Paidbtn: RadioButton
    private lateinit var datePicker: DatePicker


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)


        titletxt = findViewById(R.id.titletxt)
        desctxt = findViewById(R.id.desctxt)
        loctxt = findViewById(R.id.loctxt)
        UserComment = findViewById(R.id.usercomment)
        Freebtn = findViewById(R.id.free)
        Paidbtn = findViewById(R.id.paid)
        datePicker = findViewById(R.id.datePicker)
        SubmitSave = findViewById(R.id.btnSubmit)

        SubmitSave.setOnClickListener {
            val titletxt = titletxt.text.toString()
            val desctxt = desctxt.text.toString()
            val loctxt = loctxt.text.toString()
            val comment = UserComment.text.toString()
            val day = datePicker.dayOfMonth
            val month = datePicker.month + 1
            val year = datePicker.year

            val selectedDate = "$day/$month/$year"

            if (titletxt.isEmpty()) {
                Toast.makeText(this@Activity_Add, "Please fill the title field", Toast.LENGTH_SHORT).show()
            } else if (desctxt.isEmpty()) {
                Toast.makeText(this@Activity_Add, "Please fill the description field", Toast.LENGTH_SHORT).show()
            } else if (loctxt.isEmpty()) {
                Toast.makeText(this@Activity_Add, "Please fill the location field", Toast.LENGTH_SHORT).show()
            } else if (comment.isEmpty()) {
                Toast.makeText(this@Activity_Add, "Please fill the Comment field", Toast.LENGTH_SHORT).show()
            } else {
                collector += "$titletxt\n$desctxt\n$selectedDate\n$loctxt\n$comment\n"

                Toast.makeText(this@Activity_Add, "Event Info:\n$collector", Toast.LENGTH_SHORT).show()
            }
        }




    }
}

package com.uesvaz.pr14_comunicacionsambwebsockets

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class UserIdentificationActivity : AppCompatActivity() {

    private lateinit var usernameInput: EditText
    private lateinit var continueButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_identification)

        usernameInput = findViewById(R.id.usernameInput)
        continueButton = findViewById(R.id.continueButton)

        continueButton.setOnClickListener {
            val username = usernameInput.text.toString().trim()

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("username", username)
            startActivity(intent) //Cambiamos la vista




        }
    }

}

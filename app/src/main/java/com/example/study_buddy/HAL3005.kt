package com.example.study_buddy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HAL3005 : AppCompatActivity() {
    lateinit var displayName : TextView
    lateinit var button5 : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hal3005)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        displayName = findViewById(R.id.displayName)
        button5 = findViewById(R.id.backButton)
        val usernameTitle = intent.getStringExtra("username2")
        displayName.text = "Thanks for visiting ${usernameTitle ?: "Userguy"}"

        button5.setOnClickListener {
            val intentBack = Intent(this, MainActivity::class.java)

            startActivity(intentBack)
            finish()
        }

    }
}
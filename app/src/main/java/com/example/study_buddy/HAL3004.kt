package com.example.study_buddy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HAL3004 : AppCompatActivity() {
    lateinit var titleUsername : TextView
    lateinit var button4 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hal3004)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        titleUsername = findViewById(R.id.testView)
        button4 = findViewById(R.id.nextButton2)

        val name = intent.getStringExtra("username")
        titleUsername.text = "Hello. ${name ?: "userguy"}."

        button4.setOnClickListener {
            val hal3005 = Intent(this, hal3005::class.java)
            hal3005.putExtra("username2", name)
            startActivity(hal3005)
        }
    }
}
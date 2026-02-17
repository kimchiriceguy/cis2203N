package com.example.study_buddy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HAL3002 : AppCompatActivity() {
    lateinit var titleUsername : TextView
    lateinit var button2 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hal3002)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        titleUsername = findViewById(R.id.titleView)
        button2 = findViewById(R.id.nextButton)

        val name = intent.getStringExtra("username")
        titleUsername.text = "Hello. ${name ?: "userguy"}."

        button2.setOnClickListener {
            val hal3003 = Intent(this, HAL3003::class.java)
            hal3003.putExtra("username2", name)
            startActivity(hal3003)
        }
    }
}
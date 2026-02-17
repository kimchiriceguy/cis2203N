package com.example.study_buddy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HAL3003 : AppCompatActivity() {
    lateinit var titleUsername : TextView
    lateinit var button3 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hal3003)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        titleUsername = findViewById(R.id.testView)
        button3 = findViewById(R.id.button3)

        val name = intent.getStringExtra("username2")
        titleUsername.text = "I'm afraid I can't do that, ${name ?: "userguy"}."

        button3.setOnClickListener {
            val hal3004 = Intent(this, HAL3004::class.java)
            hal3004.putExtra("username2", name)
            startActivity(hal3004)
        }
    }
}
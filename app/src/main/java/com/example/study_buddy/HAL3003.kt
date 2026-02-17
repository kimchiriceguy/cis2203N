package com.example.study_buddy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HAL3003 : AppCompatActivity() {

    private lateinit var titleUsername: TextView
    private lateinit var button3: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hal3003)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val items = listOf(
            Item("Joe Smoe", "J@gmail.com", R.drawable.a),
            Item("Doe Smoe", "D@gmail.com", R.drawable.b),
            Item("Poe Smoe", "P@gmail.com", R.drawable.c),
            Item("Coe Smoe", "C@gmail.com", R.drawable.d),
            Item("Zoe Smoe", "Z@gmail.com", R.drawable.e),
            Item("Loe Smoe", "L@gmail.com", R.drawable.f)
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter(items)

        titleUsername = findViewById(R.id.testView)
        button3 = findViewById(R.id.button3)

        val name = intent.getStringExtra("username2")
        titleUsername.text = "I'm afraid I can't do that, ${name ?: "userguy"}."

        button3.setOnClickListener {
            val intent = Intent(this, HAL3004::class.java)
            intent.putExtra("username2", name)
            startActivity(intent)
        }
    }
}

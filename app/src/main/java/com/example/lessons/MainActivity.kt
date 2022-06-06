package com.example.lessons

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lessons.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager.beginTransaction()
            .replace(R.id.placeholder, ContactsFragment(), null)
            .commit()

    }
}

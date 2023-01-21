package com.iwrrr.mapspolyline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.iwrrr.mapspolyline.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding

    private var counter = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCounter.setOnClickListener {
            counter++
            binding.tvCounter.text = "$counter"

            println("---------- COUNTER -> $counter ----------")
        }
    }
}
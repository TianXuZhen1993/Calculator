package com.example.calculator.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private var process: String = ""
    private var result: String = ""
    private var firstNum = StringBuilder()
    private var secondNum = StringBuilder()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initClick()
    }

    fun initClick() {
        binding.btnPoint.setOnClickListener {

        }
    }
}


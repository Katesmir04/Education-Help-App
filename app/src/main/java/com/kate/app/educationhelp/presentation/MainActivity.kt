package com.kate.app.educationhelp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.kate.app.educationhelp.R
import com.kate.app.educationhelp.data.repositories.MyBackendRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
           Log.d("KEK", MyBackendRepository.getAllTopics().toString())
        }
    }
}
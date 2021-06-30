package com.example.fcm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.fcm.databinding.ActivityMainBinding
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFirebase()
    }

    private fun initFirebase() {
        // 현재 등록된 토큰 가져오기
        FirebaseMessaging.getInstance().token.
                addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        binding.firebaseTokenTextView.text = task.result
                    } else {
                        binding.firebaseTokenTextView.text = "Error"
                    }
                }
    }
}
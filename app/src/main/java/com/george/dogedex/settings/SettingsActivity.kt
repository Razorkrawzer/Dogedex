package com.george.dogedex.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.george.dogedex.R
import com.george.dogedex.User
import com.george.dogedex.auth.LoginActivity
import com.george.dogedex.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logoutButton.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        User.logout(this)
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
package com.org.callguard.ui.admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.org.callguard.databinding.ActivityAdminLoginBinding
import com.org.callguard.sync.LoginRequest
import com.org.callguard.sync.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdminLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("admin_prefs", Context.MODE_PRIVATE)
        if (prefs.getString("token", null) != null) {
            startDashboard()
            return
        }

        binding.loginButton.setOnClickListener {
            performAuth(isLogin = true)
        }

        binding.registerButton.setOnClickListener {
            performAuth(isLogin = false)
        }
    }

    private fun performAuth(isLogin: Boolean) {
        val username = binding.usernameInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter username and password")
            return
        }

        setLoading(true)
        
        lifecycleScope.launch {
            try {
                val req = LoginRequest(username, password)
                val response = withContext(Dispatchers.IO) {
                    if (isLogin) {
                        NetworkModule.adminApi.login(req)
                    } else {
                        NetworkModule.adminApi.register(req)
                    }
                }

                if (response.isSuccessful && response.body() != null) {
                    val token = response.body()!!.token
                    getSharedPreferences("admin_prefs", Context.MODE_PRIVATE)
                        .edit()
                        .putString("token", token)
                        .apply()
                    startDashboard()
                } else {
                    showError("Authentication failed: ${response.code()}")
                }
            } catch (e: Exception) {
                showError(e.message ?: "Network error")
            } finally {
                setLoading(false)
            }
        }
    }

    private fun startDashboard() {
        startActivity(Intent(this, AdminDashboardActivity::class.java))
        finish()
    }

    private fun showError(msg: String) {
        binding.errorText.text = msg
        binding.errorText.visibility = View.VISIBLE
    }

    private fun setLoading(isLoading: Boolean) {
        binding.loginButton.isEnabled = !isLoading
        binding.registerButton.isEnabled = !isLoading
        if (isLoading) {
            binding.loginButton.text = "Processing..."
        } else {
            binding.loginButton.text = "Sign In"
        }
    }
}

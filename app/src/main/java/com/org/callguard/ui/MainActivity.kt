package com.org.callguard.ui

import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.telecom.TelecomManager
import android.text.format.DateFormat
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.org.callguard.R
import com.org.callguard.databinding.ActivityMainBinding
import com.org.callguard.sync.BlocklistSyncWorker
import kotlinx.coroutines.launch
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private val roleRequestLauncher = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
    ) {
        updateRoleStatus()
    }

    private val permissionLauncher = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.RequestPermission()
    ) {
        // Continue regardless of permission result
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Request READ_CONTACTS permission to allow screening numbers saved in the phonebook
        if (androidx.core.content.ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(android.Manifest.permission.READ_CONTACTS)
        }

        binding.roleStatusText.setOnClickListener { requestCallScreeningRole() }

        binding.syncNowButton.setOnClickListener {
            viewModel.syncNow()
            BlocklistSyncWorker.triggerImmediate(this)
        }

        binding.contactItButton.setOnClickListener { openContactIt() }

        binding.adminPortalButton.setOnClickListener {
            startActivity(Intent(this, com.org.callguard.ui.admin.AdminLoginActivity::class.java))
        }

        observeUiState()
    }

    override fun onResume() {
        super.onResume()
        updateRoleStatus()
        viewModel.refreshStatus()
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.blocklistVersionText.text = state.currentVersion.toString()

                    val lastSync = state.syncStatus?.lastSuccessfulSync
                    binding.lastUpdateText.text = if (lastSync != null) {
                        DateFormat.format("dd MMM yyyy, HH:mm", Date(lastSync)).toString()
                    } else {
                        getString(R.string.never)
                    }

                    binding.syncStatusMessage.text = when {
                        state.syncing -> getString(R.string.sync_in_progress)
                        state.syncMessage != null -> state.syncMessage
                        else -> ""
                    }
                }
            }
        }
    }

    private fun updateRoleStatus() {
        val granted = isCallScreeningRoleHeld()
        binding.roleStatusText.text = if (granted) {
            getString(R.string.role_granted)
        } else {
            getString(R.string.role_not_granted)
        }

        if (granted) {
            binding.statusIcon.text = "✅"
            binding.statusText.text = getString(R.string.status_protected)
        } else {
            binding.statusIcon.text = "⚠️"
            binding.statusText.text = getString(R.string.status_action_needed)
        }
    }

    private fun isCallScreeningRoleHeld(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return false
        val roleManager = getSystemService(RoleManager::class.java)
        return roleManager?.isRoleHeld(RoleManager.ROLE_CALL_SCREENING) ?: false
    }

    private fun requestCallScreeningRole() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) return
        val roleManager = getSystemService(RoleManager::class.java) ?: return

        if (roleManager.isRoleAvailable(RoleManager.ROLE_CALL_SCREENING) &&
            !roleManager.isRoleHeld(RoleManager.ROLE_CALL_SCREENING)
        ) {
            val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING)
            roleRequestLauncher.launch(intent)
        }
    }

    private fun openContactIt() {
        // TODO: replace with org SOC/helpdesk contact (number or email).
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:+917895765330")
        }
        startActivity(intent)
    }
}

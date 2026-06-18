package com.org.callguard.ui.admin

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.org.callguard.R
import com.org.callguard.databinding.ActivityAdminDashboardBinding
import com.org.callguard.sync.BlocklistSyncWorker
import com.org.callguard.sync.ContactEntry
import com.org.callguard.sync.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminDashboardBinding
    private lateinit var adapter: ContactAdapter
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("admin_prefs", Context.MODE_PRIVATE)
        token = prefs.getString("token", "") ?: ""

        if (token.isEmpty()) {
            finish()
            return
        }

        binding.toolbar.title = "Admin Dashboard"
        
        adapter = ContactAdapter(emptyList()) { contact ->
            deleteContact(contact)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.fabAdd.setOnClickListener {
            showAddContactDialog()
        }

        loadContacts()
    }

    private fun loadContacts() {
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    NetworkModule.adminApi.getNumbers("Bearer $token")
                }
                if (response.isSuccessful) {
                    val list = response.body() ?: emptyList()
                    adapter.updateData(list)
                } else {
                    Toast.makeText(this@AdminDashboardActivity, "Failed to load contacts", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@AdminDashboardActivity, "Network Error", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun showAddContactDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_contact, null)
        val numberInput = dialogView.findViewById<EditText>(R.id.numberInput)
        val labelInput = dialogView.findViewById<EditText>(R.id.labelInput)
        val radioBlocked = dialogView.findViewById<RadioButton>(R.id.radioBlocked)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialogView.findViewById<View>(R.id.btnCancel).setOnClickListener {
            dialog.dismiss()
        }

        dialogView.findViewById<View>(R.id.btnSave).setOnClickListener {
            val num = numberInput.text.toString().trim()
            if (num.isEmpty()) {
                Toast.makeText(this, "Number is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val isBlocked = radioBlocked.isChecked
            val contact = ContactEntry(
                id = 0,
                normalizedNumber = num,
                displayLabel = labelInput.text.toString().trim(),
                type = if (isBlocked) "BLOCKED" else "ALLOWLISTED",
                reasonCode = if (isBlocked) "FRAUD" else null,
                severity = if (isBlocked) "HIGH" else null,
                category = if (!isBlocked) "SOC" else null
            )

            dialog.dismiss()
            addContact(contact)
        }

        dialog.show()
    }

    private fun addContact(contact: ContactEntry) {
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    NetworkModule.adminApi.addNumber("Bearer $token", contact)
                }
                if (response.isSuccessful) {
                    Toast.makeText(this@AdminDashboardActivity, "Added successfully", Toast.LENGTH_SHORT).show()
                    loadContacts()
                    BlocklistSyncWorker.triggerImmediate(this@AdminDashboardActivity)
                } else {
                    Toast.makeText(this@AdminDashboardActivity, "Failed to add", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@AdminDashboardActivity, "Network Error", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun deleteContact(contact: ContactEntry) {
        AlertDialog.Builder(this)
            .setTitle("Delete Contact")
            .setMessage("Are you sure you want to delete ${contact.normalizedNumber}?")
            .setPositiveButton("Delete") { _, _ ->
                binding.progressBar.visibility = View.VISIBLE
                lifecycleScope.launch {
                    try {
                        val response = withContext(Dispatchers.IO) {
                            NetworkModule.adminApi.deleteNumber("Bearer $token", contact.id)
                        }
                        if (response.isSuccessful) {
                            Toast.makeText(this@AdminDashboardActivity, "Deleted", Toast.LENGTH_SHORT).show()
                            loadContacts()
                            BlocklistSyncWorker.triggerImmediate(this@AdminDashboardActivity)
                        } else {
                            Toast.makeText(this@AdminDashboardActivity, "Failed to delete", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this@AdminDashboardActivity, "Network Error", Toast.LENGTH_SHORT).show()
                    } finally {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}

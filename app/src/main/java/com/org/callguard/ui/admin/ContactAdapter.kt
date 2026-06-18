package com.org.callguard.ui.admin

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.org.callguard.databinding.ItemContactBinding
import com.org.callguard.sync.ContactEntry

class ContactAdapter(
    private var contacts: List<ContactEntry>,
    private val onDeleteClick: (ContactEntry) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    fun updateData(newContacts: List<ContactEntry>) {
        contacts = newContacts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount() = contacts.size

    inner class ViewHolder(private val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: ContactEntry) {
            binding.numberText.text = contact.normalizedNumber
            binding.labelText.text = contact.displayLabel ?: "No Label"
            
            if (contact.type == "BLOCKED") {
                binding.typeText.text = "BLOCKED"
                binding.typeText.setBackgroundColor(Color.parseColor("#ef4444")) // danger
                binding.detailText.text = "${contact.severity ?: "N/A"} • ${contact.reasonCode ?: "N/A"}"
            } else {
                binding.typeText.text = "ALLOWLISTED"
                binding.typeText.setBackgroundColor(Color.parseColor("#10b981")) // success
                binding.detailText.text = "${contact.category ?: "N/A"}"
            }

            binding.btnDelete.setOnClickListener {
                onDeleteClick(contact)
            }
        }
    }
}

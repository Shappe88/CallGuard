package com.org.callguard.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.org.callguard.data.db.CallGuardDatabase
import com.org.callguard.data.db.entity.SyncStatusEntity
import com.org.callguard.sync.BlocklistSyncRepository
import com.org.callguard.sync.SyncResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MainUiState(
    val syncStatus: SyncStatusEntity? = null,
    val currentVersion: Long = 0,
    val syncing: Boolean = false,
    val syncMessage: String? = null
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = CallGuardDatabase.getInstance(application)
    private val syncRepo = BlocklistSyncRepository(application, db)

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        refreshStatus()
    }

    fun refreshStatus() {
        viewModelScope.launch {
            val status = db.syncStatusDao().get()
            val version = db.blocklistVersionDao().getCurrentApplied()?.version ?: 0L
            _uiState.value = _uiState.value.copy(syncStatus = status, currentVersion = version)
        }
    }

    fun syncNow() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(syncing = true, syncMessage = null)
            val result = syncRepo.sync()
            val message = when (result) {
                is SyncResult.Updated -> "Updated to version ${result.toVersion} (${result.method})"
                is SyncResult.UpToDate -> "Already up to date (v${result.version})"
                is SyncResult.Rejected -> "Update rejected: ${result.reason} — keeping current list"
                is SyncResult.NetworkError -> "Sync failed: ${result.reason} — using last known good list"
            }
            _uiState.value = _uiState.value.copy(syncing = false, syncMessage = message)
            refreshStatus()
        }
    }
}

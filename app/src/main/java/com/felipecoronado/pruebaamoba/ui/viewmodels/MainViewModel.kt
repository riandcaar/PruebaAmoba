package com.felipecoronado.pruebaamoba.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felipecoronado.pruebaamoba.domain.repositories.LocalRepositoryInterface
import com.felipecoronado.pruebaamoba.domain.repositories.NetworkRepositoryInterface
import com.felipecoronado.pruebaamoba.ui.states.LoginState
import com.felipecoronado.pruebaamoba.ui.utils.ErrorCodes.ERROR_CODE_2
import com.felipecoronado.pruebaamoba.ui.utils.ErrorCodes.ERROR_CODE_3
import com.felipecoronado.pruebaamoba.ui.utils.ErrorCodes.PENDING_CODE
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val localRepository: LocalRepositoryInterface,
    private val networkRepository: NetworkRepositoryInterface,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun setConnectedState(isConnected: Boolean) {
        _state.value = _state.value.copy(isConnected = isConnected)
    }

    fun signIn(email: String, password: String) = viewModelScope.launch {
        val result = networkRepository.signInUser(email, password)
        if (result.isSuccess) {
            result.getOrNull()?.let { fetchDocument(it) }
        } else {
            _state.value = _state.value.copy(errorMessage = result.exceptionOrNull()?.message)
        }
    }

    fun fetchDocument(userUid: String) = viewModelScope.launch {
        _state.value = _state.value.copy(isLoading = true)
        if (state.value.isConnected) {
            val result = networkRepository.fetchUserData(userUid)
            if (result.isSuccess) {
                _state.value = _state.value.copy(
                    signedIn = true, isLoading = false, userPatients = result.getOrDefault(listOf())
                )
                localRepository.savePatientsList(result.getOrDefault(listOf()))
            } else {
                _state.value = _state.value.copy(errorMessage = result.exceptionOrNull()?.message)
            }
        } else {
            val userData = localRepository.fetchPatientList()
            if (userData.isNotEmpty()) {
                _state.value =
                    _state.value.copy(signedIn = true, isLoading = false, userPatients = userData)
            } else {
                _state.value = _state.value.copy(errorMessage = ERROR_CODE_2)
            }
        }
    }

    fun chargePatient() = viewModelScope.launch {
        _state.value = _state.value.copy(isLoading = true)
        val result = networkRepository.chargePatient()
        if (result.isSuccess) {
            _state.value = _state.value.copy(
                isLoading = false,
                transactionInProgress = true,
                transactionId = result.getOrNull()
            )
        } else {
            val exceptionMessage = result.exceptionOrNull()?.message
            if (exceptionMessage == PENDING_CODE) {
                _state.value = _state.value.copy(isLoading = false, transactionInProgress = true)
            } else {
                _state.value = _state.value.copy(errorMessage = ERROR_CODE_3)
            }
        }
    }

    fun checkTransactionStatus() = viewModelScope.launch {
        while (state.value.transactionInProgress) {
            try {
                state.value.transactionId?.let { transactionId ->
                    networkRepository.checkChargeStatus(transactionId.toInt()).collect { result ->
                        if (result.isSuccess) {
                            _state.value = _state.value.copy(
                                transactionInProgress = false,
                                transactionCompleted = true
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    errorMessage = ERROR_CODE_3,
                    transactionInProgress = false,
                    transactionCompleted = false,
                )
            }
        }
    }

    fun newTransaction() {
        _state.value = _state.value.copy(
            transactionInProgress = false,
            transactionCompleted = false,
            //  transactionId = null
        )
    }


    fun signOutUser() {
        FirebaseAuth.getInstance().signOut()
        _state.value =
            _state.value.copy(signedIn = false)
    }

}


package com.SPA.Perfulandia.viewmodel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.SPA.Perfulandia.model.FormData

class FormViewModel : ViewModel() {

    var form by mutableStateOf(FormData())
        private set

    var nameError: String? by mutableStateOf(null)
        private set

    var emailError: String? by mutableStateOf(null)
        private set

    fun onNameChange(new: String) {
        form = form.copy(name = new)
        nameError = validateName(new)
    }

    fun onEmailChange(new: String) {
        form = form.copy(email = new)
        emailError = validateEmail(new)
    }

    fun isValid(): Boolean =
        nameError == null && emailError == null &&
                form.name.isNotBlank() && form.email.isNotBlank()

    private fun validateName(value: String): String? =
        when {
            value.isBlank()      -> "El nombre es obligatorio"
            value.length < 3     -> "Debe tener al menos 3 caracteres"
            else                 -> null
        }

    private fun validateEmail(value: String): String? =
        when {
            value.isBlank() -> "El email es obligatorio"
            !Patterns.EMAIL_ADDRESS.matcher(value).matches() -> "Email no válido"
            else -> null
        }
}
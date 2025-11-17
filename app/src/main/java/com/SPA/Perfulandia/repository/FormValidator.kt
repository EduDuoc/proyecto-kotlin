package com.SPA.Perfulandia.repository

class FormValidator {

    fun validateName(name: String): String? {
        if (name.isBlank()) return "El nombre es obligatorio"
        if (name.length < 3) return "Debe tener al menos 3 caracteres"
        return null
    }

    fun validateEmail(email: String): String? {
        if (email.isBlank()) return "El email es obligatorio"
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return "Correo no válido"
        return null
    }

    fun isFormValid(name: String, email: String): Boolean {
        return validateName(name) == null && validateEmail(email) == null
    }
}

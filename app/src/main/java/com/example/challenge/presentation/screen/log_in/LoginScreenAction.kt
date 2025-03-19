package com.example.challenge.presentation.screen.log_in

sealed class LoginScreenAction {
    data class LogIn(val email: String, val password: String) : LoginScreenAction()
}
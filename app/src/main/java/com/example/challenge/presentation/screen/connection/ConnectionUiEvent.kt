package com.example.challenge.presentation.screen.connection

sealed interface ConnectionUiEvent {
    data object NavigateToLogIn : ConnectionUiEvent
    data class ShowError(val error: String) : ConnectionUiEvent
}
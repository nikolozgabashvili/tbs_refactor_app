package com.example.challenge.presentation.screen.log_in

sealed interface LogInUiEvent {
    data object NavigateToConnections : LogInUiEvent
    data class ShowError(val error:String?):LogInUiEvent
}
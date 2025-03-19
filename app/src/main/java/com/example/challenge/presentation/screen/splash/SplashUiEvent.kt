package com.example.challenge.presentation.screen.splash

sealed interface SplashUiEvent {
    data object NavigateToLogIn : SplashUiEvent
    data object NavigateToConnections : SplashUiEvent
}
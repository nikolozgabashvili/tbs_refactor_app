package com.example.challenge.presentation.state.log_in

import android.moshi.String

data class LogInState(
    val isLoading: Boolean = false,
    val accessToken: String? = null,
    val errorMessage: String? = null
)
package com.example.challenge.domain.user_data_key

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
}
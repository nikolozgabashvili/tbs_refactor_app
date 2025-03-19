package com.example.challenge.presentation.screen.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.challenge.presentation.base.BaseFragment
import com.example.challenge.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {
    private val viewModel: SplashViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun init() {

    }

    override fun bindViewActionListeners() {

    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleNavigationEvents(event = it)
                }
            }
        }
    }

    private fun handleNavigationEvents(event: SplashUiEvent) {
        when (event) {
            SplashUiEvent.NavigateToConnections -> {
                navController.navigate(SplashFragmentDirections.actionSplashFragmentToConnectionsFragment())

            }

            SplashUiEvent.NavigateToLogIn -> {
                navController.navigate(SplashFragmentDirections.actionSplashFragmentToLogInFragment())

            }
        }
    }
}

package com.example.challenge.presentation.screen.log_in

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.challenge.databinding.FragmentLogInBinding
import com.example.challenge.presentation.base.BaseFragment
import com.example.challenge.presentation.extension.showSnackBar
import com.example.challenge.presentation.state.log_in.LogInState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    private val viewModel: LogInViewModel by viewModels()
    private val navController by lazy { findNavController() }

    override fun init() {

    }

    override fun bindViewActionListeners() {
        binding.btnLogIn.setOnClickListener {
            logIn()
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.logInState.collectLatest {
                    handleLogInState(logInState = it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect {
                    handleEvents(event = it)
                }
            }
        }
    }

    private fun logIn() {
        viewModel.onEvent(
            LoginScreenAction.LogIn(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
        )
    }

    private fun handleLogInState(logInState: LogInState) {
        binding.loaderInclude.loaderContainer.visibility =
            if (logInState.isLoading) View.VISIBLE else View.GONE


    }

    private fun handleEvents(event: LogInUiEvent) {
        when (event) {
            is LogInUiEvent.NavigateToConnections -> navController.navigate(
                LogInFragmentDirections.actionLogInFragmentToConnectionsFragment()
            )

            is LogInUiEvent.ShowError -> {
                event.error?.let {
                    binding.root.showSnackBar(message = event.error)
                }
            }


        }
    }
}




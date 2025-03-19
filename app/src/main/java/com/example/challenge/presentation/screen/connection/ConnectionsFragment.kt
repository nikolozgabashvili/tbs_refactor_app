package com.example.challenge.presentation.screen.connection

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge.presentation.base.BaseFragment
import com.example.challenge.databinding.FragmentConnectionsBinding
import com.example.challenge.presentation.extension.showSnackBar
import com.example.challenge.presentation.state.connection.ConnectionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConnectionsFragment :
    BaseFragment<FragmentConnectionsBinding>(FragmentConnectionsBinding::inflate) {

    private val viewModel: ConnectionsViewModel by viewModels()
    private val connectionsRecyclerAdapter by lazy { ConnectionsRecyclerAdapter() }
    private val navController by lazy { findNavController() }

    override fun init() {
        initRecycler()
    }

    private fun initRecycler() {
        with(binding){
            recyclerConnections.layoutManager = LinearLayoutManager(requireContext())
            recyclerConnections.adapter = connectionsRecyclerAdapter
        }
    }

    override fun bindViewActionListeners() {
        binding.btnLogOut.setOnClickListener {
            viewModel.onEvent(ConnectionScreenActions.LogOut)
        }
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.connectionState.collectLatest {
                    handleConnectionState(state = it)
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

    private fun handleConnectionState(state: ConnectionState) {
        binding.loaderInclude.loaderContainer.isVisible = state.isLoading
        connectionsRecyclerAdapter.submitList(state.connections)
    }

    private fun handleEvents(event: ConnectionUiEvent) {
        when (event) {
            ConnectionUiEvent.NavigateToLogIn -> {
                navController.navigate(ConnectionsFragmentDirections.actionConnectionsFragmentToLogInFragment())
            }

            is ConnectionUiEvent.ShowError -> {
                binding.root.showSnackBar(event.error)
            }
        }

    }
}

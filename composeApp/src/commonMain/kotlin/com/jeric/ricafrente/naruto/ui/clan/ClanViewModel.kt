package com.jeric.ricafrente.naruto.ui.clan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeric.ricafrente.naruto.core.model.clan.ClanModel
import com.jeric.ricafrente.naruto.core.use_cases.UseCases
import com.jeric.ricafrente.naruto.core.utils.DataState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ClanViewModel (
    useCases: UseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(ClanViewStateEvent.UiState())
    val uiState: StateFlow<ClanViewStateEvent.UiState> get() = _uiState.asStateFlow()

    private val _navigation = Channel<ClanViewStateEvent.Navigation>()
    val navigation: Flow<ClanViewStateEvent.Navigation> = _navigation.receiveAsFlow()

    init {
        viewModelScope.launch {
            useCases.getAllClanUseCase().collectLatest { result ->
                when(result){
                    is DataState.Error -> {
                        if (result.data?.isNotEmpty() == true) {
                            _uiState.update {
                                it.copy(
                                    isSuccess = result.data ?: emptyList(),
                                    isLoading = false
                                )
                            }
                        } else {
                            _uiState.update { it.copy(error = result.message, isLoading = false) }
                        }
                    }
                    is DataState.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is DataState.Success -> {
                        _uiState.update { it.copy(isSuccess = result.data ?: emptyList(), isLoading = false) }
                    }
                }
            }
        }
    }

    fun onEvent(event: ClanViewStateEvent.Event){
        when(event){
            ClanViewStateEvent.Event.GotoHomeEvent -> {
                viewModelScope.launch {
                    _navigation.send(ClanViewStateEvent.Navigation.GotoHomeNav)
                }
            }
        }
    }
}


object ClanViewStateEvent {
    data class UiState(
        val isLoading: Boolean = false,
        val error: String ?=null,
        val isSuccess: List<ClanModel> = emptyList()
    )
    sealed class Navigation {
        data object GotoHomeNav: Navigation()
    }
    sealed interface Event{
        data object GotoHomeEvent: Event
    }
}



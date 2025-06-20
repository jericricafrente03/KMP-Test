package com.jeric.ricafrente.naruto.ui.buruto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeric.ricafrente.naruto.core.model.buruto.KaraModel
import com.jeric.ricafrente.naruto.core.use_cases.UseCases
import com.jeric.ricafrente.naruto.core.utils.DataState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class KaraViewModel(
    private val useCases: UseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(KaraViewStateEvent.UiState())
    val uiState: StateFlow<KaraViewStateEvent.UiState> get() = _uiState.asStateFlow()

    private val _navigation = Channel<KaraViewStateEvent.Navigation>()
    val navigation: Flow<KaraViewStateEvent.Navigation> = _navigation.receiveAsFlow()

    init {
        viewModelScope.launch {
            useCases.getAllKaraUseCase().collectLatest { result ->
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

    fun onEvent(event: KaraViewStateEvent.Event){
        when(event){
            is KaraViewStateEvent.Event.GotoDetailsEvent -> {
                viewModelScope.launch {
                    _navigation.send(KaraViewStateEvent.Navigation.GotoDetailsNav(event.id))
                }
            }
            KaraViewStateEvent.Event.GotoBackEvent -> {
                viewModelScope.launch {
                    _navigation.send(KaraViewStateEvent.Navigation.GotoBackNav)
                }
            }
        }
    }

}

object KaraViewStateEvent {
    data class UiState(
        val isLoading: Boolean = false,
        val error: String ?=null,
        val isSuccess: List<KaraModel> = emptyList()
    )
    sealed class Navigation {
        data object GotoBackNav: Navigation()
        data class GotoDetailsNav(val id : String): Navigation()
    }
    sealed interface Event{
        data class GotoDetailsEvent(val id: String): Event
        data object GotoBackEvent: Event
    }
}

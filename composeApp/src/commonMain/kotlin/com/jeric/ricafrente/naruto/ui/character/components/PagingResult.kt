package com.jeric.ricafrente.naruto.ui.character.components

import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import app.cash.paging.compose.LazyPagingItems
import com.jeric.ricafrente.naruto.core.model.character.CharacterModel
import com.jeric.ricafrente.naruto.ui.helper.NarutoErrorMessage

@Composable
fun handlePagingResult(
    heroes: LazyPagingItems<CharacterModel>
): Boolean {
    val loadState = heroes.loadState

    val errorState = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when {
        errorState != null -> {
            val errorMessage = errorState.error.message ?: "Unknown error"

            val isNetworkError = errorMessage.contains("Unable to resolve host", ignoreCase = true) ||
                    errorMessage.contains("Failed to connect", ignoreCase = true)

            NarutoErrorMessage(
                message = if (isNetworkError) "No Internet Connection" else errorMessage
            )
            false
        }

        heroes.itemCount == 0 -> {
            NarutoErrorMessage(message = "No characters found.")
            false
        }

        else -> true
    }
}
package com.jeric.ricafrente.naruto.ui.search.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AbilityItem(
    name: String,
    containerColor: Color,
    modifier: Modifier = Modifier,
) {

    Text(
        text = name.replaceFirstChar { it.uppercaseChar() },
        color = containerColor,
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier
            .border(1.dp, containerColor, CircleShape)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}
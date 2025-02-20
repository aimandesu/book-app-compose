package com.redblazer.bookapp.book.presentation.util

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun NavigationTappable(content: @Composable () -> Unit) {

    var isExpanded by remember { mutableStateOf(false) }
    val height by animateDpAsState(
        targetValue =
        if (isExpanded) 100.dp else 0.dp,
        label = ""
    )

    Column {
        if (isExpanded) {
            Box(
                modifier = Modifier
                    .height(height)
                    .clip(CircleShape)
                    .border(2.dp,  color = MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                    .background(color = MaterialTheme.colorScheme.primary)
//            .wrapContentSize(align = Alignment.Center),
            ) {
                content()
            }
        }
        IconButton(
            onClick = {
                isExpanded = !isExpanded
            },
        ) {
            Icon(
                imageVector = Icons.Filled.Apps,
                contentDescription = "",
//                tint = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                    .background(color = MaterialTheme.colorScheme.primary)
            )
        }
    }
}
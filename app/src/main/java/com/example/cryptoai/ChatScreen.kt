package com.example.cryptoai

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = viewModel()
) {
    var messageText by remember { mutableStateOf("") }
    val chatState = viewModel.chatState.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(chatState.value.messages.size) {
        if (chatState.value.messages.isNotEmpty()) {
            listState.animateScrollToItem(chatState.value.messages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Chat messages
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            state = listState
        ) {
            items(chatState.value.messages) { message ->
                ChatMessage(message)
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Show loading indicator
            if (chatState.value.isLoading) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        // Error message
        chatState.value.error?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        // Input area
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(24.dp)),
                placeholder = { Text("Type a message...") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface
                ),
                enabled = !chatState.value.isLoading
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    if (messageText.isNotBlank()) {
                        viewModel.sendMessage(messageText)
                        messageText = ""
                    }
                },
                enabled = !chatState.value.isLoading
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = if (chatState.value.isLoading) 
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                    else 
                        MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun ChatMessage(message: ChatMessage) {
    val isUser = message.isUser
    val backgroundColor = if (isUser) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.secondaryContainer
    }
    val textColor = if (isUser) {
        Color.White
    } else {
        MaterialTheme.colorScheme.onSecondaryContainer
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (isUser) 16.dp else 0.dp,
                        bottomEnd = if (isUser) 0.dp else 16.dp
                    )
                )
                .background(backgroundColor)
                .padding(12.dp)
        ) {
            Text(
                text = message.content,
                color = textColor,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
} 
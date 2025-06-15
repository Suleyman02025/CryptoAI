package com.example.cryptoai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptoai.R
import com.example.cryptoai.viewmodel.ChatViewModel
import com.example.cryptoai.viewmodel.ChatMessage
import com.example.cryptoai.ui.theme.PrimaryGreen
import com.example.cryptoai.ui.theme.Urbanist

@Composable
fun ChatScreen(onBack: () -> Unit = {}, viewModel: ChatViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    var messageText by remember { mutableStateOf("") }
    val chatState = viewModel.chatState.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(chatState.value.messages.size) {
        if (chatState.value.messages.isNotEmpty()) {
            listState.animateScrollToItem(chatState.value.messages.size - 1)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF131316))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            // Top Bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { onBack() }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Ai Chat",
                    color = Color.White,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Chat messages
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(chatState.value.messages) { message ->
                    ChatMessageItem(message)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // Input field
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(24.dp)),
                    placeholder = { Text("Type a message...", color = Color.White) },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFF18181C),
                        focusedContainerColor = Color(0xFF18181C),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = PrimaryGreen
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
                        painter = androidx.compose.ui.res.painterResource(id = R.drawable.ic_send),
                        contentDescription = "Send",
                        tint = if (chatState.value.isLoading) Color.Gray else PrimaryGreen
                    )
                }
            }
        }

        // Loading indicator
        if (chatState.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center),
                color = PrimaryGreen
            )
        }
    }
}

@Composable
private fun ChatMessageItem(message: ChatMessage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (message.isUser) 16.dp else 4.dp,
                        bottomEnd = if (message.isUser) 4.dp else 16.dp
                    )
                )
                .background(if (message.isUser) PrimaryGreen else Color(0xFF18181C))
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = message.content,
                color = Color.White,
                fontFamily = Urbanist,
                fontSize = 16.sp,
                textAlign = if (message.isUser) TextAlign.End else TextAlign.Start
            )
        }
    }
}

@Composable
fun ChatMessageBubble(message: ChatMessage) {
    val isUser = message.isUser
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .background(
                    color = if (isUser) PrimaryGreen else Color(0xFF23232A),
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomEnd = if (isUser) 0.dp else 16.dp,
                        bottomStart = if (isUser) 16.dp else 0.dp
                    )
                )
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            if (isUser) {
                Text(
                    text = message.content,
                    color = Color.White,
                    fontSize = 16.sp
                )
            } else {
                val formattedText = buildAnnotatedString {
                    var currentIndex = 0
                    val text = message.content
                    
                    while (currentIndex < text.length) {
                        // Handle headers (lines starting with #)
                        if (text.startsWith("# ", currentIndex)) {
                            val endOfLine = text.indexOf('\n', currentIndex).let { 
                                if (it == -1) text.length else it 
                            }
                            val headerText = text.substring(currentIndex + 2, endOfLine)
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                            ) {
                                append(headerText)
                            }
                            append("\n")
                            currentIndex = endOfLine + 1
                        }
                        // Handle bold text (**text**)
                        else if (text.startsWith("**", currentIndex)) {
                            val endBold = text.indexOf("**", currentIndex + 2)
                            if (endBold != -1) {
                                val boldText = text.substring(currentIndex + 2, endBold)
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFD1FFD6)
                                    )
                                ) {
                                    append(boldText)
                                }
                                currentIndex = endBold + 2
                            } else {
                                append(text[currentIndex])
                                currentIndex++
                            }
                        }
                        // Handle regular text
                        else {
                            append(text[currentIndex])
                            currentIndex++
                        }
                    }
                }
                
                Text(
                    text = formattedText,
                    color = Color(0xFFD1FFD6),
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )
            }
        }
    }
} 
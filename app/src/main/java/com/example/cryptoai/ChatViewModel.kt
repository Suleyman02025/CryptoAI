package com.example.cryptoai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ChatMessage(
    val content: String,
    val isUser: Boolean
)

data class ChatState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ChatViewModel : ViewModel() {
    private val _chatState = MutableStateFlow(ChatState())
    val chatState: StateFlow<ChatState> = _chatState.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey
    )

    fun sendMessage(content: String) {
        // Add user message
        _chatState.update { currentState ->
            currentState.copy(
                messages = currentState.messages + ChatMessage(content, true),
                isLoading = true,
                error = null
            )
        }

        // Generate AI response
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(content)
                response.text?.let { outputContent ->
                    _chatState.update { currentState ->
                        currentState.copy(
                            messages = currentState.messages + ChatMessage(outputContent, false),
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _chatState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        error = e.localizedMessage ?: "An error occurred"
                    )
                }
            }
        }
    }
} 
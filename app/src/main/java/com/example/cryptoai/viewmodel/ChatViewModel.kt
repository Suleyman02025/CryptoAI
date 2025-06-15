package com.example.cryptoai.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.example.cryptoai.BuildConfig
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

class ChatViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _chatState = MutableStateFlow(ChatState())
    val chatState: StateFlow<ChatState> = _chatState.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey
    )

    init {
        // Load saved messages if any
        savedStateHandle.get<List<ChatMessage>>("chat_messages")?.let { messages ->
            _chatState.update { it.copy(messages = messages) }
        } ?: run {
            // Add initial system message if no saved messages
            _chatState.update { 
                it.copy(messages = listOf(
                    ChatMessage(
                        "Hello! I'm your AI assistant. I can help you with cryptocurrency information, trading strategies, and market analysis. How can I assist you today?",
                        isUser = false
                    )
                ))
            }
        }
    }

    fun sendMessage(message: String) {
        _chatState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Add user message
                val updatedMessages = _chatState.value.messages + ChatMessage(message, true)
                _chatState.update { it.copy(messages = updatedMessages) }
                
                // Save messages to SavedStateHandle
                savedStateHandle["chat_messages"] = updatedMessages

                // Call Gemini AI for a real response
                val response = generativeModel.generateContent(message)
                val aiText = response.candidates
                    ?.firstOrNull()
                    ?.content
                    ?.parts
                    ?.firstOrNull()
                    ?.let { part -> (part as? com.google.ai.client.generativeai.type.TextPart)?.text }
                    ?: "[No response]"

                // Add AI response and save
                val finalMessages = updatedMessages + ChatMessage(aiText, false)
                _chatState.update { it.copy(messages = finalMessages, isLoading = false) }
                savedStateHandle["chat_messages"] = finalMessages
            } catch (e: Exception) {
                _chatState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun clearChat() {
        _chatState.update { it.copy(messages = emptyList()) }
        savedStateHandle.remove<Any>("chat_messages")
    }
} 
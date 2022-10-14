package io.github.horaciocome1.lucia.setup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.horaciocome1.lucia.api.LuciaApi
import io.github.horaciocome1.lucia.setup.model.Topic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class TopicsViewModel @Inject constructor(
    private val luciaApi: LuciaApi
) : ViewModel() {

    private val _state = MutableStateFlow(TopicsState(loading = true))
    val state = _state.asStateFlow()

    init {
        retrieveTopics()
    }

    private fun retrieveTopics() {
        Log.v("SetupViewModel", "retrieveTopics")
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val response: Response<List<Topic>>
            try {
                response = withContext(Dispatchers.IO) {
                    luciaApi.getTopics()
                }
            } catch (e: UnknownHostException) {
                Log.e("SetupViewModel", "retrieveTopics", e)
                _state.update { currentState ->
                    currentState.copy(
                        loading = false,
                        retrieveError = true,
                        errorMessage = "Cannot reach servers"
                    )
                }
                return@launch
            }
            Log.d("SetupViewModel", "retrieveTopics response=$response size=${response.body()?.size}")
            _state.update { currentState ->
                if (response.isSuccessful) {
                    currentState.copy(
                        loading = false,
                        retrieveSuccess = true,
                        topics = response.body()?.sortedBy { it.name } ?: emptyList()
                    )
                } else {
                    currentState.copy(
                        loading = false,
                        retrieveError = true,
                        errorMessage = response.errorBody().toString()
                    )
                }
            }
        }
    }

    fun onTopicSelected(topic: Topic) {
        Log.v("SetupViewModel", "onTopicSelected topic=$topic")
        _state.update { currentState ->
            val selectedTopics = currentState.selectedTopics.toMutableList()
            if (!isTopicSelected(topic)) {
                selectedTopics.add(topic)
            } else {
                selectedTopics.remove(topic)
            }
            currentState.copy(selectedTopics = selectedTopics)
        }
    }

    fun isTopicSelected(topic: Topic) = state.value.selectedTopics.contains(topic)

    fun randomize() {
        viewModelScope.launch {
            _state.update { it.copy(disableUserActions = true) }
            for (i in 1..3) {
                _state.update { currentState ->
                    currentState.copy(
                        selectedTopics = buildList {
                            for (j in 1..3) {
                                add(currentState.topics.random())
                            }
                        }
                    )
                }
                delay(150L * (i + 1))
            }
            _state.update { it.copy(disableUserActions = false) }
        }
    }

    data class TopicsState(
        val loading: Boolean = false,
        val retrieveSuccess: Boolean = false,
        val retrieveError: Boolean = false,
        val errorMessage: String = "",
        val topics: List<Topic> = emptyList(),
        val selectedTopics: List<Topic> = emptyList(),
        val disableUserActions: Boolean = false
    )
}
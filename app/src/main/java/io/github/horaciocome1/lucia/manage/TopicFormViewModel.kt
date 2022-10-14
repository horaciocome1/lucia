package io.github.horaciocome1.lucia.manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.horaciocome1.lucia.BuildConfig
import io.github.horaciocome1.lucia.api.ManagementApi
import io.github.horaciocome1.lucia.setup.model.Topic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class TopicFormViewModel @Inject constructor(
    private val managementApi: ManagementApi
) : ViewModel() {

    private val _state = MutableStateFlow(State(loading = false))
    val state = _state.asStateFlow()

    fun createTopic(topic: Topic) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val response: Response<Unit>
            try {
                response = withContext(Dispatchers.IO) {
                    managementApi.createTopic(
                        topic = Topic.Add(
                            name = topic.name,
                            createdBy = BuildConfig.EMPLOYEE_ID
                        )
                    )
                }
            } catch (e: UnknownHostException) {
                _state.update { currentState ->
                    currentState.copy(
                        loading = false,
                        createTopicError = true,
                        errorMessage = "We were not able to reach our servers"
                    )
                }
                return@launch
            }
            _state.update { currentState ->
                if (response.isSuccessful) {
                    currentState.copy(
                        loading = false,
                        createTopicSuccess = true
                    )
                } else {
                    currentState.copy(
                        loading = false,
                        createTopicError = true,
                        errorMessage = response.errorBody().toString()
                    )
                }
            }
        }
    }

    fun updateTopic(topic: Topic) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val response: Response<Unit>
            try {
                response = withContext(Dispatchers.IO) {
                    managementApi.updateTopic(
                        queryValueTopicId = "eq.${topic.id}",
                        topic = Topic.Edit(
                            name = topic.name,
                            createdBy = topic.createdBy,
                            reviewedBy = BuildConfig.EMPLOYEE_ID
                        )
                    )
                }
            } catch (e: UnknownHostException) {
                _state.update { currentState ->
                    currentState.copy(
                        loading = false,
                        updateTopicError = true,
                        errorMessage = "We were not able to reach our servers"
                    )
                }
                return@launch
            }
            _state.update { currentState ->
                if (response.isSuccessful) {
                    currentState.copy(
                        loading = false,
                        updateTopicSuccess = true
                    )
                } else {
                    currentState.copy(
                        loading = false,
                        updateTopicError = true,
                        errorMessage = response.errorBody().toString()
                    )
                }
            }
        }
    }

    fun deleteTopic(topicId: String) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            val response: Response<Unit>
            try {
                response = withContext(Dispatchers.IO) {
                    managementApi.deleteTopic(
                        queryValueTopicId = "eq.$topicId"
                    )
                }
            } catch (e: UnknownHostException) {
                _state.update { currentState ->
                    currentState.copy(
                        loading = false,
                        deleteTopicError = true,
                        errorMessage = "We were not able to reach our servers"
                    )
                }
                return@launch
            }
            _state.update { currentState ->
                if (response.isSuccessful) {
                    currentState.copy(
                        loading = false,
                        deleteTopicSuccess = true
                    )
                } else {
                    currentState.copy(
                        loading = false,
                        deleteTopicError = true,
                        errorMessage = response.errorBody().toString()
                    )
                }
            }
        }
    }

    data class State(
        val loading: Boolean = false,
        val createTopicSuccess: Boolean = false,
        val createTopicError: Boolean = false,
        val updateTopicSuccess: Boolean = false,
        val updateTopicError: Boolean = false,
        val deleteTopicSuccess: Boolean = false,
        val deleteTopicError: Boolean = false,
        val errorMessage: String = ""
    )
}
package io.github.horaciocome1.lucia.api

import io.github.horaciocome1.lucia.setup.model.Topic
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ManagementApi {

    @Headers("Content-Type: application/json")
    @POST("/rest/v1/topic")
    suspend fun createTopic(@Body topic: Topic.Add): Response<Unit>

    @Headers(
        "Content-Type: application/json",
        "Prefer: resolution=merge-duplicates"
    )
    @POST("/rest/v1/topic")
    suspend fun updateTopic(@Body topic: Topic.Edit): Response<Unit>

    @DELETE("/rest/v1/topic")
    suspend fun deleteTopic(@Query("id") queryValueTopicId: String): Response<Unit>
}
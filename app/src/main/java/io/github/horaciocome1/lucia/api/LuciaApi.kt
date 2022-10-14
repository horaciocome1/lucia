package io.github.horaciocome1.lucia.api

import io.github.horaciocome1.lucia.setup.model.Topic
import retrofit2.Response
import retrofit2.http.GET

interface LuciaApi {

    @GET("/rest/v1/topic?select=id,description,created_by_employee_id")
    suspend fun getTopics(): Response<List<Topic>>
}
package io.github.horaciocome1.lucia.setup.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Topic(
    @Json(name = "id")
    val id: String,
    @Json(name = "description")
    val name: String,
    @Json(name = "created_by_employee_id")
    val createdBy: String = ""
) : Parcelable {

    data class Add(
        @Json(name = "description")
        val name: String,
        @Json(name = "created_by_employee_id")
        val createdBy: String
    )

    data class Edit(
        @Json(name = "id")
        val id: String,
        @Json(name = "description")
        val name: String,
        @Json(name = "created_by_employee_id")
        val createdBy: String,
        @Json(name = "reviewed_by_employee_id")
        val reviewedBy: String = ""
    )
}
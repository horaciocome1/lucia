package io.github.horaciocome1.lucia.setup.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Topic(
    @Json(name = "id")
    val id: String,
    @Json(name = "description")
    val name: String
) : Parcelable
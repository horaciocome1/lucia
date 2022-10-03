package io.github.horaciocome1.lucia.setup.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Topic(
    val id: String,
    val name: String
) : Parcelable
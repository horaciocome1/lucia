package io.github.horaciocome1.lucia.setup.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player(
    val id: String,
    val name: String,
    val joinedAt: Long
) : Parcelable
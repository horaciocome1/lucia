package io.github.horaciocome1.lucia.results.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    val playerId: String,
    val points: Int
) : Parcelable

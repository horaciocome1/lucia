package io.github.horaciocome1.lucia.setup.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Level(
    val name: String,
    val color: ULong,
    val colorSelected: ULong
) : Parcelable
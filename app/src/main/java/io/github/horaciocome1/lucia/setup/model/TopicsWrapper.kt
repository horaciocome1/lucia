package io.github.horaciocome1.lucia.setup.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopicsWrapper(
    val topics: List<Topic>
) : Parcelable

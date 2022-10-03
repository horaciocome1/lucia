package io.github.horaciocome1.lucia.results.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultsWrapper(val results: List<Result>) : Parcelable
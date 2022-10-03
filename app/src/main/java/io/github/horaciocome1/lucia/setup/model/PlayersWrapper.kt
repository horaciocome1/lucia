package io.github.horaciocome1.lucia.setup.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlayersWrapper(val players: List<Player>) : Parcelable

package io.github.horaciocome1.lucia

object LuciaConfig {
    const val VERSION_MANAGEMENT = BuildConfig.FLAVOR == "management"
    const val VERSION_PLAY = BuildConfig.FLAVOR == "play"
}
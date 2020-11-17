package ru.agladkov.questgo

import android.graphics.Color

data class Configuration(
    val latency: Int,
    val userColor: Int,
    val sensitivity: Int
)

class ConfigurationBuilder() {

    var latency: Int = 300
    var userColor: Int = Color.BLUE
    var sensitivity: Int = 0

    fun sensitivity(value: Int): ConfigurationBuilder {
        this.sensitivity = value
        return this
    }

    fun latency(value: Int): ConfigurationBuilder {
        this.latency = value
        return this
    }

    fun userColor(value: Int): ConfigurationBuilder {
        this.userColor = value
        return this
    }

    fun build(): Configuration = Configuration(
        latency = latency,
        sensitivity = sensitivity,
        userColor = userColor
    )

}
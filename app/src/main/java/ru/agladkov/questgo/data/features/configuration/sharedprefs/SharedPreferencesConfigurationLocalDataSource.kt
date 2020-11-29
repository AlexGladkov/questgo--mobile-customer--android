package ru.agladkov.questgo.data.features.configuration.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.agladkov.questgo.R
import ru.agladkov.questgo.data.features.configuration.UserConfigurationLocalDataSource
import ru.agladkov.questgo.data.features.configuration.models.UserConfiguration
import javax.inject.Inject

class SharedPreferencesConfigurationLocalDataSource @Inject constructor(
    @ApplicationContext val context: Context,
    private val gson: Gson
) : UserConfigurationLocalDataSource {

    private val configurationKey = "ConfigurationKey"

    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(
        context.getString(
            R.string.app_name
        ), 0
    )

    override fun updateConfiguration(configuration: UserConfiguration) {
        sharedPreferences.edit().putString(configurationKey, gson.toJson(configuration)).apply()
    }

    override fun readConfiguration(): UserConfiguration {
        return try {
            val json = sharedPreferences.getString(configurationKey, "")
            gson.fromJson<UserConfiguration>(json, UserConfiguration::class.java)
        } catch (e: Exception) {
            UserConfiguration.getInstance()
        }
    }
}
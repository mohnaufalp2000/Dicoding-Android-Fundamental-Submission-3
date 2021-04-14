package com.naufal.githubuser.preference

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.naufal.githubuser.R
import com.naufal.githubuser.alarm.AlarmReceiver


class PreferenceSettings : PreferenceFragmentCompat(){

    private lateinit var reminderPreference: SwitchPreference
    private lateinit var languagePreference: Preference
    private lateinit var reminder: String
    private lateinit var language: String
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, "settings_preferences")
        setupReminder()
        setupLanguage()
    }


    private fun setupLanguage() {
        language = resources.getString(R.string.key_language)
        languagePreference = findPreference<Preference>(language) as Preference
        languagePreference.setOnPreferenceClickListener {

            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
            true
        }
    }

    private fun setupReminder() {
        alarmReceiver = AlarmReceiver()
        reminder = resources.getString(R.string.key_reminder)
        reminderPreference = findPreference<SwitchPreference>(reminder) as SwitchPreference

        reminderPreference.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, _ ->
                reminderPreference.isChecked = !reminderPreference.isChecked

                if (reminderPreference.isChecked){
                    context?.let { alarmReceiver.setRepeatingAlarm(it) }
                } else {
                    context?.let { alarmReceiver.cancelAlarm(it) }
                }
                true
            }
    }

}
package com.naufal.githubuser.preference

import android.os.Bundle
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.naufal.githubuser.R
import com.naufal.githubuser.alarm.AlarmReceiver

class PreferenceSettings : PreferenceFragmentCompat(){

    private lateinit var reminderPreference: SwitchPreference
    private lateinit var REMINDER: String
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

        setupReminder()

    }

    private fun setupReminder() {
        alarmReceiver = AlarmReceiver()

        REMINDER = resources.getString(R.string.key_reminder)

        reminderPreference = findPreference<SwitchPreference>(REMINDER) as SwitchPreference

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
package edu.mines.csci448.kcooper_A2.ui

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import edu.mines.csci448.kcooper_A2.R

class SettingsFragment: PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_preferences, rootKey)
    }
}
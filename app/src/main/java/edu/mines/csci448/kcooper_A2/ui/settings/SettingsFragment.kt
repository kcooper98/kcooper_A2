package edu.mines.csci448.kcooper_A2.ui.settings

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import edu.mines.csci448.kcooper_A2.R
import edu.mines.csci448.kcooper_A2.data.GameRepository

class SettingsFragment: PreferenceFragmentCompat() {
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_preferences, rootKey)

        val factory = SettingsViewModelFactory(requireContext())
        settingsViewModel = ViewModelProvider(this, factory)
            .get(SettingsViewModel::class.java)

        val button: Preference? = findPreference("wipe_button")
        if (button != null) {
            button.setOnPreferenceClickListener {
                settingsViewModel.wipeGames()
                true
            }
        }
    }
}
package com.androiddevs.runningappyt.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.androiddevs.runningappyt.R
import com.androiddevs.runningappyt.other.Constants.KEY_NAME
import com.androiddevs.runningappyt.other.Constants.KEY_WEIGHT
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    @Inject
    lateinit var sharedPref: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadFieldsFromSharedPref()

        btnApplyChanges.setOnClickListener {
            val success = applyChangesToSharedPref()

            if(success){
                Snackbar.make(
                    requireView(),
                    "Saved changes",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            else{
                Snackbar.make(
                    requireView(),
                    "Please fill all the fields",
                    Snackbar.LENGTH_LONG
                ).show()

            }
        }
    }

    private fun loadFieldsFromSharedPref(){
        val name = sharedPref.getString(KEY_NAME, "")?:""
        val weight = sharedPref.getFloat(KEY_WEIGHT, 80f).toString()

        etName.setText(name)
        etWeight.setText(weight)
    }

    private fun applyChangesToSharedPref(): Boolean{
        val nameText = etName.text.toString()
        val weightText = etWeight.text.toString()

        if(nameText.isEmpty() || weightText.isEmpty())
            return false

        sharedPref.edit()
            .putString(KEY_NAME, nameText)
            .putFloat(KEY_WEIGHT, weightText.toFloat())
            .apply()

        val toolbarText = "Let's go $nameText"
        requireActivity().tvToolbarTitle.text = toolbarText
        return true
    }
}
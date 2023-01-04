package fr.pcmprojet2022.learndico.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.content.Context
import android.content.SharedPreferences
import fr.pcmprojet2022.learndico.R
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.timepicker.TimeFormat
import com.google.android.material.timepicker.MaterialTimePicker
import fr.pcmprojet2022.learndico.databinding.FragmentSettingsBinding
import fr.pcmprojet2022.learndico.sharedviewmodel.DaoViewModel

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    /**
     * Classe SettingsFragment est la parametre de l'application.
     */

    private lateinit var binding: FragmentSettingsBinding
    private val daoViewModel by lazy { ViewModelProvider(this)[DaoViewModel::class.java] }

    private var moteurRecherche : String = ""
    private var motByDay : Int = 6

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentSettingsBinding.bind(view)
        loadValueFromBundle(savedInstanceState)
        laodBundleValue()
        val shared = this.requireActivity().getSharedPreferences("params_learn_dico", Context.MODE_PRIVATE)
        val edit = shared.edit()
        binding.filledTextFieldNbrWordMax.editText?.setText(shared.getInt("numNotification", 0).toString())
        binding.filledTextFavoritBrowser.editText?.setText(shared.getString("urlBrowser", "https://www.google.com/search?q=exemple"))
        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(shared.getInt("timeHour",12))
                .setMinute(shared.getInt("timeMin", 10))
                .setTitleText("Heure de notification")
                .build()

        binding.timeNotificationButton.setOnClickListener {
            picker.show(requireActivity().supportFragmentManager, "Setting_Fragment")
        }

        buttonEvent()
        saveAll(edit, shared, picker)

    }

    private fun buttonEvent() {
        binding.buttonClearDb.setOnClickListener {
            daoViewModel.dropAll()
            Toast.makeText(context, R.string.deleteBd, Toast.LENGTH_LONG).show()
        }

        binding.peuplerBdd?.setOnClickListener {
            daoViewModel.peuplementBaseDonne()
            Toast.makeText(context, R.string.deleteBd, Toast.LENGTH_LONG).show()
        }
    }

    private fun saveAll(
        edit: SharedPreferences.Editor,
        shared: SharedPreferences,
        picker: MaterialTimePicker
    ) {
        binding.buttonSaveChange.setOnClickListener {

            val value = try {
                binding.filledTextFieldNbrWordMax.editText?.text.toString().toInt()
            }catch (e: Exception){1}
            edit.putInt(
                "numNotification",
                value
            )

            val urlBrowser = try{
                binding.filledTextFavoritBrowser.editText?.text.toString()
            }catch (e:Exception){"https://google.com"}
            edit.putString(
                "urlBrowser",
                urlBrowser
            )

            try {
                edit.putInt("timeMin", picker.minute)
                edit.putInt("timeHour", picker.hour)
            }catch (e: Exception){
                edit.putInt("timeMin", shared.getInt("timeMin", 10))
                edit.putInt("timeHour", shared.getInt("timeHour",12))
            }

            edit.apply()
            Toast.makeText(context, "Enregistrement des données effectué.", Toast.LENGTH_LONG).show()
        }
    }

    private fun loadValueFromBundle(savedInstanceState: Bundle?) {
        moteurRecherche = savedInstanceState?.getString("moteur_recherche")?: ""
        motByDay = (savedInstanceState?.getInt("mot_by_day")?: 6)
    }
    private fun laodBundleValue() {
        with(binding) {
            filledTextFavoritBrowser.editText?.setText(moteurRecherche)
            filledTextFieldNbrWordMax.editText?.setText(motByDay.toString())
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("moteur_recherche", moteurRecherche)
        outState.putInt("mot_by_day", motByDay)

    }

}
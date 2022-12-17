package fr.pcmprojet2022.learndico.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.content.Context
import fr.pcmprojet2022.learndico.R
import androidx.fragment.app.Fragment
import com.google.android.material.timepicker.TimeFormat
import com.google.android.material.timepicker.MaterialTimePicker
import fr.pcmprojet2022.learndico.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    lateinit var binding: FragmentSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentSettingsBinding.bind(view)

        val shared = this.requireActivity().getSharedPreferences("params_learn_dico", Context.MODE_PRIVATE)
        val edit = shared.edit()

        binding.filledTextFieldNbrWordMax.editText?.setText(shared.getInt("numNotification", 1).toString())

        binding.filledTextFavoritBrowser.editText?.setText(shared.getString("urlBrowser", "https://www.google.com"))

        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(shared.getInt("timeHour",12))
                .setMinute(shared.getInt("timeMin", 10))//TODO: get value in shared file
                .setTitleText("Heure de notification")
                .build()

        binding.timeNotificationButton.setOnClickListener {
            picker.show(requireActivity().supportFragmentManager, "Setting_Fragment")
        }

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
            )//TODO: gérer le favoris dans le searchonlineword

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

        binding.buttonClearDb.setOnClickListener {
            //TODO: implement
        }

    }

}
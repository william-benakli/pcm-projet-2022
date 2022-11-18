package fr.pcmprojet2022.learndico.fragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import fr.pcmprojet2022.learndico.databinding.FragmentPracticeBinding

class PracticeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentPracticeBinding.inflate(inflater, container, false).root
    }

}
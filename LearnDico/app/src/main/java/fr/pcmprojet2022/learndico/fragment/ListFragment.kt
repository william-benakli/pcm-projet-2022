package fr.pcmprojet2022.learndico.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import fr.pcmprojet2022.learndico.adapter.SearchRecycleAdapter
import fr.pcmprojet2022.learndico.data.entites.Words
import fr.pcmprojet2022.learndico.databinding.FragmentListBinding
import fr.pcmprojet2022.learndico.sharedviewmodel.SharedViewModel


class ListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val sharedViewModel by lazy { ViewModelProvider(this)[SharedViewModel::class.java] }
 //   private val sharedViewModel by lazy { ViewModelProvider(this)[SharedViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        val lst: MutableList<Words> = listOf(
            Words(
                "Avion A",
                "Avion A",
                "English",
                "Airplane",
                "Un véhicule conçu pour le transport aérien qui a des ailes et un ou plusieurs moteurs.",
                "A vehicle designed for air travel that has wings and one or more engines."
            ,"url"
            ),
            Words(
                "Avion A",
                "Avion A",
                "English",
                "Airplane",
                "Un véhicule conçu pour le transport aérien qui a des ailes et un ou plusieurs moteurs.",
                "A vehicle designed for air travel that has wings and one or more engines."
                ,"url"
            ),
            Words(
                "Avion A",
                "Avion A",
                "English",
                "Airplane",
                "Un véhicule conçu pour le transport aérien qui a des ailes et un ou plusieurs moteurs.",
                "A vehicle designed for air travel that has wings and one or more engines."
                ,"url"
            ),
            Words(
                "Avion A",
                "Avion A",
                "English",
                "Airplane",
                "Un véhicule conçu pour le transport aérien qui a des ailes et un ou plusieurs moteurs.",
                "A vehicle designed for air travel that has wings and one or more engines."
                ,"url"
            )
        ) as MutableList<Words>

        recyclerView = binding.recycler
        recyclerView.setHasFixedSize(true);
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = SearchRecycleAdapter(sharedViewModel, lst, requireContext())


        binding.buttonMoreVert.setOnClickListener {
            val direction = ListFragmentDirections.actionListFragmentToSelectLangFragment()
            findNavController().navigate(direction)
        }

        return view
    }

}
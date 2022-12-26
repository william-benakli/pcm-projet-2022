package fr.pcmprojet2022.learndico.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.pcmprojet2022.learndico.adapter.SearchRecycleAdapter
import fr.pcmprojet2022.learndico.databinding.FragmentListBinding
import fr.pcmprojet2022.learndico.dialog.DialogCallback
import fr.pcmprojet2022.learndico.sharedviewmodel.DaoViewModel


class ListFragment : Fragment(), DialogCallback {

    private lateinit var recyclerView: RecyclerView
    private val daoViewModel by lazy { ViewModelProvider(this)[DaoViewModel::class.java] }
    private lateinit var adapter : SearchRecycleAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = binding.recycler
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        //daoViewModel.insertWord();
        daoViewModel.loadAllWord()
        daoViewModel.getAllWordBD().observe(viewLifecycleOwner) {
            recyclerView.adapter = SearchRecycleAdapter(it.toMutableList(), requireContext(), daoViewModel, this)
        }

        //update Télécharger - Ouvrir
        daoViewModel.getUpdateFileName().observe(viewLifecycleOwner) {
            recyclerView.adapter!!.notifyDataSetChanged()
        }

        binding.textField.editText!!.doOnTextChanged { text, start, before, count ->
            updateAdapter(text.toString())
        }

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateAdapter(s: String) {
        daoViewModel.getResultPartialWord().removeObservers(this@ListFragment)
        daoViewModel.loadPartialWords(s)
        daoViewModel.getResultPartialWord().observe(viewLifecycleOwner) {
            adapter = SearchRecycleAdapter(it.toMutableList(), requireContext(), daoViewModel, this)
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    override fun onPositiveButtonClicked() {
        updateAdapter("");
    }

}
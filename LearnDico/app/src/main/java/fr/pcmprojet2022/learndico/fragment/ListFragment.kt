package fr.pcmprojet2022.learndico.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.pcmprojet2022.learndico.adapter.SearchRecycleAdapter
import fr.pcmprojet2022.learndico.databinding.FragmentListBinding
import fr.pcmprojet2022.learndico.dialog.DialogCallback
import fr.pcmprojet2022.learndico.sharedviewmodel.DaoViewModel
import fr.pcmprojet2022.learndico.sharedviewmodel.ModifiedWordViewModel

class ListFragment : Fragment(), DialogCallback {

    /**
     * Classe ListFragment à un nom ambigue mais contient la list des mots visibles dans le fragment.
     * Elle contient egalement un systeme recherche avancé avec un EditInputText.
     *
     */
    private lateinit var recyclerView: RecyclerView
    private val daoViewModel by lazy { ViewModelProvider(this)[DaoViewModel::class.java] }
    private val modifiedWordViewModel : ModifiedWordViewModel by activityViewModels()
    private lateinit var binding: FragmentListBinding
    private lateinit var adapter : SearchRecycleAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = binding.recycler
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        updateAdapter("")
        textEvent()
        spinnerEvent()
        return view
    }

    private fun textEvent(){
        daoViewModel.getUpdateFileName().observe(viewLifecycleOwner) {
            recyclerView.adapter!!.notifyDataSetChanged()
        }
        binding.textField.editText!!.doOnTextChanged { text, _, _, _ ->
            updateAdapter(text.toString())
        }
    }

    private fun spinnerEvent(){
        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?,
                                        p1: View?, position: Int, p3: Long) {
                daoViewModel.getAllWordBD().removeObservers(this@ListFragment)
                daoViewModel.loadAllWord()
                daoViewModel.getAllWordBD().observe(viewLifecycleOwner) {
                    recyclerView.adapter = SearchRecycleAdapter(it.toMutableList(), binding.spinner2.selectedItem.toString(),requireContext(), daoViewModel, this@ListFragment, modifiedWordViewModel)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun updateAdapter(s: String) {
        daoViewModel.getResultPartialWord().removeObservers(this@ListFragment)
        daoViewModel.loadPartialWords(s)
        daoViewModel.getResultPartialWord().observe(viewLifecycleOwner) {
            adapter = SearchRecycleAdapter(it.toMutableList(),  binding.spinner2.selectedItem.toString(), requireContext(), daoViewModel, this, modifiedWordViewModel)
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onPositiveButtonClicked() {
        updateAdapter("")
    }

}
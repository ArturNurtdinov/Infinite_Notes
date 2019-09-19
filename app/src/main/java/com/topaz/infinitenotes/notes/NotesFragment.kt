package com.topaz.infinitenotes.notes


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration

import com.topaz.infinitenotes.R
import com.topaz.infinitenotes.database.Note
import com.topaz.infinitenotes.database.NotesDatabase
import com.topaz.infinitenotes.databinding.FragmentNotesBinding
import com.topaz.infinitenotes.newnotes.NewnoteFragment

/**
 * A simple [Fragment] subclass.
 */
class NotesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNotesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_notes, container, false)

        setHasOptionsMenu(true)

        val dataSource =
            NotesDatabase.getInstance(requireNotNull(this.activity).application).noteDatabaseDao
        val viewModelFactory = NotesViewModelFactory(dataSource)

        val notesViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(NotesViewModel::class.java)

        binding.notesViewModel = notesViewModel

        val adapter = NotesAdapter(object : OnItemClickListener{
            override fun onClick(vh: View, item: Note) {
                val fragment = NewnoteFragment()
                val bundle = Bundle()
                bundle.putParcelable("NOTE_KEY", item)
                fragment.arguments = bundle
                val transaction = fragmentManager?.beginTransaction() ?: return
                transaction.replace(R.id.nav_host_fragment, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }

        })
        binding.notesList.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.notesList.adapter = adapter



        notesViewModel.notes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it.sortedBy { it.timeEdit })
            }
        })
        binding.lifecycleOwner = this

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_all_notes, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_new -> {
                val fragment = NewnoteFragment()
                val transaction = fragmentManager?.beginTransaction() ?: return false
                transaction.replace(R.id.nav_host_fragment, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

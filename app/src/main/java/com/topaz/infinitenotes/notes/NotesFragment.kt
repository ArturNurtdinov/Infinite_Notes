package com.topaz.infinitenotes.notes


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.topaz.infinitenotes.R
import com.topaz.infinitenotes.database.Note
import com.topaz.infinitenotes.database.NotesDatabase
import com.topaz.infinitenotes.databinding.FragmentNotesBinding

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

        val dataSource =
            NotesDatabase.getInstance(requireNotNull(this.activity).application).noteDatabaseDao
        val viewModelFactory = NotesViewModelFactory(dataSource)

        val notesViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(NotesViewModel::class.java)

        binding.notesViewModel = notesViewModel

        val adapter = NotesAdapter()
        binding.notesList.adapter = adapter

        notesViewModel.notes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        binding.lifecycleOwner = this

        return binding.root
    }


}

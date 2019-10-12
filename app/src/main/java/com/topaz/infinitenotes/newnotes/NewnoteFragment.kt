package com.topaz.infinitenotes.newnotes


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import com.topaz.infinitenotes.R
import com.topaz.infinitenotes.database.Note
import com.topaz.infinitenotes.database.NotesDatabase
import com.topaz.infinitenotes.databinding.FragmentNewnoteBinding

/**
 * A simple [Fragment] subclass.
 */
class NewnoteFragment : Fragment() {

    private var note: Note? = null
    private var isNoteChanged = false
    private lateinit var binding: FragmentNewnoteBinding
    private lateinit var viewModel: NewnoteViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_newnote, container, false)
        setHasOptionsMenu(true)
        note = arguments?.getParcelable("NOTE_KEY")
        binding.note = note

        val dataSource =
            NotesDatabase.getInstance(requireNotNull(this.activity).application).noteDatabaseDao
        val viewModelFactory = NewnoteViewModelFactory(dataSource)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewnoteViewModel::class.java)

        binding.content.setOnClickListener { isNoteChanged = true }

        binding.content.setOnClickListener { isNoteChanged = true }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_new_note, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.done -> {
                if (note == null) {
                    if (binding.title.text.isNotEmpty() || binding.content.text.isNotEmpty()) {
                        viewModel.insertNote(
                            Note(
                                0,
                                System.currentTimeMillis(),
                                binding.title.text.toString(),
                                binding.content.text.toString()
                            )
                        )
                    } else if (isNoteChanged) {
                        viewModel.updateNote(
                            Note(
                                note!!.noteID,
                                System.currentTimeMillis(),
                                binding.title.text.toString(),
                                binding.content.text.toString()
                            )
                        )
                    }
                    activity?.onBackPressed()
                }
                true
            }
            R.id.cancel -> {
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

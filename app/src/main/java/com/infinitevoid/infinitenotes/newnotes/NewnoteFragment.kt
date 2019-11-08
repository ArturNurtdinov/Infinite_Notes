package com.infinitevoid.infinitenotes.newnotes


import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import com.infinitevoid.infinitenotes.R
import com.infinitevoid.infinitenotes.database.NotesDatabase
import com.infinitevoid.infinitenotes.databinding.FragmentNewnoteBinding
import com.infinitevoid.infinitenotes.domain.Note
import com.infinitevoid.painter_feature.PainterFragment

/**
 * A simple [Fragment] subclass.
 */
class NewnoteFragment : Fragment() {

    private var note: Note? = null
    private var titleOnOpen: String? = null
    private var contentOnOpen: String? = null
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
        binding.executePendingBindings()

        val dataSource =
            NotesDatabase.getInstance(requireNotNull(this.activity).application).noteDatabaseDao
        val viewModelFactory = NewnoteViewModelFactory(dataSource)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewnoteViewModel::class.java)

        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as (InputMethodManager)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        binding.content.requestFocus()

        titleOnOpen = binding.title.text.toString()
        contentOnOpen = binding.content.text.toString()

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
                    }
                } else if ((binding.title.text.toString() != titleOnOpen) ||
                    (binding.content.text.toString() != contentOnOpen)
                ) {
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
                true
            }
            R.id.cancel -> {
                activity?.onBackPressed()
                true
            }
            R.id.palette -> {
                openPaint()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as (InputMethodManager)
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        super.onDestroyView()
    }

    private fun openPaint(){
        val fragment = PainterFragment()
        val transaction = fragmentManager?.beginTransaction() ?: return
        transaction.addToBackStack(null)
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.commit()
    }
}

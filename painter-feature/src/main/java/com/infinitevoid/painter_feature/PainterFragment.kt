package com.infinitevoid.painter_feature

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass.
 */
class PainterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_painter, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.palette_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.close -> {
                val dialog = AlertDialog.Builder(this.context)
                    .setTitle(getString(R.string.unsaved_changes))
                    .setMessage(getString(R.string.do_you_want_to_save_changes))
                    .setPositiveButton(getString(R.string.yes)) { _, _->
                        activity?.onBackPressed()
                    }
                    .setNegativeButton(getString(R.string.no)) {_, _ ->
                        activity?.onBackPressed()
                    }
                    .setNeutralButton(getString(R.string.cancel)) {dialog, _ ->
                        dialog.dismiss()
                    }
                    .setCancelable(true)
                    .create()
                dialog.show()
            }

            R.id.save -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }
}

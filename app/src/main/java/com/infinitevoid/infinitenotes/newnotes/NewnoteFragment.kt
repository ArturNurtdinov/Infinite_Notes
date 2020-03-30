package com.infinitevoid.infinitenotes.newnotes


import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.infinitevoid.infinitenotes.R
import com.infinitevoid.infinitenotes.database.NotesDatabase
import com.infinitevoid.infinitenotes.databinding.FragmentNewnoteBinding
import com.infinitevoid.infinitenotes.models.Note
import kotlinx.android.synthetic.main.fragment_newnote.*
import java.util.*

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

        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as (InputMethodManager)
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
            R.id.delay_notification -> {
                val calCurrent = Calendar.getInstance()
                val currentYear = calCurrent.get(Calendar.YEAR)
                val currentMonth = calCurrent.get(Calendar.MONTH)
                val currentDay = calCurrent.get(Calendar.DAY_OF_MONTH)

                val dpd = DatePickerDialog(
                    requireContext(),
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        val timeSetListener =
                            TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                                calCurrent.set(Calendar.HOUR_OF_DAY, hour)
                                calCurrent.set(Calendar.MINUTE, minute)
                                Log.d("LOG_TAG", "$year $monthOfYear $dayOfMonth $hour $minute")

                                val intent = Intent(requireContext(), NotificationReceiver::class.java)
                                intent.putExtra(CONTENT_KEY, title.text.toString())
                                val pendingIntent = PendingIntent.getBroadcast(
                                    requireContext(),
                                    1,
                                    intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                                )
                                val alarmManager =
                                    requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
                                val cal = Calendar.getInstance()
                                cal.set(year, monthOfYear, dayOfMonth, hour, minute)
                                alarmManager.set(
                                    AlarmManager.RTC_WAKEUP,
                                    System.currentTimeMillis() + calCurrent.timeInMillis - cal.timeInMillis,
                                    pendingIntent
                                )
                            }
                        TimePickerDialog(
                            requireContext(),
                            timeSetListener,
                            calCurrent.get(Calendar.HOUR_OF_DAY),
                            calCurrent.get(Calendar.MINUTE),
                            true
                        ).show()
                    },
                    currentYear,
                    currentMonth,
                    currentDay
                )

                dpd.show()
                true
            }
            R.id.cancel -> {
                activity?.onBackPressed()
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

    companion object {
        val CONTENT_KEY = "CONTENT_KEY"
    }
}

package hn.edu.ujcv.pdm_2022_i_l2_equipo3.clases

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment(val listener: (String) -> Unit): DialogFragment(),
    TimePickerDialog.OnTimeSetListener {
    var hora: Int = 0
    var minuto: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        hora = Calendar.HOUR_OF_DAY
        minuto = Calendar.MINUTE
        return TimePickerDialog(activity as Context, this, hora, minuto, true)
    }
    override fun onTimeSet(p0: TimePicker?, hora: Int, minuto: Int) {
        listener("$hora:$minuto")
    }
}
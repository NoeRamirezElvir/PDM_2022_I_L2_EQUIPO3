package hn.edu.ujcv.pdm_2022_i_l2_equipo3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.clases.Alumno
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.ui.registraralumno.RegistrarAlumnoFragment
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.ui.registraralumno.RegistrarAlumnoViewModel

class RegistrarAlumnoActivity : AppCompatActivity() {
    private lateinit var viewModel: RegistrarAlumnoViewModel
    private lateinit var fragment: RegistrarAlumnoFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registrar_alumno__activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fraAlumno, RegistrarAlumnoFragment.newInstance())
                .commitNow()
            viewModel = RegistrarAlumnoViewModel()
            fragment = RegistrarAlumnoFragment()
        }
    }


}
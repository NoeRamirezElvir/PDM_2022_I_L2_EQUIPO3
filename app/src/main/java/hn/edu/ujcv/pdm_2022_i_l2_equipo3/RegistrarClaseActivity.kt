package hn.edu.ujcv.pdm_2022_i_l2_equipo3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.ui.registrarclase.RegistrarClaseFragment
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.ui.registrarclase.RegistrarClaseViewModel

class RegistrarClaseActivity : AppCompatActivity() {
    private lateinit var viewModel: RegistrarClaseViewModel
    private lateinit var fragment: RegistrarClaseFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registrar_clase_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RegistrarClaseFragment.newInstance())
                .commitNow()
            viewModel = RegistrarClaseViewModel()
            fragment = RegistrarClaseFragment()
        }
    }

}
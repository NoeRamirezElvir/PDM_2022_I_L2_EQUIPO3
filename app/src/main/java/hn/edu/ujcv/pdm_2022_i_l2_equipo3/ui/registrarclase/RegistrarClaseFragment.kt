package hn.edu.ujcv.pdm_2022_i_l2_equipo3.ui.registrarclase

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.FragmentManager
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.R
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.clases.TimePickerFragment
import kotlinx.android.synthetic.main.registrar_clase_fragment.*

class RegistrarClaseFragment : Fragment() {

    companion object {
        fun newInstance() = RegistrarClaseFragment()
    }

    lateinit var viewModel: RegistrarClaseViewModel
    var hora: Int = 0
    var minuto: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.registrar_clase_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegistrarClaseViewModel::class.java)
        viewModel.recibirInformacion(this@RegistrarClaseFragment)

        //Time Picker
        //Validaciones
        txtCodigo.doAfterTextChanged {
            txtCodigo.error = viewModel.Validaciones().validarCodigo(this@RegistrarClaseFragment)
        }
        txtNombreClase.doAfterTextChanged {
            txtNombreClase.error = viewModel.Validaciones().validarNombre(this@RegistrarClaseFragment)
        }
        txtSeccion.doAfterTextChanged {
            txtSeccion.error = viewModel.Validaciones().validarSeccion(this@RegistrarClaseFragment)
        }
        txtHora.setOnClickListener { timePicker(this@RegistrarClaseFragment) }
        txtHora.doAfterTextChanged {
            txtHora.error = viewModel.Validaciones().validarHora(this@RegistrarClaseFragment)
        }
        txtEdificio.doAfterTextChanged {
            txtEdificio.error = viewModel.Validaciones().validarEdificio(this@RegistrarClaseFragment)
        }
        txtAula.doAfterTextChanged {
            txtAula.error = viewModel.Validaciones().validarAula(this@RegistrarClaseFragment)
        }
        //Botones
        btnRegistrarClase.setOnClickListener {
            if(!viewModel.validarBoton(this@RegistrarClaseFragment)){
                Toast.makeText(this.requireContext(), "Comprobar los datos", Toast.LENGTH_SHORT).show()
            }else{
                viewModel.registrarClase(this@RegistrarClaseFragment)
            }
        }
        ivbtnRegresarC.setOnClickListener{
            viewModel.enviarInformacion(this@RegistrarClaseFragment)
        }
        //boton regresar/Interfaz
        val callback =  object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.enviarInformacion(this@RegistrarClaseFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)
    }
    private fun timePicker(fragment: RegistrarClaseFragment){
        val timePicker =TimePickerFragment { onTimeSelected(it, fragment) }
        timePicker.show(this.childFragmentManager,"time")
    }

    private fun onTimeSelected(time: String,fragment: RegistrarClaseFragment) {
        fragment.txtHora.setText(time)
    }

}
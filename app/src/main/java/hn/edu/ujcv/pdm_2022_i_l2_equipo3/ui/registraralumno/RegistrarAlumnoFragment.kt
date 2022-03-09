package hn.edu.ujcv.pdm_2022_i_l2_equipo3.ui.registraralumno

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.viewModelScope
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.R
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.clases.Alumno
import kotlinx.android.synthetic.main.registrar_alumno__fragment.*

class RegistrarAlumnoFragment : Fragment() {
    companion object {
        fun newInstance() = RegistrarAlumnoFragment()
    }
    lateinit var viewModel: RegistrarAlumnoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.registrar_alumno__fragment, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegistrarAlumnoViewModel::class.java)
        viewModel.recibirInformacion(this@RegistrarAlumnoFragment)

        //validaciones de los cuadros de texto
        txtNumeroCuenta.doAfterTextChanged {
            txtNumeroCuenta.error =  viewModel.Validaciones().validarCuenta(this@RegistrarAlumnoFragment)
        }
        txtNombre.doAfterTextChanged {
            txtNombre.error = viewModel.Validaciones().validarNombre(this@RegistrarAlumnoFragment)
        }
        txtCorreo.doAfterTextChanged {
            txtCorreo.error = viewModel.Validaciones().validarCorreo(this@RegistrarAlumnoFragment) }

        //Botones
        btnRegistrarAlumno.setOnClickListener {
            if(!viewModel.validarBoton(this@RegistrarAlumnoFragment)){
                Toast.makeText(this.requireContext(), "Comprobar los datos", Toast.LENGTH_SHORT).show()
            }else{
                viewModel.registrarAlumno(this@RegistrarAlumnoFragment)
            }
        }
        ivbtnRegresarA.setOnClickListener{
            viewModel.enviarInformacion(this@RegistrarAlumnoFragment)
        }
        //boton regresar/Interfaz
        val callback =  object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.enviarInformacion(this@RegistrarAlumnoFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)
    }
}
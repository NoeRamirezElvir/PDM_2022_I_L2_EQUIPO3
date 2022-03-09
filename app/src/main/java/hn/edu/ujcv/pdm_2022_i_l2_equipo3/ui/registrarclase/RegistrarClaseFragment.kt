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
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.R
import kotlinx.android.synthetic.main.registrar_clase_fragment.*

class RegistrarClaseFragment : Fragment() {

    companion object {
        fun newInstance() = RegistrarClaseFragment()
    }

    lateinit var viewModel: RegistrarClaseViewModel

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

        //spinner
        val spinner = spnHora
        val lista = resources.getStringArray(R.array.spinner)
        val adaptador = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, lista)
        spinner.adapter = adaptador

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
        txtHora.doAfterTextChanged {
            txtHora.error = viewModel.Validaciones().validarHora(this@RegistrarClaseFragment)
        }
        txtMinutos.doAfterTextChanged {
            txtMinutos.error = viewModel.Validaciones().validarMinutos(this@RegistrarClaseFragment)
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

}
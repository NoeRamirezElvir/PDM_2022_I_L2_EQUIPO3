package hn.edu.ujcv.pdm_2022_i_l2_equipo3.ui.registraralumno

import android.content.Intent
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.MainActivity
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.R
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.clases.Alumno
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.clases.Clase
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.clases.Matricula
import kotlinx.android.synthetic.main.registrar_alumno__fragment.*


class RegistrarAlumnoViewModel : ViewModel() {
    var listaAlumnos = ArrayList<Alumno>()
    var listaClases:ArrayList<Clase>? = ArrayList()
    var listaMatricula:ArrayList<Matricula>? = ArrayList()

    fun registrarAlumno(fragment: RegistrarAlumnoFragment){
        val cuenta = fragment.txtNumeroCuenta.text.toString().toLong()
        val nombre = fragment.txtNombre.text.toString()
        val correo = fragment.txtCorreo.text.toString()
        dialogo(Alumno(cuenta,nombre,correo),fragment)
    }
    fun enviarInformacion(fragment: RegistrarAlumnoFragment){
        val intent = Intent(fragment.context, MainActivity::class.java)
        intent.putExtra("Alumnos",listaAlumnos)
        intent.putExtra("Clases",listaClases)
        intent.putExtra("Matricula", listaMatricula)
        fragment.startActivity(intent)
    }
    fun recibirInformacion(fragment: RegistrarAlumnoFragment){
        val intent = fragment.requireActivity().intent
        if(fragment.requireActivity().intent.getParcelableArrayListExtra<Alumno>("Alumnos") != null){
            listaAlumnos = intent.getParcelableArrayListExtra("Alumnos")!!
        }
        if(fragment.requireActivity().intent.getParcelableArrayListExtra<Alumno>("Clases") != null){
            listaClases = intent.getParcelableArrayListExtra("Clases")!!
        }
        if(fragment.requireActivity().intent.getParcelableArrayListExtra<Clase>("Matricula") != null){
            listaMatricula = intent.getParcelableArrayListExtra("Matricula")!!
        }
    }
    fun dialogo(alumno: Alumno,fragment: RegistrarAlumnoFragment){
        val dialog = AlertDialog.Builder(fragment.requireContext())
            .setTitle("¿Desea registrar el siguiente estudiante?")
            .setMessage("No. Cuenta: ${alumno.cuenta}\n" +
                    " Nombre: ${alumno.nombre}\n" +
                    " Correo: ${alumno.correo}.")
            .setIcon(R.drawable.ic_registrar_alumno)
            .setPositiveButton("Si"){_,_ ->
                listaAlumnos.add(alumno)
                Toast.makeText(fragment.requireContext(),"Se ha registrado correctamente", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No"){_,_ ->
                Toast.makeText(fragment.requireContext(),"No se ha registrado", Toast.LENGTH_SHORT).show()
            }.create()
        dialog.show()
    }
    inner class Validaciones{
        fun validarCuenta(fragment: RegistrarAlumnoFragment):String?{
            var mensaje:String?=null
            try{
                if(fragment.txtNumeroCuenta.text.isNullOrEmpty()){
                    throw IllegalArgumentException("El número de cuenta esta vacío")
                }
                if(fragment.txtNumeroCuenta.text.toString().length < 10){
                    throw IllegalArgumentException("El número de cuenta es muy corto")
                }
                if(fragment.txtNumeroCuenta.text.toString().length > 10){
                    throw IllegalArgumentException("El número de cuenta es muy largo")
                }
            }catch (e:IllegalArgumentException){
                mensaje = e.message
            }
            finally{
                return mensaje
            }
        }
        fun validarNombre(fragment: RegistrarAlumnoFragment):String?{
            var mensaje:String?=null
            try{
                if (fragment.txtNombre.text.isNullOrEmpty()) {
                    throw IllegalArgumentException("El nombre está en blanco.")
                }
                if (fragment.txtNombre.text.length < 3) {
                    throw IllegalArgumentException("El nombre es muy corto.")
                }
                if (fragment.txtNombre.text.length > 40) {
                    throw IllegalArgumentException("El nombre es muy largo.")
                }
            }catch (e:IllegalArgumentException){
                mensaje = e.message
            }
            finally{
                return mensaje
            }
        }
        fun validarCorreo(fragment: RegistrarAlumnoFragment):String?{
            var mensaje:String?=null
            try{
                if (fragment.txtCorreo.text.isNullOrEmpty()) {
                    throw IllegalArgumentException("El correo está en blanco.")
                }
                if (fragment.txtCorreo.text.length < 5) {
                    throw IllegalArgumentException("El correo es muy corto.")
                }
                if (fragment.txtCorreo.text.length > 50) {
                    throw IllegalArgumentException("El correo es muy largo.")
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(fragment.txtCorreo.text).matches()){
                    throw IllegalArgumentException("Dirección de correo invalida.")
                }
            }catch (e:IllegalArgumentException){
                mensaje = e.message
            }
            finally{
                return mensaje
            }
        }
    }
    fun validarBoton(fragment: RegistrarAlumnoFragment):Boolean{
        return when{
            fragment.txtNumeroCuenta.text.isNullOrEmpty() -> false
            fragment.txtNumeroCuenta.text.toString().length < 10 -> false
            fragment.txtNumeroCuenta.text.toString().length > 10 -> false
            fragment.txtNombre.text.isNullOrEmpty() -> false
            fragment.txtNombre.text.length < 3 -> false
            fragment.txtNombre.text.length > 40 -> false
            fragment.txtCorreo.text.isNullOrEmpty() -> false
            fragment.txtCorreo.text.length < 5 -> false
            fragment.txtCorreo.text.length > 50 -> false
            !Patterns.EMAIL_ADDRESS.matcher(fragment.txtCorreo.text).matches() -> false
            else-> true
        }
    }

}
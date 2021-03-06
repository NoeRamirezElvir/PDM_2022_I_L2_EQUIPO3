package hn.edu.ujcv.pdm_2022_i_l2_equipo3.ui.registrarclase

import android.app.AlertDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.MainActivity
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.R
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.clases.Alumno
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.clases.Clase
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.clases.Matricula
import kotlinx.android.synthetic.main.registrar_clase_fragment.*
import java.util.*
import kotlin.IllegalArgumentException
import kotlin.collections.ArrayList

class RegistrarClaseViewModel : ViewModel() {
    var listaClases = ArrayList<Clase>()
    var listaAlumnos:ArrayList<Alumno>? = ArrayList()
    var listaMatricula:ArrayList<Matricula>? = ArrayList()

    fun registrarClase(fragment: RegistrarClaseFragment){
        val codigo   =  fragment.txtCodigo.text.toString()
        val nombre   = fragment.txtNombreClase.text.toString()
        val seccion  = fragment.txtSeccion.text.toString()
        val hora     = fragment.txtHora.text.toString()
        val edificio = fragment.txtEdificio.text.toString()
        val aula     = fragment.txtAula.text.toString()
        dialogo(Clase(codigo, nombre, seccion, hora, edificio, aula),fragment)

    }
    fun enviarInformacion(fragment: RegistrarClaseFragment){
        val intent = Intent(fragment.context, MainActivity::class.java)
        intent.putExtra("Clases",listaClases)
        intent.putExtra("Alumnos",listaAlumnos)
        intent.putExtra("Matricula", listaMatricula)
        fragment.startActivity(intent)
    }
    fun recibirInformacion(fragment: RegistrarClaseFragment){
        val intent = fragment.requireActivity().intent
        if(fragment.requireActivity().intent.getParcelableArrayListExtra<Clase>("Clases") != null){
            listaClases = intent.getParcelableArrayListExtra("Clases")!!
        }
        if(fragment.requireActivity().intent.getParcelableArrayListExtra<Clase>("Alumnos") != null){
            listaAlumnos = intent.getParcelableArrayListExtra("Alumnos")!!
        }
        if(fragment.requireActivity().intent.getParcelableArrayListExtra<Clase>("Matricula") != null){
            listaMatricula = intent.getParcelableArrayListExtra("Matricula")!!
        }

    }
    fun dialogo(clase: Clase,fragment: RegistrarClaseFragment){
        val dialog = AlertDialog.Builder(fragment.requireContext())
            .setTitle("??Desea registrar la siguiente clase?")
            .setMessage("C??digo:${clase.codigo}\n" +
                    " Nombre: ${clase.nombre}\n" +
                    " Secci??n: ${clase.seccion}\n"+
                    " Hora: ${clase.hora}\n"+
                    " Edificio/Piso: ${clase.edificio}\n"+
                    " Aula: ${clase.aula}.")
            .setIcon(R.drawable.ic_registrar_clase)
            .setPositiveButton("Si"){_,_ ->
                listaClases.add(clase)
                Toast.makeText(fragment.requireContext(),"Se ha registrado correctamente", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No"){_,_ ->
                Toast.makeText(fragment.requireContext(),"No se ha registrado", Toast.LENGTH_SHORT).show()
            }.create()
        dialog.show()
    }
    inner class Validaciones{
        fun validarCodigo(fragment: RegistrarClaseFragment):String?{
            var mensaje:String? = null
            try{
                if(fragment.txtCodigo.text.isNullOrEmpty()){
                    throw IllegalArgumentException("El c??digo de la clase esta vac??o")
                }
                if(fragment.txtCodigo.text.toString().length < 7){
                    throw IllegalArgumentException("El c??digo de la clase es muy corto")
                }
                if(fragment.txtCodigo.text.toString().length > 10 ){
                    throw IllegalArgumentException("El c??digo de la clase es muy largo")
                }
            }catch (e:IllegalArgumentException){
                mensaje = e.message
            }
            finally {
                return mensaje
            }
        }
        fun validarNombre(fragment: RegistrarClaseFragment):String?{
            var mensaje:String? = null
            try{
                if(fragment.txtNombreClase.text.isNullOrEmpty()){
                    throw IllegalArgumentException("El nombre de la clase esta vac??o")
                }
                if(fragment.txtNombreClase.text.toString().length < 4){
                    throw IllegalArgumentException("El nombre de la clase es muy corto")
                }
                if(fragment.txtNombreClase.text.toString().length > 50 ){
                    throw IllegalArgumentException("El nombre de la clase es muy largo")
                }
            }catch (e:IllegalArgumentException){
                mensaje = e.message
            }
            finally {
                return mensaje
            }
        }
        fun validarSeccion(fragment: RegistrarClaseFragment):String?{
            var mensaje:String? = null
            try{
                if(fragment.txtSeccion.text.isNullOrEmpty()){
                    throw IllegalArgumentException("La secci??n esta vac??a")
                }
                if(fragment.txtSeccion.text.toString().isEmpty()){
                    throw IllegalArgumentException("La secci??n es muy corta")
                }
                if(fragment.txtSeccion.text.toString().length > 2 ){
                    throw IllegalArgumentException("La secci??n es muy larga")
                }
            }catch (e:IllegalArgumentException){
                mensaje = e.message
            }
            finally {
                return mensaje
            }
        }
        fun validarHora(fragment: RegistrarClaseFragment):String?{
            var mensaje:String? = null
            try{
                if(fragment.txtHora.text.isNullOrEmpty()){
                    throw IllegalArgumentException("La hora esta vac??a")
                }
                if(fragment.txtHora.text.toString().isEmpty()){
                    throw IllegalArgumentException("La hora es muy corta")
                }
            }catch (e:IllegalArgumentException){
                mensaje = e.message
            }
            finally {
                return mensaje
            }
        }
        fun validarEdificio(fragment: RegistrarClaseFragment):String?{
            var mensaje:String? = null
            try{
                if(fragment.txtEdificio.text.isNullOrEmpty()){
                    throw IllegalArgumentException("Edificio esta vac??o")
                }
                if(fragment.txtEdificio.text.toString().length < 3){
                    throw IllegalArgumentException("Edificio es muy corto")
                }
                if(fragment.txtEdificio.text.toString().length > 50 ){
                    throw IllegalArgumentException("Edificio es muy largo")
                }
            }catch (e:IllegalArgumentException){
                mensaje = e.message
            }
            finally {
                return mensaje
            }
        }
        fun validarAula(fragment: RegistrarClaseFragment):String?{
            var mensaje:String? = null
            try{
                if(fragment.txtAula.text.isNullOrEmpty()){
                    throw IllegalArgumentException("Aula esta vac??a")
                }
                if(fragment.txtAula.text.toString().isEmpty()){
                    throw IllegalArgumentException("Aula es muy corta")
                }
                if(fragment.txtAula.text.toString().length > 50 ){
                    throw IllegalArgumentException("Aula es muy larga")
                }
            }catch (e:IllegalArgumentException){
                mensaje = e.message
            }
            finally {
                return mensaje
            }
        }
    }
    fun validarBoton(fragment: RegistrarClaseFragment):Boolean{
        return when{
            fragment.txtCodigo.text.isNullOrEmpty() -> false
            fragment.txtCodigo.text.toString().length < 7 -> false
            fragment.txtCodigo.text.toString().length > 10 -> false
            fragment.txtNombreClase.text.isNullOrEmpty() -> false
            fragment.txtNombreClase.text.toString().length < 4 -> false
            fragment.txtNombreClase.text.toString().length > 50 -> false
            fragment.txtSeccion.text.isNullOrEmpty() -> false
            fragment.txtSeccion.text.toString().isEmpty() -> false
            fragment.txtSeccion.text.toString().length > 2 -> false
            fragment.txtHora.text.isNullOrEmpty() -> false
            fragment.txtHora.text.toString().isEmpty() -> false
            fragment.txtEdificio.text.isNullOrEmpty() -> false
            fragment.txtEdificio.text.toString().length < 3 -> false
            fragment.txtEdificio.text.toString().length > 50 -> false
            fragment.txtAula.text.isNullOrEmpty() -> false
            fragment.txtAula.text.toString().isEmpty() -> false
            fragment.txtAula.text.toString().length > 50 -> false
            else -> true
        }
    }

}
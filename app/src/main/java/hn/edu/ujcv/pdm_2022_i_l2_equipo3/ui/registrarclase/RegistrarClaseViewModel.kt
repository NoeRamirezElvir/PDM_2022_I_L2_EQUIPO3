package hn.edu.ujcv.pdm_2022_i_l2_equipo3.ui.registrarclase

import android.app.AlertDialog
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.MainActivity
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.R
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.clases.Alumno
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.clases.Clase
import kotlinx.android.synthetic.main.registrar_clase_fragment.*
import kotlin.IllegalArgumentException

class RegistrarClaseViewModel : ViewModel() {
    var listaClases = ArrayList<Clase>()
    var listaAlumnos:ArrayList<Alumno>? = ArrayList()

    fun registrarClase(fragment: RegistrarClaseFragment){
        val codigo   =  fragment.txtCodigo.text.toString()
        val nombre   = fragment.txtNombreClase.text.toString()
        val seccion  = fragment.txtSeccion.text.toString()
        val hora     = "${fragment.txtHora.text}:${fragment.txtMinutos.text} ${fragment.spnHora.selectedItem}"
        val edificio = fragment.txtEdificio.text.toString()
        val aula     = fragment.txtAula.text.toString()
        dialogo(Clase(codigo, nombre, seccion, hora, edificio, aula),fragment)

    }
    fun enviarInformacion(fragment: RegistrarClaseFragment){
        val intent = Intent(fragment.context, MainActivity::class.java)
        intent.putExtra("Clases",listaClases)
        intent.putExtra("Alumnos",listaAlumnos)
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

    }
    fun dialogo(clase: Clase,fragment: RegistrarClaseFragment){
        val dialog = AlertDialog.Builder(fragment.requireContext())
            .setTitle("¿Desea registrar la siguiente clase?")
            .setMessage("Código:${clase.codigo}\n" +
                    " Nombre: ${clase.nombre}\n" +
                    " Sección: ${clase.seccion}\n"+
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
                    throw IllegalArgumentException("El código de la clase esta vacío")
                }
                if(fragment.txtCodigo.text.toString().length < 7){
                    throw IllegalArgumentException("El código de la clase es muy corto")
                }
                if(fragment.txtCodigo.text.toString().length > 10 ){
                    throw IllegalArgumentException("El código de la clase es muy largo")
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
                    throw IllegalArgumentException("El nombre de la clase esta vacío")
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
                    throw IllegalArgumentException("La sección esta vacía")
                }
                if(fragment.txtSeccion.text.toString().isEmpty()){
                    throw IllegalArgumentException("La sección es muy corta")
                }
                if(fragment.txtSeccion.text.toString().length > 2 ){
                    throw IllegalArgumentException("La sección es muy larga")
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
                    throw IllegalArgumentException("La hora esta vacía")
                }
                if(fragment.txtHora.text.toString().isEmpty()){
                    throw IllegalArgumentException("La hora es muy corta")
                }
                if(fragment.txtHora.text.toString().length > 2){
                    throw IllegalArgumentException("La hora es muy larga")
                }
                if(fragment.txtHora.text.toString().toInt() < 0){
                    throw IllegalArgumentException("El formato de hora es invalido")
                }
                if(fragment.txtHora.text.toString().toInt() > 12){
                    throw IllegalArgumentException("El formato de hora es invalido")
                }
            }catch (e:IllegalArgumentException){
                mensaje = e.message
            }
            finally {
                return mensaje
            }
        }
        fun validarMinutos(fragment: RegistrarClaseFragment):String?{
            var mensaje:String? = null
            try{
                if(fragment.txtMinutos.text.isNullOrEmpty()){
                    throw IllegalArgumentException("Los minutos están vacíos")
                }
                if(fragment.txtMinutos.text.toString().length < 2){
                    throw IllegalArgumentException("Los minutos son invalidos")
                }
                if(fragment.txtMinutos.text.toString().length > 2 ){
                    throw IllegalArgumentException("Los minutos exceden el límite")
                }
                if(fragment.txtMinutos.text.toString().toInt() < 0){
                    throw IllegalArgumentException("El formato de hora es invalido")
                }
                if(fragment.txtMinutos.text.toString().toInt() > 59){
                    throw IllegalArgumentException("El formato de hora es invalido")
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
                    throw IllegalArgumentException("Edificio esta vacío")
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
                    throw IllegalArgumentException("Aula esta vacía")
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
            fragment.txtHora.text.toString().length > 2 -> false
            fragment.txtHora.text.toString().toInt() < 0 -> false
            fragment.txtHora.text.toString().toInt() >12 -> false
            fragment.txtMinutos.text.isNullOrEmpty() -> false
            fragment.txtMinutos.text.toString().length < 2 -> false
            fragment.txtMinutos.text.toString().length > 2 -> false
            fragment.txtMinutos.text.toString().toInt() < 0 -> false
            fragment.txtMinutos.text.toString().toInt() >59 -> false
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
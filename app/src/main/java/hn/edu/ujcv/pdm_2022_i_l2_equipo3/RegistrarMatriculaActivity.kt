package hn.edu.ujcv.pdm_2022_i_l2_equipo3

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.clases.Alumno
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.clases.Clase
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.clases.Matricula
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.databinding.ActivityRegistrarMatriculaBinding
import kotlinx.android.synthetic.main.activity_registrar_matricula.*
import kotlinx.android.synthetic.main.fragment_first.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RegistrarMatriculaActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityRegistrarMatriculaBinding

    var listaAlumnos = ArrayList<Alumno>()
    var listaClases:ArrayList<Clase>? = ArrayList()
    var listaMatricula:ArrayList<Matricula>? = ArrayList()
    var listItems = ArrayList<String>()
    var adapter: ArrayAdapter<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrarMatriculaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_registrar_matricula)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        inicializar()

        //Botones
        binding.fab.setOnClickListener { view ->
            if (validarClase()) {
                Toast.makeText(this, "Clase ya agregada", Toast.LENGTH_LONG).show()
            } else {
                agregarClaseLista()
                Snackbar.make(view, "La clase fue adicionada", Snackbar.LENGTH_LONG)
                    .setAction("Deshacer", deshacerOnClickListener).show()
            }
        }
        binding.btnGuardar.setOnClickListener {
            if (listItems.isEmpty())
                Toast.makeText(this, "Ingrese clases a matricular", Toast.LENGTH_LONG).show()
            else if (validarMatricula()) {
                Toast.makeText(this, "Alumno(a) ya ha registrado su matricula",
                Toast.LENGTH_LONG).show()
            }
            else
                mostrarDialog()
        }
        binding.btnCorreo.setOnClickListener {
            if ((listaMatricula!!.isEmpty()) || (!validarMatricula()))
                Toast.makeText(this, "Guarde los datos", Toast.LENGTH_LONG).show()
            else
                enviarCorreo()

        }

        //tooltip de floating button
        if (Build.VERSION.SDK_INT > 25) {
            fab.tooltipText = "Agregar clase"
        }


        val actionbar = supportActionBar
        actionbar!!.title = "Matrícula"
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp():Boolean {
        regresar()
        return super.onSupportNavigateUp()
    }
    fun regresar(){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("Alumnos",listaAlumnos)
        intent.putExtra("Clases",listaClases)
        intent.putExtra("Matricula", listaMatricula)
        startActivity(intent)
    }

    private fun inicializar() {
        val intent = intent
        if (intent.getParcelableArrayListExtra<Alumno>("Alumnos") != null) {
            listaAlumnos = intent.getParcelableArrayListExtra("Alumnos")!!
        }
        if (intent.getParcelableArrayListExtra<Clase>("Clases") != null) {
            listaClases = intent.getParcelableArrayListExtra("Clases")!!
        }
        if (intent.getParcelableArrayListExtra<Matricula>("Matricula") != null) {
            listaMatricula = intent.getParcelableArrayListExtra("Matricula")!!
        }
        agregarSpinnerAlumno()
        agregarSpinnerClase()
    }

    private fun validarClase():Boolean {
        var item = ""
        for (clase in listaClases!!) {
            if (spnClases.selectedItem.toString() == clase.codigo) {
                item = clase.codigo + "  " + clase.nombre
                break
            }
        }
        return listItems.contains(item)
    }

    private fun validarMatricula():Boolean {
        var validar:Boolean = false
        for (matricula in listaMatricula!!) {
            if (spnAlumno.selectedItem == matricula.alumno.nombre) {
                validar = true
                break
            }
        }
        return validar
    }

   private fun agregarSpinnerAlumno() {
       var alumnos = ArrayAdapter<String>(this,
       android.R.layout.simple_spinner_dropdown_item)
       for (items in listaAlumnos) {
           alumnos.addAll(items.nombre)
       }
       spnAlumno.onItemSelectedListener = this
       spnAlumno.adapter = alumnos
   }

    private fun agregarSpinnerClase() {
        var clases = ArrayAdapter<String>(this,
        android.R.layout.simple_spinner_dropdown_item)
        for (items in listaClases!!) {
            clases.addAll(items.codigo)
        }
        spnClases.onItemSelectedListener = this
        spnClases.adapter = clases
    }

    private fun agregarClaseLista() {
        var clase:String? = null
        for (items in listaClases!!) {
          if (items.codigo == spnClases.selectedItem.toString()) {
              clase = items.codigo + "  " + items.nombre
              break
          }
        }
        listItems.add(clase!!)
        adapter?.notifyDataSetChanged()
    }

    override fun onStart() {
        super.onStart()
        adapter = ArrayAdapter(this,
        android.R.layout.simple_list_item_1, listItems)
        lstView.adapter = adapter
    }

    var deshacerOnClickListener: View.OnClickListener = View.OnClickListener {
        view ->
        listItems.removeAt(listItems.size - 1)
        adapter?.notifyDataSetChanged()
        Snackbar.make(view, "Clase eliminada", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    private fun mostrarDialog() {
        var alumno = spnAlumno.selectedItem.toString()
        var clases:String = ""

        for (item in listaAlumnos) {
            if (alumno == item.nombre) {
                alumno = "Nombre: " + item.nombre + "\nNúmero de cuenta: " + item.cuenta
                break
            }
        }
        for (item in listItems) {
            clases += item + "\n"
        }

        var mensaje = alumno +
                "\n\nClases:\n" + clases
        val dialog = AlertDialog.Builder(this)
            .setTitle("¿Desea registrar la siguiente matrícula?")
            .setMessage(mensaje)
            .setIcon(R.drawable.registration_form_icon)
            .setPositiveButton("Si"){_,_ ->
                Toast.makeText(this, "Guardado Exitosamente", Toast.LENGTH_SHORT).show()
                guardarDatos()
            }
            .setNegativeButton("No"){_,_ ->
                Toast.makeText(this, "No se guardó el registro",
                    Toast.LENGTH_SHORT).show()
            }.create()
        dialog.show()
    }

    private fun guardarDatos() {
        var lista_clases:ArrayList<Clase>? = ArrayList()
        var cuenta:Long? = null
        var nombre:String? = null
        var correo:String? = null
        for (clase in listaClases!!) {
            if (listItems.contains(clase.codigo + "  " + clase.nombre)) {
                lista_clases!!.add(clase)
            }
        }
        for (item in listaAlumnos) {
            if(spnAlumno.selectedItem.toString() == item.nombre) {
                cuenta = item.cuenta
                nombre = item.nombre
                correo=item.correo
                break
            }
        }
        listaMatricula!!.add(Matricula(Alumno(cuenta!!, nombre!!, correo!!), lista_clases!!))
    }

    private fun enviarCorreo() {
        var alumno:String = ""
        var emailAddress:String = ""
        var mensajeClases:String = ""
        for (item in listaMatricula!!) {
            if (spnAlumno.selectedItem.toString() == item.alumno.nombre) {
                alumno = "Nombre: " + item.alumno.nombre + "\nNúmero de Cuenta: " + item.alumno.cuenta
                emailAddress = item.alumno.correo
                for (clase in item.listaClases) {
                    mensajeClases += "\nCódigo: " + clase.codigo +
                            "\nClase: " + clase.nombre +
                            "\nSección: " + clase.seccion +
                            "\nHora: " + clase.hora +
                            "\nEdificio/Piso: " + clase.edificio +
                            "\nAula: " + clase.aula + "\n"
                }
                break
            }
        }
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val hourFormat = SimpleDateFormat("HH:mm:ss", Locale.US)
        var mensaje = alumno +
                "\n\nFecha de Inscripción: " + dateFormat.format(Date()) +
                "\nHora de Inscripción: " + hourFormat.format(Date()) +
                "\n\nASIGNATURAS INSCRITAS"
        mensaje += mensajeClases
        val asunto = "Inscripción de Clases"
        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto:")
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
        intent.putExtra(Intent.EXTRA_SUBJECT, asunto)
        intent.putExtra(Intent.EXTRA_TEXT, mensaje)

        try {
            startActivity(Intent.createChooser(intent,"Elija una opción.."))
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    /*override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_registrar_matricula)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }*/
}
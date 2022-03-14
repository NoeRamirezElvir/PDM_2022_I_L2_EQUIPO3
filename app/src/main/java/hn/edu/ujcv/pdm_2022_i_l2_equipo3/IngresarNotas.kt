package hn.edu.ujcv.pdm_2022_i_l2_equipo3

import android.content.Intent
import android.net.Uri
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
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.clases.*
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.databinding.ActivityIngresarNotasBinding
import kotlinx.android.synthetic.main.activity_ingresar_notas.*
import kotlinx.android.synthetic.main.activity_registrar_matricula.*
import kotlinx.android.synthetic.main.fragment_first2.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class IngresarNotas : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityIngresarNotasBinding

    var listaMatricula: ArrayList<Matricula> = ArrayList()
    var listaNotas: ArrayList<Notas>? = ArrayList()
    var listaClases: ArrayList<Clase> = ArrayList()
    var listaAlumnos = ArrayList<Alumno>()
    var listItems = ArrayList<String>()
    var nombreItems = ArrayList<String>()
    var listNotaC = ArrayList<NotaC>()
    var adapter: ArrayAdapter<String>? = null
    var n1 = 0F
    var n2 = 0F
    var n3 = 0F
    var nf = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIngresarNotasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarNotas)

        val navController = findNavController(R.id.nav_host_fragment_content_ingresar_notas)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        inicializar()

        binding.btnCalcularNota.setOnClickListener {
            if (calcularNf() != null) {
                Toast.makeText(this, calcularNf(), Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnGuardarN.setOnClickListener {
            if (listItems.isEmpty())
                Toast.makeText(this, "Agregue notas ", Toast.LENGTH_LONG).show()
            else if (validarNota()) {
                Toast.makeText(this, "Alumno(a) ya ha asignado sus notas", Toast.LENGTH_LONG).show()
            } else {
                mostrarDialog()
            }
        }
        binding.fab2.setOnClickListener { view ->
            if (txtNF.text.isNullOrEmpty()) {
                Toast.makeText(this, "Calcule La nota", Toast.LENGTH_SHORT).show()
            } else if (validarClase()) {
                Toast.makeText(this, "Nota ya asignada a esta Clase", Toast.LENGTH_LONG).show()
            } else {
                agregarNotasLista()
                spnMatriculaAlumno.isEnabled = false
                Snackbar.make(view, "Nota Agregada", Snackbar.LENGTH_LONG)
                    .setAction("Deshacer", deshacerOnClickListener).show()
            }
        }
        binding.btnCorreoN.setOnClickListener {
            if ((listaNotas!!.isEmpty()) || (!validarNota()))
                Toast.makeText(this, "Guarde los datos", Toast.LENGTH_LONG).show()
            else
                enviarCorreo()
        }
        val actionbar = supportActionBar
        actionbar!!.title = "Ingreso de Notas"
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun inicializar() {
        val intent = intent
        if (intent.getParcelableArrayListExtra<Matricula>("Matricula") != null) {
            listaMatricula = intent.getParcelableArrayListExtra("Matricula")!!
        }
        if (intent.getParcelableArrayListExtra<Clase>("Clases") != null) {
            listaClases = intent.getParcelableArrayListExtra("Clases")!!
        }
        if (intent.getParcelableArrayListExtra<Alumno>("Alumnos") != null) {
            listaAlumnos = intent.getParcelableArrayListExtra("Alumnos")!!
        }
        agregarSpinnerAlumno()
        txtNF.isEnabled = false
        btnCorreoN.isEnabled = false
    }

    fun regresar() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("Alumnos", listaAlumnos)
        intent.putExtra("Clases", listaClases)
        intent.putExtra("Matricula", listaMatricula)
        intent.putExtra("Notas", listaNotas)
        startActivity(intent)
    }

    private fun calcularNf(): String? {
        var mensaje: String? = null
        try {
            validarEntradas()
            var n1 = txtN1.text.toString().toFloat()
            var n2 = txtN2.text.toString().toFloat()
            var n3 = txtN3.text.toString().toFloat()
            var nf = ((n1 * 0.30) + (n2 * 0.30) + (n3 * 0.40)).toFloat()
            txtNF.setText(nf.toString())
        } catch (e: Exception) {
            mensaje = e.message.toString()
        } finally {
            return mensaje
        }
    }

    private fun validarEntradas() {
        if (txtN1.text.toString().isNullOrEmpty()) {
            throw IllegalArgumentException("Nota 1 esta vacia")
        }
        if (txtN1.text.toString().toFloat() <= 0.0) {
            throw IllegalArgumentException("La nota 1 debe ser menor o igual a 0")
        }
        if (txtN1.text.toString().toFloat() > 100.0) {
            throw IllegalArgumentException("La nota 1 No debe ser mayor 100")
        }
        if (txtN2.text.toString().isNullOrEmpty()) {
            throw IllegalArgumentException("Nota 2 esta vacia")
        }
        if (txtN2.text.toString().toFloat() <= 0.0) {
            throw IllegalArgumentException("La nota 2 debe ser menor o igual a 0")
        }
        if (txtN2.text.toString().toFloat() > 100.0) {
            throw IllegalArgumentException("La nota 2 No debe ser mayor 100")
        }
        if (txtN3.text.toString().isNullOrEmpty()) {
            throw IllegalArgumentException("Nota 3 esta vacia")
        }
        if (txtN3.text.toString().toFloat() <= 0.0) {
            throw IllegalArgumentException("La nota 3 debe ser menor o igual a 0")
        }
        if (txtN3.text.toString().toFloat() > 100.0) {
            throw IllegalArgumentException("La nota 3 No debe ser mayor 100")
        }
    }

    private fun validarNotas(): Boolean {
        var condicion = false
        var listN = listItems.size
        var notas = 0
        for (list in listaMatricula) {
            notas = list.listaClases.size
            break
        }
        if (listN < notas) {
            condicion = true
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        regresar()
        return super.onSupportNavigateUp()
    }

    private fun agregarSpinnerAlumno() {
        var matriculaAlu = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item
        )
        for (items in listaMatricula) {
            matriculaAlu.addAll(items.alumno.nombre)
        }
        spnMatriculaAlumno.onItemSelectedListener = this
        spnMatriculaAlumno.adapter = matriculaAlu
    }

    private fun agregarNotasLista() {
        var clase: String? = null
        clase = spnClasesM.selectedItem.toString() + "  " + txtNF.text.toString()
        nombreItems.add(spnClasesM.selectedItem.toString()!!)
        listItems.add(clase!!)
        adapter?.notifyDataSetChanged()
        n1 = txtN1.text.toString().toFloat()
        n2 = txtN2.text.toString().toFloat()
        n3 = txtN3.text.toString().toFloat()
        nf = txtNF.text.toString().toFloat()
        listNotaC!!.add(NotaC(spnMatriculaAlumno.selectedItem.toString(),spnClasesM.selectedItem.toString(), n1, n2, n3, nf))
        txtN1.text = null
        txtN2.text = null
        txtN3.text = null
        txtNF.text = null
    }

    /*private fun validarClase(): Boolean {
        var item = ""
        for (nota in nombreItems!!) {
            for (alumno in listaNotas!!){
                if (spnClasesM.selectedItem.toString() == nota && spnMatriculaAlumno.selectedItem.toString() == alumno.matricula.alumno.nombre ) {
                    item = nota
                    break
                }
            }
        }
        return nombreItems.contains(item)
    }*/
    private fun validarClase(): Boolean {
        var item = ""
        for (nota in nombreItems!!) {
            if (spnClasesM.selectedItem.toString() == nota) {
                item = nota
                break
            }

        }
        return nombreItems.contains(item)
    }

    private fun guardarNotas() {
        var lista_notas: ArrayList<Clase>? = ArrayList()
        var cuenta: Long? = null
        var nombre: String? = null
        var correo: String? = null
        for (clase in listaClases!!) {
            for (notaf in listNotaC) {
                if (notaf.clase == clase.nombre) {
                    if (listItems.contains(clase.nombre + "  " + notaf.NF)) {
                        lista_notas!!.add(clase)
                    }
                }
            }
        }
        for (item in listaAlumnos) {
            if (spnMatriculaAlumno.selectedItem.toString() == item.nombre) {
                cuenta = item.cuenta
                nombre = item.nombre
                correo = item.correo
                break
            }
        }
        listaNotas!!.add(
            Notas(
                Matricula(Alumno(cuenta!!, nombre!!, correo!!), lista_notas!!),
                n1,
                n2,
                n3,
                nf
            )
        )
    }

    private fun mostrarDialog() {
        var alumno = spnMatriculaAlumno.selectedItem.toString()
        var notas: String = ""

        for (item in listaAlumnos) {
            if (alumno == item.nombre) {
                alumno = "Nombre: " + item.nombre + "\nNúmero de cuenta: " + item.cuenta.toString()
                break
            }
        }
        for (item in listItems) {
            notas += item + "\n"
        }

        var mensaje = alumno + "\n\nNotas:\n" + notas
        val dialog = AlertDialog.Builder(this)
            .setTitle("¿Desea Guardar las siguientes Notas?")
            .setMessage(mensaje)
            .setIcon(R.drawable.registration_form_icon)
            .setPositiveButton("Si") { _, _ ->
                guardarNotas()
                Toast.makeText(this, "Guardado Exitosamente", Toast.LENGTH_SHORT).show()
                operacionesAlGuardar()
            }
            .setNegativeButton("No") { _, _ ->
                Toast.makeText(
                    this, "No se guardó el registro",
                    Toast.LENGTH_SHORT
                ).show()
            }.create()
        dialog.show()
    }

    private fun operacionesAlGuardar() {
        btnCorreoN.isEnabled = true
        spnMatriculaAlumno.isEnabled = true
        listItems.clear()
        nombreItems.clear()
        adapter?.notifyDataSetChanged()
    }

    private fun validarNota(): Boolean {
        var validar = false
        for (nota in listaNotas!!) {
            if (spnMatriculaAlumno.selectedItem == nota.matricula.alumno.nombre) {
                validar = true
                break
            }
        }
        return validar
    }

    private fun enviarCorreo() {
        var alumno: String = ""
        var emailAddress: String = ""
        var mensajeNotas: String = ""
        for (item in listaNotas!!) {
            if (spnMatriculaAlumno.selectedItem.toString() == item.matricula.alumno.nombre) {
                alumno = "Estimado(a) " + item.matricula.alumno.nombre +
                        " a continuación se describen los resultados de sus calificaciones" + "\nNúmero de Cuenta: " + item.matricula.alumno.cuenta
                emailAddress = item.matricula.alumno.correo
                for (clase in item.matricula.listaClases) {
                    for (nota in listNotaC) {
                        if (nota.alumno == item.matricula.alumno.nombre) {
                            mensajeNotas += "\nClase: "+ nota.clase +"\nNota Primer Parcial  : " + nota.N1 +
                                    "\nNota Segundo Parcial : " + nota.N2 +
                                    "\nNota Tercer Parcial  : " + nota.N3 +
                                    "\nPromedio Nota Final  : " + nota.NF + "\n"
                            if (nota.NF > 65.0) {
                                mensajeNotas += "Felicitaciones ha APROBADO el curso\n"
                            } else {
                                mensajeNotas += "Ha REPROBADO el curso\n"
                            }
                        }
                    }
                    break
                }
            }
        }
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val hourFormat = SimpleDateFormat("HH:mm:ss", Locale.US)
        var mensaje = alumno +
                "\n\nFecha de Revision: " + dateFormat.format(Date()) +
                "\nHora: " + hourFormat.format(Date()) +
                "\n\nEVALUACIÓN A DETALLE DE CADA MATERIA:\n"
        mensaje += mensajeNotas
        val asunto = "CALIFICACIONES"
        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto:")
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
        intent.putExtra(Intent.EXTRA_SUBJECT, asunto)
        intent.putExtra(Intent.EXTRA_TEXT, mensaje)

        try {
            startActivity(Intent.createChooser(intent, "Elija una opción..."))
        } catch (e: java.lang.Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onStart() {
        super.onStart()
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, listItems
        )
        lstVNotas.adapter = adapter
    }

    var deshacerOnClickListener: View.OnClickListener = View.OnClickListener { view ->
        listItems.removeAt(listItems.size - 1)
        nombreItems.removeAt(nombreItems.size - 1)
        listNotaC.removeAt(listNotaC.size - 1)
        adapter?.notifyDataSetChanged()
        Snackbar.make(view, "Nota eliminada", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        var matri = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item
        )
        for (items in listaMatricula.get(position).listaClases) {
            matri.addAll(items.nombre)
        }
        spnClasesM.adapter = matri
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

}


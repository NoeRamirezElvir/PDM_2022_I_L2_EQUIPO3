package hn.edu.ujcv.pdm_2022_i_l2_equipo3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


import hn.edu.ujcv.pdm_2022_i_l2_equipo3.clases.Alumno
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.clases.Clase
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.clases.Matricula
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.clases.Notas
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.ui.registraralumno.RegistrarAlumnoFragment
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.ui.registrarclase.RegistrarClaseFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //Listas
    var listaAlumnos:ArrayList<Alumno>? = ArrayList()
    var listaClases:ArrayList<Clase>?   = ArrayList()
    var listaMatricula:ArrayList<Matricula>? = ArrayList()
    var listaNotas:ArrayList<Notas>? =ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Llenar las listas
        inicio()
        //Botones
        btnRegistrarAlumnoM.setOnClickListener {
            enviarListaAlumnos()
        }
        btnRegistrarClaseM.setOnClickListener {
            enviarListaClases()
        }
        btnRegistrarMatricula.setOnClickListener {
            enviarListasMatricula()
        }
        btnIngresarNotasP.setOnClickListener {
            enviarListasNotas()
        }

    }

    //Llenar las listas
    private fun inicio(){
        val intent = intent
        if(intent.getParcelableArrayListExtra<Alumno>("Alumnos") != null){
            listaAlumnos = intent.getParcelableArrayListExtra("Alumnos")!!
        }
        if(intent.getParcelableArrayListExtra<Clase>("Clases") != null){
            listaClases = intent.getParcelableArrayListExtra("Clases")!!
        }
        if(intent.getParcelableArrayListExtra<Matricula>("Matricula") != null) {
            listaMatricula = intent.getParcelableArrayListExtra("Matricula")!!
        }
    }
    //Datos Alumnos
    private fun enviarListaAlumnos(){
        val intent = Intent(this, RegistrarAlumnoActivity::class.java)
        intent.putExtra("Alumnos",listaAlumnos)
        intent.putExtra("Clases",listaClases)
        intent.putExtra("Matricula", listaMatricula)
        startActivity(intent)
    }
    //Datos Clases
    private fun enviarListaClases(){
        val intent = Intent(this, RegistrarClaseActivity::class.java)
        intent.putExtra("Clases",listaClases)
        intent.putExtra("Alumnos",listaAlumnos)
        intent.putExtra("Matricula", listaMatricula)
        startActivity(intent)
    }

    private fun enviarListasMatricula() {
        if(listaAlumnos.isNullOrEmpty()){
            val cancelDialog = AlertDialog.Builder(this)
                .setTitle("No hay alumnos registrados")
                .setMessage("Falta de informaci??n")
                .setIcon(R.drawable.icono_cancelar)
                .setPositiveButton("Aceptar"){_,_ ->
                }.create()
            cancelDialog.show()
        }
        else if(listaClases.isNullOrEmpty()) {
            val cancelDialog = AlertDialog.Builder(this)
                .setTitle("No hay clases registradas")
                .setMessage("Falta de informaci??n")
                .setIcon(R.drawable.icono_cancelar)
                .setPositiveButton("Aceptar"){_,_ ->
                }.create()
            cancelDialog.show()
        }
        else {
            val intent = Intent(this, RegistrarMatriculaActivity::class.java)
            intent.putExtra("Clases", listaClases)
            intent.putExtra("Alumnos", listaAlumnos)
            intent.putExtra("Matricula", listaMatricula)
            startActivity(intent)
        }
    }

    private fun enviarListasNotas() {
        if(listaMatricula.isNullOrEmpty()){
            val cancelDialog = AlertDialog.Builder(this)
                .setTitle("No hay Matriculas registradas")
                .setMessage("Falta de informaci??n")
                .setIcon(R.drawable.icono_cancelar)
                .setPositiveButton("Aceptar"){_,_ ->
                }.create()
            cancelDialog.show()
        }else{
            val intent = Intent(this, IngresarNotas::class.java)
            intent.putExtra("Clases", listaClases)
            intent.putExtra("Alumnos", listaAlumnos)
            intent.putExtra("Matricula", listaMatricula)
            intent.putExtra("Notas",listaNotas)
            startActivity(intent)
        }
    }

}
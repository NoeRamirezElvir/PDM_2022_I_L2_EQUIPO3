package hn.edu.ujcv.pdm_2022_i_l2_equipo3.clases

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Matricula(val alumno: Alumno, val listaClases: ArrayList<Clase>):Parcelable {
}
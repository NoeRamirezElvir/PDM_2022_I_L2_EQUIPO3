package hn.edu.ujcv.pdm_2022_i_l2_equipo3.clases

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Notas(val matricula: Matricula,var N1 :Float,var N2:Float,var N3: Float,var NF:Float):Parcelable {

}
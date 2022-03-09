package hn.edu.ujcv.pdm_2022_i_l2_equipo3.clases

import android.os.Parcel
import android.os.Parcelable
import hn.edu.ujcv.pdm_2022_i_l2_equipo3.ui.registraralumno.RegistrarAlumnoFragment

class Alumno(val cuenta:Long,val nombre: String,val correo:String): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(cuenta)
        parcel.writeString(nombre)
        parcel.writeString(correo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Alumno> {
        override fun createFromParcel(parcel: Parcel): Alumno {
            return Alumno(parcel)
        }

        override fun newArray(size: Int): Array<Alumno?> {
            return arrayOfNulls(size)
        }
    }

}
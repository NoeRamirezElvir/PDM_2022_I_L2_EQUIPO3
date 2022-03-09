package hn.edu.ujcv.pdm_2022_i_l2_equipo3.clases

import android.os.Parcel
import android.os.Parcelable

class Clase(val codigo:String,val nombre:String,val seccion:String,val hora:String,
            val edificio:String,val aula:String) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(codigo)
        parcel.writeString(nombre)
        parcel.writeString(seccion)
        parcel.writeString(hora)
        parcel.writeString(edificio)
        parcel.writeString(aula)
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<Clase> {
        override fun createFromParcel(parcel: Parcel): Clase {
            return Clase(parcel)
        }

        override fun newArray(size: Int): Array<Clase?> {
            return arrayOfNulls(size)
        }
    }
}
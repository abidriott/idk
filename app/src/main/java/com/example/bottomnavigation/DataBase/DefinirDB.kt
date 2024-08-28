package com.example.bottomnavigation.DataBase

import android.provider.BaseColumns

class DefinirDB {
    object Alumnos: BaseColumns{
        //clase estatica y abtsracta
        const val  TABLA="alumnos"
        const val ID ="id"
        const val MATRICULA="matricula"
        const val NOMBRE="nombre"
        const val DOMICILIO="domicilio"

        const val ESPECIALIDAD="especialidad"
        const val FOTO="foto"

    }
}
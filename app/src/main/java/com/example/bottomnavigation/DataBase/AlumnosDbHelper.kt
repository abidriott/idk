package com.example.bottomnavigation.DataBase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AlumnosDbHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABSE_VERSION){
        companion object{
            private const val DATABASE_NAME="sistema.db"
            private const val DATABSE_VERSION=1
            private const val TEXT_TYPE=" TEXT"
            private const val INTEGER_TYPE=" INTEGER"
            private const val COMA=","
            private const val SQL_CREATE_ALUMNO= "CREATE TABLE" + DefinirDB.Alumnos.TABLA +
                    "(${DefinirDB.Alumnos.ID}INTEGER PRIMARY KEY $COMA" +
                    "${DefinirDB.Alumnos.MATRICULA}$TEXT_TYPE$COMA"+
                    "${DefinirDB.Alumnos.NOMBRE}$TEXT_TYPE$COMA"+
                    "${DefinirDB.Alumnos.DOMICILIO}$TEXT_TYPE$COMA"+
                    "${DefinirDB.Alumnos.ESPECIALIDAD}$TEXT_TYPE$COMA"+
                    "${DefinirDB.Alumnos.FOTO}$TEXT_TYPE"

            private const val SQL_DELETE_ALUMNO =
                "DROP TABLE IF EXISTS ${DefinirDB.Alumnos.TABLA}"
        }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_DELETE_ALUMNO)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ALUMNO)
        db?.execSQL(SQL_CREATE_ALUMNO)
    }

}



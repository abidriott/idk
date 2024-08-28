package com.example.bottomnavigation.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.Cursor

class dbAlumnos (private val context: Context) {

    //12:14:02
    private val dbHelper: AlumnosDbHelper = AlumnosDbHelper(context)
    private lateinit var db: SQLiteDatabase
    private val leerRegistro = arrayOf(
        DefinirDB.Alumnos.ID,
        DefinirDB.Alumnos.MATRICULA,
        DefinirDB.Alumnos.NOMBRE,
        DefinirDB.Alumnos.DOMICILIO,
        DefinirDB.Alumnos.ESPECIALIDAD
    )

    fun openDataBase() {
        db = dbHelper.writableDatabase
    }

    fun insertarAlumno(alumno: Alumno): Long {
        val value = ContentValues().apply {
            put(DefinirDB.Alumnos.MATRICULA, alumno.matricula)
            put(DefinirDB.Alumnos.NOMBRE, alumno.nombre)
            put(DefinirDB.Alumnos.DOMICILIO, alumno.domicilio)
            put(DefinirDB.Alumnos.ESPECIALIDAD, alumno.especialidad)
            put(DefinirDB.Alumnos.FOTO, alumno.foto)
        }
        return db.insert(DefinirDB.Alumnos.TABLA, null, value)
    }

    //actualizar alumno
    fun actualizarAlumno(alumno: Alumno, id: Int): Int {
        val values = ContentValues().apply {
            put(DefinirDB.Alumnos.MATRICULA, alumno.matricula)
            put(DefinirDB.Alumnos.NOMBRE, alumno.nombre)
            put(DefinirDB.Alumnos.DOMICILIO, alumno.domicilio)
            put(DefinirDB.Alumnos.ESPECIALIDAD, alumno.especialidad)
            put(DefinirDB.Alumnos.FOTO, alumno.foto)
        }
        return db.update(DefinirDB.Alumnos.TABLA, values, "${DefinirDB.Alumnos.ID}=$id", null)
    }

    //borrar el alumno asi de trakatelas, no existes
    fun borrarAlumno(id: Int): Int {
        return db.delete(
            DefinirDB.Alumnos.TABLA,
            "${DefinirDB.Alumnos.ID}=?",
            arrayOf(id.toString())
        )
    }

    //ya me canse de los alumnos
    //mostrar alumno, que se manifieste
    fun mostrarAlumnos(cursor: Cursor): Alumno {
        return Alumno().apply {
            id = cursor.getInt(0)
            matricula = cursor.getString(1)
            nombre = cursor.getString(2)
            domicilio = cursor.getString(3)
            especialidad = cursor.getString(4)
            foto = cursor.getString(5)
        }
    }
//Buscar al morrillo por id
    fun getAlumno (id:Long):Alumno{
        val db= dbHelper.readableDatabase
        val cursor = db.query(DefinirDB.Alumnos.TABLA,leerRegistro, "${DefinirDB.Alumnos.ID}=?", arrayOf(id.toString() ), null, null, null )
        cursor.moveToFirst()
    val alumno=mostrarAlumnos(cursor)
    cursor.close()
    return alumno
    }

    //obtener los registros

    fun leerTodos(): ArrayList<Alumno>{
        val cursor = db.query(DefinirDB.Alumnos.TABLA, leerRegistro, null, null, null, null, null)
        val listaAlumno = ArrayList<Alumno>()
        cursor.moveToFirst()

        while (!cursor.isAfterLast) {
            val alumno = mostrarAlumnos(cursor)
            listaAlumno.add(alumno)
            cursor.moveToNext()
        }
        cursor.close()
        return listaAlumno
    }
    fun close(){
        dbHelper.close()
    }

}
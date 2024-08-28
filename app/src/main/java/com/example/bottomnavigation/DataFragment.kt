package com.example.bottomnavigation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.imageview.ShapeableImageView
import com.example.bottomnavigation.DataBase.Alumno
import com.example.bottomnavigation.DataBase.dbAlumnos

class DataFragment : Fragment() {
    private lateinit var inMatricula: EditText
    private lateinit var inNombre: EditText
    private lateinit var inDomicilio: EditText
    private lateinit var inEspecialidad: EditText
    private lateinit var imgAlumno: ShapeableImageView
    private lateinit var btnUpload: Button
    private lateinit var btnGuardar: Button
    private lateinit var btnBuscar: Button
    private lateinit var btnBorrar: Button
    private lateinit var db: dbAlumnos

    private val PICK_IMAGE_REQUEST = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout del fragmento
        val view = inflater.inflate(R.layout.fragment_data, container, false)

        // Inicializar las vistas
        inMatricula = view.findViewById(R.id.inMatricula)
        inNombre = view.findViewById(R.id.inNombre)
        inDomicilio = view.findViewById(R.id.inDomicilio)
        inEspecialidad = view.findViewById(R.id.inEspecialidad)
        imgAlumno = view.findViewById(R.id.imgAlumno)
        btnUpload = view.findViewById(R.id.btnUpload)
        btnGuardar = view.findViewById(R.id.btnGuardar)
        btnBuscar = view.findViewById(R.id.btnBuscar)
        btnBorrar = view.findViewById(R.id.btnBorrar)

        // Inicializar la base de datos
        db = dbAlumnos(requireContext())
        db.openDataBase()

        // Configurar los listeners de los botones
        btnUpload.setOnClickListener {
            openGallery()
        }

        btnGuardar.setOnClickListener {
            onGuardarClicked()
        }

        btnBuscar.setOnClickListener {
            onBuscarClicked()
        }

        btnBorrar.setOnClickListener {
            onBorrarClicked()
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = data?.data
            imgAlumno.setImageURI(imageUri)
        }
    }

    private fun onGuardarClicked() {
        // Lógica para guardar los datos del alumno
        val matricula = inMatricula.text.toString()
        val nombre = inNombre.text.toString()
        val domicilio = inDomicilio.text.toString()
        val especialidad = inEspecialidad.text.toString()

        if (matricula.isEmpty() || nombre.isEmpty() || domicilio.isEmpty() || especialidad.isEmpty()) {
            Toast.makeText(requireContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val alumno = Alumno(matricula.toInt(), nombre, domicilio, especialidad)
        db.insertarAlumno(alumno)
        Toast.makeText(requireContext(), "Alumno guardado", Toast.LENGTH_SHORT).show()

        // Limpiar los campos después de guardar
        inMatricula.text.clear()
        inNombre.text.clear()
        inDomicilio.text.clear()
        inEspecialidad.text.clear()
    }

    private fun onBuscarClicked() {
        // Lógica para buscar un alumno (por ejemplo, por matrícula)
        val matricula = inMatricula.text.toString()
        if (matricula.isEmpty()) {
            Toast.makeText(requireContext(), "Ingresa una matrícula para buscar", Toast.LENGTH_SHORT).show()
            return
        }

        val alumno = db.getAlumno(matricula.toLong())
        if (alumno != null) {
            inNombre.setText(alumno.nombre)
            inDomicilio.setText(alumno.domicilio)
            inEspecialidad.setText(alumno.especialidad)
            Toast.makeText(requireContext(), "Alumno encontrado", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Alumno no encontrado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onBorrarClicked() {
        // Lógica para borrar un alumno (por ejemplo, por matrícula)
        val matricula = inMatricula.text.toString()
        if (matricula.isEmpty()) {
            Toast.makeText(requireContext(), "Ingresa una matrícula para borrar", Toast.LENGTH_SHORT).show()
            return
        }

        val rowsDeleted = db.borrarAlumno(matricula.toInt())
        if (rowsDeleted > 0) {
            Toast.makeText(requireContext(), "Alumno borrado", Toast.LENGTH_SHORT).show()
            inMatricula.text.clear()
            inNombre.text.clear()
            inDomicilio.text.clear()
            inEspecialidad.text.clear()
        } else {
            Toast.makeText(requireContext(), "Alumno no encontrado", Toast.LENGTH_SHORT).show()
        }
    }
}




package com.unsa.idnp.listsample

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.unsa.idnp.listsample.ui.theme.ListSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListSampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ListSample()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListSample(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    var nombreCurso by remember { mutableStateOf("") }
    var idCurso by remember { mutableStateOf("") }

    val cursos = remember {
        mutableStateListOf<Curso>().apply {
            for (i in 1..100) {
                val curso = Curso(i, "Nombre $i", "Descripcion $i")
                add(curso)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 60.dp, start = 16.dp, end = 16.dp, bottom = 60.dp)
    ) {

        OutlinedTextField(
            label = { Text("Id") },
            value = idCurso,
            onValueChange = { idCurso = it },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            label = { Text("Nombre del curso") },
            value = nombreCurso,
            onValueChange = { nombreCurso = it },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            val idNum = idCurso.toIntOrNull()
            val nombre = nombreCurso.trim()

            if (idNum != null && nombre.isNotEmpty()) {
                val index = cursos.indexOfFirst { it.id == idNum }

                if (index != -1) {
                    val cursoModificado = cursos[index].copy(nombre = nombre)
                    cursos[index] = cursoModificado

                    Toast.makeText(context, "Curso modificado", Toast.LENGTH_SHORT).show()
                    idCurso = ""
                    nombreCurso = ""
                } else {
                    Toast.makeText(context, "ID no encontrado", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Datos inválidos", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Modificar")
        }

        Button(onClick = {
            cursos.forEach {
                Log.d("ComposeTest", "Item: ${it.id} | ${it.nombre}")
            }
        }) {
            Text("Ver Lista (en Logcat)")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items(
                items = cursos,
                key = { it.id }
            ) { curso ->
                Log.d("ComposeTest", "Item en Lazy: ${curso.id}")
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(Color.Red)
                        .padding(8.dp),
                    color = Color.White,
                    text = "Item -> ${curso.id} | ${curso.nombre} | ${curso.descripcion}"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ListSampleTheme {
        ListSample()
    }
}
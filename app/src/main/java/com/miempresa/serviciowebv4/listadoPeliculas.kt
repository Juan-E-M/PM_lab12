package com.miempresa.serviciowebv4

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_listado_peliculas.*

import org.json.JSONException

class listadoPeliculas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_peliculas)
        cargarLista()
        txtBuscar.addTextChangedListener{
            text->text.toString()
            if(text.toString() != ""){
                buscar_nombre(text.toString())
            }
            else{
                cargarLista()
            }
        }


        btnSalir.setOnClickListener({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })
        btnAgregar.setOnClickListener({
            val intent = Intent(this, verPelicula::class.java)
            startActivity(intent)
        })
    }

    fun cargarLista() {
        lista.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        lista.layoutManager = LinearLayoutManager(this)
        var llenarLista = ArrayList<Elementos>()
        AsyncTask.execute {
            val queue = Volley.newRequestQueue(applicationContext)
            val url = getString(R.string.urlAPI) + "/peliculas"
            val stringRequest = JsonArrayRequest(url,
                Response.Listener { response ->
                    try {
                        for (i in 0 until response.length()) {
                            val id =
                                response.getJSONObject(i).getString("id")
                            val nombre =
                                response.getJSONObject(i).getString("nombre")
                            val duracion =
                                response.getJSONObject(i).getString("duracion")
                            val imagen =
                                response.getJSONObject(i).getString("imagen")
                            val categoria =
                                response.getJSONObject(i).getString("categoria")
                            llenarLista.add(Elementos(id.toInt(),imagen,nombre, duracion.toInt(),categoria))
                        }
                        val adapter = AdaptadorElementos(llenarLista)
                        lista.adapter = adapter
                    } catch (e: JSONException) {
                        Toast.makeText(
                            applicationContext,
                            "Error al obtener los datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(
                        applicationContext,
                        "Verifique que esta conectado a internet",
                        Toast.LENGTH_LONG
                    ).show()
                })
            queue.add(stringRequest)
        }
    }

    fun buscar_nombre(name:String) {
        lista.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        lista.layoutManager = LinearLayoutManager(this)
        var llenarLista = ArrayList<Elementos>()
        var name2 = name.trim()
        AsyncTask.execute {
            val queue = Volley.newRequestQueue(applicationContext)
            val url = getString(R.string.urlAPI) + "/peliculas?nombre_like=" + name2
            val stringRequest = JsonArrayRequest(url,
                Response.Listener { response ->
                    try {
                        for (i in 0 until response.length()) {
                            val nombre =
                                response.getJSONObject(i).getString("nombre")
                            if (nombre.lowercase().length >= name2.length){
                                if(nombre.lowercase().substring(0,name2.length)==name2){
                                    val nombre =
                                        response.getJSONObject(i).getString("nombre")
                                    val id =
                                        response.getJSONObject(i).getString("id")
                                    val duracion =
                                        response.getJSONObject(i).getString("duracion")
                                    val imagen =
                                        response.getJSONObject(i).getString("imagen")
                                    val categoria =
                                        response.getJSONObject(i).getString("categoria")
                                    llenarLista.add(Elementos(id.toInt(),imagen,nombre, duracion.toInt(),categoria))
                                }
                            }
                        }
                        val adapter = AdaptadorElementos(llenarLista)
                        lista.adapter = adapter
                    } catch (e: JSONException) {
                        Toast.makeText(
                            applicationContext,
                            "Error al obtener los datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(
                        applicationContext,
                        "Verifique que esta conectado a internet",
                        Toast.LENGTH_LONG
                    ).show()
                })
            queue.add(stringRequest)
        }
    }

    fun buscar_categoria(name:String) {
        lista.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        lista.layoutManager = LinearLayoutManager(this)
        var llenarLista = ArrayList<Elementos>()
        var name2 = name.trim()
        AsyncTask.execute {
            val queue = Volley.newRequestQueue(applicationContext)
            val url = getString(R.string.urlAPI) + "/peliculas?categoria_like=" + name2
            val stringRequest = JsonArrayRequest(url,
                Response.Listener { response ->
                    try {
                        for (i in 0 until response.length()) {
                            val categoria =
                                response.getJSONObject(i).getString("categoria")
                            if (categoria.lowercase().length >= name2.length){
                                if(categoria.lowercase().substring(0,name2.length)==name2){
                                    val nombre =
                                        response.getJSONObject(i).getString("nombre")
                                    val id =
                                        response.getJSONObject(i).getString("id")
                                    val duracion =
                                        response.getJSONObject(i).getString("duracion")
                                    val imagen =
                                        response.getJSONObject(i).getString("imagen")
                                    val categoria =
                                        response.getJSONObject(i).getString("categoria")
                                    llenarLista.add(Elementos(id.toInt(),imagen,nombre, duracion.toInt(),categoria))
                                }
                            }
                        }
                        val adapter = AdaptadorElementos(llenarLista)
                        lista.adapter = adapter
                    } catch (e: JSONException) {
                        Toast.makeText(
                            applicationContext,
                            "Error al obtener los datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(
                        applicationContext,
                        "Verifique que esta conectado a internet",
                        Toast.LENGTH_LONG
                    ).show()
                })
            queue.add(stringRequest)
        }
    }

    fun buscar_nom_cat(name:String, cat:String) {
        lista.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        lista.layoutManager = LinearLayoutManager(this)
        var llenarLista = ArrayList<Elementos>()
        var name2 = name.trim()
        var cat2 = cat.trim()
        AsyncTask.execute {
            val queue = Volley.newRequestQueue(applicationContext)
            val url = getString(R.string.urlAPI) + "/peliculas?nombre_like="+name2+"&categoria_like=" + cat2
            val stringRequest = JsonArrayRequest(url,
                Response.Listener { response ->
                    try {
                        for (i in 0 until response.length()) {
                            val categoria =
                                response.getJSONObject(i).getString("categoria")
                            val nombre =
                                response.getJSONObject(i).getString("nombre")
                            if (categoria.lowercase().length >= cat2.length && nombre.lowercase().length >= name2.length){
                                if(categoria.lowercase().substring(0,cat2.length)==cat2 &&
                                    nombre.lowercase().substring(0,name2.length)==name2){
                                    val nombre =
                                        response.getJSONObject(i).getString("nombre")
                                    val id =
                                        response.getJSONObject(i).getString("id")
                                    val duracion =
                                        response.getJSONObject(i).getString("duracion")
                                    val imagen =
                                        response.getJSONObject(i).getString("imagen")
                                    val categoria =
                                        response.getJSONObject(i).getString("categoria")
                                    llenarLista.add(Elementos(id.toInt(),imagen,nombre, duracion.toInt(),categoria))
                                }
                            }
                        }
                        val adapter = AdaptadorElementos(llenarLista)
                        lista.adapter = adapter
                    } catch (e: JSONException) {
                        Toast.makeText(
                            applicationContext,
                            "Error al obtener los datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(
                        applicationContext,
                        "Verifique que esta conectado a internet",
                        Toast.LENGTH_LONG
                    ).show()
                })
            queue.add(stringRequest)
        }
    }
}
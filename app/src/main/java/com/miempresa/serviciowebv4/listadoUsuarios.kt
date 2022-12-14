package com.miempresa.serviciowebv4

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_listado_usuarios.*
import org.json.JSONException

class listadoUsuarios : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_usuarios)
        cargarLista()
        btn_add.setOnClickListener({
            val intent = Intent(this, verUsuario::class.java)
            startActivity(intent)
        })
    }

    fun cargarLista() {
        lista_usuarios.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        lista_usuarios.layoutManager = LinearLayoutManager(this)
        var llenarLista = ArrayList<Elementos_usuarios>()
        AsyncTask.execute {
            val queue = Volley.newRequestQueue(applicationContext)
            val url = getString(R.string.urlAPI) + "/usuarios"
            val stringRequest = JsonArrayRequest(url,
                Response.Listener { response ->
                    try {
                        for (i in 0 until response.length()) {
                            val id =
                                response.getJSONObject(i).getString("id")
                            val usuario =
                                response.getJSONObject(i).getString("usuario")
                            val clave =
                                response.getJSONObject(i).getString("clave")
                            val estado =
                                response.getJSONObject(i).getString("estado")
                            llenarLista.add(Elementos_usuarios(id.toInt(),usuario,clave,estado.first()))
                        }
                        val adapter = AdaptadorUsuarios(llenarLista)
                        lista_usuarios.adapter = adapter
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
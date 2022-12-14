package com.miempresa.serviciowebv4

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_ver_usuario.*

class verUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_usuario)

        btn_guardar.setOnClickListener(){
            agregarUsuario()
        }
        val bundle :Bundle ?=intent.extras
        if(bundle!= null){
            txt_id.setText(bundle.getString("id").toString())
            txt_usuario.setText(bundle.getString("usuario").toString())
            txt_clave.setText(bundle.getString("clave").toString())
            txt_estado.setText(bundle.getString("estado").toString())

            btn_guardar.setEnabled(false)
            btn_eliminar.setEnabled(true)
            btn_editar.setEnabled(true)
        }else{
            btn_guardar.setEnabled(true)
            btn_eliminar.setEnabled(false)
            btn_editar.setEnabled(false)
        }
        btn_eliminar.setOnClickListener({
            eliminarUsuario()
        })
        btn_editar.setOnClickListener({
            editarUsuario()
        })

    }

    fun agregarUsuario() {
        AsyncTask.execute {

            val id = txt_id.text.toString()
            val usuario = txt_usuario.text.toString()
            val clave = txt_clave.text.toString()
            val estado = txt_estado.text.toString()

            val queue = Volley.newRequestQueue(this)
            var url = getString(R.string.urlAPI) + "/usuarios"
            val postRequest: StringRequest = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener { response -> // response
                    Toast.makeText(
                        applicationContext,
                        "Registro agregado correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                },
                Response.ErrorListener { response ->// error
                    Toast.makeText(
                        applicationContext,
                        "Se produjo un error al guardar los datos",
                        Toast.LENGTH_LONG
                    ).show()
                }
            ) {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> =
                        HashMap()
                    params["id"] = id
                    params["usuario"] = usuario
                    params["clave"] = clave
                    params["estado"] = estado
                    return params
                }
            }
            queue.add(postRequest)
        }
    }
    fun eliminarUsuario() {
        AsyncTask.execute {
            val id = txt_id.text.toString()

            val queue = Volley.newRequestQueue(this)
            var url = getString(R.string.urlAPI) + "/usuarios/" + id
            val postRequest: StringRequest = object : StringRequest(
                Request.Method.DELETE, url,
                Response.Listener { response -> // response
                    Toast.makeText(
                        applicationContext,
                        "Registro eliminado correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                },
                Response.ErrorListener { response ->// error
                    Toast.makeText(
                        applicationContext,
                        "Se produjo un error al eliminar el registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            ){}
            queue.add(postRequest)
        }
    }

    fun editarUsuario() {
        AsyncTask.execute {

            val id = txt_id.text.toString()
            val usuario = txt_usuario.text.toString()
            val clave = txt_clave.text.toString()
            val estado = txt_estado.text.toString()

            val queue = Volley.newRequestQueue(this)
            var url = getString(R.string.urlAPI) + "/usuarios" + "/$id"
            val postRequest: StringRequest = object : StringRequest(
                Request.Method.PUT, url,
                Response.Listener { response -> // response
                    Toast.makeText(
                        applicationContext,
                        "Se edito correctamente el usuario : "+ usuario,
                        Toast.LENGTH_LONG
                    ).show()
                },
                Response.ErrorListener { response ->// error
                    Toast.makeText(
                        applicationContext,
                        "Se produjo un error al guardar los datos",
                        Toast.LENGTH_LONG
                    ).show()
                }
            ) {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> =
                        HashMap()
                    params["id"] = id
                    params["usuario"] = usuario
                    params["clave"] = clave
                    params["estado"] = estado
                    return params
                }
            }
            queue.add(postRequest)
        }
    }

}
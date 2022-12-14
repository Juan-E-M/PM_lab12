package com.miempresa.serviciowebv4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        btnLogear.setOnClickListener({
            //Instantiate the RequestQueue.
            val usuario = txtUsuario.text.toString()
            val clave = txtClave.text.toString()
            val queue = Volley.newRequestQueue(this)
            var url = getString(R.string.urlAPI)+"/usuarios?"
            url = url + "usuario="+usuario+"&clave="+clave

            val stringRequest = JsonArrayRequest(url,
            Response.Listener {  response ->
                try{
                    val valor = response.getJSONObject(0)
                    Toast.makeText(
                        applicationContext,
                        "Validación de usuario: "+valor.getString("usuario")+
                                " con clave: "+valor.getString("clave")+" correcta",
                        Toast.LENGTH_LONG
                    ).show()

                    val intent = Intent(this, opciones::class.java)
                    startActivity(intent)

                }catch (e: JSONException){
                    Toast.makeText(
                        applicationContext,
                        "Error en las credenciales proporcionadas",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }, Response.ErrorListener {
                Toast.makeText(
                    applicationContext,
                    "Compruebe que tiene acceso a internet",
                    Toast.LENGTH_LONG
                ).show()
            })
            queue.add((stringRequest))
        })

        btnRegistrar.setOnClickListener({
            val usuario = txtUsuario.text.toString()
            val clave = txtClave.text.toString()
            val queue = Volley.newRequestQueue(this)
            var url = getString(R.string.urlAPI) + "/usuarios"
            val requestBody = "usuario="+usuario+"&clave="+clave+"&estado=A"

            val stringReq : StringRequest =
                object : StringRequest(Method.POST, url,
                    Response.Listener { response ->
                        if (usuario=="" ||clave==""){
                            Toast.makeText(
                                applicationContext,
                                "Campor vacíos: no se puede realizar el ingreso",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else{
                            var strResp = JSONObject(response.toString())
                            Toast.makeText(
                                applicationContext,
                                "Usuario Registrado: "+strResp.getString("usuario"),
                                Toast.LENGTH_LONG
                            ).show()
                            Log.d("API", response.toString())
                            txtUsuario.setText("")
                            txtClave.setText("")
                        }
                    },
                    Response.ErrorListener { error ->
                        Toast.makeText(
                            applicationContext,
                            "No se ha podido registrar el usuario",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d("API", "error => $error")
                    }
                ) {
                    override fun getBody(): ByteArray {
                        return requestBody.toByteArray(Charset.defaultCharset())
                    }
                }
            queue.add(stringReq)
        })

        btnSalir.setOnClickListener({
            val builder = AlertDialog.Builder(this)
            builder.setTitle("¡¡Alerta!!")
            builder.setMessage("¿Desea salir de la aplicación?")
            builder.setPositiveButton("si") { dialog, which ->
                finish()
            }
            builder.setNegativeButton("cancelar") { dialog, which ->
            }
            builder.show()
        })

    }
}
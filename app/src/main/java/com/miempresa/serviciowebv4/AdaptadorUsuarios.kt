package com.miempresa.serviciowebv4

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class AdaptadorUsuarios(val ListaElementos:ArrayList<Elementos_usuarios>): RecyclerView.Adapter<AdaptadorUsuarios.ViewHolder>() {

    override fun getItemCount(): Int {
        return ListaElementos.size;
    }
    class ViewHolder (itemView : View): RecyclerView.ViewHolder(itemView) {
        val fid = itemView.findViewById<TextView>(R.id.id_number)
        val fusuario = itemView.findViewById<TextView>(R.id.usuario);
        val fclave = itemView.findViewById<TextView>(R.id.clave)
        val festado = itemView.findViewById<TextView>(R.id.estado)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.fid?.text= ListaElementos[position].id.toString()
        holder?.fusuario?.text=ListaElementos[position].usuario
        holder?.fclave?.text=ListaElementos[position].clave
        holder?.festado?.text= ListaElementos[position].estado.toString()
        var id = ListaElementos[position].id
        var usuario = ListaElementos[position].usuario
        var clave = ListaElementos[position].clave
        var estado = ListaElementos[position].estado

        holder.itemView.setOnClickListener(){
            val llamaractividad = Intent(holder.itemView.context,verUsuario::class.java)
            llamaractividad.putExtra("id",id.toString())
            llamaractividad.putExtra("usuario",usuario.toString())
            llamaractividad.putExtra("clave",clave.toString())
            llamaractividad.putExtra("estado",estado.toString())
            holder.itemView.context.startActivity(llamaractividad)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.usuarioslista, parent, false);
        return ViewHolder(v);
    }



}
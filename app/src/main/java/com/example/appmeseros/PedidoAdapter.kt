package com.example.appmeseros

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appmeseros.R


class PedidoAdapter(
    private val pedidos: List<Pedido>
) : RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder>() {


    class PedidoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val entrega: TextView = itemView.findViewById(R.id.entrega)
        val plato: TextView = itemView.findViewById(R.id.plato)
        val alimento: TextView = itemView.findViewById(R.id.alimento)
        val cantidad: TextView = itemView.findViewById(R.id.cantidad)
        val complemento: TextView = itemView.findViewById(R.id.complemento)
        val notas: TextView = itemView.findViewById(R.id.notas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.plato, parent, false)
        return PedidoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedido = pedidos[position]

        holder.entrega.text = pedido.entrega
        holder.plato.text = pedido.plato
        holder.alimento.text = pedido.alimento
        holder.cantidad.text = pedido.cantidad
        holder.complemento.text = pedido.complemento
        holder.notas.text = pedido.notas
    }

    override fun getItemCount(): Int {
        return pedidos.size
    }
}

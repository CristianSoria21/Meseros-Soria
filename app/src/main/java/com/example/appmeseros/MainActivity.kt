package com.example.appmeseros

import AddPedido
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

enum class ProviderType {
    BASIC
}

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var pedidoAdapter: PedidoAdapter
    private val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.RecyclerViewPlato)
        pedidoAdapter = PedidoAdapter(emptyList())

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = pedidoAdapter

        // Cargar los datos de Firestore al inicio de la actividad
        CargarFirestore()

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            // Abre el fragmento de diálogo para agregar un nuevo pedido
            val addPedidoDialog = AddPedido()
            addPedidoDialog.show(supportFragmentManager, "AddPedido")

            // CargarFirestore después de agregar un nuevo pedido
            CargarFirestore()
        }
    }

    private fun CargarFirestore() {
        // Accede a tu colección de Firestore
        db.collection("pedidos")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {

                    return@addSnapshotListener
                }

                // Mapea los documentos a objetos Pedido
                val pedidos = snapshot?.documents?.mapNotNull { document ->
                    document.toObject(Pedido::class.java)
                }

                // Actualiza el adaptador con la nueva lista de pedidos
                pedidos?.let {
                    pedidoAdapter = PedidoAdapter(it)
                    recyclerView.adapter = pedidoAdapter
                }
            }
    }
}

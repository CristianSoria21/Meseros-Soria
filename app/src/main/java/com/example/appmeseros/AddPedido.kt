// MenuDialogFragment.kt
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import androidx.fragment.app.DialogFragment
import com.example.appmeseros.MainActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.example.appmeseros.R

class AddPedido() : DialogFragment() {

    private lateinit var txtEntrega: AutoCompleteTextView
    private lateinit var txtPlato: AutoCompleteTextView
    private lateinit var txtAlimento: AutoCompleteTextView
    private lateinit var txtCantidad: TextInputEditText
    private lateinit var txtComplemento: AutoCompleteTextView
    private lateinit var txtNotas: TextInputEditText
    private lateinit var btnAgregar: Button
    private lateinit var btnCancelar: Button
    private val db = FirebaseFirestore.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_pedido, container, false)

        txtEntrega = view.findViewById(R.id.txtEntrega)
        txtPlato = view.findViewById(R.id.txtPlato)
        txtAlimento = view.findViewById(R.id.txtAlimento)
        txtCantidad = view.findViewById(R.id.txtCantidad)
        txtComplemento = view.findViewById(R.id.txtComplemento)
        txtNotas = view.findViewById(R.id.txtNotas)
        btnAgregar = view.findViewById(R.id.btnAgregar)
        btnCancelar = view.findViewById(R.id.btnCancelar)


        // Lista de opciones para cada AutoCompleteTextView
        // Lista de opciones para cada AutoCompleteTextView
        val optionsEntrega = arrayOf("Mesa 1", "Mesa 2", "Mesa 3", "Mesa 4", "Mesa 5", "Levar")
        val optionsPlato = arrayOf("Plato 1", "Plato 2", "Plato 3", "Plato 4", "Plato 5")
        val optionsAlimento = arrayOf("Tacos", "Gringas", "Tortas", "Carne en su jugo", "Volcanes")
        val optionsComplemento = arrayOf("Con todo", "Sin cilantro", "Sin cebolla")

        // Crear adaptadores para cada AutoCompleteTextView
        val adapterEntrega =
            ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, optionsEntrega)
        val adapterPlato = ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, optionsPlato)
        val adapterAlimento =
            ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, optionsAlimento)
        val adapterComplemento =
            ArrayAdapter(requireContext(), R.layout.dropdown_menu_item, optionsComplemento)

        // Configurar adaptadores en los AutoCompleteTextView
        txtEntrega.setAdapter(adapterEntrega)
        txtPlato.setAdapter(adapterPlato)
        txtAlimento.setAdapter(adapterAlimento)
        txtComplemento.setAdapter(adapterComplemento)

        btnAgregar.setOnClickListener {
            agregarPedido()
            dismissAllowingStateLoss()
        }

        btnCancelar.setOnClickListener {
            dismissAllowingStateLoss()
        }
        return view
    }

    private fun agregarPedido() {

        val entrega = txtEntrega.text.toString()
        val plato = txtPlato.text.toString()
        val alimento = txtAlimento.text.toString()
        val cantidad = txtCantidad.text.toString()
        val complemento = txtComplemento.text.toString()
        val notas = txtNotas.text.toString()

        //mapa con los datos del pedido
        val pedido = hashMapOf(
            "entrega" to entrega,
            "plato" to plato,
            "alimento" to alimento,
            "cantidad" to cantidad,
            "complemento" to complemento,
            "notas" to notas
        )
        // Agrega el pedido a la colección "pedidos" (o la colección que desees)
        db.collection("pedidos")
            .add(pedido)
            .addOnFailureListener {
                Log.d("fallo", "Inicio de agregarPedido()")
            }
    }
}

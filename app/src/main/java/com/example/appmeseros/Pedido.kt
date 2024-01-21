package com.example.appmeseros

data class Pedido(
    val entrega: String = "",
    val plato: String = "",
    val alimento: String = "",
    val cantidad: String = "",
    val complemento: String = "",
    val notas: String = ""
)
package com.netec.pe7_1.parte_uno

import com.netec.pe7_1.parte_uno.Usuario

class Calculos {
    private val usuario = Usuario("Eduardo", 60)
    private var pais = "US"

    fun getNombre(): String {
        return usuario.nombre
    }

    fun setPais(pais: String) {
        this.pais = pais
    }

    fun getEdades(): Array<Int> {
        return arrayOf(12, 20, 23, 98)
    }

    fun checkRobot(usuario: Usuario): Boolean {
        return usuario.esRobot
    }

    fun checkRobot(usuario: Usuario? = null): Boolean? {
        return usuario?.esRobot ?: null
    }

    fun esAdulto(usuario: Usuario): Boolean {
        if (usuario.esRobot) return true

        return if (pais == "US") usuario.edad >= 21 else usuario.edad >= 18
    }

    fun suma (a : Int = 0, b : Int = 0) : Int{
        return a + b
    }
}
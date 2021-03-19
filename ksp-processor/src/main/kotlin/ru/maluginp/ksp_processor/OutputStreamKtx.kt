package ru.maluginp.ksp_processor

import java.io.OutputStream

fun OutputStream.appendText(str: String) {
    this.write(str.toByteArray())
}
package com.example

import ru.maluginp.common.Builder

@Builder
class AClass(private val a: Int, val b: String, val c: Double, val d: Double) {
    val p = "$a, $b, $c, $d"
    fun foo() = p
}

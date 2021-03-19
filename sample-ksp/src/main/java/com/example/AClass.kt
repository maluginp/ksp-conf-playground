package com.example

import ru.maluginp.common.Builder
import HELLO

@Builder
class AClass(private val a: Int, val b: String, val c: Double, val d: HELLO) {
    val p = "$a, $b, $c, ${d.foo()}"
    fun foo() = p
}

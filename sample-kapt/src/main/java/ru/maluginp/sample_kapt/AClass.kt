package ru.maluginp.sample_kapt

import ru.maluginp.common.Builder

@Builder
class AClass(private val a: Int, private val b: String, private val c: Double) {
    private val p = "$a, $b, $c"
    fun foo() = p
}

@Builder
class BClass(private val a: AClass) {
    private val p = "$a"
    fun foo() = p
}
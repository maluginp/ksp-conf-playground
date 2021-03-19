import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.kspWithCompilation
import com.tschuchort.compiletesting.symbolProcessorProviders
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.system.measureTimeMillis
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class BuilderProcessorTest {
    private val countRuns = 5

    @Test
    fun kspTest() {
        val result = compile()
        assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)
    }

    @Test
    fun kspBenchmark() {
        val time = measureTimeMillis {
            repeat(countRuns) {
                compile()
            }
        }

        println("KSP(runs=$countRuns): $time ms (it = ${time / countRuns} ms)")
    }

    private fun compile():  KotlinCompilation.Result {
        val builder = SourceFile.kotlin(
            name = "Builder.kt",
            trimIndent = false,
            contents = """
            package ru.maluginp.common
                    
            @Target(AnnotationTarget.CLASS)
            @Retention(AnnotationRetention.SOURCE)
            annotation class Builder
        """.trimIndent(),)
        val kotlinSource = SourceFile.kotlin(
            "AClass.kt",
            trimIndent = false,
            contents = """
            package com.example
            import ru.maluginp.common.Builder

            @Builder
            class AClass(private val a: Int, val b: String, val c: Double) {
                val p = "${'$'}a, ${'$'}b, ${'$'}c"
                fun foo() = p
            }

            @Builder
            class BClass(private val a: Int, val b: String, val c: Double) {
                val p = "${'$'}a, ${'$'}b, ${'$'}c"
                fun foo() = p
            }
"""
        )

        val compilation = KotlinCompilation().apply {
            sources = listOf(
                builder,
                kotlinSource
            )
            symbolProcessorProviders = listOf(
                BuilderProcessorProvider()
            )
            // NOTE: https://github.com/tschuchortdev/kotlin-compile-testing/issues/312
            // Enable KSP compilation
            kspWithCompilation = true
        }
        return compilation.compile()
    }
}

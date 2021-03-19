package ru.maluginp.kapt_processor

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.system.measureTimeMillis
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
internal class BuilderProcessorTest {
    private val countRuns = 5

    @Test
    fun kaptTest() {
        val result = compile()
        assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)
    }

    @Test
    fun kaptBenchmark() {
        val time = measureTimeMillis {
            repeat(countRuns) {
                compile()
            }
        }

        println("KAPT(runs=$countRuns): $time ms (it = ${time / countRuns} ms)")
    }


    private fun compile(): KotlinCompilation.Result {
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
            package ru.maluginp.kapt_processor
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


        return KotlinCompilation().apply {
            sources = listOf(builder, kotlinSource)

            // pass your own instance of an annotation processor
            annotationProcessors = listOf(BuilderProcessor())

            // pass your own instance of a compiler plugin
            inheritClassPath = true
            messageOutputStream = System.out // see diagnostics in real time
        }.compile()
    }
}
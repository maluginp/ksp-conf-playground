package ru.maluginp.ksp_processor

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSNode
import java.io.IOException
import java.io.OutputStream

class CompositeLogger(
    private vararg val loggers: KSPLogger
) : KSPLogger {
    override fun error(message: String, symbol: KSNode?) {
        loggers.forEach { it.error(message, symbol) }
    }

    override fun exception(e: Throwable) {
        loggers.forEach { it.exception(e) }
    }

    override fun info(message: String, symbol: KSNode?) {
        loggers.forEach { it.info(message, symbol) }
    }

    override fun logging(message: String, symbol: KSNode?) {
        loggers.forEach { it.logging(message, symbol) }
    }

    override fun warn(message: String, symbol: KSNode?) {
        loggers.forEach { it.warn(message, symbol) }
    }
}

class FileLogger(
    private val file: OutputStream
) : KSPLogger {

    override fun error(message: String, symbol: KSNode?) {
        try {
            file.write("$message Symbol:$symbol\n".toByteArray())
        } catch (e: IOException) {
        }
    }

    override fun exception(e: Throwable) {
        try {
            file.write("Exception: $e\n".toByteArray())
        } catch (e: IOException) {
        }
    }

    override fun info(message: String, symbol: KSNode?) {
        try {
            file.write("$message Symbol:$symbol\n".toByteArray())
        } catch (e: IOException) {
        }
    }

    override fun logging(message: String, symbol: KSNode?) {
        try {
            if (symbol != null) {
                file.write("$message Symbol:$symbol\n".toByteArray())
            } else {
                file.write("$message\n".toByteArray())
            }
        } catch (e: IOException) {
        }
    }

    override fun warn(message: String, symbol: KSNode?) {
        try {
            file.write("$message Symbol:$symbol\n".toByteArray())
        } catch (e: IOException) {
        }
    }
}
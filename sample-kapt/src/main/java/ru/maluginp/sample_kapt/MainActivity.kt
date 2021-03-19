package ru.maluginp.sample_kapt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val classA = AClassBuilder()
            .withA(1)
            .withB("test")
            .withC(1.0)
            .build()
        val classB = BClassBuilder()
            .withA(classA)
            .build()
    }
}
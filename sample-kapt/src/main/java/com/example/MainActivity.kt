package com.example

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val builder = AClassBuilder()
        val aClass = builder
            .withA(1)
            .withB("foo")
            .withC(2.3)
            .withD(2.5)
            .build()

        Log.e("TEST", "builder $aClass = ${aClass.foo()}")
    }
}

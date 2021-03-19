package com.example

import HELLO
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hello = HELLO()
        println(hello.foo())

        val builder = AClassBuilder()
        val aClass = builder
            .withA(1)
            .withB("foo")
            .withC(2.3)
            .withD(hello)
            .build()

        Log.e("TEST", "builder $aClass = ${aClass.foo()}")
//        AClassBuilder
    }
}

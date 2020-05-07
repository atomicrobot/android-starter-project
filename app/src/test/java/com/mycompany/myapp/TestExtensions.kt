package com.mycompany.myapp

import okio.Okio
import java.io.File
import java.nio.charset.Charset


object TestExtensions

@Throws(Exception::class)
fun String.loadResourceAsString(): String {
    val url = TestExtensions::class.java.getResource(this)
    val file = File(url?.file ?: "")
   //val ss = Okio.source(file);
   //return Okio.buffer(Okio.source(file)).readString(Charset.forName("UTF-8"))
 return "" // TODO

}

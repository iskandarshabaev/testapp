package com.ishabaev.testapp.utils

import android.content.Context
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader


object CurrencyNames {

    private var names: Map<String, String>? = null

    fun names(context: Context): Map<String, String> {
        if (names == null) {
            val json = readNames(context)
            val typeOfHashMap = object : TypeToken<Map<String, String>>() {
            }.type
            names = GsonHolder.gson.fromJson(json, typeOfHashMap)
        }
        return names!!
    }

    private fun readNames(context: Context): String {
        val buf = StringBuilder()
        val json = context.assets.open("names.json")
        val input = BufferedReader(InputStreamReader(json, "UTF-8"))
        var str = input.readLine()
        while (str != null) {
            buf.append(str)
            str = input.readLine()
        }
        input.close()
        return buf.toString()
    }
}
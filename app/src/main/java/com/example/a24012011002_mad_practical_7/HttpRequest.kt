package com.example.a24012011002_mad_practical_7

import android.util.Log
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HttpRequest {

    private val TAG = "HttpRequest"

    fun makeServiceCall(
        reqUrl: String,
        token: String
    ): String? {

        var response: String? = null

        try {

            val url = URL(reqUrl)

            val conn =
                url.openConnection() as HttpURLConnection

            conn.setRequestProperty(
                "Authorization",
                "Bearer $token"
            )

            conn.setRequestProperty(
                "Content-Type",
                "application/json"
            )

            response = convertStreamToString(
                BufferedInputStream(
                    conn.inputStream
                )
            )

            conn.disconnect()

        } catch (e: Exception) {

            Log.e(
                TAG,
                "makeServiceCall: ${e.message}"
            )
        }

        return response
    }

    private fun convertStreamToString(
        inputStream: BufferedInputStream
    ): String {

        val reader =
            BufferedReader(
                InputStreamReader(inputStream)
            )

        val stringBuilder = StringBuilder()

        var line: String?

        try {

            while (
                reader.readLine().also {
                    line = it
                } != null
            ) {

                stringBuilder
                    .append(line)
                    .append("\n")
            }

        } catch (e: Exception) {

            e.printStackTrace()

        } finally {

            try {

                inputStream.close()

            } catch (e: IOException) {

                e.printStackTrace()
            }
        }

        return stringBuilder.toString()
    }
}
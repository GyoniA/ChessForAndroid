package com.gyoniA.chess.model

import android.graphics.Point
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.io.IOException
/*
class PointBabuDeserializer : KeyDeserializer() {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserializeKey(key: String?, ctxt: DeserializationContext?): Point {
        val data = key?.substringAfter("(", "")?.substringBefore(")", "")
        val coordinates = data?.split(",")
        val x = coordinates!![0].toInt()
        val y = coordinates!![1].toInt()
        return Point(x, y)// replace null with your logic
    }
}*/

class PointBabuDeserializer :
    JsonDeserializer<HashMap<Point?, Babu?>?>() {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext?): HashMap<Point?, Babu?> {
        val raw: List<Babu> = jp.readValueAs(object : TypeReference<List<Babu?>?>() {})
        val result: MutableMap<Point, Babu> = HashMap()
        for (babu in raw) {
            result[babu.pozicio] = babu
        }
        return result as HashMap<Point?, Babu?>
    }
}
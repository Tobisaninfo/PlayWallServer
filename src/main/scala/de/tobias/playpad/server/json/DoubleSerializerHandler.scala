package de.tobias.playpad.server.json

import com.google.gson.{JsonElement, JsonPrimitive}

class DoubleSerializerHandler extends SerializerHandler {
	override def serialize(value: Any): JsonPrimitive = value match {
		case number: Number => new JsonPrimitive(number)
		case _ => null
	}

	override def deserialize(jsonElement: JsonElement): Double = jsonElement match {
		case jsonPrimitive: JsonPrimitive => jsonPrimitive.getAsDouble
		case _ => 0
	}
}

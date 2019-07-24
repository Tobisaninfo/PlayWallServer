package de.tobias.playpad.server.json

import com.google.gson.{JsonElement, JsonPrimitive}

class IntSerializerHandler extends SerializerHandler {
	override def serialize(value: Any): JsonPrimitive = value match {
		case number: Number => new JsonPrimitive(number)
		case _ => null
	}

	override def deserialize(jsonElement: JsonElement): Int = jsonElement match {
		case jsonPrimitive: JsonPrimitive => jsonPrimitive.getAsInt
		case _ => 0
	}
}

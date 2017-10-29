package de.tobias.playpad.server.json

import com.google.gson.{JsonElement, JsonPrimitive}

class NumberSerializerHandler extends SerializerHandler {
	override def serialize(value: Any): JsonPrimitive = value match {
		case number: Number => new JsonPrimitive(number)
		case _ => null
	}

	override def deserialize(jsonElement: JsonElement): Number = jsonElement match {
		case jsonPrimitive: JsonPrimitive => jsonPrimitive.getAsInt
		case _ => null
	}
}

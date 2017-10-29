package de.tobias.playpad.server.json

import com.google.gson.{JsonElement, JsonPrimitive}

class StringSerializerHandler extends SerializerHandler {
	override def serialize(value: Any): JsonPrimitive = value match {
		case str: String => new JsonPrimitive(str)
		case _ => null
	}

	override def deserialize(jsonElement: JsonElement): String = jsonElement match {
		case jsonPrimitive: JsonPrimitive => jsonPrimitive.getAsString
		case _ => null
	}
}

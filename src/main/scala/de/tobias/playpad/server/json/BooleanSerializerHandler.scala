package de.tobias.playpad.server.json

import com.google.gson.{JsonElement, JsonPrimitive}

class BooleanSerializerHandler extends SerializerHandler {
	override def serialize(value: Any): JsonPrimitive = value match {
		case bool: Boolean => new JsonPrimitive(bool)
		case _ => null
	}

	override def deserialize(jsonElement: JsonElement): Boolean = jsonElement match {
		case jsonPrimitive: JsonPrimitive => jsonPrimitive.getAsBoolean
		case _ => false
	}
}

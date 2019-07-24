package de.tobias.playpad.server.json

import com.google.gson.{JsonElement, JsonPrimitive}

class UUIDSerializerHandler extends SerializerHandler {
	override def serialize(value: Any): JsonPrimitive = value match {
		case uuid: UUID => new JsonPrimitive(uuid.toString)
		case _ => null
	}

	override def deserialize(jsonElement: JsonElement): UUID = jsonElement match {
		case jsonPrimitive: JsonPrimitive => UUID.fromString(jsonPrimitive.getAsString)
		case _ => null
	}
}

package de.tobias.playpad.server.json

import com.google.gson.{JsonElement, JsonPrimitive}

trait SerializerHandler {
	def serialize(value: Any): JsonPrimitive

	def deserialize(jsonElement: JsonElement): Any
}

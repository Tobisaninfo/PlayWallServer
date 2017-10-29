package de.tobias.playpad.server.json

import javafx.util.Duration

import com.google.gson.{JsonElement, JsonPrimitive}

class DurationSerializerHandler extends SerializerHandler {
	override def serialize(value: Any): JsonPrimitive = value match {
		case duration: Duration => new JsonPrimitive(duration.toMillis)
		case _ => null
	}

	override def deserialize(jsonElement: JsonElement): Duration = jsonElement match {
		case jsonPrimitive: JsonPrimitive => new Duration(jsonPrimitive.getAsInt)
		case _ => Duration.ZERO
	}
}

package de.tobias.playpad.server.project.saver.json

import com.google.gson.{JsonElement, JsonObject}
import de.tobias.playpad.server.project.Pad

/**
  * Created by tobias on 17.02.17.
  */
class PadSaver {

	def save(pad: Pad): JsonElement = {
		val jsonObject = new JsonObject()

		jsonObject.addProperty("id", pad.id.toString)
		jsonObject.addProperty("name", pad.name)
		jsonObject.addProperty("position", pad.position)
		jsonObject.addProperty("page", pad.pageIndex)

		jsonObject
	}
}

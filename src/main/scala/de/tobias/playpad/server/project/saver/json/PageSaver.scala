package de.tobias.playpad.server.project.saver.json

import com.google.gson.{JsonArray, JsonElement, JsonObject}
import de.tobias.playpad.server.project.Page

/**
  * Created by tobias on 17.02.17.
  */
class PageSaver {

	def save(page: Page): JsonElement = {
		val jsonObject = new JsonObject()

		val padSaver = new PadSaver()
		val padArray = new JsonArray()
		page.pads.foreach(pad => padArray.add(padSaver.save(pad)))

		jsonObject.addProperty("name", page.name)
		jsonObject.addProperty("position", page.position)
		jsonObject.add("pads", padArray)

		jsonObject
	}
}

package de.tobias.playpad.server.project.saver.json

import com.google.gson.{JsonArray, JsonElement, JsonObject}
import de.tobias.playpad.server.project.Page
import de.tobias.playpad.server.project.utils.JsonDef._

/**
  * Created by tobias on 17.02.17.
  */
class PageSaver {
	def save(page: Page): JsonElement = {
		val jsonObject = new JsonObject()

		val padSaver = new PadSaver()
		val padArray = new JsonArray()
		page.pads.foreach(pad => padArray.add(padSaver.save(pad)))

		jsonObject.addProperty(PAGE_ID, page.id.toString)
		jsonObject.addProperty(PAGE_NAME, page.name)
		jsonObject.addProperty(PAGE_POSITION, page.position)
		jsonObject.add(PAGE_PADS, padArray)

		jsonObject
	}
}

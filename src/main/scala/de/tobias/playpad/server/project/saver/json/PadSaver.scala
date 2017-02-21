package de.tobias.playpad.server.project.saver.json

import com.google.gson.{JsonArray, JsonElement, JsonObject}
import de.tobias.playpad.server.project.Pad
import de.tobias.playpad.server.project.utils.JsonDef._

/**
  * Created by tobias on 17.02.17.
  */
class PadSaver {
	def save(pad: Pad): JsonElement = {
		val jsonObject = new JsonObject()

		val pathSaver = new PathSaver()
		val pathArray = new JsonArray()
		pad.paths.foreach(path => pathArray.add(pathSaver.save(path)))

		jsonObject.addProperty(PAD_ID, pad.id.toString)
		jsonObject.addProperty(PAD_NAME, pad.name)
		jsonObject.addProperty(PAD_POSITION, pad.position)
		jsonObject.addProperty(PAD_CONTENT_TYPE, pad.contentType)
		jsonObject.add(PAD_PATHS, pathArray)

		jsonObject
	}
}

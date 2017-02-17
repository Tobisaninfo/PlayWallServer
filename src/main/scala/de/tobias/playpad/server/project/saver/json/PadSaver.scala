package de.tobias.playpad.server.project.saver.json

import com.google.gson.{JsonElement, JsonObject}
import de.tobias.playpad.server.project.JsonDef._
import de.tobias.playpad.server.project.Pad

/**
  * Created by tobias on 17.02.17.
  */
class PadSaver {

	def save(pad: Pad): JsonElement = {
		val jsonObject = new JsonObject()

		jsonObject.addProperty(PAD_ID, pad.id.toString)
		jsonObject.addProperty(PAD_NAME, pad.name)
		jsonObject.addProperty(PAD_POSITION, pad.position)
		jsonObject.addProperty(PAD_PAGE, pad.pageIndex)

		jsonObject
	}
}
package de.tobias.playpad.server.project.saver.json

import com.google.gson.{JsonElement, JsonObject}
import de.tobias.playpad.server.project.Design
import de.tobias.playpad.server.project.utils.JsonDef._

/**
  * Created by tobias on 23.02.17.
  */
class DesignSaver {
	def save(design: Design): JsonElement = {
		val json = new JsonObject()
		if (design != null) {
			json.addProperty(DESIGN_ID, design.id.toString)
			json.addProperty(DESIGN_BACKGROUND_COLOR, design.backgroundColor)
			json.addProperty(DESIGN_PLAY_COLOR, design.playColor)
		}
		json
	}
}

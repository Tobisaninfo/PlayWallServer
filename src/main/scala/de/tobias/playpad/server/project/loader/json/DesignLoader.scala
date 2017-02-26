package de.tobias.playpad.server.project.loader.json

import java.util.UUID

import com.google.gson.JsonObject
import de.tobias.playpad.server.project.utils.JsonDef
import de.tobias.playpad.server.project.{Design, Pad}

/**
  * Created by tobias on 23.02.17.
  */
class DesignLoader {
	def load(jsonObject: JsonObject, pad: Pad): Design = {
		val design = new Design()

		design.id = UUID.fromString(jsonObject.get(JsonDef.DESIGN_ID).getAsString)
		design.backgroundColor = jsonObject.get(JsonDef.DESIGN_BACKGROUND_COLOR).getAsString
		design.playColor = jsonObject.get(JsonDef.DESIGN_PLAY_COLOR).getAsString
		design.pad = pad

		design
	}
}

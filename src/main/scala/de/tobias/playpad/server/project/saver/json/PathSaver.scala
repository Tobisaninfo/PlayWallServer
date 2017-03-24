package de.tobias.playpad.server.project.saver.json

import com.google.gson.{JsonElement, JsonObject}
import de.tobias.playpad.server.project.Path
import de.tobias.playpad.server.project.utils.JsonDef._

/**
  * Created by tobias on 20.02.17.
  */
class PathSaver {
	def save(path: Path): JsonElement = {
		val jsonObject = new JsonObject()

		jsonObject.addProperty(PATH_ID, path.id.toString)
		jsonObject.addProperty(PATH_PATH, path.filename)

		jsonObject
	}
}

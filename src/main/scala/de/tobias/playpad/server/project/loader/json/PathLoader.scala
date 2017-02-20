package de.tobias.playpad.server.project.loader.json

import java.util.UUID

import com.google.gson.{JsonArray, JsonObject}
import de.tobias.playpad.server.project.utils.JsonDef._
import de.tobias.playpad.server.project.{Pad, Path}

import scala.collection.JavaConverters

/**
  * Created by tobias on 20.02.17.
  */
class PathLoader {
	def load(jsonArray: JsonArray, pad: Pad): List[Path] = {
		val it = JavaConverters.asScalaIterator(jsonArray.iterator())
		val paths = it.filter(_.isJsonObject).map(i => {
			val json = i.asInstanceOf[JsonObject]

			val path = new Path()
			path.id = UUID.fromString(json.get(PATH_ID).getAsString)
			path.path = json.get(PATH_PATH).getAsString
			path.pad = pad

			pad
		}).toList
		paths
	}
}

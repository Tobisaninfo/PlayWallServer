package de.tobias.playpad.server.project.loader.json

import java.util.UUID

import com.google.gson.{JsonArray, JsonObject}
import de.tobias.playpad.server.project.utils.JsonDef._
import de.tobias.playpad.server.project.{Pad, Page}

import scala.collection.JavaConverters

/**
  * Created by tobias on 17.02.17.
  */
class PadLoader {
	def load(jsonArray: JsonArray, page: Page): List[Pad] = {
		val it = JavaConverters.asScalaIterator(jsonArray.iterator())
		val pads = it.filter(_.isJsonObject).map(i => {
			val json = i.asInstanceOf[JsonObject]

			val pad = new Pad()
			pad.id = UUID.fromString(json.get(PAD_ID).getAsString)
			if (json.get(PAD_NAME) != null)
				pad.name = json.get(PAD_NAME).getAsString
			pad.position = json.get(PAD_POSITION).getAsInt
			if (json.get(PAD_CONTENT_TYPE) != null)
				pad.contentType = json.get(PAD_CONTENT_TYPE).getAsString
			pad.page = page

			val pathLoader = new PathLoader
			val pathJson = json.getAsJsonArray(PAD_PATHS)
			pad.paths = pathLoader.load(pathJson, pad)

			val designLoader = new DesignLoader
			val designJson = json.getAsJsonObject(PAD_DESIGN)
			if (designJson != null)
				pad.design = designLoader.load(designJson, pad)

			pad
		}).toList
		pads
	}

}

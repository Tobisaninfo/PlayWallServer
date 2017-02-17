package de.tobias.playpad.server.project.loader.json

import java.util.UUID

import com.google.gson.{JsonArray, JsonObject}
import de.tobias.playpad.server.project.JsonDef._
import de.tobias.playpad.server.project.Pad

import scala.collection.JavaConverters

/**
  * Created by tobias on 17.02.17.
  */
class PadLoader {
	def load(jsonArray: JsonArray): List[Pad] = {
		val it = JavaConverters.asScalaIterator(jsonArray.iterator())
		val pads = it.filter(_.isJsonObject).map(i => {
			val json = i.asInstanceOf[JsonObject]

			val pad = new Pad()
			pad.id = UUID.fromString(json.get(PAD_ID).getAsString)
			pad.name = json.get(PAD_NAME).getAsString

			pad
		}).toList
		pads
	}

}

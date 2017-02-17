package de.tobias.playpad.server.project.loader.json

import java.util.UUID

import com.google.gson.{JsonArray, JsonObject}
import de.tobias.playpad.server.project.JsonDef._
import de.tobias.playpad.server.project.Page

import scala.collection.JavaConverters

/**
  * Created by tobias on 17.02.17.
  */
class PageLoader {

	def load(jsonArray: JsonArray): List[Page] = {
		val it = JavaConverters.asScalaIterator(jsonArray.iterator())
		val pages = it.filter(_.isJsonObject).map(i => {
			val json = i.asInstanceOf[JsonObject]

			val page = new Page()
			page.id = UUID.fromString(json.get(PAGE_ID).getAsString)
			page.name = json.get(PAGE_NAME).getAsString

			val padLoader = new PadLoader
			page.pads = padLoader.load(json.get(PAGE_PADS).getAsJsonArray)

			page
		}).toList
		pages
	}
}

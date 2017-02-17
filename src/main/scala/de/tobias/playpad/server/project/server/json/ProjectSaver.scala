package de.tobias.playpad.server.project.server.json

import com.google.gson.{JsonArray, JsonObject}
import de.tobias.playpad.server.project.Project

/**
  * Created by tobias on 17.02.17.
  */
class ProjectSaver {

	def save(project: Project): JsonObject = {
		val jsonObject = new JsonObject()

		val pageSaver = new PageSaver()
		val pageArray = new JsonArray
		project.pages.foreach(page => pageArray.add(pageSaver.save(page)))

		jsonObject.addProperty("name", project.name)
		jsonObject.add("pages", pageArray)

		jsonObject
	}
}

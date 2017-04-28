package de.tobias.playpad.server.project.loader.json

import java.util.UUID

import com.google.gson.JsonObject
import de.tobias.playpad.server.project.Project
import de.tobias.playpad.server.project.utils.JsonDef._

/**
  * Created by tobias on 17.02.17.
  */
class ProjectLoader {
	def load(json: JsonObject): Project = {
		val project = new Project()
		project.projectReference.id = UUID.fromString(json.get(PROJECT_ID).getAsString)
		project.projectReference.name = json.get(PROJECT_NAME).getAsString

		val pageLoader = new PageLoader
		project.pages = pageLoader.load(json.getAsJsonArray(PROJECT_PAGES), project)

		project
	}
}

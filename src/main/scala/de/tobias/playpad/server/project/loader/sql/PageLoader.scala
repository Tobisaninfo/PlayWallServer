package de.tobias.playpad.server.project.loader.sql

import java.sql.Connection
import java.util.UUID

import de.tobias.playpad.server.project.utils.SqlDef._
import de.tobias.playpad.server.project.{Page, Project}

/**
  * Created by tobias on 17.02.17.
  */
class PageLoader(val connection: Connection) {
	def load(project: Project): List[Page] = {
		val sql = s"SELECT * FROM $PAGE WHERE $PAGE_PROJECT_REF = ?"
		val preparedStatement = connection.prepareStatement(sql)
		preparedStatement.setString(1, project.projectReference.id.toString)
		val result = preparedStatement.executeQuery()

		var pages: List[Page] = List()

		while (result.next()) {
			val page = new Page()
			page.id = UUID.fromString(result.getString(PAGE_ID))
			page.name = result.getString(PAGE_NAME)

			val padLoader = new PadLoader(connection)
			page.pads = padLoader.load(page)

			page.project = project
			pages = page :: pages
		}

		result.close()
		preparedStatement.close()

		pages
	}
}

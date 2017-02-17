package de.tobias.playpad.server.project.loader.sql

import java.sql.Connection
import java.util.UUID

import de.tobias.playpad.server.project.Page

/**
  * Created by tobias on 17.02.17.
  */
class PageLoader {

	def load(connection: Connection, projectId: UUID): List[Page] = {
		val sql = "SELECT * FROM Page WHERE project_id = ?"
		val preparedStatement = connection.prepareStatement(sql)
		preparedStatement.setString(1, projectId.toString)
		val result = preparedStatement.executeQuery()

		var pages: List[Page] = List()

		while (result.next()) {
			val page = new Page()
			page.id = UUID.fromString(result.getString("id"))
			page.name = result.getString("name")

			val padLoader = new PadLoader()
			page.pads = padLoader.load(connection, page.id)

			pages = page :: pages
		}

		result.close()
		preparedStatement.close()

		pages
	}
}

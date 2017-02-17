package de.tobias.playpad.server.project.loader.sql

import java.sql.Connection
import java.util.UUID

import de.tobias.playpad.server.project.Project
import de.tobias.playpad.server.project.utils.SqlDef._

/**
  * Created by tobias on 17.02.17.
  */
class ProjectLoader {
	def load(connection: Connection, id: UUID): List[Project] = {
		val sql = s"SELECT * FROM $PROJECT WHERE $PROJECT_ID = ?"
		val preparedStatement = connection.prepareStatement(sql)
		preparedStatement.setString(1, id.toString)
		val result = preparedStatement.executeQuery()

		var projects: List[Project] = List()

		while (result.next()) {
			val project = new Project()
			project.id = UUID.fromString(result.getString(PROJECT_ID))
			project.name = result.getString(PROJECT_NAME)
			project.accountId = result.getInt(PROJECT_ACCOUNT_ID)

			val pageLoader = new PageLoader()
			project.pages = pageLoader.load(connection, project)

			projects = project :: projects
		}

		result.close()
		preparedStatement.close()

		projects
	}
}

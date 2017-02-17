package de.tobias.playpad.server.project.loader.sql

import java.sql.Connection
import java.util.UUID

import de.tobias.playpad.server.project.Project

/**
  * Created by tobias on 17.02.17.
  */
class ProjectLoader {

	def load(connection: Connection, id: UUID): List[Project] = {
		val sql = "SELECT * FROM Project WHERE id = ?"
		val preparedStatement = connection.prepareStatement(sql)
		preparedStatement.setString(1, id.toString)
		val result = preparedStatement.executeQuery()

		var projects: List[Project] = List()

		while (result.next()) {
			val project = new Project()
			project.id = UUID.fromString(result.getString("id"))
			project.name = result.getString("name")
			project.accountId = result.getInt("account_id")

			val pageLoader = new PageLoader()
			project.pages = pageLoader.load(connection, id)

			projects = project :: projects
		}

		result.close()
		preparedStatement.close()

		projects
	}
}

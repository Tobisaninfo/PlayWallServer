package de.tobias.playpad.server.project.loader.sql

import java.sql.Connection
import java.util.UUID

import de.tobias.playpad.server.account.Session
import de.tobias.playpad.server.project.utils.SqlDef._
import de.tobias.playpad.server.project.{Project, ProjectReference}

/**
  * Created by tobias on 17.02.17.
  */
class ProjectLoader(val connection: Connection) {
	def load(id: UUID): List[Project] = {
		val sql = s"SELECT * FROM $PROJECT WHERE $PROJECT_ID = ?"
		val preparedStatement = connection.prepareStatement(sql)
		preparedStatement.setString(1, id.toString)
		val result = preparedStatement.executeQuery()

		var projects: List[Project] = List()

		while (result.next()) {
			val project = new Project()
			val projectReference = project.projectReference
			projectReference.id = UUID.fromString(result.getString(PROJECT_ID))
			projectReference.name = result.getString(PROJECT_NAME)
			projectReference.accountId = result.getInt(PROJECT_ACCOUNT_ID)
			projectReference.lastModified = result.getLong(PROJECT_LAST_MODIFIED)
			projectReference.session = result.getString(PROJECT_SESSION_KEY)

			val pageLoader = new PageLoader(connection)
			project.pages = pageLoader.load(project)

			projects = project :: projects
		}

		result.close()
		preparedStatement.close()

		projects
	}

	def list(accountId: Int): List[ProjectReference] = {
		val sql = s"SELECT * FROM $PROJECT WHERE $PROJECT_ACCOUNT_ID = ?"
		val preparedStatement = connection.prepareStatement(sql)
		preparedStatement.setInt(1, accountId)
		val result = preparedStatement.executeQuery()

		var projects: List[ProjectReference] = List()

		while (result.next()) {
			val projectReference = new ProjectReference()

			projectReference.id = UUID.fromString(result.getString(PROJECT_ID))
			projectReference.name = result.getString(PROJECT_NAME)
			projectReference.accountId = result.getInt(PROJECT_ACCOUNT_ID)
			projectReference.lastModified = result.getLong(PROJECT_LAST_MODIFIED)
			projectReference.session = result.getString(PROJECT_SESSION_KEY)

			projects = projectReference :: projects
		}

		result.close()
		preparedStatement.close()

		projects
	}

	def getAccountId(id: UUID): Int = {
		val sql = s"SELECT account_id FROM $PROJECT WHERE $PROJECT_ID = ?"

		val preparedStatement = connection.prepareStatement(sql)
		preparedStatement.setString(1, id.toString)
		val result = preparedStatement.executeQuery()

		while (result.next()) {
			val account_id = result.getInt(PROJECT_ACCOUNT_ID)

			result.close()
			preparedStatement.close()

			return account_id
		}

		result.close()
		preparedStatement.close()

		-1
	}
}

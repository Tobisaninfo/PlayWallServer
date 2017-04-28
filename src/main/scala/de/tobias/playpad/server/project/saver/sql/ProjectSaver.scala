package de.tobias.playpad.server.project.saver.sql

import java.sql.Connection
import java.util.UUID

import de.tobias.playpad.server.project.Project
import de.tobias.playpad.server.project.utils.SqlDef._
import de.tobias.playpad.server.server.SqlHelper

/**
  * Created by tobias on 17.02.17.
  */
class ProjectSaver(val connection: Connection) {
	def save(project: Project): Unit = {
		val reference = project.projectReference
		SqlHelper.insertOrUpdate(connection, PROJECT, reference.id, PROJECT_NAME, reference.name)
		SqlHelper.insertOrUpdate(connection, PROJECT, reference.id, PROJECT_ACCOUNT_ID, reference.accountId)

		val pageSaver = new PageSaver(connection)
		project.pages.foreach(pageSaver.save)
	}
	def delete(project: UUID): Unit = {
		SqlHelper.delete(connection, PROJECT, project)
	}
}

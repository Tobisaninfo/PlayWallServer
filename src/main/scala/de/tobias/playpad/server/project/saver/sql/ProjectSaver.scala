package de.tobias.playpad.server.project.saver.sql

import java.sql.Connection

import de.tobias.playpad.server.project.Project
import de.tobias.playpad.server.project.utils.SqlDef._
import de.tobias.playpad.server.server.SqlHelper

/**
  * Created by tobias on 17.02.17.
  */
class ProjectSaver {
	def save(connection: Connection, project: Project): Unit = {
		SqlHelper.insertOrUpdate(connection, PROJECT, project.id, PROJECT_NAME, project.name)
		SqlHelper.insertOrUpdate(connection, PROJECT, project.id, PROJECT_ACCOUNT_ID, project.accountId)

		val pageSaver = new PageSaver
		project.pages.foreach(pageSaver.save(connection, _))
	}
}

package de.tobias.playpad.server.server.project.sync.listener.project

import java.sql.Connection
import java.util.UUID

import com.google.gson.JsonObject
import de.tobias.playpad.server.account.Session
import de.tobias.playpad.server.project.utils.SqlDef
import de.tobias.playpad.server.server.SqlHelper
import de.tobias.playpad.server.server.project.sync.listener.Listener

/**
 * Created by tobias on 19.02.17.
 */
class ProjectAddListener extends Listener {
	override def onChange(json: JsonObject, connection: Connection, session: Session): Unit = {
		val projectId = UUID.fromString(json.get("id").getAsString)
		val projectName = json.get("name").getAsString

		SqlHelper.insertOrUpdate(connection, SqlDef.PROJECT, projectId, SqlDef.PROJECT_NAME, projectName)
		SqlHelper.insertOrUpdate(connection, SqlDef.PROJECT, projectId, SqlDef.PROJECT_ACCOUNT_ID, session.getAccount.id)
	}
}

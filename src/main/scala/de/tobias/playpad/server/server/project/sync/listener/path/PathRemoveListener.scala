package de.tobias.playpad.server.server.project.sync.listener.path

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
class PathRemoveListener extends Listener {
	override def onChange(json: JsonObject, connection: Connection, session: Session): Unit = {
		val pageId = UUID.fromString(json.get("id").getAsString)

		SqlHelper.delete(connection, SqlDef.PATH, pageId)
	}
}

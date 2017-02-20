package de.tobias.playpad.server.server.project.sync.listener.pad

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
class PadAddListener extends Listener {
	override def onChange(json: JsonObject, connection: Connection, session: Session): Unit = {
		val padId = UUID.fromString(json.get("id").getAsString)
		val pageId = UUID.fromString(json.get("page").getAsString)
		val padName = json.get("name").getAsString
		val padPosition= json.get("position").getAsInt

		SqlHelper.insertOrUpdate(connection, SqlDef.PAD, padId, SqlDef.PAD_PAGE_REF, pageId)
		SqlHelper.insertOrUpdate(connection, SqlDef.PAD, padId, SqlDef.PAD_NAME, padName)
		SqlHelper.insertOrUpdate(connection, SqlDef.PAD, padId, SqlDef.PAD_POSITION, padPosition)
	}
}

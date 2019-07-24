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
class PadUpdateListener extends Listener {
	override def onChange(json: JsonObject, connection: Connection, session: Session): Unit = {
		val padID = UUID.fromString(json.get("id").getAsString)
		val pageId = UUID.fromString(json.get("page").getAsString)
		val field = json.get("field").getAsString

		SqlHelper.insertOrUpdate(connection, SqlDef.PAD, padID, SqlDef.PAD_PAGE_REF, pageId)

		field match {
			case "name" => SqlHelper.insertOrUpdate(connection, SqlDef.PAD, padID, SqlDef.PAD_NAME, json.get("value").getAsString)
			case "position" => SqlHelper.insertOrUpdate(connection, SqlDef.PAD, padID, SqlDef.PAD_POSITION, json.get("value").getAsInt)
			case "contentType" => if (!json.get("value").isJsonNull) {
				SqlHelper.insertOrUpdate(connection, SqlDef.PAD, padID, SqlDef.PAD_CONTENT_TYPE, json.get("value").getAsString)
			} else {
				SqlHelper.insertOrUpdate(connection, SqlDef.PAD, padID, SqlDef.PAD_CONTENT_TYPE, null)
			}
		}
	}
}

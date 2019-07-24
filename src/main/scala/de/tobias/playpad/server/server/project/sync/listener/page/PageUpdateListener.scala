package de.tobias.playpad.server.server.project.sync.listener.page

import com.google.gson.JsonObject
import de.tobias.playpad.server.account.Session
import de.tobias.playpad.server.project.utils.SqlDef
import de.tobias.playpad.server.server.SqlHelper
import de.tobias.playpad.server.server.project.sync.listener.Listener

/**
 * Created by tobias on 19.02.17.
 */
class PageUpdateListener extends Listener {
	override def onChange(json: JsonObject, connection: Connection, session: Session): Unit = {
		val pageId = UUID.fromString(json.get("id").getAsString)
		val projectId = UUID.fromString(json.get("project").getAsString)
		val field = json.get("field").getAsString

		SqlHelper.insertOrUpdate(connection, SqlDef.PAGE, pageId, SqlDef.PAGE_PROJECT_REF, projectId)

		field match {
			case "name" => SqlHelper.insertOrUpdate(connection, SqlDef.PAGE, pageId, SqlDef.PAGE_NAME, json.get("value").getAsString)
			case "position" => SqlHelper.insertOrUpdate(connection, SqlDef.PAGE, pageId, SqlDef.PAGE_POSITION, json.get("value").getAsInt)
		}
	}
}

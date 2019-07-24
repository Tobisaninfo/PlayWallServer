package de.tobias.playpad.server.server.project.sync.listener.page

import com.google.gson.JsonObject
import de.tobias.playpad.server.account.Session
import de.tobias.playpad.server.project.utils.SqlDef
import de.tobias.playpad.server.server.SqlHelper
import de.tobias.playpad.server.server.project.sync.listener.Listener

/**
 * Created by tobias on 19.02.17.
 */
class PageAddListener extends Listener {
	override def onChange(json: JsonObject, connection: Connection, session: Session): Unit = {
		val pageId = UUID.fromString(json.get("id").getAsString)
		val projectId = UUID.fromString(json.get("project").getAsString)
		val pageName = json.get("name").getAsString
		val pagePosition = json.get("position").getAsInt

		SqlHelper.insertOrUpdate(connection, SqlDef.PAGE, pageId, SqlDef.PAGE_PROJECT_REF, projectId)
		SqlHelper.insertOrUpdate(connection, SqlDef.PAGE, pageId, SqlDef.PAGE_NAME, pageName)
		SqlHelper.insertOrUpdate(connection, SqlDef.PAGE, pageId, SqlDef.PAGE_POSITION, pagePosition)
	}
}

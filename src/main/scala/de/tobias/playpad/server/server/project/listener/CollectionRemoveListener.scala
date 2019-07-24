package de.tobias.playpad.server.server.project.listener

import java.sql.Connection
import java.util.UUID

import com.google.gson.JsonObject
import de.tobias.playpad.server.server.SqlHelper

/**
 * Created by tobias on 18.02.17.
 */
@Deprecated
class CollectionRemoveListener extends Listener {
	override def onChange(json: JsonObject, connection: Connection): Unit = {
		val childClass = json.get("child_class").getAsString
		val childId = json.get("child_id").getAsString

		if (childId.matches("[0-9]*")) {
			val idInt = childId.toInt
			SqlHelper.delete(connection, childClass, idInt)
		} else {
			val uuid = UUID.fromString(childId)
			SqlHelper.delete(connection, childClass, uuid)
		}
	}
}

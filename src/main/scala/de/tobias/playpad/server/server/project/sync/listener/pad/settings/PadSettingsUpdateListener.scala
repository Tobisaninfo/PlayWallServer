package de.tobias.playpad.server.server.project.sync.listener.pad.settings

import java.sql.Connection
import java.util.UUID

import com.google.gson.JsonObject
import de.tobias.playpad.server.account.Session
import de.tobias.playpad.server.project.utils.SqlDef
import de.tobias.playpad.server.server.SqlHelper
import de.tobias.playpad.server.server.project.sync.listener.Listener

class PadSettingsUpdateListener extends Listener {
	override def onChange(json: JsonObject, connection: Connection, session: Session): Unit = {
		val settingsId = UUID.fromString(json.get("id").getAsString)
		val padId = UUID.fromString(json.get("pad_id").getAsString)
		val field = json.get("field").getAsString

		SqlHelper.insertOrUpdate(connection, SqlDef.PAD_SETTINGS, settingsId, SqlDef.PAD_SETTINGS_PAD_REF, padId)

		field match {
			case "volume" => SqlHelper.insertOrUpdate(connection, SqlDef.PAD_SETTINGS, settingsId, SqlDef.PAD_SETTINGS_VOLUME, json.get("value").getAsDouble)
			case "loop" => SqlHelper.insertOrUpdate(connection, SqlDef.PAD_SETTINGS, settingsId, SqlDef.PAD_SETTINGS_LOOP, json.get("value").getAsBoolean)
			case "time_mode" =>
				val value = if (!json.get("value").isJsonNull) {
					json.get("value").getAsString
				} else {
					null
				}
				SqlHelper.insertOrUpdate(connection, SqlDef.PAD_SETTINGS, settingsId, SqlDef.PAD_SETTINGS_TIME_MODE, value)
			case "warning" =>
				val value = if (!json.get("value").isJsonNull) {
					json.get("value").getAsInt
				} else {
					null
				}
				SqlHelper.insertOrUpdate(connection, SqlDef.PAD_SETTINGS, settingsId, SqlDef.PAD_SETTINGS_WARNING, value)
		}
	}
}

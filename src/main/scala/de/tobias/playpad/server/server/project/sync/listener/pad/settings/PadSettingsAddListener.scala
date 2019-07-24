package de.tobias.playpad.server.server.project.sync.listener.pad.settings

import com.google.gson.JsonObject
import de.tobias.playpad.server.account.Session
import de.tobias.playpad.server.project.utils.SqlDef
import de.tobias.playpad.server.server.SqlHelper
import de.tobias.playpad.server.server.project.sync.listener.Listener

class PadSettingsAddListener extends Listener {
	override def onChange(json: JsonObject, connection: Connection, session: Session): Unit = {
		val padSettingsId = UUID.fromString(json.get("id").getAsString)
		val padId = UUID.fromString(json.get("pad_id").getAsString)
		val volume = json.get("volume").getAsDouble
		val loop = json.get("loop").getAsBoolean

		SqlHelper.insertOrUpdate(connection, SqlDef.PAD_SETTINGS, padSettingsId, SqlDef.PAD_SETTINGS_PAD_REF, padId)
		SqlHelper.insertOrUpdate(connection, SqlDef.PAD_SETTINGS, padSettingsId, SqlDef.PAD_SETTINGS_VOLUME, volume)
		SqlHelper.insertOrUpdate(connection, SqlDef.PAD_SETTINGS, padSettingsId, SqlDef.PAD_SETTINGS_LOOP, loop)

		if (json.get("time_mode") != null) {
			val timeMode = json.get("time_mode").getAsString
			SqlHelper.insertOrUpdate(connection, SqlDef.PAD_SETTINGS, padSettingsId, SqlDef.PAD_SETTINGS_TIME_MODE, timeMode)
		}

		if (json.get("warning") != null) {
			val warning = json.get("warning").getAsInt
			SqlHelper.insertOrUpdate(connection, SqlDef.PAD_SETTINGS, padSettingsId, SqlDef.PAD_SETTINGS_WARNING, warning)
		}
	}
}

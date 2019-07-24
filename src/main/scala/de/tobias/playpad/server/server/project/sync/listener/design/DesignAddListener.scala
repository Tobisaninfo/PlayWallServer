package de.tobias.playpad.server.server.project.sync.listener.design

import com.google.gson.JsonObject
import de.tobias.playpad.server.account.Session
import de.tobias.playpad.server.project.utils.SqlDef
import de.tobias.playpad.server.server.SqlHelper
import de.tobias.playpad.server.server.project.sync.listener.Listener

/**
 * Created by tobias on 19.02.17.
 */
class DesignAddListener extends Listener {
	override def onChange(json: JsonObject, connection: Connection, session: Session): Unit = {
		val designId = UUID.fromString(json.get("id").getAsString)
		val padSettingsId = UUID.fromString(json.get("pad_settings").getAsString)
		val backgroundColor = json.get("background_color").getAsString
		val playColor = json.get("play_color").getAsString

		SqlHelper.insertOrUpdate(connection, SqlDef.DESIGN, designId, SqlDef.DESIGN_PAD_REF, padSettingsId)
		SqlHelper.insertOrUpdate(connection, SqlDef.DESIGN, designId, SqlDef.DESIGN_BACKGROUND_COLOR, backgroundColor)
		SqlHelper.insertOrUpdate(connection, SqlDef.DESIGN, designId, SqlDef.DESIGN_PLAY_COLOR, playColor)
	}
}

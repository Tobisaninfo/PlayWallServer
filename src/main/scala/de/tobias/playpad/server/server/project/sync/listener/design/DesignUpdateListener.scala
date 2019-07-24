package de.tobias.playpad.server.server.project.sync.listener.design

import com.google.gson.JsonObject
import de.tobias.playpad.server.account.Session
import de.tobias.playpad.server.project.utils.SqlDef._
import de.tobias.playpad.server.server.SqlHelper
import de.tobias.playpad.server.server.project.sync.listener.Listener

/**
 * Created by tobias on 19.02.17.
 */
class DesignUpdateListener extends Listener {
	override def onChange(json: JsonObject, connection: Connection, session: Session): Unit = {
		val designId = UUID.fromString(json.get("id").getAsString)
		val padSettingsId = UUID.fromString(json.get("pad_settings").getAsString)
		val field = json.get("field").getAsString

		SqlHelper.insertOrUpdate(connection, DESIGN, designId, DESIGN_PAD_REF, padSettingsId)

		field match {
			case "background_color" => SqlHelper.insertOrUpdate(connection, DESIGN, designId, DESIGN_BACKGROUND_COLOR, json.get("value").getAsString)
			case "play_color" => SqlHelper.insertOrUpdate(connection, DESIGN, designId, DESIGN_PLAY_COLOR, json.get("value").getAsString)
		}
	}
}

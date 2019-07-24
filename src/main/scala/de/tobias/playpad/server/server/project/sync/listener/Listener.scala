package de.tobias.playpad.server.server.project.sync.listener

import com.google.gson.JsonObject
import de.tobias.playpad.server.account.Session

/**
 * Created by tobias on 18.02.17.
 */
trait Listener {

	def onChange(json: JsonObject, connection: Connection, session: Session): Unit
}

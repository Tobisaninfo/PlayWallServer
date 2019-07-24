package de.tobias.playpad.server.server.project.listener

import java.sql.Connection

import com.google.gson.JsonObject

/**
 * Created by tobias on 18.02.17.
 */
@Deprecated
trait Listener {

	def onChange(json: JsonObject, connection: Connection): Unit
}

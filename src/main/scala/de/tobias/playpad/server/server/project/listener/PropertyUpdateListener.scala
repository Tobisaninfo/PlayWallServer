package de.tobias.playpad.server.server.project.listener

import java.sql.Connection
import java.util.UUID

import com.google.gson.JsonObject
import de.tobias.playpad.server.server.SqlHelper

/**
 * Created by tobias on 18.02.17.
 */
@Deprecated
class PropertyUpdateListener extends Listener {
	override def onChange(json: JsonObject, connection: Connection): Unit = {
		json match {
			case jsonObject: JsonObject =>
				val className = jsonObject.get("class").getAsString
				val field = jsonObject.get("field").getAsString
				val id = jsonObject.get("id").getAsString

				val valueType = Class.forName(jsonObject.get("type").getAsString)

				if (valueType == classOf[String]) {
					val value = jsonObject.get("value").getAsString

					if (id.matches("[0-9]*")) {
						val idInt = id.toInt
						SqlHelper.insertOrUpdate(connection, className, idInt, field, value)
					} else {
						val uuid = UUID.fromString(id)
						SqlHelper.insertOrUpdate(connection, className, uuid, field, value)
					}
				} else if (valueType == classOf[Boolean]) {
					val value = jsonObject.get("value").getAsBoolean

					if (id.matches("[0-9]*")) {
						val idInt = id.toInt
						SqlHelper.insertOrUpdate(connection, className, idInt, field, value)
					} else {
						val uuid = UUID.fromString(id)
						SqlHelper.insertOrUpdate(connection, className, uuid, field, value)
					}
                } else if (valueType == classOf[Integer]) {
					val value = jsonObject.get("value").getAsInt

					if (id.matches("[0-9]*")) {
						val idInt = id.toInt
						SqlHelper.insertOrUpdate(connection, className, idInt, field, value)
					} else {
						val uuid = UUID.fromString(id)
						SqlHelper.insertOrUpdate(connection, className, uuid, field, value)
					}
				} else if (valueType == classOf[Double]) {
					val value = jsonObject.get("value").getAsDouble

					if (id.matches("[0-9]*")) {
						val idInt = id.toInt
						SqlHelper.insertOrUpdate(connection, className, idInt, field, value)
					} else {
						val uuid = UUID.fromString(id)
						SqlHelper.insertOrUpdate(connection, className, uuid, field, value)
					}
				}

			case _ =>
		}
	}
}

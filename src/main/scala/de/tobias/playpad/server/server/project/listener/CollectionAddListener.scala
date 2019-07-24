package de.tobias.playpad.server.server.project.listener

import com.google.gson.{JsonArray, JsonElement, JsonObject}
import de.tobias.playpad.server.server.SqlHelper

import scala.collection.JavaConverters

/**
 * Created by tobias on 18.02.17.
 */
@Deprecated
class CollectionAddListener extends Listener {
	override def onChange(json: JsonObject, connection: Connection): Unit = {
		val childClass = json.get("child_class").getAsString
		val childId = json.get("child_id").getAsString

		val fields = JavaConverters.asScalaIterator(json.get("fields").asInstanceOf[JsonArray].iterator())
		fields.filter(_.isJsonObject)
			.map(f => f.asInstanceOf[JsonObject])
			.foreach(jsonField => {
				val fieldName = jsonField.get("field").getAsString
				val valueType = Class.forName(jsonField.get("type").getAsString)
				val jsonValue = jsonField.get("value")
				setValue(connection, childClass, childId, fieldName, valueType, jsonValue)
			})

		// Foreign Key
		val fieldName = s"${json.get("parent_class").getAsString.toLowerCase}_id"
		val valueType = Class.forName(json.get("parent_type").getAsString)
		val jsonValue = json.get("parent_id")
		setValue(connection, childClass, childId, fieldName, valueType, jsonValue)
	}

	private def setValue(connection: Connection, className: String, objectId: String, fieldName: String, valueType: Class[_], jsonValue: JsonElement) = {
		if (valueType == classOf[String]) {
			val value = jsonValue.getAsString

			if (objectId.matches("[0-9]*")) {
				val idInt = objectId.toInt
				SqlHelper.insertOrUpdate(connection, className, idInt, fieldName, value)
			} else {
				val uuid = UUID.fromString(objectId)
				SqlHelper.insertOrUpdate(connection, className, uuid, fieldName, value)
			}
		} else if (valueType == classOf[Boolean]) {
			val value = jsonValue.getAsBoolean

			if (objectId.matches("[0-9]*")) {
				val idInt = objectId.toInt
				SqlHelper.insertOrUpdate(connection, className, idInt, fieldName, value)
			} else {
				val uuid = UUID.fromString(objectId)
				SqlHelper.insertOrUpdate(connection, className, uuid, fieldName, value)
			}
		} else if (valueType == classOf[Integer]) {
			val value = jsonValue.getAsInt

			if (objectId.matches("[0-9]*")) {
				val idInt = objectId.toInt
				SqlHelper.insertOrUpdate(connection, className, idInt, fieldName, value)
			} else {
				val uuid = UUID.fromString(objectId)
				SqlHelper.insertOrUpdate(connection, className, uuid, fieldName, value)
			}
		} else if (valueType == classOf[Double]) {
			val value = jsonValue.getAsDouble

			if (objectId.matches("[0-9]*")) {
				val idInt = objectId.toInt
				SqlHelper.insertOrUpdate(connection, className, idInt, fieldName, value)
			} else {
				val uuid = UUID.fromString(objectId)
				SqlHelper.insertOrUpdate(connection, className, uuid, fieldName, value)
			}
		}
	}
}

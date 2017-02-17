package de.tobias.playpad.server.server

import java.sql.{Connection, PreparedStatement}
import java.util.UUID

/**
  * Created by tobias on 17.02.17.
  */
object SqlHelper {

	def insertOrUpdate[T](connection: Connection, table: String, idValue: Int, field: String, value: T): Unit = {
		val preparedStatement = createQuery(connection, table, field, value)
		preparedStatement.setInt(1, idValue)
		preparedStatement.execute()
		preparedStatement.close()
	}

	def insertOrUpdate[T](connection: Connection, table: String, idValue: UUID, field: String, value: T): Unit = {
		val preparedStatement = createQuery(connection, table, field, value)
		preparedStatement.setString(1, idValue.toString)
		preparedStatement.execute()
		preparedStatement.close()
	}

	private def createQuery[T](connection: Connection, table: String, field: String, value: T): PreparedStatement = {
		val sql = "INSERT INTO " + table + " (id, " + field + ") VALUES(?, ?) " +
			"ON DUPLICATE KEY UPDATE " + field + "=?"
		val preparedStatement = connection.prepareStatement(sql)

		value match {
			case value: String =>
				preparedStatement.setString(2, value)
				preparedStatement.setString(3, value)
			case value: Boolean =>
				preparedStatement.setBoolean(2, value)
				preparedStatement.setBoolean(3, value)
			case value: Int =>
				preparedStatement.setInt(2, value)
				preparedStatement.setInt(3, value)
			case value: Double =>
				preparedStatement.setDouble(2, value)
				preparedStatement.setDouble(3, value)
		}

		preparedStatement
	}
}

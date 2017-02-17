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
		val sql = "INSERT INTO %s (id, %s) VALUES(?, ?) ON DUPLICATE KEY UPDATE %s=?".format(table, field, field)
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

	def createTables(connection: Connection): Unit = {
		def createTable(sql: String) = {
			val preparedStatement = connection.prepareStatement(sql)
			preparedStatement.execute()
			preparedStatement.close()
		}

		createTable(
			"""CREATE TABLE `Project` (
			  |  `id` varchar(48) NOT NULL DEFAULT '',
			  |  `name` varchar(255) DEFAULT NULL,
			  |  `account_id` int(11) DEFAULT NULL,
			  |  PRIMARY KEY (`id`),
			  |  UNIQUE KEY `id` (`id`)
			  |) ENGINE=InnoDB DEFAULT CHARSET=latin1;""".stripMargin)
		createTable(
			"""CREATE TABLE `Page` (
			  |  `id` varchar(48) NOT NULL DEFAULT '',
			  |  `name` varchar(255) DEFAULT NULL,
			  |  `position` int(11) DEFAULT NULL,
			  |  `project_id` varchar(48) DEFAULT NULL,
			  |  PRIMARY KEY (`id`),
			  |  UNIQUE KEY `id` (`id`)
			  |  CONSTRAINT `Page_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`) ON DELETE CASCADE
			  |) ENGINE=InnoDB DEFAULT CHARSET=latin1;""".stripMargin)
		createTable(
			"""CREATE TABLE `Pad` (
			  |  `id` varchar(48) NOT NULL DEFAULT '',
			  |  `name` varchar(255) DEFAULT NULL,
			  |  `position` int(11) DEFAULT NULL,
			  |  `page` int(11) DEFAULT NULL,
			  |  `design_id` varchar(48) DEFAULT NULL,
			  |  `page_id` varchar(48) DEFAULT NULL,
			  |  PRIMARY KEY (`id`),
			  |  UNIQUE KEY `id` (`id`)
			  |  CONSTRAINT `Pad_ibfk_1` FOREIGN KEY (`page_id`) REFERENCES `Page` (`id`) ON DELETE CASCADE
			  |) ENGINE=InnoDB DEFAULT CHARSET=latin1;""".stripMargin)
	}
}

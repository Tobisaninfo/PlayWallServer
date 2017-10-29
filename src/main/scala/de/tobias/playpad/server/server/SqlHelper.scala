package de.tobias.playpad.server.server

import java.sql.Types.NULL
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
		val sql = s"INSERT INTO $table (id, $field) VALUES(?, ?) ON DUPLICATE KEY UPDATE $field=?"
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
			case value: Long =>
				preparedStatement.setLong(2, value)
				preparedStatement.setLong(3, value)
			case value: Double =>
				preparedStatement.setDouble(2, value)
				preparedStatement.setDouble(3, value)
			case value: UUID =>
				preparedStatement.setString(2, value.toString)
				preparedStatement.setString(3, value.toString)
			case null =>
				preparedStatement.setNull(2, NULL)
				preparedStatement.setNull(3, NULL)

		}

		preparedStatement
	}

	def delete(connection: Connection, table: String, id: Int): Unit = {
		val sql = s"DELETE FROM $table WHERE id = ?"
		val preparedStatement = connection.prepareStatement(sql)
		preparedStatement.setInt(1, id)
		preparedStatement.execute()
		preparedStatement.close()
	}

	def delete(connection: Connection, table: String, uuid: UUID): Unit = {
		val sql = s"DELETE FROM $table WHERE id = ?"
		val preparedStatement = connection.prepareStatement(sql)
		preparedStatement.setString(1, uuid.toString)
		preparedStatement.execute()
		preparedStatement.close()
	}

	def createTables(connection: Connection): Unit = {
		def createTable(sql: String): Unit = {
			val preparedStatement = connection.prepareStatement(sql)
			preparedStatement.execute()
			preparedStatement.close()
		}

		createTable(
			"""CREATE TABLE IF NOT EXISTS `Project` (
			  |  `id` varchar(48) NOT NULL DEFAULT '',
			  |  `name` varchar(255) DEFAULT NULL,
			  |  `account_id` int(11) DEFAULT NULL,
			  |  `last_modified` bigint(11) DEFAULT NULL,
			  |  `session_key` varchar(255) DEFAULT NULL,
			  |  PRIMARY KEY (`id`),
			  |  UNIQUE KEY `id` (`id`)
			  |) ENGINE=InnoDB DEFAULT CHARSET=latin1;""".stripMargin)
		createTable(
			"""CREATE TABLE IF NOT EXISTS `Page` (
			  |  `id` varchar(48) NOT NULL DEFAULT '',
			  |  `name` varchar(255) DEFAULT NULL,
			  |  `position` int(11) DEFAULT NULL,
			  |  `project_id` varchar(48) DEFAULT NULL,
			  |  PRIMARY KEY (`id`),
			  |  UNIQUE KEY `id` (`id`),
			  |  CONSTRAINT `Page_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `Project` (`id`) ON DELETE CASCADE
			  |) ENGINE=InnoDB DEFAULT CHARSET=latin1;""".stripMargin)

		createTable(
			"""CREATE TABLE IF NOT EXISTS `Fade` (
			  |  `id` varchar(48) NOT NULL DEFAULT '',
			  |  `fadeIn` int(11) DEFAULT NULL,
			  |  `fadeOut` int(11) DEFAULT NULL,
			  |  `fadeInStart` tinyint(1) DEFAULT NULL,
			  |  `fadeInPause` tinyint(1) DEFAULT NULL,
			  |  `fadeOutPause` tinyint(1) DEFAULT NULL,
			  |  `fadeOutStop` tinyint(1) DEFAULT NULL,
			  |  PRIMARY KEY (`id`)
			  |) ENGINE=InnoDB DEFAULT CHARSET=latin1;""".stripMargin)
		createTable(
			"""CREATE TABLE IF NOT EXISTS `Design` (
			  |  `id` varchar(40) NOT NULL DEFAULT '',
			  |  `background_color` varchar(20) DEFAULT NULL,
			  |  `play_color` varchar(20) DEFAULT NULL,
			  |  PRIMARY KEY (`id`)
			  |) ENGINE=InnoDB DEFAULT CHARSET=latin1;""".stripMargin)
		createTable(
			"""CREATE TABLE IF NOT EXISTS `PadSettings` (
			  |  `id` varchar(48) NOT NULL DEFAULT '',
			  |  `volume` double DEFAULT NULL,
			  |  `loop` tinyint(1) DEFAULT NULL,
			  |  `timeMode` varchar(20) DEFAULT NULL,
			  |  `fade` varchar(48) DEFAULT NULL,
			  |  `warning` int(11) DEFAULT NULL,
			  |  `design` varchar(48) DEFAULT NULL,
			  |  PRIMARY KEY (`id`),
			  |  KEY `fade` (`fade`),
			  |  KEY `design` (`design`),
			  |  CONSTRAINT `PadSettings_ibfk_2` FOREIGN KEY (`design`) REFERENCES `Design` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
			  |  CONSTRAINT `PadSettings_ibfk_1` FOREIGN KEY (`fade`) REFERENCES `Fade` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
			  |) ENGINE=InnoDB DEFAULT CHARSET=latin1;""".stripMargin)

		createTable(
			"""CREATE TABLE IF NOT EXISTS `Pad` (
			  |  `id` varchar(48) NOT NULL DEFAULT '',
			  |  `name` varchar(255) DEFAULT NULL,
			  |  `position` int(11) DEFAULT NULL,
			  |  `content_type` varchar(100) DEFAULT NULL,
			  |  `page_id` varchar(48) DEFAULT NULL,
			  |  `settings_id` varchar(48) DEFAULT NULL,
			  |  PRIMARY KEY (`id`),
			  |  UNIQUE KEY `id` (`id`),
			  |  KEY `Pad_ibfk_1` (`page_id`),
			  |  KEY `settings_id` (`settings_id`),
			  |  CONSTRAINT `Pad_ibfk_2` FOREIGN KEY (`settings_id`) REFERENCES `PadSettings` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
			  |  CONSTRAINT `Pad_ibfk_1` FOREIGN KEY (`page_id`) REFERENCES `Page` (`id`) ON DELETE CASCADE
			  |) ENGINE=InnoDB DEFAULT CHARSET=latin1;""".stripMargin)
		createTable(
			"""CREATE TABLE IF NOT EXISTS `Path` (
			  |  `id` varchar(40) NOT NULL DEFAULT '',
			  |  `filename` text,
			  |  `pad_id` varchar(40) DEFAULT NULL,
			  |  PRIMARY KEY (`id`),
			  |  KEY `pad_id` (`pad_id`),
			  |  CONSTRAINT `Path_ibfk_1` FOREIGN KEY (`pad_id`) REFERENCES `Pad` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
			  |) ENGINE=InnoDB DEFAULT CHARSET=latin1;""".stripMargin)
	}
}

package de.tobias.playpad.server.sql

import java.util.UUID

class UUIDSqlResultHandler extends SqlResultHandler {
	override def fromResult(o: Any): Any = {
		o match {
			case string: String => UUID.fromString(string)
			case _ => null
		}
	}

	override def toQuery(o: Any): Any = {
		o match {
			case uuid: UUID => uuid.toString
			case _ => null
		}
	}
}

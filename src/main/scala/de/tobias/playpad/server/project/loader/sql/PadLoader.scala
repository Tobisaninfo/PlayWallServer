package de.tobias.playpad.server.project.loader.sql

import java.sql.Connection
import java.util.UUID

import de.tobias.playpad.server.project.Pad

/**
  * Created by tobias on 17.02.17.
  */
class PadLoader {

	def load(connection: Connection, pageId: UUID): List[Pad] = {
		val sql = "SELECT * FROM Pad WHERE page_id = ?"
		val preparedStatement = connection.prepareStatement(sql)
		preparedStatement.setString(1, pageId.toString)
		val result = preparedStatement.executeQuery()

		var pads: List[Pad] = List()

		while (result.next()) {
			val pad = new Pad()
			pad.id = UUID.fromString(result.getString("id"))
			pad.name = result.getString("name")
			pad.position = result.getInt("position")
			pad.pageIndex = result.getInt("page")

			pads = pad :: pads
		}

		result.close()
		preparedStatement.close()

		pads
	}
}

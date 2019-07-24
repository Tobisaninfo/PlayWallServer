package de.tobias.playpad.server.project.loader.sql

import java.sql.Connection
import java.util.UUID

import de.tobias.playpad.server.project.utils.SqlDef._
import de.tobias.playpad.server.project.{Design, Pad}

/**
 * Created by tobias on 23.02.17.
 */
class DesignLoader(val connection: Connection) {
	def load(pad: Pad): Design = {
		val sql = s"SELECT * FROM $DESIGN WHERE $DESIGN_PAD_REF = ?"
		val preparedStatement = connection.prepareStatement(sql)
		preparedStatement.setString(1, pad.id.toString)
		val result = preparedStatement.executeQuery()

		while (result.next()) {
			val design = new Design
			design.id = UUID.fromString(result.getString(DESIGN_ID))
			design.backgroundColor = result.getString(DESIGN_BACKGROUND_COLOR)
			design.playColor = result.getString(DESIGN_PLAY_COLOR)
			design.pad = pad

			result.close()
			preparedStatement.close()

			return design
		}

		null
	}
}

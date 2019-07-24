package de.tobias.playpad.server.project.loader.sql

import de.tobias.playpad.server.project.utils.SqlDef._
import de.tobias.playpad.server.project.{Pad, Path}

/**
 * Created by tobias on 20.02.17.
 */
class PathLoader(val connection: Connection) {
	def load(pad: Pad): List[Path] = {
		val sql = s"SELECT * FROM $PATH WHERE $PATH_PAD_REF = ?"
		val preparedStatement = connection.prepareStatement(sql)
		preparedStatement.setString(1, pad.id.toString)
		val result = preparedStatement.executeQuery()

		var paths: List[Path] = List()

		while (result.next()) {
			val path = new Path()
			path.id = UUID.fromString(result.getString(PATH_ID))
			path.filename = result.getString(PATH_NAME)

			path.pad = pad
			paths = path :: paths
		}

		result.close()
		preparedStatement.close()

		paths
	}
}

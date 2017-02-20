package de.tobias.playpad.server.project.saver.sql

import java.sql.Connection

import de.tobias.playpad.server.project.Path
import de.tobias.playpad.server.project.utils.SqlDef._
import de.tobias.playpad.server.server.SqlHelper

/**
  * Created by tobias on 20.02.17.
  */
class PathSaver(val connection: Connection) {
	def save(path: Path): Unit = {
		SqlHelper.insertOrUpdate(connection, PATH, path.id, PAD_NAME, path.path)
		SqlHelper.insertOrUpdate(connection, PATH, path.id, PAD_POSITION, path.pad.id)

	}
}

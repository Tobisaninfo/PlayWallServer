package de.tobias.playpad.server.project.saver.sql

import java.sql.Connection

import de.tobias.playpad.server.project.Pad
import de.tobias.playpad.server.project.utils.SqlDef._
import de.tobias.playpad.server.server.SqlHelper

/**
  * Created by tobias on 17.02.17.
  */
class PadSaver(val connection: Connection) {
	def save(pad: Pad): Unit = {
		SqlHelper.insertOrUpdate(connection, PAD, pad.id, PAD_NAME, pad.name)
		SqlHelper.insertOrUpdate(connection, PAD, pad.id, PAD_POSITION, pad.position)
		SqlHelper.insertOrUpdate(connection, PAD, pad.id, PAD_PAGE, pad.pageIndex)
		SqlHelper.insertOrUpdate(connection, PAD, pad.id, PAD_PAGE_REF, pad.page.id)
	}
}

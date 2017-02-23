package de.tobias.playpad.server.project.saver.sql

import java.sql.Connection

import de.tobias.playpad.server.project.Design
import de.tobias.playpad.server.project.utils.SqlDef._
import de.tobias.playpad.server.server.SqlHelper

/**
  * Created by tobias on 23.02.17.
  */
class DesignSaver(val connection: Connection) {
	def save(design: Design): Unit = {
		SqlHelper.insertOrUpdate(connection, DESIGN, design.id, DESIGN_BACKGROUND_COLOR, design.backgroundColor)
		SqlHelper.insertOrUpdate(connection, DESIGN, design.id, DESIGN_PLAY_COLOR, design.playColor)
		SqlHelper.insertOrUpdate(connection, DESIGN, design.id, DESIGN_PAD_REF, design.pad.id)
	}
}

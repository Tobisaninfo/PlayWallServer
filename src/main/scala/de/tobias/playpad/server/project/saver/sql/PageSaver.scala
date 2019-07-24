package de.tobias.playpad.server.project.saver.sql

import de.tobias.playpad.server.project.Page
import de.tobias.playpad.server.project.utils.SqlDef._
import de.tobias.playpad.server.server.SqlHelper

/**
 * Created by tobias on 17.02.17.
 */
class PageSaver(connection: Connection) {
	def save(page: Page): Unit = {
		SqlHelper.insertOrUpdate(connection, PAGE, page.id, PAGE_NAME, page.name)
		SqlHelper.insertOrUpdate(connection, PAGE, page.id, PAGE_POSITION, page.position)
		SqlHelper.insertOrUpdate(connection, PAGE, page.id, PAGE_PROJECT_REF, page.project.id)

		val padSaver = new PadSaver(connection)
		page.pads.foreach(padSaver.save)
	}
}

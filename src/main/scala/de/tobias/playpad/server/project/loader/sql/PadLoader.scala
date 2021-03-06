package de.tobias.playpad.server.project.loader.sql

import java.sql.Connection
import java.util.UUID

import de.tobias.playpad.server.project.utils.SqlDef._
import de.tobias.playpad.server.project.{Pad, Page}
import de.tobias.playpad.server.sql.SqlSerializer

/**
 * Created by tobias on 17.02.17.
 */
class PadLoader(val connection: Connection) {
	def load(page: Page): List[Pad] = {
		val sql = s"SELECT * FROM $PAD WHERE $PAD_PAGE_REF = ?"
		val preparedStatement = connection.prepareStatement(sql)
		preparedStatement.setString(1, page.id.toString)
		val result = preparedStatement.executeQuery()

		var pads: List[Pad] = List()


		val padss = new SqlSerializer().queryObj(classOf[Pad], connection)
		println(padss)

		while (result.next()) {
			val pad = new Pad()
			pad.id = UUID.fromString(result.getString(PAD_ID))
			pad.name = result.getString(PAD_NAME)
			pad.position = result.getInt(PAD_POSITION)
			pad.contentType = result.getString(PAD_CONTENT_TYPE)

			val pathLoader = new PathLoader(connection)
			pad.paths = pathLoader.load(pad)

			val designLoader = new DesignLoader(connection)
			// pad.design = designLoader.load(pad) TODO

			pad.page = page
			pads = pad :: pads
		}

		result.close()
		preparedStatement.close()

		pads
	}
}

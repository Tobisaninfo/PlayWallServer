package de.tobias.playpad.server.server.project

import java.sql.Connection
import java.util.UUID

import com.j256.ormlite.dao.Dao
import de.tobias.playpad.server.account.Session
import de.tobias.playpad.server.project.loader.sql.ProjectLoader
import de.tobias.playpad.server.project.saver.sql.ProjectSaver
import de.tobias.playpad.server.server.{Result, Status}
import spark.{Request, Response, Route}

/**
 * Created by tobias on 17.02.17.
 */
class ProjectDelete(connection: Connection, sessionDao: Dao[Session, Int]) extends Route {
	override def handle(request: Request, response: Response): AnyRef = {
		val sessionKey = request.queryParams("session")
		val session = Session.getSession(sessionKey, sessionDao)
		session match {
			case Some(s) =>
				val projectId = UUID.fromString(request.queryParams("project"))

				val projectLoader = new ProjectLoader(connection)
				val account_id = projectLoader.getAccountId(projectId)

				if (account_id == s.getAccount.id) {
					val projectSaver = new ProjectSaver(connection)
					projectSaver.delete(projectId)

					return new Result(Status.OK, "added project")
				}
				new Result(Status.ERROR, "session mismatch")
			case None =>
				new Result(Status.ERROR, "session invalid")
		}
	}
}

package de.tobias.playpad.server.server.project

import java.sql.Connection
import java.util.UUID

import com.j256.ormlite.dao.Dao
import de.tobias.playpad.server.account.Session
import de.tobias.playpad.server.json.JsonSerializer
import de.tobias.playpad.server.project.loader.sql.ProjectLoader
import de.tobias.playpad.server.server.{Result, Status}
import spark.{Request, Response, Route}

/**
 * Created by tobias on 17.02.17.
 */
class ProjectGet(connection: Connection, sessionDao: Dao[Session, Int]) extends Route {

	override def handle(request: Request, response: Response): AnyRef = {
		val sessionKey = request.queryParams("session")
		val projectId = request.params(":id")

		val session = Session.getSession(sessionKey, sessionDao)

		session match {
			case Some(s) =>
				val projectLoader = new ProjectLoader(connection)
				val projects = projectLoader.load(UUID.fromString(projectId))

				if (projects.size == 1) {
					val project = projects.head
					if (project.accountId == s.getAccount.id) {
						return new JsonSerializer().serialize(project)
					}
				}
				new Result(Status.ERROR, "Project invalid")
			case None =>
				new Result(Status.ERROR, "Session invalid")
		}
	}

}

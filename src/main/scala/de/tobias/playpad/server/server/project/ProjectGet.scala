package de.tobias.playpad.server.server.project

import java.sql.Connection
import java.util.UUID

import com.j256.ormlite.dao.Dao
import de.tobias.playpad.server.account.Session
import de.tobias.playpad.server.project.loader.ProjectLoader
import de.tobias.playpad.server.project.server.json.ProjectSaver
import de.tobias.playpad.server.server.{Result, Status}
import spark.{Request, Response, Route}

/**
  * Created by tobias on 17.02.17.
  */
class ProjectGet(connection: Connection, sessionDao: Dao[Session, Int]) extends Route {

	override def handle(request: Request, response: Response): AnyRef = {
		val sessionKey = request.queryParams("session")
		val projectId = request.queryParams("project")

		val sessions = sessionDao.queryForEq("key", sessionKey)
		if (sessions.size() == 1) {
			val projectLoader = new ProjectLoader()
			val projects = projectLoader.load(connection, UUID.fromString(projectId))
			val session = sessions.get(0)

			if (projects.size == 1) {
				val project = projects.head
				if (project.accountId == session.getAccount.id) {
					val projectSaver = new ProjectSaver()
					return projectSaver.save(project)
				}
			}
		}
		new Result(Status.ERROR, "Session invalid")
	}

}

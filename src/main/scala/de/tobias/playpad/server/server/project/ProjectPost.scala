package de.tobias.playpad.server.server.project

import java.sql.Connection

import com.google.gson.JsonParser
import com.j256.ormlite.dao.Dao
import de.tobias.playpad.server.account.Session
import de.tobias.playpad.server.json.JsonSerializer
import de.tobias.playpad.server.project.Project
import de.tobias.playpad.server.project.saver.sql.ProjectSaver
import de.tobias.playpad.server.server.{Result, Status}
import spark.{Request, Response, Route}

/**
  * Created by tobias on 17.02.17.
  */
class ProjectPost(connection: Connection, sessionDao: Dao[Session, Int]) extends Route {
	override def handle(request: Request, response: Response): AnyRef = {
		val sessionKey = request.queryParams("session")
		val session = Session.getSession(sessionKey, sessionDao)
		session match {
			case Some(s) =>
				val projectParam = request.body()

				val json = new JsonParser().parse(projectParam).getAsJsonObject

				val project = new JsonSerializer().deserialize(json, classOf[Project])

				project.accountId = s.getAccount.id

				val projectSaver = new ProjectSaver(connection)
				projectSaver.save(project)

				new Result(Status.OK, "added project")
			case None =>
				new Result(Status.ERROR, "session error")
		}
	}
}

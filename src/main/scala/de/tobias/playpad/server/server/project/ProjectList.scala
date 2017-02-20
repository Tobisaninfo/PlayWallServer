package de.tobias.playpad.server.server.project

import java.sql.Connection

import com.google.gson.{JsonArray, JsonObject}
import com.j256.ormlite.dao.Dao
import de.tobias.playpad.server.account.Session
import de.tobias.playpad.server.project.loader.sql.ProjectLoader
import de.tobias.playpad.server.server.{Result, Status}
import spark.{Request, Response, Route}

/**
  * Created by tobias on 17.02.17.
  */
class ProjectList(connection: Connection, sessionDao: Dao[Session, Int]) extends Route {

	override def handle(request: Request, response: Response): AnyRef = {
		val sessionKey = request.queryParams("session")

		val session = Session.getSession(sessionKey, sessionDao)

		session match {
			case Some(s) =>
				val projectLoader = new ProjectLoader(connection)
				val projects = projectLoader.list(s.getAccount.id)

				val array = new JsonArray()
				projects.foreach(project => {
					val json = new JsonObject()
					json.addProperty("uuid", project.id.toString)
					json.addProperty("name", project.name)
					array.add(json)
				})

				array
			case None =>
				new Result(Status.ERROR, "Session invalid")
		}
	}

}

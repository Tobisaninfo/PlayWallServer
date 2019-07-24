package de.tobias.playpad.server.server.project

import java.sql.Connection

import com.google.gson.{JsonArray, JsonObject}
import com.j256.ormlite.dao.Dao
import de.tobias.playpad.server.account.Session
import de.tobias.playpad.server.project.loader.sql.ProjectLoader
import de.tobias.playpad.server.server.{Result, Status}
import spark.{Request, Response, Route}

/**
 * Created by tobias on 01.03.17.
 */
class ProjectModificationGet(connection: Connection, sessionDao: Dao[Session, Int]) extends Route {

	override def handle(request: Request, response: Response): AnyRef = {
		val sessionKey = request.queryParams("session")

		val session = Session.getSession(sessionKey, sessionDao)

		session match {
			case Some(s) =>
				val projectLoader = new ProjectLoader(connection)
				val projects = projectLoader.list(s.getAccount.id)

				val array = new JsonArray()
				projects.foreach(projectReference => {
					val json = new JsonObject()
					json.addProperty("uuid", projectReference.id.toString)
					json.addProperty("name", projectReference.name)
					json.addProperty("last_modification", projectReference.lastModified)
					json.addProperty("session", projectReference.session)
					array.add(json)
				})

				array
			case None =>
				new Result(Status.ERROR, "Session invalid")
		}
	}
}

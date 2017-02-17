package de.tobias.playpad.server.server.project

import java.sql.Connection
import java.util.UUID

import com.google.gson.{JsonObject, JsonParser}
import com.j256.ormlite.dao.Dao
import de.tobias.playpad.server.account
import de.tobias.playpad.server.account.Account
import de.tobias.playpad.server.server.SqlHelper
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.{OnWebSocketClose, OnWebSocketConnect, OnWebSocketMessage, WebSocket}

import scala.collection.{Map, mutable}

/**
  * Created by tobias on 13.02.17.
  */
@WebSocket class ProjectHandler(sessionDao: Dao[account.Session, Int], connection: Connection) {

	// TODO mutable.HashSet --> Set
	private var sessions: Map[Account, mutable.HashSet[Session]] = new mutable.HashMap[Account, mutable.HashSet[Session]]()

	@OnWebSocketConnect def onConnect(serverSession: Session): Unit = {
		val key = serverSession.getUpgradeRequest.getHeader("key")
		if (key == null) {
			serverSession.close(500, "Invalid Key")
		}

		val sessions = sessionDao.queryForEq("key", key)
		if (sessions.size() == 1) {
			val session = sessions.get(0)
			if (!this.sessions.contains(session.getAccount)) {
				this.sessions += (session.getAccount -> new mutable.HashSet[Session]())
			}
			this.sessions(session.getAccount) += serverSession
		} else {
			serverSession.close(500, "Invalid Key")
		}
	}

	@OnWebSocketClose def onClose(serverSession: Session, status: Int, reason: String): Unit = {
		val key = serverSession.getUpgradeRequest.getHeader("key")
		if (key == null) {
			serverSession.close(500, "Invalid Key")
		}

		val sessions = sessionDao.queryForEq("key", key)
		if (sessions.size() == 1) {
			val session = sessions.get(0)
			this.sessions(session.getAccount) -= serverSession
		}
	}

	@OnWebSocketMessage def onMessage(serverSession: Session, text: String): Unit = {
		// Store in Database
		try {
			val json = new JsonParser().parse(text)
			json match {
				case jsonObject: JsonObject =>
					val className = jsonObject.get("class").getAsString
					val field = jsonObject.get("field").getAsString
					val id = jsonObject.get("id").getAsString

					val valueType = Class.forName(jsonObject.get("type").getAsString)

					if (valueType == classOf[String]) {
						val value = jsonObject.get("value").getAsString

						if (id.matches("[0-9]*")) {
							val idInt = id.toInt
							SqlHelper.insertOrUpdate(connection, className, idInt, field, value)
						} else {
							val uuid = UUID.fromString(id)
							SqlHelper.insertOrUpdate(connection, className, uuid, field, value)
						}
					} else if (valueType == classOf[Boolean]) {
						val value = jsonObject.get("value").getAsBoolean

						if (id.matches("[0-9]*")) {
							val idInt = id.toInt
							SqlHelper.insertOrUpdate(connection, className, idInt, field, value)
						} else {
							val uuid = UUID.fromString(id)
							SqlHelper.insertOrUpdate(connection, className, uuid, field, value)
						}
					} else if (valueType == classOf[Integer]){
						val value = jsonObject.get("value").getAsInt

						if (id.matches("[0-9]*")) {
							val idInt = id.toInt
							SqlHelper.insertOrUpdate(connection, className, idInt, field, value)
						} else {
							val uuid = UUID.fromString(id)
							SqlHelper.insertOrUpdate(connection, className, uuid, field, value)
						}
					} else if (valueType == classOf[Double]) {
						val value = jsonObject.get("value").getAsDouble

						if (id.matches("[0-9]*")) {
							val idInt = id.toInt
							SqlHelper.insertOrUpdate(connection, className, idInt, field, value)
						} else {
							val uuid = UUID.fromString(id)
							SqlHelper.insertOrUpdate(connection, className, uuid, field, value)
						}
					}

				case _ =>
			}

			// Push to clients
			val key = serverSession.getUpgradeRequest.getHeader("key")
			if (key == null) {
				serverSession.close(500, "Invalid Key")
			}

			val sessions = sessionDao.queryForEq("key", key)
			if (sessions.size() == 1) {
				val session = sessions.get(0)
				this.sessions(session.getAccount)
					.filter(s => s != serverSession)
					.foreach(s => s.getRemote.sendStringByFuture(text))
			}
		} catch {
			case e: Exception => e.printStackTrace()
		}
	}
}
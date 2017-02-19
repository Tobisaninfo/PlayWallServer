package de.tobias.playpad.server.server.project.sync

import java.sql.Connection

import com.google.gson.{JsonObject, JsonParser}
import com.j256.ormlite.dao.Dao
import de.tobias.playpad.server.account
import de.tobias.playpad.server.account.Account
import de.tobias.playpad.server.server.project.sync.listener.project.{ProjectAddListener, ProjectRemoveListener, ProjectUpdateListener}
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.{OnWebSocketClose, OnWebSocketConnect, OnWebSocketMessage, WebSocket}

import scala.collection.{Map, mutable}

/**
  * Created by tobias on 19.02.17.
  */
@WebSocket class ProjectSyncHandler(sessionDao: Dao[account.Session, Int], connection: Connection) {

	// TODO mutable.HashSet --> Set
	private var sessions: Map[Account, mutable.HashSet[Session]] = new mutable.HashMap[Account, mutable.HashSet[Session]]()

	private val listeners = Map(
		"pro-add" -> new ProjectAddListener(),
		"pro-update" -> new ProjectUpdateListener(),
		"pro-rm" -> new ProjectRemoveListener()
	)

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
		println(text)
		// Store in Database
		try {
			// Push to clients
			val key = serverSession.getUpgradeRequest.getHeader("key")
			if (key != null) {

				val parser = new JsonParser()
				val json = parser.parse(text)
				json match {
					case json: JsonObject =>
						val session = account.Session.getSession(key, sessionDao)
						session match {
							case Some(s) => listeners(json.get("cmd").getAsString).onChange(json, connection, s)
							case None => serverSession.close(500, "Invalid Session")
						}
					case _ => serverSession.close(500, "Invalid Data")
				}
			} else {
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

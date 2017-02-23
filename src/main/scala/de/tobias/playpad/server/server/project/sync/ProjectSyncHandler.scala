package de.tobias.playpad.server.server.project.sync

import java.sql.Connection

import com.google.gson.{JsonObject, JsonParser}
import com.j256.ormlite.dao.Dao
import de.tobias.playpad.server.account
import de.tobias.playpad.server.account.Account
import de.tobias.playpad.server.server.project.sync.listener.design.{DesignAddListener, DesignRemoveListener, DesignUpdateListener}
import de.tobias.playpad.server.server.project.sync.listener.pad.{PadAddListener, PadClearListener, PadRemoveListener, PadUpdateListener}
import de.tobias.playpad.server.server.project.sync.listener.page.{PageAddListener, PageRemoveListener, PageUpdateListener}
import de.tobias.playpad.server.server.project.sync.listener.path.{PathAddListener, PathRemoveListener, PathUpdateListener}
import de.tobias.playpad.server.server.project.sync.listener.project.{ProjectAddListener, ProjectRemoveListener, ProjectUpdateListener}
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.{OnWebSocketClose, OnWebSocketConnect, OnWebSocketMessage, WebSocket}

import scala.collection.{Map, mutable}

/**
  * Created by tobias on 19.02.17.
  */
@WebSocket class ProjectSyncHandler(sessionDao: Dao[account.Session, Int], connection: Connection) {

	val SESSION_KEY_HEADER = "key"

	// TODO mutable.HashSet --> Set
	private var sessions: Map[Account, mutable.HashSet[Session]] = new mutable.HashMap[Account, mutable.HashSet[Session]]()

	private val listeners = Map(
		"pro-add" -> new ProjectAddListener(),
		"pro-update" -> new ProjectUpdateListener(),
		"pro-rm" -> new ProjectRemoveListener(),

		"page-add" -> new PageAddListener(),
		"page-update" -> new PageUpdateListener(),
		"page-rm" -> new PageRemoveListener(),

		"pad-add" -> new PadAddListener(),
		"pad-update" -> new PadUpdateListener(),
		"pad-rm" -> new PadRemoveListener(),
		"pad-clear" -> new PadClearListener(),

		"path-add" -> new PathAddListener(),
		"path-update" -> new PathUpdateListener(),
		"path-rm" -> new PathRemoveListener(),

		"design-add" -> new DesignAddListener(),
		"design-update" -> new DesignUpdateListener(),
		"design-rm" -> new DesignRemoveListener()
	)

	@OnWebSocketConnect def onConnect(serverSession: Session): Unit = {
		val key = serverSession.getUpgradeRequest.getHeader(SESSION_KEY_HEADER)
		if (key == null) {
			serverSession.close(500, "Invalid Key")
		}

		val session = account.Session.getSession(key, sessionDao)
		session match {
			case Some(s) =>
				if (!this.sessions.contains(s.getAccount)) {
					this.sessions += (s.getAccount -> new mutable.HashSet[Session]())
				}
				this.sessions(s.getAccount) += serverSession
			case None => serverSession.close(500, "Invalid Key")
		}
	}

	@OnWebSocketClose def onClose(serverSession: Session, status: Int, reason: String): Unit = {
		val key = serverSession.getUpgradeRequest.getHeader(SESSION_KEY_HEADER)
		if (key == null) {
			serverSession.close(500, "Invalid Key")
		}

		val session = account.Session.getSession(key, sessionDao)
		session match {
			case Some(s) => this.sessions(s.getAccount) -= serverSession
			case None => serverSession.close(500, "Invalid Key")
		}
	}

	@OnWebSocketMessage def onMessage(serverSession: Session, text: String): Unit = {
		println(text)
		// Store in Database
		try {
			// Push to clients
			val key = serverSession.getUpgradeRequest.getHeader(SESSION_KEY_HEADER)
			if (key != null) {

				val parser = new JsonParser()
				val json = parser.parse(text)
				json match {
					case json: JsonObject =>
						val session = account.Session.getSession(key, sessionDao)
						session match {
							case Some(s) =>
								val cmd = json.get("cmd").getAsString
								if (listeners.contains(cmd)) {
									listeners(cmd).onChange(json, connection, s)
								}
							case None => serverSession.close(500, "Invalid Session")
						}
					case _ => serverSession.close(500, "Invalid Data")
				}
			} else {
				serverSession.close(500, "Invalid Key")
			}

			val session = account.Session.getSession(key, sessionDao)
			session match {
				case Some(s) =>
					this.sessions(s.getAccount)
						.filter(s => s != serverSession)
						.foreach(s => s.getRemote.sendStringByFuture(text))
				case None => serverSession.close(500, "Invalid Key")
			}
		} catch {
			case e: Exception => e.printStackTrace()
		}
	}
}

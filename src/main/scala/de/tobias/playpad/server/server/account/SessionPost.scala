package de.tobias.playpad.server.server.account

import com.j256.ormlite.dao.Dao
import de.tobias.playpad.server.account.{Account, Session}
import de.tobias.playpad.server.server.{Result, Status}
import spark.{Request, Response, Route}

/**
 * Created by tobias on 15.02.17.
 */
class SessionPost(accountDao: Dao[Account, Int]) extends Route {

	override def handle(request: Request, response: Response): AnyRef = {
		val username = request.queryParams("username")
		val password = request.queryParams("password")

		val account = Account.getAccount(username, accountDao)

		account match {
			case Some(a) =>
				if (a.password.equals(password)) {

					val randomKey = Session.generateKey()
					val session = new Session(a, randomKey)

					a.sessions.add(session)
					accountDao.update(a)
					return new SessionPostResult(Status.OK, randomKey)
				}
				new Result(Status.ERROR, "Password invalid")
			case None =>
				new Result(Status.ERROR, "Account invalid")
		}
	}

	private class SessionPostResult {
		var status: String = _
		var message: String = _
		var key: String = _

		def this(status: Status.Value, key: String, message: String = "") {
			this()
			this.status = status.toString
			this.message = message
			this.key = key
		}
	}

}

package de.tobias.playpad.server.server.account

import com.j256.ormlite.dao.Dao
import de.tobias.playpad.server.account.Account
import de.tobias.playpad.server.server.{Result, Status}
import spark.{Request, Response, Route}

/**
 * Created by tobias on 15.02.17.
 */
class SessionDelete(accountDao: Dao[Account, Int]) extends Route {

	override def handle(request: Request, response: Response): AnyRef = {
		val username = request.queryParams("username")
		val password = request.queryParams("password")
		val key = request.queryParams("key")

		val account = Account.getAccount(username, accountDao)

		account match {
			case Some(a) =>
				if (a.password.equals(password)) {
					a.sessions.removeIf(s => s.key.equals(key))
					accountDao.update(a)
					return new Result(Status.OK, "deleted")
				}
				new Result(Status.ERROR, "Password invalid")
			case None =>
				new Result(Status.ERROR, "Account invalid")

		}
	}

}

package de.tobias.playpad.server.server.account

import com.j256.ormlite.dao.Dao
import de.tobias.playpad.server.account.{Account, Session}
import spark.{Request, Response, Route}

/**
  * Created by tobias on 15.02.17.
  */
class SessionPost(accountDao: Dao[Account, Int]) extends Route {

	override def handle(request: Request, response: Response): AnyRef = {
		val username = request.queryParams("username")
		val password = request.queryParams("password")

		// check account
		val accounts = accountDao.queryForEq("username", username)
		if (accounts.size() == 1) {
			val account = accounts.get(0)
			if (account.password.equals(password)) {

				val randomKey = Session.generateKey()
				val session = new Session(account, randomKey)

				account.sessions.add(session)
				accountDao.update(account)
				return randomKey
			}
		}

		null
	}

}

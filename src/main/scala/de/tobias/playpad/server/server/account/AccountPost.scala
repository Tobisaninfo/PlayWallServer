package de.tobias.playpad.server.server.account

import java.sql.SQLException

import com.j256.ormlite.dao.Dao
import de.tobias.playpad.server.account.Account
import spark.{Request, Response, Route, Spark}

/**
  * Created by tobias on 15.02.17.
  */
class AccountPost(accountDao: Dao[Account, Int]) extends Route {

	override def handle(request: Request, response: Response): AnyRef = {
		val username = request.queryMap().get("username").value
		val password = request.queryMap().get("password").value

		val account = new Account(username, password)
		try {
			accountDao.create(account)
		} catch {
			case e: SQLException => Spark.halt(409, "username not allowed")
		}

		"created"
	}

}

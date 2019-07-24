package de.tobias.playpad.server.server.account

import com.google.gson.{JsonArray, JsonObject}
import com.j256.ormlite.dao.Dao
import de.tobias.playpad.server.account.Account
import de.tobias.playpad.server.server.{Result, Status}
import spark.{Request, Response, Route}

/**
 * Created by tobias on 15.02.17.
 */
class SessionGet(accountDao: Dao[Account, Int]) extends Route {

	override def handle(request: Request, response: Response): AnyRef = {
		val username = request.queryParams("username")
		val password = request.queryParams("password")

		val account = Account.getAccount(username, accountDao)

		account match {
			case Some(a) =>
				if (a.password.equals(password)) {
					val array = new JsonArray
					a.sessions.forEach(session => {
						val jsonObj = new JsonObject
						jsonObj.addProperty("key", session.key)
						jsonObj.addProperty("createDate", session.createDate.getTime)
						array.add(jsonObj)
					})
					return array
				}
				new Result(Status.ERROR, "Password invalid")
			case None =>
				new Result(Status.ERROR, "Account invalid")
		}
	}
}

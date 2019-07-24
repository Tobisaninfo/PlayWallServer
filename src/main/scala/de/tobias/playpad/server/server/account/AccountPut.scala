package de.tobias.playpad.server.server.account

import com.j256.ormlite.dao.Dao
import de.tobias.playpad.server.account.Account
import de.tobias.playpad.server.server.{Result, Status}
import spark.{Request, Response, Route}

/**
 * Created by tobias on 15.02.17.
 */
class AccountPut(accountDao: Dao[Account, Int]) extends Route {

	override def handle(request: Request, response: Response): AnyRef = {
		try {
			print(request.queryParams())
			val username = request.queryMap().get("username").value
			val oldPassword = request.queryMap().get("old_password").value
			val newPassword = request.queryMap().get("new_password").value

			val account = Account.getAccount(username, accountDao)
			account match {
				case Some(a) =>
					if (a.password.equals(oldPassword)) {
						a.password = newPassword
						accountDao.update(a)
						return new Result(Status.OK)
					}
					new Result(Status.ERROR, "Password invalid")
				case None =>
					new Result(Status.ERROR, "Account invalid")
			}
		} catch {
			case e: SQLException => return new Result(Status.ERROR)
		}

		new Result(Status.ERROR)
	}

}

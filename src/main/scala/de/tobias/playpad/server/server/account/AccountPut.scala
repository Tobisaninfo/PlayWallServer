package de.tobias.playpad.server.server.account

import com.j256.ormlite.dao.Dao
import de.tobias.playpad.server.account.Account
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

			val accounts = accountDao.queryForEq("username", username)

			if (accounts.size() == 1) {
				val account = accounts.get(0)
				if (account.password.equals(oldPassword)) {
					account.password = newPassword
					accountDao.update(account)
				}
			}
		} catch {
			case e: Exception => e.printStackTrace()
		}

		""
	}

}

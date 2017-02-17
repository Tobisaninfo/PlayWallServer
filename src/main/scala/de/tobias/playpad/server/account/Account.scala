package de.tobias.playpad.server.account

import com.j256.ormlite.dao.{Dao, ForeignCollection}
import com.j256.ormlite.field.{DatabaseField, ForeignCollectionField}
import com.j256.ormlite.table.DatabaseTable

/**
  * Created by tobias on 15.02.17.
  */
@DatabaseTable(tableName = "Account") class Account() {

	@DatabaseField(generatedId = true) val id: Int = 0
	@DatabaseField(unique = true) var username: String = _ // Mail Address
	@DatabaseField() var password: String = _
	@ForeignCollectionField var sessions: ForeignCollection[Session] = _

	def this(username: String, password: String) {
		this()
		this.username = username
		this.password = password
	}

	def getId: Int = id


	def canEqual(other: Any): Boolean = other.isInstanceOf[Account]

	override def equals(other: Any): Boolean = other match {
		case that: Account =>
			(that canEqual this) &&
				id == that.id
		case _ => false
	}

	override def hashCode(): Int = {
		val state = Seq(id)
		state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
	}

}

object Account {
	def getAccount(username: String, accountDao: Dao[Account, Int]): Option[Account] = {
		val accountList = accountDao.queryForEq("username", username)
		if (accountList.size() == 1) {
			return Some(accountList.get(0))
		}
		None
	}
}



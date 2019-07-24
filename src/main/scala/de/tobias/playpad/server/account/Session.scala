package de.tobias.playpad.server.account

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

import scala.util.Random

/**
 * Created by tobias on 15.02.17.
 */
@DatabaseTable(tableName = "Session") class Session() {

	@DatabaseField(generatedId = true) private val id: Int = 0
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true) private var account: Account = _
	@DatabaseField var key: String = _
	@DatabaseField var createDate: Date = _

	def this(account: Account, key: String) {
		this()
		this.account = account
		this.key = key
		this.createDate = new Date(System.currentTimeMillis)
	}

	def getId: Int = id

	def getAccount: Account = account


	def canEqual(other: Any): Boolean = other.isInstanceOf[Session]

	override def equals(other: Any): Boolean = other match {
		case that: Session =>
			(that canEqual this) &&
				id == that.id &&
				key == that.key
		case _ => false
	}

	override def hashCode(): Int = {
		val state = Seq(id, key)
		state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
	}
}

object Session {
	private val length = 100

	val alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

	def generateKey(): String = (1 to length).map(_ => alpha(Random.nextInt.abs % alpha.length())).mkString

	def getSession(sessionKey: String, sessionDao: Dao[Session, Int]): Option[Session] = {
		val sessionList = sessionDao.queryForEq("key", sessionKey)
		if (sessionList.size() == 1) {
			return Some(sessionList.get(0))
		}
		None
	}
}

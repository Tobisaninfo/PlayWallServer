package de.tobias.playpad.server.account

import java.sql.Date

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
}

object Session {
	private val length = 100

	val alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
	def generateKey(): String = (1 to length).map(_ => alpha(Random.nextInt.abs % alpha.length())).mkString
}

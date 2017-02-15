package de.tobias.playpad.server.account

import java.sql.Date

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
  * Created by tobias on 15.02.17.
  */
@DatabaseTable(tableName = "Session") class Session() {

	@DatabaseField(generatedId = true) private val id: Int = 0
	@DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true) private var account: Account = _
	@DatabaseField var key: String = _
	@DatabaseField var createDate: Date = _

	def this(key: String, createDate: Date) {
		this()
		this.key = key
		this.createDate = createDate
	}

	def getId: Int = id
}

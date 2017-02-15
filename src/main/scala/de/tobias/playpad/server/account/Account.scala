package de.tobias.playpad.server.account

import com.j256.ormlite.dao.ForeignCollection
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
}

package de.tobias.playpad.server.plugin

import com.j256.ormlite.dao.ForeignCollection
import com.j256.ormlite.field.{DatabaseField, ForeignCollectionField}
import com.j256.ormlite.table.DatabaseTable
import com.sun.xml.internal.xsom.impl.ForeignAttributesImpl

/**
  * Created by tobias on 31.01.17.
  */
@DatabaseTable(tableName = "Plugin") class Plugin() {
	@DatabaseField(generatedId = true) private val id: Int = 0
	@DatabaseField var name: String = _
	@DatabaseField var description: String = _
	@DatabaseField var version: String = _
	@DatabaseField var build: Int = _

	def this(name: String, description: String, version: String, build: Int) {
		this()
		this.name = name
		this.description = description
		this.version = version
		this.build = build
	}

	def getId: Int = id
}
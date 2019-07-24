package de.tobias.playpad.server.plugin

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 * Created by tobias on 31.01.17.
 */
@DatabaseTable(tableName = "Plugin") class Plugin() {
	@DatabaseField(generatedId = true) private val id: Int = 0
	@DatabaseField var name: String = _
	@DatabaseField var displayName: String = _
	@DatabaseField var description: String = _
	@DatabaseField var path: String = _
	@DatabaseField var version: String = _
	@DatabaseField var build: Int = _
	@DatabaseField var icon: String = _
	@DatabaseField var category: String = _

	def this(name: String, displayName: String, path: String, description: String, version: String, build: Int, icon: String, category: String) {
		this()
		this.name = name
		this.displayName = displayName
		this.path = path
		this.description = description
		this.version = version
		this.build = build
		this.icon = icon
		this.category = category
	}

	def getId: Int = id
}
package de.tobias.playpad.server.project

import java.util.UUID

/**
  * Created by tobias on 19.02.17.
  */
class ProjectReference {

	var id: UUID = UUID.randomUUID()
	var name: String = _

	var accountId: Int = _ // Account that own this project
	var lastModified: Long = _
	var session: String = _

}

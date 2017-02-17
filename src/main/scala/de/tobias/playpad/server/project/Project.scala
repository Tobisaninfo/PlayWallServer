package de.tobias.playpad.server.project

import java.util.UUID

/**
  * Created by tobias on 17.02.17.
  */
class Project {

	var id: UUID = UUID.randomUUID()
	var name: String = _

	var accountId: Int = _ // Account that own this project

	var pages: List[Page] = _

}

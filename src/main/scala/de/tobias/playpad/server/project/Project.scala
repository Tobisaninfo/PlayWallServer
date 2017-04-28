package de.tobias.playpad.server.project

import java.util.UUID

import de.tobias.playpad.server.account.Session

/**
  * Created by tobias on 17.02.17.
  */
class Project {

	val projectReference = new ProjectReference

	var pages: List[Page] = _

}

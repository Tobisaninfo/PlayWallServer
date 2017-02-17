package de.tobias.playpad.server.project

import java.util.UUID

/**
  * Created by tobias on 17.02.17.
  */
class Page {

	var id: UUID = UUID.randomUUID()
	var name: String = _

	var position: Int = _

	var pads: List[Pad] = List()
	var project: Project = _ // FOREIGN KEY
}

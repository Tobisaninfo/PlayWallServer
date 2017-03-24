package de.tobias.playpad.server.project

import java.util.UUID

/**
  * Created by tobias on 20.02.17.
  */
class Path {

	var id: UUID = UUID.randomUUID()
	var filename: String = _

	var pad: Pad = _
}

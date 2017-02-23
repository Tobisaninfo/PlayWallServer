package de.tobias.playpad.server.project

import java.util.UUID

/**
  * Created by tobias on 23.02.17.
  */
class Design {

	var id: UUID = UUID.randomUUID()
	var backgroundColor: String = _
	var playColor: String = _

	var pad: Pad = _
}

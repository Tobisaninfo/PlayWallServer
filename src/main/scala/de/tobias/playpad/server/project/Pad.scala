package de.tobias.playpad.server.project

import java.util.UUID

/**
  * Created by tobias on 17.02.17.
  */
class Pad() {

	var id: UUID = UUID.randomUUID()
	var name: String = _
	var position: Int = _

	var contentType: String = _
	var paths: List[Path] = List()

	var design: Design = _

	var page: Page = _
}

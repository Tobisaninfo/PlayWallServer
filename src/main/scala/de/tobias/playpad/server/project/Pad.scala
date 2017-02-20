package de.tobias.playpad.server.project

import java.util.UUID

/**
  * Created by tobias on 17.02.17.
  */
class Pad() {

	var id: UUID = UUID.randomUUID()
	var name: String = _

	var position: Int = _
	var pageIndex: Int = _

	var paths: List[Path] = List()

	var page: Page = _
}

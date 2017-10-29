package de.tobias.playpad.server.project

import java.util.UUID

import de.tobias.playpad.server.json._
import de.tobias.playpad.server.project.settings.PadSettings

/**
  * Created by tobias on 17.02.17.
  */
class Pad() {

	@JsonName(value = "id", handler = classOf[UUIDSerializerHandler])
	var id: UUID = UUID.randomUUID()

	@JsonName("name")
	var name: String = _
	@JsonName(value = "position", handler = classOf[IntSerializerHandler])
	var position: Int = _

	@JsonName("contentType")
	var contentType: String = _
	@JsonCollection(value = "paths", `type` = classOf[Path])
	var paths: List[Path] = List()

	@JsonObj("settings")
	var padSettings: PadSettings = _

	@JsonParent
	var page: Page = _
}

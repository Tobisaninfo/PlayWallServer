package de.tobias.playpad.server.project

import java.util.UUID

import de.tobias.playpad.server.json._

/**
  * Created by tobias on 17.02.17.
  */
class Page {

	@JsonName(value = "id", handler = classOf[UUIDSerializerHandler])
	var id: UUID = UUID.randomUUID()
	@JsonName("name")
	var name: String = _

	@JsonName(value = "position", handler = classOf[NumberSerializerHandler])
	var position: Int = _

	@JsonCollection(value = "pads", `type` = classOf[Pad])
	var pads: List[Pad] = List()

	@JsonParent
	var project: Project = _ // FOREIGN KEY
}

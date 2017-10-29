package de.tobias.playpad.server.project

import java.util.UUID

import de.tobias.playpad.server.json.{JsonName, JsonParent, UUIDSerializerHandler}

/**
  * Created by tobias on 20.02.17.
  */
class Path {

	@JsonName(value = "id", handler = classOf[UUIDSerializerHandler])
	var id: UUID = UUID.randomUUID()
	@JsonName("filename")
	var filename: String = _

	@JsonParent
	var pad: Pad = _
}

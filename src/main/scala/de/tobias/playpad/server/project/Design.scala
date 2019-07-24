package de.tobias.playpad.server.project

import de.tobias.playpad.server.json.{JsonName, JsonParent, UUIDSerializerHandler}

/**
 * Created by tobias on 23.02.17.
 */
class Design {

	@JsonName(value = "id", handler = classOf[UUIDSerializerHandler])
	var id: UUID = UUID.randomUUID()
	@JsonName("background_color")
	var backgroundColor: String = _
	@JsonName("play_color")
	var playColor: String = _

	@JsonParent
	var pad: Pad = _
}

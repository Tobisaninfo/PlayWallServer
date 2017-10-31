package de.tobias.playpad.server.project.settings

import java.util.UUID
import javafx.util.Duration

import de.tobias.playpad.server.json._
import de.tobias.playpad.server.project.{Design, Pad}

class PadSettings {

	@JsonName(value = "id", handler = classOf[UUIDSerializerHandler])
	var id: UUID = UUID.randomUUID()

	@JsonName(value = "volume", handler = classOf[DoubleSerializerHandler])
	var volume: Double = _
	@JsonName(value = "loop", handler = classOf[BooleanSerializerHandler])
	var loop: Boolean = _

	@JsonName("timeMode")
	var timeMode: String = _ // In PlayWallDesktop ENUM

	@JsonObj("fade")
	var fade: Fade = _
	@JsonName(value = "warning", handler = classOf[DurationSerializerHandler])
	var warning: Duration = _

	@JsonObj("design")
	var design: Design = _

	@JsonParent
	var pad: Pad = _
}

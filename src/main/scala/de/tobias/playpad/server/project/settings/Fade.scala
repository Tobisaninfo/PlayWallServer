package de.tobias.playpad.server.project.settings

import java.util.UUID
import javafx.util.Duration

import de.tobias.playpad.server.json._

class Fade {

	@JsonName(value = "id", handler = classOf[UUIDSerializerHandler])
	var id: UUID = UUID.randomUUID()

	@JsonName(value = "fadeIn", handler = classOf[DurationSerializerHandler])
	var fadeIn: Duration = _
	@JsonName(value = "fadeOut", handler = classOf[DurationSerializerHandler])
	var fadeOut: Duration = _

	@JsonName(value = "fadeInStart", handler = classOf[BooleanSerializerHandler])
	var fadeInStart: Boolean = _
	@JsonName(value = "fadeInPause", handler = classOf[BooleanSerializerHandler])
	var fadeInPause: Boolean = _
	@JsonName(value = "fadeOutPause", handler = classOf[BooleanSerializerHandler])
	var fadeOutPause: Boolean = _
	@JsonName(value = "fadeOutStop", handler = classOf[BooleanSerializerHandler])
	var fadeOutStop: Boolean = _

	@JsonParent
	var padSettings: PadSettings = _
}
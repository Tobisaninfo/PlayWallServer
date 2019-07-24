package de.tobias.playpad.server.project

import java.util.UUID

import de.tobias.playpad.server.json._
import de.tobias.playpad.server.project.settings.PadSettings
import de.tobias.playpad.server.sql.{ColumnName, ForeignKey, Id, Table}

/**
  * Created by tobias on 17.02.17.
  */
@Table("Pad")
class Pad() {

	@JsonName(value = "id", handler = classOf[UUIDSerializerHandler])
	@Id
	@ColumnName("id")
	var id: UUID = UUID.randomUUID()

	@JsonName("name")
	@ColumnName("name")
	var name: String = _
	@JsonName(value = "position", handler = classOf[IntSerializerHandler])
	@ColumnName("position")
	var position: Int = _

	@JsonName("contentType")
	@ColumnName("content_type")
	var contentType: String = _
	@JsonCollection(value = "paths", `type` = classOf[Path])
	var paths: List[Path] = List()

	@JsonObj("settings")
	var padSettings: PadSettings = _

	@JsonParent
	@ForeignKey
	var page: Page = _


	override def toString = s"Pad($id, $name, $position, $contentType, $paths, $padSettings, $page)"
}

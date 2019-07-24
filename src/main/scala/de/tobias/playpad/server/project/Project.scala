package de.tobias.playpad.server.project

import java.util.UUID

import de.tobias.playpad.server.json.{JsonCollection, JsonName, UUIDSerializerHandler}

/**
 * Created by tobias on 17.02.17.
 */
class Project {

	@JsonName(value = "id", handler = classOf[UUIDSerializerHandler])
	var id: UUID = UUID.randomUUID()
	@JsonName("name")
	var name: String = _

	var accountId: Int = _ // Account that own this project
	var lastModified: Long = _
	var session: String = _

	@JsonCollection(value = "pages", `type` = classOf[Page])
	var pages: List[Page] = List()

}

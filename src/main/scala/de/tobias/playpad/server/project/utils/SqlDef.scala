package de.tobias.playpad.server.project.utils

/**
  * Created by tobias on 17.02.17.
  */
object SqlDef {

	val PROJECT = "Project"
	val PROJECT_ID = "id"
	val PROJECT_NAME = "name"
	val PROJECT_ACCOUNT_ID = "account_id"

	val PAGE = "Page"
	val PAGE_ID = "id"
	val PAGE_NAME = "name"
	val PAGE_POSITION = "position"
	val PAGE_PROJECT_REF = "project_id"

	val PAD = "Pad"
	val PAD_ID = "id"
	val PAD_NAME = "name"
	val PAD_POSITION = "position"
	val PAD_CONTENT_TYPE = "content_type"
	val PAD_PAGE_REF = "page_id"

	val PATH = "Path"
	val PATH_ID = "id"
	val PATH_NAME = "path"
	val PATH_PAD = "pad_id"

}

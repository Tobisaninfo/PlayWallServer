package de.tobias.playpad.server.project.utils

/**
 * Created by tobias on 17.02.17.
 */
object SqlDef {

	val PROJECT = "Project"
	val PROJECT_ID = "id"
	val PROJECT_NAME = "name"
	val PROJECT_ACCOUNT_ID = "account_id"
	val PROJECT_LAST_MODIFIED = "last_modified"
	val PROJECT_SESSION_KEY = "session_key"

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
	val PATH_NAME = "filename"
	val PATH_PAD_REF = "pad_id"

	val DESIGN = "Design"
	val DESIGN_ID = "id"
	val DESIGN_PAD_REF = "settings_id"
	val DESIGN_BACKGROUND_COLOR = "background_color"
	val DESIGN_PLAY_COLOR = "play_color"

	val PAD_SETTINGS = "PadSettings"
	val PAD_SETTINGS_PAD_REF = "pad_id"
	val PAD_SETTINGS_VOLUME = "volume"
	val PAD_SETTINGS_LOOP = "looping"
	val PAD_SETTINGS_TIME_MODE = "timemode"
	val PAD_SETTINGS_WARNING = "warning"
}

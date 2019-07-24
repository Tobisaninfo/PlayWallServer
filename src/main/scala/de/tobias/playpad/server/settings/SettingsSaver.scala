package de.tobias.playpad.server.settings

/**
 * Created by tobias on 05.02.17.
 */
trait SettingsSaver {

	@throws[IOException]
	def save(settings: Settings, path: Path)

	def default(path: Path): Unit = save(new Settings(), path)
}

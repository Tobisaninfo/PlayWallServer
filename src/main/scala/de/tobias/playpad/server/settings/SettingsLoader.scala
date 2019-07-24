package de.tobias.playpad.server.settings

/**
 * Created by tobias on 05.02.17.
 */
trait SettingsLoader {

	@throws[IOException]
	def load(path: Path): Settings
}

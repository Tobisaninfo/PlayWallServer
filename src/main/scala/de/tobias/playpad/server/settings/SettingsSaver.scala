package de.tobias.playpad.server.settings

import java.io.IOException
import java.nio.file.Path

/**
  * Created by tobias on 05.02.17.
  */
trait SettingsSaver {

	@throws[IOException]
	def save(settings: Settings, path: Path)

	def default(path: Path) = save(new Settings(), path)
}

package de.tobias.playpad.server.settings

/**
  * Created by tobias on 05.02.17.
  */
object SettingsHandler {

	def loader = new PropertiesSettingsHandler()

	def saver = new PropertiesSettingsHandler()

}

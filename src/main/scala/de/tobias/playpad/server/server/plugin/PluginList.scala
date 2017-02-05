package de.tobias.playpad.server.server.plugin

import com.google.gson.Gson
import com.j256.ormlite.dao.Dao
import de.tobias.playpad.server.plugin.Plugin
import spark.{Request, Response, Route}

/**
  * Created by tobias on 05.02.17.
  */
class PluginList(val dao: Dao[Plugin, Int]) extends Route {

	val gson = new Gson()

	override def handle(request: Request, response: Response): AnyRef = {
		val plugins = dao.queryForAll()
		plugins
	}
}

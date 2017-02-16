package de.tobias.playpad.server.server.plugin

import com.google.gson.Gson
import com.j256.ormlite.dao.Dao
import de.tobias.playpad.server.plugin.Plugin
import de.tobias.playpad.server.server.{Result, Status}
import spark.{Request, Response, Route, Spark}

/**
  * Created by tobias on 05.02.17.
  */
class PluginGet(dao: Dao[Plugin, Int]) extends Route {

	val gson = new Gson()

	override def handle(request: Request, response: Response): AnyRef = {
		val plugin = dao.queryForId(request.params(":id").toInt)
		if (plugin == null) {
			return new Result(Status.ERROR, "Bad request")
		}
		plugin
	}
}

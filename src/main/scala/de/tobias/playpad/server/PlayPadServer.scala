package de.tobias.playpad.server

import java.nio.file.{Files, Paths}

import com.j256.ormlite.dao.{Dao, DaoManager}
import com.j256.ormlite.jdbc.JdbcConnectionSource
import com.j256.ormlite.table.TableUtils
import de.tobias.playpad.server.plugin.Plugin
import de.tobias.playpad.server.server.plugin.{PluginGet, PluginList}
import de.tobias.playpad.server.settings.SettingsHandler
import de.tobias.playpad.server.transformer.JsonTransformer
import spark.Spark._
import spark.route.RouteOverview

/**
  * Created by tobias on 29.01.17.
  */
object PlayPadServer extends App {

	// Load Config
	private val settingsLoader = SettingsHandler.loader
	private val settingsPath = Paths.get("settings.properties")

	if (Files.notExists(settingsPath)) {
		SettingsHandler.saver.default(settingsPath)
	}

	private val settings = settingsLoader.load(settingsPath)

	private val databaseUrl = "jdbc:mysql://" + settings.db_host + ":" + settings.db_port + "/" + settings.db_database
	var connectionSource = new JdbcConnectionSource(databaseUrl)
	connectionSource.setUsername(settings.db_username)
	connectionSource.setPassword(settings.db_password)

	val dao: Dao[Plugin, Int] = DaoManager.createDao(connectionSource, classOf[Plugin])
	TableUtils.createTableIfNotExists(connectionSource, classOf[Plugin])

	// Setup Http Server
	port(8090)

	private val externalPath = Paths.get(settings.download_folder).toAbsolutePath.toString
	externalStaticFileLocation(externalPath)

	secure("deploy/keystore.jks", settings.keystorePassword, null, null)

	get("/plugins/:id", new PluginGet(dao), new JsonTransformer)
	get("/plugins", new PluginList(dao), new JsonTransformer)

	RouteOverview.enableRouteOverview()

	SettingsHandler.saver.save(settings, settingsPath)
}

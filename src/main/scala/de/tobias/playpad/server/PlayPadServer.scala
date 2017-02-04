package de.tobias.playpad.server

import java.nio.file.{Files, Paths}
import java.util.Properties

import com.google.gson.Gson
import com.j256.ormlite.dao.{Dao, DaoManager}
import com.j256.ormlite.jdbc.JdbcConnectionSource
import com.j256.ormlite.table.TableUtils
import de.tobias.playpad.server.plugin.Plugin
import spark.Spark
import spark.Spark._
import spark.route.RouteOverview

/**
  * Created by tobias on 29.01.17.
  */
object PlayPadServer extends App {

	// Load Config
	val properties = new Properties()
	private val path = Paths.get("settings.properties")
	if (Files.notExists(path)) {
		val loader = Thread.currentThread.getContextClassLoader
		val input = loader.getResourceAsStream("settings/settings.properties")
		Files.copy(input, path)
	}
	properties.load(Files.newBufferedReader(path))

	// Setup Database
	private val host = properties.getProperty("host")
	private val port = properties.getProperty("port")
	private val username = properties.getProperty("username")
	private val password = properties.getProperty("password")
	private val database = properties.getProperty("database")

	private val databaseUrl = "jdbc:mysql://" + host + ":" + port + "/" + database
	var connectionSource = new JdbcConnectionSource(databaseUrl)
	connectionSource.setUsername(username)
	connectionSource.setPassword(password)

	val dao: Dao[Plugin, String] = DaoManager.createDao(connectionSource, classOf[Plugin])
	TableUtils.createTableIfNotExists(connectionSource, classOf[Plugin])

	// Setup Http Server
	Spark.port(8090)
	secure("deploy/keystore.jks", "password", null, null);
	get("/", (req, res) => "Hallo World")

	get("/plugin/list", (req, res) => {
		val plugins = dao.queryForAll()
		val gson = new Gson()
		gson.toJson(plugins)
	})

	RouteOverview.enableRouteOverview()
}

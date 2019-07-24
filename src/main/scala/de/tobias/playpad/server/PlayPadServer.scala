package de.tobias.playpad.server

import com.j256.ormlite.dao.{Dao, DaoManager}
import com.j256.ormlite.jdbc.JdbcConnectionSource
import com.j256.ormlite.table.TableUtils
import de.tobias.playpad.server.account.{Account, Session}
import de.tobias.playpad.server.plugin.Plugin
import de.tobias.playpad.server.server.SqlHelper
import de.tobias.playpad.server.server.account._
import de.tobias.playpad.server.server.plugin.{PluginGet, PluginList}
import de.tobias.playpad.server.server.project._
import de.tobias.playpad.server.server.project.sync.ProjectSyncHandler
import de.tobias.playpad.server.settings.SettingsHandler
import de.tobias.playpad.server.transformer.JsonTransformer
import spark.Spark._

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

	private val databaseUrl = s"jdbc:mysql://${settings.db_host}:${settings.db_port}/${settings.db_database}?" +
		s"autoReconnect=true&wait_timeout=86400&serverTimezone=Europe/Berlin"
	var connectionSource = new JdbcConnectionSource(databaseUrl)
	connectionSource.setUsername(settings.db_username)
	connectionSource.setPassword(settings.db_password)

	private val databaseConnection = DriverManager.getConnection(databaseUrl, settings.db_username, settings.db_password)

	val pluginDao: Dao[Plugin, Int] = DaoManager.createDao(connectionSource, classOf[Plugin])
	val accountDao: Dao[Account, Int] = DaoManager.createDao(connectionSource, classOf[Account])
	val sessionDao: Dao[Session, Int] = DaoManager.createDao(connectionSource, classOf[Session])

	// Management Tables
	TableUtils.createTableIfNotExists(connectionSource, classOf[Plugin])
	TableUtils.createTableIfNotExists(connectionSource, classOf[Account])
	TableUtils.createTableIfNotExists(connectionSource, classOf[Session])
	SqlHelper.createTables(databaseConnection)

	// Setup Http Server
	port(8090)
	threadPool(8, 2, 60 * 60 * 1000)

	private val externalPath = Paths.get(settings.download_folder).toAbsolutePath.toString
	externalStaticFileLocation(externalPath)

	secure("deploy/keystore.jks", settings.keystorePassword, null, null)

	// PlayWall Cloud
	webSocket("/project", new ProjectSyncHandler(sessionDao, databaseConnection))

	// Project
	get("/projects/:id", new ProjectGet(databaseConnection, sessionDao), new JsonTransformer)
	get("/projects", new ProjectList(databaseConnection, sessionDao), new JsonTransformer)
	post("/projects", new ProjectPost(databaseConnection, sessionDao), new JsonTransformer)
	delete("/projects", new ProjectDelete(databaseConnection, sessionDao), new JsonTransformer)

	// Plugins
	get("/plugins/:id", new PluginGet(pluginDao), new JsonTransformer)
	get("/plugins", new PluginList(pluginDao), new JsonTransformer)

	// Account
	post("/accounts", new AccountPost(accountDao), new JsonTransformer)
	put("/accounts", new AccountPut(accountDao), new JsonTransformer)

	post("/sessions", new SessionPost(accountDao), new JsonTransformer)
	delete("/sessions", new SessionDelete(accountDao), new JsonTransformer)
	get("/sessions", new SessionGet(accountDao), new JsonTransformer)

	// DEBUG
	// DebugScreen.enableDebugScreen()

	SettingsHandler.saver.save(settings, settingsPath)

	Runtime.getRuntime.addShutdownHook(new Thread(() => {
		databaseConnection.close()
		connectionSource.close()
		stop()
	}))
}

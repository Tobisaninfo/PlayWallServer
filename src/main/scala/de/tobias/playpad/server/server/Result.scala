package de.tobias.playpad.server.server

/**
  * Created by tobias on 16.02.17.
  */
class Result {

	var status: Status.Value = _
	var message: String = _

	def this(status: Status.Value, message: String = "") {
		this()
		this.status = status
		this.message = message
	}
}

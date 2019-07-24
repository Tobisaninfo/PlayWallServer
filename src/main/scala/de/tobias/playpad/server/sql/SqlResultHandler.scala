package de.tobias.playpad.server.sql

trait SqlResultHandler {

    def fromResult(o: Any): Any

    def toQuery(o: Any): Any
}

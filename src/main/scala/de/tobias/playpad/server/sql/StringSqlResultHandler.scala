package de.tobias.playpad.server.sql

class StringSqlResultHandler extends SqlResultHandler {
    override def fromResult(o: Any): Any = o

    override def toQuery(o: Any): Any = o
}

package de.tobias.playpad.server.logger

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.filter.Filter
import ch.qos.logback.core.spi.FilterReply

/**
  * Created by tobias on 26.02.17.
  */
class LogFilter extends Filter[ILoggingEvent] {
	override def decide(e: ILoggingEvent): FilterReply = {
		if (e.getLoggerName.contains("com.j256.ormlite.table.TableUtils")) {
			FilterReply.DENY
		} else {
			FilterReply.NEUTRAL
		}
	}
}

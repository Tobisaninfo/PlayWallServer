package de.tobias.playpad.server.sql;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ColumnName
{
	/**
	 * Name of the table column.
	 *
	 * @return name
	 */
	String value();

	Class<? extends SqlResultHandler> handler() default StringSqlResultHandler.class;

}

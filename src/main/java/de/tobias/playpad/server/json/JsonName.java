package de.tobias.playpad.server.json;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonName {

	/**
	 * Name of the field for the json.
	 *
	 * @return name
	 */
	String value();

	Class<? extends SerializerHandler> handler() default StringSerializerHandler.class;
}

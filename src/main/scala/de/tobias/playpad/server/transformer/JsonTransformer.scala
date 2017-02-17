package de.tobias.playpad.server.transformer

import com.google.gson.{Gson, JsonObject}
import spark.ResponseTransformer

/**
  * Created by tobias on 05.02.17.
  */
class JsonTransformer extends ResponseTransformer {

	val gson = new Gson()

	override def render(o: scala.Any): String = {
		if (!o.isInstanceOf[JsonObject]) {
			gson.toJson(o)
		} else {
			o.toString
		}
	}
}

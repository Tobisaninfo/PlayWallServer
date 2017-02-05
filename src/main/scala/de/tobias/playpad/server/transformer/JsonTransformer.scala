package de.tobias.playpad.server.transformer

import com.google.gson.Gson
import spark.ResponseTransformer

/**
  * Created by tobias on 05.02.17.
  */
class JsonTransformer extends ResponseTransformer {

	val gson = new Gson()

	override def render(o: scala.Any): String = {
		gson.toJson(o)
	}
}

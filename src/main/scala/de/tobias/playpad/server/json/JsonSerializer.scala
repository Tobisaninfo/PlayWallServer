package de.tobias.playpad.server.json

import java.util

import com.google.gson.{JsonArray, JsonObject}

class JsonSerializer {

	def serialize(o: Any): JsonObject = {
		val jsonObject = new JsonObject
		o.getClass.getDeclaredFields
			.filter(f => f.isAnnotationPresent(classOf[JsonName]))
			.foreach(f => {
				f.setAccessible(true)
				val annotation = f.getAnnotation(classOf[JsonName])
				val value = f.get(o)
				if (value != null) {
					jsonObject.add(annotation.value(), annotation.handler().newInstance().serialize(value))
				}
			})

		o.getClass.getDeclaredFields
			.filter(f => f.isAnnotationPresent(classOf[JsonObj]))
			.foreach(f => {
				f.setAccessible(true)
				val annotation = f.getAnnotation(classOf[JsonObj])
				val value = f.get(o)
				if (value != null) {
					jsonObject.add(annotation.value(), serialize(value))
				}
			})

		o.getClass.getDeclaredFields
			.filter(f => f.isAnnotationPresent(classOf[JsonCollection]))
			.foreach(f => {
				f.setAccessible(true)
				val annotation = f.getAnnotation(classOf[JsonCollection])
				val value = f.get(o)

				val jsonValues = value match {
					case seq: Seq[_] => seq.map(serialize)
				}
				val jsonArray = new JsonArray(jsonValues.size)
				jsonValues.foreach(jsonArray.add)
				jsonObject.add(annotation.value(), jsonArray)
			})

		jsonObject
	}

	def deserialize[T](jsonObject: JsonObject, clazz: Class[T]): T = {
		val obj = clazz.newInstance()

		clazz.getDeclaredFields
			.filter(f => f.isAnnotationPresent(classOf[JsonName]))
			.foreach(f => {
				println(clazz + "\t" + f.getName)
				f.setAccessible(true)
				val annotation = f.getAnnotation(classOf[JsonName])
				val value = jsonObject.get(annotation.value())
				f.set(obj, annotation.handler().newInstance().deserialize(value))
			})


		clazz.getDeclaredFields
			.filter(f => f.isAnnotationPresent(classOf[JsonObj]))
			.foreach(f => {
				f.setAccessible(true)
				val annotation = f.getAnnotation(classOf[JsonObj])
				val jsonObj = jsonObject.getAsJsonObject(annotation.value())
				val value = if (jsonObj != null) {
					val value = deserialize(jsonObj, f.getType)
					setParent(value, obj)
					value
				} else {
					null
				}
				f.set(obj, value)
			})

		clazz.getDeclaredFields
			.filter(f => f.isAnnotationPresent(classOf[JsonCollection]))
			.foreach(f => {
				f.setAccessible(true)
				val annotation = f.getAnnotation(classOf[JsonCollection])
				val jsonArray = jsonObject.getAsJsonArray(annotation.value())

				val tempList = new util.ArrayList[Any]()
				jsonArray.forEach(elem => {
					val value = deserialize(elem.getAsJsonObject, annotation.`type`())
					setParent(value, obj)
					tempList.add(value)
				})

				var col = f.get(obj)
				col match {
					case _: List[_] => tempList.forEach(e => col = e :: col.asInstanceOf[List[_]])
				}
				f.set(obj, col)
			})

		obj
	}

	private def setParent(value: Any, parent: Any): Unit = {
		value.getClass.getDeclaredFields
			.filter(f => f.isAnnotationPresent(classOf[JsonParent]))
			.foreach(f => {
				f.setAccessible(true)
				f.set(value, parent)
			})
	}
}

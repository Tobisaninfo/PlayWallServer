package de.tobias.playpad.server.sql

import java.sql.{Connection, ResultSet}
import java.util.UUID

class SqlSerializer {

	def queryObj[T](clazz: Class[T], key: UUID, connection: Connection, keyName: String = null): T = {
		val obj = clazz.newInstance()
		if (clazz.isAnnotationPresent(classOf[Table])) {
			val table = clazz.getAnnotation(classOf[Table]).value()

			val columnName = if (keyName != null) {
				keyName
			} else {
				getKeyName(clazz)
			}

			val stmt = connection.prepareStatement(s"SELECT * FROM $table WHERE $columnName = ?")
			stmt.setString(1, key.toString)
			val result = stmt.executeQuery()

			if (result.first()) {
				clazz.getDeclaredFields
					.filter(f => f.isAnnotationPresent(classOf[ColumnName]))
					.foreach(f => {
						f.setAccessible(true)
						f.set(obj, result.getObject(f.getAnnotation(classOf[ColumnName]).value()))
					})
			}

			result.close()
			stmt.close()
		}
		obj
	}

	private def getKeyName[T](clazz: Class[T]): String = {
		val field = clazz.getDeclaredFields
			.filter(f => f.isAnnotationPresent(classOf[ColumnName]))
			.find(f => f.isAnnotationPresent(classOf[Id]))

		if (field.isDefined) {
			field.get.getAnnotation(classOf[ColumnName]).value()
		} else {
			null
		}
	}

	//	def queryObj[T](clazz: Class[T], key: UUID, connection: Connection, keyName: String): List[T] = {
	//		if (clazz.isAnnotationPresent(classOf[Table])) {
	//			val table = clazz.getAnnotation(classOf[Table]).value()
	//
	//			val stmt = connection.prepareStatement(s"SELECT * FROM $table WHERE $keyName = ?")
	//			stmt.setString(1, key.toString)
	//			val result = stmt.executeQuery()
	//
	//			val list = getResult(clazz, result)
	//
	//			result.close()
	//			stmt.close()
	//			list
	//		} else {
	//			null
	//		}
	//	}

	def queryObj[T](clazz: Class[T], connection: Connection): List[T] = {
		if (clazz.isAnnotationPresent(classOf[Table])) {
			val table = clazz.getAnnotation(classOf[Table]).value()

			val stmt = connection.prepareStatement(s"SELECT * FROM $table")
			val result = stmt.executeQuery()

			val list = getResult(clazz, result)

			result.close()
			stmt.close()
			list
		} else {
			null
		}
	}

	private def getResult[T](clazz: Class[T], result: ResultSet): List[T] = {
		var list = List[T]()
		while (result.next()) {
			val obj = clazz.newInstance()
			clazz.getDeclaredFields
				.filter(f => f.isAnnotationPresent(classOf[ColumnName]))
				.foreach(f => {
					f.setAccessible(true)

					if (f.getType.equals(classOf[UUID])) {
						f.set(obj, UUID.fromString(result.getString(f.getAnnotation(classOf[ColumnName]).value())))
					} else {
						val column = f.getAnnotation(classOf[ColumnName])
						val columnName = column.value()
						f.set(obj, column.handler().newInstance().fromResult(result.getObject(columnName)))
					}

				})
			list = obj :: list
		}
		list
	}
}

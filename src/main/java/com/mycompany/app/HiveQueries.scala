package com.mycompany.app

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.hive.HiveContext
// OBS run as hduser, need classpaths 
object HiveQueries{
	def main(args: Array[String]) {
		val sc = new SparkContext(new SparkConf().setAppName("Spark Queries").setMaster("local"))
		val context = new HiveContext(sc)
		import context.implicits._
		import context.sql
		//context.setConf("hive.metastore.warehouse.dir", "hdfs://http://localhost:50070/user/hive/warehouse")
		//context.sql("SET hive.metastore.warehouse.dir=hdfs://localhost:50070/user/hive/warehouse");
		val res = time{context.sql("SELECT name, SUM(CAST(length AS INT)) FROM track GROUP BY name HAVING COUNT(name)>1").count()}
		
		//res.show()
		println("Number of results: " + res)
		println("==========================================")
}
def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    println("==========================================")
    println("Elapsed time: " + (t1 - t0) /1e6+" ms")
    result
}
}
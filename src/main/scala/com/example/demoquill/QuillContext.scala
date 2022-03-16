package com.example.demoquill

import io.getquill.context.ZioJdbc.DataSourceLayer
import io.getquill.{MysqlJdbcContext, SnakeCase}
import zio.{Has, ULayer}

import javax.sql.DataSource

object QuillContext extends MysqlJdbcContext(SnakeCase, "database") {
  val dataSourceLayer: ULayer[Has[DataSource]] =
    DataSourceLayer.fromPrefix("database").orDie
}

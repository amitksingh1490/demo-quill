package com.example.demoquill

import io.getquill.context.ZioJdbc.DataSourceLayer
import io.getquill.{MysqlZioJdbcContext, SnakeCase}
import zio.{Has, ULayer}

import javax.sql.DataSource

object QuillContext extends MysqlZioJdbcContext(SnakeCase) {
  val dataSourceLayer: ULayer[Has[DataSource]] =
    DataSourceLayer.fromPrefix("ctx").orDie
}

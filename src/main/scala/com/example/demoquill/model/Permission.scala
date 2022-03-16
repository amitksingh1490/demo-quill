package com.example.demoquill.model

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class Permission(id: Int, name: String)

object Permission extends ((Int, String) => Permission) {
  implicit val decoder: JsonDecoder[Permission] = DeriveJsonDecoder.gen[Permission]
  implicit val encoder: JsonEncoder[Permission] = DeriveJsonEncoder.gen[Permission]
}

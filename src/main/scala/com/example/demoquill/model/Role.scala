package com.example.demoquill.model

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class Role(id: Int, name: String)

object Role extends ((Int, String) => Role) {
  implicit val decoder: JsonDecoder[Role] = DeriveJsonDecoder.gen[Role]
  implicit val encoder: JsonEncoder[Role] = DeriveJsonEncoder.gen[Role]
}

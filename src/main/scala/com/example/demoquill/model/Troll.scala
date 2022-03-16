package com.example.demoquill.model

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class Troll(
    id: Int,
    firstName: String,
    lastName: String,
    age: Int,
  )

object Troll extends ((Int, String, String, Int) => Troll) {
  implicit val decoder: JsonDecoder[Troll] = DeriveJsonDecoder.gen[Troll]
  implicit val encoder: JsonEncoder[Troll] = DeriveJsonEncoder.gen[Troll]
}

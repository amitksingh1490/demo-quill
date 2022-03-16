package com.example.demoquill.model

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class Robot(
    id: Int,
    name: String,
    age: Int,
  )

object Robot extends ((Int, String, Int) => Robot) {
  implicit val decoder: JsonDecoder[Robot] = DeriveJsonDecoder.gen[Robot]
  implicit val encoder: JsonEncoder[Robot] = DeriveJsonEncoder.gen[Robot]
}

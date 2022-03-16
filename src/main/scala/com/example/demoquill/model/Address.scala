package com.example.demoquill.model

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class Address(
    ownerFk: Int,
    street: String,
    zip: Int,
    state: String,
  )

object Address extends ((Int, String, Int, String) => Address) {
  implicit val decoder: JsonDecoder[Address] = DeriveJsonDecoder.gen[Address]
  implicit val encoder: JsonEncoder[Address] = DeriveJsonEncoder.gen[Address]
}

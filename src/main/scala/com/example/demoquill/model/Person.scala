package com.example.demoquill.model

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class Person(
    id: Int,
    firstName: String,
    lastName: String,
    age: Int,
  )

object Person extends ((Int, String, String, Int) => Person) {
  implicit val decoder: JsonDecoder[Person] = DeriveJsonDecoder.gen[Person]
  implicit val encoder: JsonEncoder[Person] = DeriveJsonEncoder.gen[Person]
}

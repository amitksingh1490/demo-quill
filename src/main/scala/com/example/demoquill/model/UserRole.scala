package com.example.demoquill.model

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class UserRole(
    id: Int,
    userFk: Int,
    roleFk: Int,
  )

object UserRole extends ((Int, Int, Int) => UserRole) {
  implicit val decoder: JsonDecoder[UserRole] = DeriveJsonDecoder.gen[UserRole]
  implicit val encoder: JsonEncoder[UserRole] = DeriveJsonEncoder.gen[UserRole]
}

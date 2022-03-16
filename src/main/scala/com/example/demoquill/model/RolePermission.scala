package com.example.demoquill.model

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class RolePermission(
    id: Int,
    roleFk: Int,
    permissionFk: Int,
  )

object RolePermission extends ((Int, Int, Int) => RolePermission) {
  implicit val decoder: JsonDecoder[RolePermission] = DeriveJsonDecoder.gen[RolePermission]
  implicit val encoder: JsonEncoder[RolePermission] = DeriveJsonEncoder.gen[RolePermission]
}

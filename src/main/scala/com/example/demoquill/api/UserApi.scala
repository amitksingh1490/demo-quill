package com.example.demoquill.api

import com.example.demoquill.DataService
import com.example.demoquill.model.{Address, Person}
import zhttp.http.{Http, Request, Response}
import zhttp.http._
import zio.json._

object UserApi {
  private case class UsersWithAddress(
      id: Int,
      firstName: String,
      lastName: String,
      age: Int,
      address: Address,
    )
  private object UsersWithAddress
      extends ((Int, String, String, Int, Address) => UsersWithAddress) {
    implicit val decoder: JsonDecoder[UsersWithAddress] = DeriveJsonDecoder.gen[UsersWithAddress]
    implicit val encoder: JsonEncoder[UsersWithAddress] = DeriveJsonEncoder.gen[UsersWithAddress]

    def make(person: Person, address: Address): UsersWithAddress =
      UsersWithAddress(person.id, person.firstName, person.lastName, person.age, address)
  }
  val app = Http.collectZIO[Request] {
    case request @ Method.GET -> !! / "users" =>
      DataService
        .users
        .map {
          case users if request.url.queryParams.contains("pretty") =>
            Response.json(users.toJsonPretty)
          case users                                               => Response.json(users.toJson)
        }

    case Method.GET -> !! / "userswithaddress" =>
      DataService
        .usersWithAddress
        .map(users => users.map(res => UsersWithAddress.make(res._1, res._2)))
        .map(r => Response.json(r.toJsonPretty))
  }
}

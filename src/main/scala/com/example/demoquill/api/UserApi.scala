package com.example.demoquill.api

import com.example.demoquill.model.{Address, User}
import com.example.demoquill.service.UserService
import zhttp.http._
import zio.ZIO
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

    def make(person: User, address: Address): UsersWithAddress =
      UsersWithAddress(
        person.id.getOrElse(0),
        person.firstName,
        person.lastName,
        person.age,
        address,
      )
  }

  val app = Http.collectZIO[Request] {
    case request @ Method.GET -> !! / "users" =>
      UserService
        .users
        .map {
          case users if request.url.queryParams.contains("pretty") =>
            Response.json(users.toJsonPretty)
          case users                                               => Response.json(users.toJson)
        }

    case Method.GET -> !! / "userswithaddress" =>
      UserService
        .usersWithAddress
        .map(users => users.map(res => UsersWithAddress.make(res._1, res._2)))
        .map(r => Response.json(r.toJsonPretty))

    case request @ Method.POST -> !! / "user" =>
      request.bodyAsString.map(b => b.fromJson[User]).flatMap {
        case Left(_)     => ZIO.succeed(Response.fromHttpError(HttpError.BadRequest()))
        case Right(user) =>
          UserService
            .addUser(user)
            .map(id =>
              Response(
                Status.CREATED,
                data = HttpData.fromString(s"User created with id: ${id.fold(0)(identity)}"),
              ),
            )
      }

  }
}

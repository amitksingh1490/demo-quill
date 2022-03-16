package com.example.demoquill.service

import com.example.demoquill.Queries
import com.example.demoquill.QuillContext._
import com.example.demoquill.model.{Address, User}
import zio.macros.accessible
import zio.{Function1ToLayerSyntax, Has, IO}

import java.sql.SQLException
import javax.sql.DataSource

@accessible
trait UserService {
  def usersWithAddress: IO[SQLException, List[(User, Address)]]

  def users: IO[SQLException, List[User]]

  def addUser(user: User): IO[SQLException, Option[Int]]
}

object UserService {
  val live = (DataServiceLive.apply _).toLayer[UserService]
}

final case class DataServiceLive(
    dataSource: DataSource,
  ) extends UserService {
  val env = Has(dataSource)

  def usersWithAddress: IO[SQLException, List[(User, Address)]] =
    run {
      for {
        users   <- Queries.users
        address <- query[Address].join(address =>
          address.ownerFk == users.id.getOrElse(User(None, "", "", 0)),
        )
      } yield (users, address)
    }.provide(env)

  def users: IO[SQLException, List[User]] =
    run {
      Queries.users
    }.provide(env)

  override def addUser(user: User): IO[SQLException, Option[Int]] = run {
    Queries.addUser(user)
  }.provide(env)
}

package com.example.demoquill

import com.example.demoquill.model.User
import com.example.demoquill.QuillContext._

object Queries {
  final val largeAge = 100

  val users = quote {
    query[User]
  }

  def addUser(user: User) = quote {
    query[User]
      .insert(
        _.age       -> lift(user.age),
        _.firstName -> lift(user.firstName),
        _.lastName  -> lift(user.lastName),
      )
      .returningGenerated(_.id)
  }
}

package com.example.demoquill

import com.example.demoquill.model.Person
import com.example.demoquill.QuillContext._
object Queries {
  final val largeAge = 100

  val personsOlderThan =
    quote { (age: Int) =>
      query[Person].filter(person => person.age > largeAge)
    }
}

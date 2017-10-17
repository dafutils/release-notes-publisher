package com.github.dafutils.release.notes

import scala.sys.process._

package object publisher {
  type CommitMessageResolver[A] = (String) => Seq[A]

  val listTagsFromMostRecent = "git tag -l --sort=-version:refname"

  /**
    * Returns the titles of commit messages between the indicated version and the previous version.
    */
  implicit val gitCommitMessageResolver: CommitMessageResolver[String] = currentVersionTag => {

    val tagNames = listTagsFromMostRecent.lineStream
    val previousVersionTag =
      if (currentVersionTag == "HEAD") {
        tagNames.take(1)
      } else {
        tagNames
          .dropWhile(_ != currentVersionTag)
          .dropWhile(_ == currentVersionTag)
          .head
      }
    
    s"git log --pretty=format:'%s' $previousVersionTag..$currentVersionTag"
      .lineStream
      .toList
  }
}

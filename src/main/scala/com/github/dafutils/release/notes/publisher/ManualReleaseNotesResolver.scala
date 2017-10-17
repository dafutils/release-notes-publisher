package com.github.dafutils.release.notes.publisher

import java.nio.file.Path

import scala.io.Source

/**
  * Figures out if there is manually written text that is to be added in this release
  * and returns it.
  */
class ManualReleaseNotesResolver(releaseNotesFilePath: Path) {
  def releaseNotesForCurrentVersion(): String = {
    Option(releaseNotesFilePath.toFile)
      .filter(_.exists())
      .filter(_.isFile)
      .map { file =>
        val source = Source.fromFile(file)
        try source.mkString
        finally source.close()
      } getOrElse ""
  }
}

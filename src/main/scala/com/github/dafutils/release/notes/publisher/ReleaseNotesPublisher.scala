package com.github.dafutils.release.notes.publisher

trait ReleaseNotesPublisher {
  def publish(releaseNotes: ReleaseNotes): Unit
}

class GitHibReleaseNotesPublisher extends ReleaseNotesPublisher {
  override def publish(releaseNotes: ReleaseNotes): Unit = {}
}

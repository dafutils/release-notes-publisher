package com.github.dafutils.release.notes.publisher

trait ReleaseNotesPublisher {
  def publish(releaseNotes: ReleaseNotes): Unit
}

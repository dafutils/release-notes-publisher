package com.github.dafutils.release.notes.publisher

/**
  * Generates the release notes for the current version of a software;
  * The assumption is that we can resolve the tickets closed between the last ve
  *
  * @param ticketUrlConstructor
  * @param manualReleaseNotesResolver
  */
class ReleaseNotesGenerator(ticketUrlConstructor: TicketUrlConstructor,
                            manualReleaseNotesResolver: ManualReleaseNotesResolver) {
  
  def generateReleaseNotesFor[A: TicketIdExtractor : CommitMessageResolver]
  (currentVersion: String): ReleaseNotes = {

    val commitMessages = implicitly[CommitMessageResolver[A]].apply(currentVersion)
    val urlsForTicketsSinceLastVersion = commitMessages.foldLeft(Vector.empty[String]) {
      case (ticketIds, commitMessage) =>
        val ticketId = implicitly[TicketIdExtractor[A]].extractTicketId(commitMessage)
        val ticketUrl = ticketUrlConstructor.constructUrlFor(ticketId)
        ticketIds :+ ticketUrl
    }
    val manualReleaseNotes = manualReleaseNotesResolver.releaseNotesForCurrentVersion()
    ReleaseNotes(urlsForTicketsSinceLastVersion, manualReleaseNotes)
  }
}

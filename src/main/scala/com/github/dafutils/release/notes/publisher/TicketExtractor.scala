package com.github.dafutils.release.notes.publisher


/**
  *
  */
trait TicketIdExtractor[A] {
  def extractTicketId(commitMessageBody: A): String
}

/**
  * Represents the release notes for a given version of a software product
  *
  * @param ticketUrls  the urls (in a issue tracking system) of the tickets closed since 
  *                    last release; We are assuming that by viewing that URL, the reader
  *                    of the release notes can see detailed information about the ticket
  * @param manualNotes optional manually written text that can be included in the release
  *                    notes.
  */
case class ReleaseNotes(ticketUrls: Seq[String], manualNotes: String)


// Version control system specific
trait CommitMessageResolver[A] {
  def commitMessagesSinceLastReleasedVersion[A](currentVersion: String): Seq[A]
}

// Ticket management system specific
/**
  * For a given ticket identifier, it constructs an URL pointing to the ticket description
  */
trait TicketUrlConstructor {
  def constructUrlFor(ticketId: String): String
}

trait ReleaseNotesPublisher {
  def publish(releaseNotes: ReleaseNotes): Unit
}

///////////////////////////////////
// All these classes can be implemented in this project
/**
  * Generates the release notes for the current version of a software;
  * The assumption is that we can resolve the tickets closed between the last ve
  *
  * @param ticketUrlConstructor
  * @param manualReleaseNotesResolver
  */
class ReleaseNotesGenerator(ticketUrlConstructor: TicketUrlConstructor,
                            manualReleaseNotesResolver: ManualReleaseNotesResolver) {
  
  def generateReleaseNotesFor[A : TicketIdExtractor : CommitMessageResolver]
    (currentVersion: String): ReleaseNotes = {

    val commitMessages = implicitly[CommitMessageResolver[A]].commitMessagesSinceLastReleasedVersion(currentVersion)
    val urlsForTicketsSinceLastVersion = commitMessages.foldLeft(Vector.empty[String]) {
      case (ticketIds, commitMessage) =>
        val ticketId = implicitly[TicketIdExtractor[A]].extractTicketId(commitMessage)
        val ticketUrl = ticketUrlConstructor.constructUrlFor(ticketId)
        ticketIds :+ ticketUrl
    }
    val manualReleaseNotes = manualReleaseNotesResolver.resolveManualReleaseNotesForCurrentVersion()
    ReleaseNotes(urlsForTicketsSinceLastVersion, manualReleaseNotes)
  }
}

/**
  * Figures out if there is manually written text that is to be added in this release
  * and returns it
  */
class ManualReleaseNotesResolver {
  def resolveManualReleaseNotesForCurrentVersion(): String = ???
}



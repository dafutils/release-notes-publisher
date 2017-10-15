package com.github.dafutils.release.notes.publisher

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

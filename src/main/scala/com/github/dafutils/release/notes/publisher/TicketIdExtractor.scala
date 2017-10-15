package com.github.dafutils.release.notes.publisher

/**
  * Defines the logic for getting a ticket identifier from a commit message object
  */
trait TicketIdExtractor[A] {
  def extractTicketId(commitMessageBody: A): String
}

package com.github.dafutils.release.notes.publisher

/**
  * For a given ticket identifier, it constructs an URL pointing to the ticket description.
  * This needs to be implemented for every supported release
  */
trait TicketUrlConstructor {
  def constructUrlFor(ticketId: String): String
}

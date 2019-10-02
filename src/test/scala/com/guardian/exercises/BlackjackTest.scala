package com.guardian.exercises

import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}
import com.outworkers.util.samplers._

class BlackjackTest extends FlatSpec with Matchers with GeneratorDrivenPropertyChecks {

  implicit val deckSample: Sample[Deck] = new Sample[Deck] {
    override def sample: Deck = new Deck().shuffle()
  }

  it should "run a game of blackjack" in {
    forAll(Sample.generator[Deck], minSuccessful(1000)) { deck =>
      val outcome = Blackjack.runGame(deck)

      Console.println("Dealer")
      Console.println(outcome.dealerHand.showHand)

      Console.println("Player")
      Console.println(outcome.playerHand.showHand)

      outcome shouldBe an [BlackjackOutcome]
    }
  }

}

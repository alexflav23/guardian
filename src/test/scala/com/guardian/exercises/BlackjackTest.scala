package com.guardian.exercises

import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

class BlackjackTest extends FlatSpec with Matchers with GeneratorDrivenPropertyChecks {

  it should "run a game of blackjack" in {
    forAll(minSuccessful(100)) {
      val outcome = Blackjack.runGame

      Console.println("Dealer")
      Console.println(outcome.dealerHand.showHand)

      Console.println("Player")
      Console.println(outcome.playerHand.showHand)
    }
  }

}

package com.guardian.exercises

import scala.util.Random


//clumsy enumeration definition
sealed abstract class Suite(val value: String) {
  override def toString: String = value
}
case object Spade extends Suite("Spades")
case object Heart extends Suite("Hearts")
case object Club extends Suite("Clubs")
case object Diamond extends Suite("Diamonds")

sealed abstract class Rank(val value: Int)

case object Two extends Rank(2)
case object Three extends Rank(3)
case object Four extends Rank(4)
case object Five extends Rank(5)
case object Six extends Rank(6)
case object Seven extends Rank(7)
case object Eight extends Rank(8)
case object Nine extends Rank(9)
case object Ten extends Rank(10)
case object Jack extends Rank(10)
case object Queen extends Rank(10)
case object King extends Rank(10)
case object Ace extends Rank(11)


object CardDeck {

  val suites = Set(Spade, Heart, Club, Diamond)
  val ranks = List(Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace)
}

//the interesting part
case class Card(rank: Rank, suite: Suite)

class Deck(pCards: List[Card] = for (r <- CardDeck.ranks; s <- CardDeck.suites) yield Card(r, s)) {

  val cards: List[Card] = if (isValidDeck(pCards)) pCards else throw new RuntimeException("Deck is invalid!")

  def shuffle() = new Deck(Random.shuffle(cards))

  def pullFromTop(): (Card, Deck) = (cards.head, new Deck(cards.tail))

  def pullHand(): (List[Card], Deck) = (cards.take(2), new Deck(cards.drop(2)))

  def addToTop(card: Card) = new Deck(card :: cards)

  def addToTop(cardsToAdd: List[Card]) = new Deck(cardsToAdd ::: cards)

  private def isValidDeck(cards: List[Card]) = cards.size <= 52 && cards.distinct.size == cards.size

}

case class Hand(cards: List[Card]) {

  def handValue: Int = cards.map(_.rank.value).sum

  def addCard(card: Card): Hand = this.copy(card :: cards)

  def showHand: String = {
    val cardList = cards.map { card => s"${card.rank} of ${card.suite}" }.mkString(", ")

    s"$handValue: $cardList"
  }
}

trait Outcome {
  def playerHand: Hand

  def dealerHand: Hand
}

case class DealerWon(
  playerHand: Hand,
  dealerHand: Hand
) extends Outcome

case class PlayerWon(
  playerHand: Hand,
  dealerHand: Hand
) extends Outcome

object Blackjack {

  def play(
    deck: Deck,
    sam: Hand,
    dealer: Hand
  ): Outcome = {

    if (sam.handValue == 21) {
      println(s"Sam won: ${sam.handValue}; Dealer: ${sam.handValue}")
      PlayerWon(sam, dealer)
    } else if (dealer.handValue == 21 || sam.handValue > 21) {
      println(s"Dealer won: ${dealer.handValue}; Sam: ${sam.handValue}")
      DealerWon(sam, dealer)
    } else if (sam.handValue < 21 && dealer.handValue < 21) {
      if (sam.handValue >= 17) {
        val (dealerCard, newDeck) = deck.pullFromTop()
        play(newDeck, sam, dealer.addCard(dealerCard))
      } else {
        val (samCard, newDeck) = deck.pullFromTop()
        play(newDeck, sam.addCard(samCard), dealer)
      }
    } else {

      // if dealer's hand is over 21
      println("Dealer's hand is over 21")
      PlayerWon(sam, dealer)
    }
  }

  def runGame: Outcome = {
    val deck = new Deck()
    val (dealerHand, deckAfterDealer) = deck.pullHand()
    val (samsHand, remainingDeck) = deckAfterDealer.pullHand()

    play(remainingDeck, Hand(dealerHand), Hand(samsHand))
  }

}

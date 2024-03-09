package blackjack.model

import blackjack.model.GameResult.Companion.BLACKJACK_NUMBER

class Hand(
    val cards: List<Card>,
    private val pointCalculator: PointCalculator = DefaultPointCalculator(),
) {
    init {
        require(cards.size >= MIN_HAND_CARDS_SIZE) { "손패는 $MIN_HAND_CARDS_SIZE 장 이상임" }
    }

    constructor(vararg cards: Card) : this(cards.toList())

    fun first(): Card = cards.first()

    fun sumOptimized(): Int = pointCalculator.sumOf(cards)

    fun isBust(): Boolean = sumOptimized() > BLACKJACK_NUMBER

    fun isBlackjack(): Boolean {
        return (sumOptimized() == BLACKJACK_NUMBER) && (cards.size == MIN_HAND_CARDS_SIZE)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Hand

        return cards == other.cards
    }

    override fun hashCode(): Int {
        return cards.hashCode()
    }

    companion object {
        private const val MIN_HAND_CARDS_SIZE = 2
    }
}

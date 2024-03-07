package blackjack.model

class GameResult(
    private val dealer: Dealer,
    private val players: List<Player>,
) {
    fun createScoreBoard(): ScoreBoard {
        val results: List<PlayerResult> =
            players.map { player ->
                PlayerResult(player.name, player.comparePoints(dealer))
            }
        return ScoreBoard.from(results)
    }

    private fun Player.comparePoints(dealer: Dealer): State {
        val playerCards = handCards
        val dealerCards = dealer.handCards
        if (playerCards.isBust()) return State.LOSS
        if (dealerCards.isBust()) return State.WIN
        val compared = playerCards.sumOptimized() compareTo dealerCards.sumOptimized()
        return State.from(compared)
    }

    enum class State {
        WIN,
        LOSS,
        DRAW,
        ;

        fun reversed(): State {
            if (this == WIN) return LOSS
            if (this == LOSS) return WIN
            return DRAW
        }

        companion object {
            fun from(compared: Int): State {
                return when {
                    compared == 0 -> DRAW
                    compared > 0 -> WIN
                    compared < 0 -> LOSS
                    else -> throw IllegalArgumentException("왈왈")
                }
            }
        }
    }
}
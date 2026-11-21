package BackendLogic

import elements.SimState
import kotlin.math.abs

fun heuristicScore(state: SimState, targets: List<Long>): Double {
    val remaining = targets.filter { it !in state.found_targets }
    if (remaining.isEmpty()) return state.program.size.toDouble()

    val nextTarget = remaining.first()
    val minDistance = state.Slots.minOf { slotVal ->
        abs(slotVal.toDouble() - nextTarget.toDouble())
    }

    val targetsFoundBonus = -(state.found_targets.size * 1_000_000.0)
    val lengthPenalty = state.program.size * 0.5
    return targetsFoundBonus + minDistance + lengthPenalty
}

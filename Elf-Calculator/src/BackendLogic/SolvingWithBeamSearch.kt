package BackendLogic

import elements.Instruction
import elements.Operations
import elements.ProgramNode
import elements.SimState
import java.util.PriorityQueue

fun SolvingWithBeamSearch(
    targets: MutableList<Long>,
    Starting_Values: LongArray,
    id: String,
    registers: Int = 48,
    beam_width: Int = 90,
    maxSteps: Int = 50
): SimState? {
    if (Starting_Values.size > registers) {
        println("Too many Starting Values!!")
        return null
    }

    val initialSlots = LongArray(registers) { index ->
        if (index < Starting_Values.size) Starting_Values[index] else 0L
    }
    val initialTargets = targets.toMutableSet()
    val startingFoundTargets = initialSlots.filter { it in initialTargets }.toSet()

    var beam = listOf(
        SimState(
            id = id,
            Slots = initialSlots,
            found_targets = startingFoundTargets.toMutableSet(),
            trace = null
        )
    )

    beam.firstOrNull { it.found_targets.containsAll(initialTargets) }?.let { return it }

    for (step in 1..maxSteps) {
        println("\t\tStep: $step")
        // Max-heap ordered by largest (worst) score first so we keep only the best beam_width states
        val topCandidates = PriorityQueue<Pair<Double, SimState>>(beam_width + 1) { a, b ->
            b.first.compareTo(a.first)
        }
        val bestScoreByBoard = HashMap<Int, Double>()

        fun offerCandidate(candidate: SimState) {
            val score = heuristicScore(candidate, targets)
            val boardKey = candidate.Slots.contentHashCode()
            val prevBest = bestScoreByBoard[boardKey]
            if (prevBest != null && score >= prevBest) return
            bestScoreByBoard[boardKey] = score

            if (topCandidates.size < beam_width) {
                topCandidates.offer(score to candidate)
            } else if (score < topCandidates.peek().first) {
                topCandidates.poll()
                topCandidates.offer(score to candidate)
            }
        }

        for (state in beam) {
            println("\t\t\t\tbeam loop")
            for (i in 0 until 16) {
                println("\t\t\t\t\t\ti loop")
                for (j in 0 until 16) {
                    println("\t\t\t\t\t\t\t\tj loop")
                    for (operation in Operations.entries) {
                        println("\t\t\t\t\t\t\t\t\t\toperation loop")
                        if ((operation == Operations.ADD || operation == Operations.MULTIPLY) && j < i) {
                            continue
                        }
                        for (saveIndex in 0 until 16) {
                            val instruction = Instruction(i, operation, j, saveIndex)
                            val newSlots = applyingInstructions(instruction, state.Slots) ?: continue
                            if (newSlots[saveIndex] == state.Slots[saveIndex]) continue

                            val updatedFound = state.found_targets.toMutableSet()
                            for (v in newSlots) {
                                if (v in initialTargets) {
                                    updatedFound.add(v)
                                    initialTargets.remove(v)
                                }
                            }

                            val newState = SimState(
                                id = id,
                                Slots = newSlots,
                                found_targets = updatedFound,
                                trace = ProgramNode(parent = state.trace, instruction = instruction)
                            )

                            if (initialTargets.isEmpty()) {
                                return newState
                            }

                            offerCandidate(newState)
                        }
                    }
                }
            }
        }

        if (topCandidates.isEmpty()) return null

        beam = topCandidates
            .sortedBy { it.first }
            .map { it.second }
    }

    return null
}
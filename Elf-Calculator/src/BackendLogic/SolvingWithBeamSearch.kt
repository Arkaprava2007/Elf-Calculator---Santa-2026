package BackendLogic

import elements.Instruction
import elements.Operations
import elements.SimState

fun SolvingWithBeamSearch(
    targets: MutableList<Long>,
    Starting_Values: LongArray,
    id: String,
    registers: Int = 48,
    beam_width: Int = 200,
    maxSteps: Int = 400
): SimState? {
    if (Starting_Values.size >= registers) {
        print("Too many Starting Values!!")
        return null
    }

    val SLOTS = LongArray(registers) { index ->
        if (index < Starting_Values.size) Starting_Values[index] else 0L
    }
    val initial_targets = targets.toSet()
    val found_targets = targets.filter { it in Starting_Values }.toSet()

    var beam = listOf(SimState(id, SLOTS, found_targets, mutableListOf()))

    beam.firstOrNull { it.found_targets.containsAll(initial_targets) }?.let { return it }

    for (step in 1..maxSteps) {
        val candidates = ArrayList<SimState>()

        for (state in beam) {
            for (i in 0 until registers) {
                for (j in 0 until registers) {
                    for (Operation in Operations.entries) {
                        for (save_Index in 10 until registers) {
                            val instruction = Instruction(i, Operation, j, save_Index)
                            val newSlots = applyingInstructions(instruction, state.Slots) ?: continue

                            val updatedFoundList = state.found_targets.toMutableSet()
                            for (v in initial_targets){
                                updatedFoundList.add(v)
                            }

                            state.program.add(instruction)
                            val _program = state.program

                            val newState = SimState(
                                id = id,
                                found_targets = updatedFoundList,
                                Slots = newSlots,
                                program = _program,
                            )

                            if (newState.found_targets.containsAll(initial_targets)){
                                return newState
                            }

                            candidates.add(newState)
                        }
                    }
                }
            }
        }

        if (candidates.isEmpty()) return null

        beam = candidates
            .sortedBy { heuristicScore(it,targets) }
            .take(beam_width)

    }

    return null
}
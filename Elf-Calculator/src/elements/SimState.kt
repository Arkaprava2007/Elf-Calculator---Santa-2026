package elements

data class SimState(
    val id: String,
    val Slots: LongArray,
    val found_targets: Set<Long>,
    val program: MutableList<Instruction>
){
    fun copyState(newSlots: LongArray = Slots.copyOf()): SimState =
        SimState(
            id = id,
            Slots = newSlots,
            found_targets = found_targets,
            program = program
        )
}

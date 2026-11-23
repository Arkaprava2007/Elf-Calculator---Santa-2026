package elements

data class SimState(
    val id: String,
    val Slots: LongArray,
    val found_targets: MutableSet<Long>,
    val trace: ProgramNode? = null
){
    fun copyState(newSlots: LongArray = Slots.copyOf()): SimState =
        SimState(
            id = id,
            Slots = newSlots,
            found_targets = found_targets,
            trace = trace
        )
}

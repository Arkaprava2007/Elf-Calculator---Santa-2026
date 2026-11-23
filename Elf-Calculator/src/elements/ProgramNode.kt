package elements

class ProgramNode(
    val parent: ProgramNode?,
    val instruction: Instruction
) {
    fun toInstructionList(): List<Instruction> {
        val result = ArrayList<Instruction>()
        var current: ProgramNode? = this
        while (current != null) {
            result.add(current.instruction)
            current = current.parent
        }
        result.reverse()
        return result
    }
}
package BackendLogic

import elements.Instruction
import elements.Operations

fun applyingInstructions(instruction: Instruction, slots: LongArray): LongArray?{
    if (instruction.a_position !in slots.indices||instruction.b_position !in slots.indices||instruction.save_index !in slots.indices) return null

    val a = slots[instruction.a_position]
    val b = slots[instruction.b_position]

    val result = try {
            when (instruction.operation) {
                Operations.ADD -> Math.addExact(a, b)
                Operations.SUBTRACT -> Math.subtractExact(a, b)
                Operations.MULTIPLY -> Math.multiplyExact(a, b)
                Operations.DIVIDE -> {
                    if (b == 0L || a % b != 0L) return null
                    a / b
                }
            }
        }catch (_: ArithmeticException){
            return null
        }

    val updatedSlots = slots.copyOf()
    updatedSlots[instruction.save_index] = result
    return updatedSlots
}
package elements

data class Instruction(
    val a_position: Int,
    val operation: Operations,
    val b_position: Int,
    val save_index: Int
)

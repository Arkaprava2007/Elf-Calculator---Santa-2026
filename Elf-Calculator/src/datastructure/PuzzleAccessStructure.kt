package datastructure

data class PuzzleAccessStructure(
    var id: String,
    var starting_values: List<Long>,
    var targets: List<Long>,
    var value_limit: Long,
    var registers: Int
)

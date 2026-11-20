package datastructure

data class PracticePuzzleAccessStructure(
    var id: String,
    var starting_values: List<Long>,
    var targets: List<Long>,
    var value_limit: Long,
    var registers: Int,
    var family: String,
    var design_tier: String,
    var lower_bound: Int,
)

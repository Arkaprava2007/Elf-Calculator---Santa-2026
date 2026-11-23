package BackendLogic

import elements.Instruction
import elements.Operations
import elements.SimState
import fileoperation.PracticePuzzleFileAccessOperation
import fileoperation.PuzzleFileAccessOperation
import fileoperation.SubmissionFileOperation

class Assemble {

    val practicePuzzleFileAccessOperation = PracticePuzzleFileAccessOperation()
    val puzzleFileAccessOperation = PuzzleFileAccessOperation()
    val submissionFileOperation = SubmissionFileOperation()

    val PracticePuzzleSimState: MutableList<SimState> = mutableListOf()
    val PuzzleSimState: MutableList<SimState> = mutableListOf()

    private fun formatProgram(instructions: List<Instruction>): String {
        return instructions.joinToString(",") { inst ->
            val operator = when (inst.operation) {
                Operations.ADD -> '+'
                Operations.SUBTRACT -> '-'
                Operations.MULTIPLY -> '*'
                Operations.DIVIDE -> '/'
            }
            "[\"$operator\",${inst.a_position},${inst.b_position},${inst.save_index}]"
        }
    }

    fun SolvingPractice_Puzzles() {
        PracticePuzzleSimState.clear()
        val practicePuzzlesList = practicePuzzleFileAccessOperation.ReadingPracticePuzzle()

        /*for (pz in practicePuzzlesList) {
            println("${pz.id}")
        }*/

        var i = 0

        for (puzzle in practicePuzzlesList) {

            println("$i. Loop is running")
            val simState = SolvingWithBeamSearch(
                targets = puzzle.targets.toMutableList(),
                Starting_Values = puzzle.starting_values.toLongArray(),
                id = puzzle.id,
                registers = puzzle.registers,
            )

            simState?.let { PracticePuzzleSimState.add(it) }
            i++
        }

        submissionFileOperation.SubmissionList.clear()

        for (simState in PracticePuzzleSimState) {
            submissionFileOperation.addSubmission(
                id = simState.id,
                program = formatProgram(simState.trace!!.toInstructionList())
            )
        }

        submissionFileOperation.CSV_File_Generator_For_Submission_AS_Output()
    }

    fun SolvingPuzzles() {
        PuzzleSimState.clear()
        val puzzleList = puzzleFileAccessOperation.ReadingPuzzle()

        for (puzzle in puzzleList) {
            val simState = SolvingWithBeamSearch(
                targets = puzzle.targets.toMutableList(),
                Starting_Values = puzzle.starting_values.toLongArray(),
                id = puzzle.id,
                registers = puzzle.registers,
            )

            simState?.let { PuzzleSimState.add(it) }
        }

        submissionFileOperation.SubmissionList.clear()

        for (simState in PuzzleSimState) {
            submissionFileOperation.addSubmission(
                id = simState.id,
                program = formatProgram(simState.trace!!.toInstructionList())
            )
        }

        submissionFileOperation.CSV_File_Generator_For_Submission_AS_Output()
    }
}
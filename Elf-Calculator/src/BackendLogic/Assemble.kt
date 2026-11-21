package BackendLogic

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

    fun SolvingPractice_Puzzles(){

        val practice_puzzles_list = practicePuzzleFileAccessOperation.ReadingPracticePuzzle()

        for (Puzzle in practice_puzzles_list){
            val simState = SolvingWithBeamSearch(
                targets = Puzzle.targets.toMutableList(),
                Starting_Values = Puzzle.starting_values.toLongArray(),
                id = Puzzle.id,
                registers = Puzzle.registers,
            )

            simState?.let { PracticePuzzleSimState.addLast(it) }
        }

        submissionFileOperation.SubmissionList.clear()

        for (simState in PracticePuzzleSimState ){
            var programStringBuilder = ""

            for (instructions in simState.program){

                val operator = when (instructions.operation) {
                    Operations.ADD -> '+'
                    Operations.SUBTRACT -> '-'
                    Operations.MULTIPLY -> '*'
                    Operations.DIVIDE -> '/'
                }

                programStringBuilder += "[\"${operator}\",${instructions.a_position},${instructions.b_position},${instructions.save_index}],"
            }

            submissionFileOperation.addSubmission(
                id = simState.id,
                program = programStringBuilder
            )
        }

        submissionFileOperation.CSV_File_Generator_For_Submission_AS_Output()

    }

    fun SolvingPuzzles(){
        val puzzle_list = puzzleFileAccessOperation.ReadingPuzzle()

        for (Puzzle in puzzle_list){
            val simState = SolvingWithBeamSearch(
                targets = Puzzle.targets.toMutableList(),
                Starting_Values = Puzzle.starting_values.toLongArray(),
                id = Puzzle.id,
                registers = Puzzle.registers,
            )

            simState?.let { PuzzleSimState.addLast(it) }
        }

        submissionFileOperation.SubmissionList.clear()

        for (simState in PuzzleSimState ){
            var programStringBuilder = ""

            for (instructions in simState.program){
                programStringBuilder += "[\"${instructions.operation}\",${instructions.a_position},${instructions.b_position},${instructions.save_index}]"
            }

            submissionFileOperation.addSubmission(
                id = simState.id,
                program = programStringBuilder
            )
        }
        submissionFileOperation.CSV_File_Generator_For_Submission_AS_Output()
    }



}
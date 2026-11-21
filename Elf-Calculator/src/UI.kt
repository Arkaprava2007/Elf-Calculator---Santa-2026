import BackendLogic.Assemble
import fileoperation.SubmissionFileOperation
import kotlin.system.exitProcess

fun UI(){
    val assemble = Assemble()

    println("******Elf-Calculator******")
    while (true) {
        println("Chose what to do: ")
        println("1. Solve Practice Puzzle.\n2. Solve Puzzle.")
        print("\nEnter Choice: ")
        val choice = readln().toInt()
        when (choice) {
            1 -> {
                assemble.SolvingPractice_Puzzles()
                exitProcess(0)
            }
            2 ->{
                assemble.SolvingPuzzles()
                exitProcess(0)
            }
            else -> println("Invalid Choice")
        }
    }
}
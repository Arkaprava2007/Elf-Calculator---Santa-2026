package fileoperation

import datastructure.PracticePuzzleAccessStructure
import java.io.File

class PracticePuzzleFileAccessOperation {
    val PracticePuzzleList: MutableList<PracticePuzzleAccessStructure> = mutableListOf()

    val puzzle_file = "/PUZZLES/practice_puzzles.csv"

    fun ReadingPracticePuzzle(){
        val file = File(puzzle_file)

        file.forEachLine { line ->
            line.drop(1)

            val field = PracticePuzzleAccessStructure(
                id = "",
                starting_values = listOf(),
                targets = listOf(),
                value_limit = 0L,
                registers = 48,
                family = "",
                design_tier = "",
                lower_bound = 0
            )

            var doubleCoteCount = 0
            var itemCount = 0
            var comaFetched = false
            var inDoubleCote = false
            var stringBuilder = ""
            var listBuilder = mutableListOf<Long>()
            var i = 0

            while (i < line.length){
                val ch = line[i]
                when(ch){
                    '"'->{
                        doubleCoteCount++
                        inDoubleCote = if (doubleCoteCount%2 == 0) false else true
                    }
                    ','->{
                        if (inDoubleCote){
                            i++
                            comaFetched = true
                        }
                        else{
                            when(itemCount){
                                0 -> {
                                    field.id = stringBuilder
                                    itemCount++
                                    stringBuilder = ""
                                }
                                1 -> {
                                    field.starting_values = listBuilder as List<Long>
                                    itemCount++
                                    listBuilder.clear()
                                }
                                2 ->{
                                    field.targets = listBuilder as List<Long>
                                    itemCount++
                                    listBuilder.clear()
                                }
                                3->{
                                    field.value_limit = stringBuilder.toLong()
                                    itemCount++
                                    stringBuilder = ""
                                }
                                4->{
                                    field.registers = stringBuilder.toInt()
                                    itemCount++
                                    stringBuilder = ""
                                }
                                5->{
                                    field.family = stringBuilder
                                    itemCount++
                                    stringBuilder = ""
                                }
                                6->{
                                    field.design_tier = stringBuilder
                                    itemCount++
                                    stringBuilder = ""
                                }
                                7->{
                                    field.lower_bound = stringBuilder.toInt()
                                    stringBuilder = ""
                                }
                            }
                            i++
                        }
                    }
                    '[' -> i++
                    ']' -> i++
                    else -> {
                        if (inDoubleCote && !comaFetched){
                            stringBuilder += ch
                        }else if (inDoubleCote && comaFetched){
                            listBuilder.addLast(stringBuilder.toLong())
                            stringBuilder = ""
                            stringBuilder += ch
                            comaFetched = false
                        } else if(!inDoubleCote){
                            stringBuilder += ch
                        }

                        if (inDoubleCote && !comaFetched && line[i+1] == ']'){
                            listBuilder.addLast(stringBuilder.toLong())
                            stringBuilder = ""
                        }
                        i++
                    }
                }
            }
            PracticePuzzleList.addLast(field)
        }
    }
}
package fileoperation

import datastructure.PuzzleAccessStructure
import java.io.File

class PuzzleFileAccessOperation {

    val puzzle_file = "PUZZLES/puzzles.csv"

    fun splitCsvLine(line: String): List<String> {
        val fields = ArrayList<String>()
        val current = StringBuilder()
        var inQuotes = false
        var i = 0

        while (i < line.length) {
            val ch = line[i]
            when {
                ch == '"' -> {
                    if (inQuotes && i + 1 < line.length && line[i + 1] == '"') {
                        current.append('"')
                        i++
                    } else {
                        inQuotes = !inQuotes
                    }
                }
                ch == ',' && !inQuotes -> {
                    fields.add(current.toString().trim())
                    current.setLength(0)
                }
                else -> current.append(ch)
            }
            i++
        }
        fields.add(current.toString().trim())
        return fields
    }

    private fun parseLongList(raw: String): List<Long> {
        val cleaned = raw.removeSurrounding("\"").trim().removeSurrounding("[", "]").trim()
        if (cleaned.isEmpty()) return emptyList()
        return cleaned.split(',').map { it.trim().toLong() }
    }

    fun ReadingPuzzle(): MutableList<PuzzleAccessStructure> {
        val puzzles = ArrayList<PuzzleAccessStructure>()

        File(puzzle_file).bufferedReader().useLines { lines ->
            var isHeader = true

            for (line in lines) {
                if (isHeader) {
                    isHeader = false
                    continue
                }
                if (line.isBlank()) continue

                val cols = splitCsvLine(line)
                require(cols.size >= 5) { "Unexpected column count (${cols.size}) in line: $line" }

                val puzzle = PuzzleAccessStructure(
                    id = cols[0],
                    starting_values = parseLongList(cols[1]),
                    targets = parseLongList(cols[2]),
                    value_limit = cols[3].toLong(),
                    registers = cols[4].toInt()
                )
                puzzles.add(puzzle)
            }
        }

        return puzzles
    }
}
package fileoperation

import datastructure.SubmissionStructure
import java.io.File

class SubmissionFileOperation {

    val SubmissionList: MutableList<SubmissionStructure> = mutableListOf()

    fun addSubmission(id: String, program: String) {
        SubmissionList.addLast(SubmissionStructure(id, program))
    }

    val submissionFilePath = "/OUTPUT/"
    val file_name = "submission.csv"

    fun CSV_File_Generator_For_Submission_AS_Output(){
        val file = File(submissionFilePath, file_name).apply { if (!exists()) mkdirs() }
        file.bufferedWriter().use { writer ->
            writer.write("id,program")
            writer.newLine()

            for (statement in SubmissionList){
                writer.write("${statement.id},\"${statement.program}\"")
                writer.newLine()
            }
        }
    }
}
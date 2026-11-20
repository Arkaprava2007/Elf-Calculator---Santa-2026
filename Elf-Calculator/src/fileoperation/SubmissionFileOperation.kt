package fileoperation

import datastructure.SubmissionStructure

class SubmissionFileOperation {

    val SubmissionList: MutableList<SubmissionStructure> = mutableListOf()

    fun addSubmission(id: String, program: String) {
        SubmissionList.addLast(SubmissionStructure(id, program))
    }


}
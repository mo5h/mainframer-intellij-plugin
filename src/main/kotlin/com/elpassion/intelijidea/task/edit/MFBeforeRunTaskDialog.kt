package com.elpassion.intelijidea.task.edit

import com.elpassion.intelijidea.task.MFTaskData
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.ui.ValidationInfo
import javax.swing.JComponent
import javax.swing.JTextField

class MFBeforeRunTaskDialog(project: Project) : DialogWrapper(project), TaskEditForm {

    private val taskEditValidator = TaskEditValidator(this)
    val form = MFBeforeRunTaskForm(project)

    init {
        isModal = true
        init()
    }

    override fun createCenterPanel(): JComponent {
        return form.panel
    }

    override fun doValidate(): ValidationInfo? {
        return taskEditValidator.doValidate()
    }

    override fun taskField(): JTextField {
        return form.taskField
    }

    override fun buildCommandField(): JTextField {
        return form.buildCommandField
    }

    override fun mainframerToolField(): TextFieldWithBrowseButton {
        return form.mainframerToolField
    }

    fun restoreMainframerTask(data: MFTaskData) {
        form.mainframerToolField.text = data.mainframerPath
        form.buildCommandField.text = data.buildCommand
        form.taskField.text = data.taskName
    }

    fun createMFTaskDataFromForms() = MFTaskData(
            mainframerPath = form.mainframerToolField.text,
            buildCommand = form.buildCommandField.text,
            taskName = form.taskField.text)
}
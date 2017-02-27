package com.elpassion.intelijidea.action

import com.elpassion.intelijidea.MFTaskInjector
import com.elpassion.intelijidea.allConfigurationMapMFSelectorItem
import com.elpassion.intelijidea.task.MFBeforeTaskDefaultSettingsProvider
import com.elpassion.intelijidea.task.mfBeforeRunTaskProvider
import com.elpassion.intelijidea.util.showError
import com.elpassion.intelijidea.util.showInfo
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages

class MFInjectBeforeRunTasksAction : AnAction(MF_INJECT_BEFORE_RUN_TASK_ACTION) {

    override fun actionPerformed(event: AnActionEvent) {
        event.project?.let { injectBeforeRunTask(it) }
    }

    private fun injectBeforeRunTask(project: Project) {
        val isTaskDataValid = MFBeforeTaskDefaultSettingsProvider.INSTANCE.taskData.isValid()
        when (isTaskDataValid) {
            true -> project.showInjectionDialog()
            else -> project.showInvalidSettingsError()
        }
    }

    private fun Project.showInjectionDialog() {
        val result = Messages.showYesNoCancelDialog(this, "Reset current mainframer task?", "MainFramer", null)
        when (result) {
            Messages.YES -> injectMainFramer(true)
            Messages.NO -> injectMainFramer(false)
        }
    }

    private fun Project.injectMainFramer(replaceAll: Boolean) {
        MFTaskInjector(this, this.mfBeforeRunTaskProvider).run {
            val allConfigurationToInject = runManager.allConfigurationMapMFSelectorItem(true)
            injectMainframerBeforeTasks(mfConfigurations = allConfigurationToInject, replaceAll = replaceAll)
        }
        showInfo(this, "Mainframer injected!")
    }

    private fun Project.showInvalidSettingsError() = showError(this, "Mainframer settings is invalid")

    companion object {
        private val MF_INJECT_BEFORE_RUN_TASK_ACTION = "Inject mainframer before run task"
    }
}
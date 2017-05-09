package com.elpassion.mainframerplugin.action.configure

import com.elpassion.mainframerplugin.common.StringsBundle
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class MFConfigureProjectAction : AnAction(MF_CONFIGURE_PROJECT) {
    override fun actionPerformed(event: AnActionEvent) {
        event.project?.let(::configureMFToProject)
    }

    companion object {
        private val MF_CONFIGURE_PROJECT = StringsBundle.getMessage("configure.action.name")
    }
}

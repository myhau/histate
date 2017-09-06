package com.michalfudala.histate

import com.intellij.execution.Executor
import com.intellij.icons.AllIcons
import com.intellij.openapi.wm.ToolWindowId
import javax.swing.Icon

class HistateExecutor: Executor() {
  override fun getIcon(): Icon {
    return AllIcons.General.Run
  }

  companion object {
    val ID = "Histate"
  }

  override fun getId(): String {
    return ID
  }

  override fun getToolWindowId(): String {
    return ToolWindowId.RUN
  }

  override fun getStartActionText(): String {
    return "Run with histate"
  }

  override fun getActionName(): String {
    return "Histate"
  }

  override fun getToolWindowIcon(): Icon {
    return AllIcons.General.Run
  }

  override fun getDisabledIcon(): Icon? {
    return null
  }

  override fun getContextActionId(): String {
    return "RunHistate"
  }

  override fun getDescription(): String {
    return "histate"
  }

  override fun getHelpId(): String? {
    return null
  }

}
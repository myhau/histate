package com.michalfudala.histate

import com.intellij.execution.configurations.ConfigurationInfoProvider
import com.intellij.execution.configurations.RunProfile
import com.intellij.execution.configurations.RunnerSettings
import com.intellij.execution.impl.DefaultJavaProgramRunner
import org.jdom.Element

class HistateRunner : DefaultJavaProgramRunner() {
  override fun canRun(executorId: String, profile: RunProfile): Boolean {
      return executorId == HistateExecutor.ID
  }



  override fun createConfigurationData(settingsProvider: ConfigurationInfoProvider?): RunnerSettings? {
    return object: RunnerSettings {
      override fun writeExternal(element: Element?) {

      }

      override fun readExternal(element: Element?) {

      }

    }
  }

  override fun getRunnerId(): String {
    return "Histate"
  }
}

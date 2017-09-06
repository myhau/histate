package com.michalfudala.histate

import com.intellij.execution.RunConfigurationExtension
import com.intellij.execution.configurations.JavaParameters
import com.intellij.execution.configurations.RunConfigurationBase
import com.intellij.execution.configurations.RunnerSettings
import com.intellij.openapi.options.SettingsEditor
import javax.swing.JComponent
import javax.swing.JTextArea

data class HistateRunParms(val pattern: String)

class A<B>: SettingsEditor<B>() {
  override fun applyEditorTo(s: B) {
    println("applyEditorTo")
  }

  override fun resetEditorFrom(s: B) {
    println("resetEditorFrom")
  }

  override fun createEditor(): JComponent {
    return JTextArea("siemanko, test")
  }

}

class HistateRunConfigurationExtension : RunConfigurationExtension() {

  override fun isApplicableFor(configuration: RunConfigurationBase): Boolean {
    return true
  }

  override fun <P : RunConfigurationBase> createEditor(configuration: P): SettingsEditor<P> {
    return A()
  }

  override fun getEditorTitle(): String {
    return "histate"
  }

  override fun <T : RunConfigurationBase> updateJavaParameters(configuration: T, params: JavaParameters, runnerSettings: RunnerSettings) {
    println("SUCCESS !")

    params.vmParametersList.add("-javaagent:/Users/mihau/workspace/histate-intellij/build/libs/histate-intellij-unspecified.jar")
  }

}
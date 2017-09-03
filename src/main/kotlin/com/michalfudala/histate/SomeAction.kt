package com.michalfudala.histate

import com.intellij.codeInsight.TargetElementUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.Project
import com.intellij.psi.*

class SomeAction: AnAction() {

  val states: StatesRepository = StatesRepository.instance

  fun findEl(project: Project, document: Document, offset: Int): PsiElement? {
    val file = PsiDocumentManager.getInstance(project).getPsiFile(document) ?: error("cannot get psi file")

    return file!!.findElementAt(TargetElementUtil.adjustOffset(file, document, offset))
  }

  override fun actionPerformed(e: AnActionEvent) {
    val project = e.getData(CommonDataKeys.PROJECT) ?: error("no project")
    val editor = e.getData(CommonDataKeys.EDITOR) ?: error("no editor")

    PsiDocumentManager.getInstance(project).commitAllDocuments()

    val offset = editor.caretModel.primaryCaret.offset

    val psiEl = findEl(project, editor.document, offset)

    val psiIdentifier = psiEl as? PsiIdentifier ?: error("is not identifier")

    val psiMethod = psiIdentifier.parent as? PsiMethod ?: error("parent is not method")



    val name = psiMethod.name

    val packageName = (psiMethod.parent.containingFile as? PsiJavaFile)?.packageName ?: ""

    val returnTypeName = psiMethod.returnType?.canonicalText ?: error("wtf return type ? expected at least void")

    val arguments = psiMethod.parameterList.parameters.map { Argument(it.name ?: error("wtf, no name ?"), it.type.canonicalText) }

    val methodDescription = MethodDescription(packageName, name, returnTypeName, arguments)

    println(methodDescription)

    val statesByMethod = states.findByMethodDescription(methodDescription)

    statesByMethod.forEach {
      println(it)
    }

  }
}
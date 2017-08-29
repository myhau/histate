package com.michalfudala.histate.testing;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import static com.intellij.openapi.actionSystem.PlatformDataKeys.PROJECT_CONTEXT;

public class TestBoxes extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {


        Project p = e.getData(PROJECT_CONTEXT);
        Messages.showMessageDialog(p, "a ", "ttl", Messages.getQuestionIcon());
    }
}

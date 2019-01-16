package com.malukenho.saleforceid;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.wm.IdeFrame;
import com.intellij.openapi.wm.WindowManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class ShowDialog extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {

        IdeFrame ideFrame = WindowManager.getInstance().getIdeFrame(anActionEvent.getProject());
        JComponent ideFrameComponent = ideFrame.getComponent();

        Point middle = new Point(
                (ideFrameComponent.getWidth() - 350) / 2,
                (ideFrameComponent.getHeight() - 100) / 2
        );

        JDialog dialog = new JDialog();
        dialog.setContentPane(mount());
        dialog.setSize(350, 100);
        dialog.setLocation(middle);
        dialog.setVisible(true);
    }

    private static JPanel mount() {
        JPanel container = new JPanel(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        JPanel line1 = new JPanel();
        line1.setLayout(new GridLayout(1, 2));
        line1.add(new JLabel("15 Length ID"));
        final JTextField encodedField = new JTextField(10);
        line1.add(encodedField);

        JPanel line2 = new JPanel();
        line2.setLayout(new GridLayout(1, 2));
        line2.add(new JLabel("Translated"));
        final JTextField decodedField = new JTextField(10);
        line2.add(decodedField);

        // @TODO remove duplication
        encodedField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                decodedField.setText(ConvertSalesForceId.convertTo18CharId(encodedField.getText()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                decodedField.setText(ConvertSalesForceId.convertTo18CharId(encodedField.getText()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                decodedField.setText(ConvertSalesForceId.convertTo18CharId(encodedField.getText()));
            }
        });

        mainPanel.add(line1);
        mainPanel.add(line2);

        container.add(mainPanel, BorderLayout.NORTH);

        return container;
    }
}

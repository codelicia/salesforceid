package com.malukenho.saleforceid;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ex.ClipboardUtil;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.wm.IdeFrame;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.Colors;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.datatransfer.StringSelection;

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
        dialog.setResizable(false);
        dialog.setContentPane(mount());
        dialog.setSize(350, 200);
        dialog.setLocation(middle);
        dialog.setVisible(true);
    }

    private static JPanel mount() {

        String textInClipboard = ClipboardUtil.getTextInClipboard();
        String clipboardResult = ConvertSalesForceId.convertTo18CharId(textInClipboard);

        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(JBUI.Borders.empty(15));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        JPanel line1 = new JPanel();
        line1.setLayout(new GridLayout(1, 2));
        line1.add(new JLabel("15.len() ID"));
        final JTextField encodedField = new JTextField(10);
        if (clipboardResult != null) {
            encodedField.setText(textInClipboard);
        }
        line1.add(encodedField);

        JPanel line2 = new JBPanel();
        JBLabel decodedLabel = new JBLabel("NOPE", SwingConstants.CENTER);
        decodedLabel.setForeground(Colors.DARK_RED);
        decodedLabel.setBorder(JBUI.Borders.emptyTop(20));
        decodedLabel.setFont(new Font(decodedLabel.getName(), Font.PLAIN, 25));

        JBLabel decodedLabel2 = new JBLabel(" ", SwingConstants.CENTER);
        decodedLabel2.setText(" ");

        calculateID(textInClipboard, decodedLabel, decodedLabel2);

        line2.add(decodedLabel);

        JPanel line3 = new JBPanel();
        line3.add(decodedLabel2);

        encodedField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                calculateID(encodedField.getText(), decodedLabel, decodedLabel2);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calculateID(encodedField.getText(), decodedLabel, decodedLabel2);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                calculateID(encodedField.getText(), decodedLabel, decodedLabel2);
            }
        });

        mainPanel.add(line1);
        mainPanel.add(line2);
        mainPanel.add(line3);

        container.add(mainPanel, BorderLayout.NORTH);

        return container;
    }

    private static void calculateID(String encodedField, JLabel decodedLabel, JLabel decodedLabel2) {
        String encoded = ConvertSalesForceId.convertTo18CharId(encodedField);

        if (encoded == null) {
            decodedLabel.setText("NOPE");
            decodedLabel.setForeground(Colors.DARK_RED);
            decodedLabel2.setText("No valid ID were found");
            return;
        }
        CopyPasteManager copyPasteManager = CopyPasteManager.getInstance();
        copyPasteManager.setContents(new StringSelection(encoded));

        decodedLabel.setText(ConvertSalesForceId.convertTo18CharId(encodedField));
        decodedLabel.setForeground(Colors.DARK_GREEN);
        decodedLabel2.setText("Copied to your clipboard!");
    }
}

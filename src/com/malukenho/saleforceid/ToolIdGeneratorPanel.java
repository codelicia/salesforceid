package com.malukenho.saleforceid;

import com.intellij.openapi.ui.SimpleToolWindowPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class ToolIdGeneratorPanel extends SimpleToolWindowPanel {

    public ToolIdGeneratorPanel() {
        super(true);

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

        JPanel line3 = new JPanel();
        line3.add(new JLabel(""));

        mainPanel.add(line3);

        container.add(mainPanel, BorderLayout.NORTH);
        getComponent().add(container);
    }
}

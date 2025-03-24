package com.mycompany.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator {

    private final JFrame frame;
    private final JTextField textField;
    private String currentInput = "";
    private double firstNum = 0;
    private String operator = "";

    public Calculator() {
        frame = new JFrame("Animated Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        textField = new JTextField();
        textField.setEditable(false);
        textField.setFont(new Font("TT Chocolate", Font.PLAIN, 36));
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.WHITE);
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(textField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        String[] buttonLabels = { "7", "8", "9", "/", 
                                  "4", "5", "6", "*", 
                                  "1", "2", "3", "-",
                                  ".", "0", "=", "+", 
                                  "C"};

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("TT Chocolate", Font.PLAIN, 24));
            button.setBackground(new Color(70, 130, 180));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            button.addActionListener(new ButtonClickListener(button));
            buttonPanel.add(button);
        }

        panel.add(buttonPanel, BorderLayout.CENTER);
        frame.add(panel);
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        private final JButton button;

        public ButtonClickListener(JButton button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            new Thread(() -> {
                try {
                    button.setFont(new Font("TT Chocolate", Font.PLAIN, 20));
                    button.setBackground(Color.PINK);
                    button.setSize(button.getWidth() - 10, button.getHeight() - 10);
                    Thread.sleep(100);
                    button.setBackground(new Color(70, 130, 180));
                    button.setFont(new Font("TT Chocolate", Font.PLAIN, 24));
                    button.setSize(button.getWidth() + 10, button.getHeight() + 10);
                } catch (InterruptedException ex) { }
            }).start();

            switch (command) {
                case "=" -> calculate();
                case "/", "*", "-", "+" -> {
                    if (!currentInput.isEmpty()) {
                        firstNum = Double.parseDouble(currentInput);
                        operator = command;
                        currentInput = "";
                        textField.setText("");
                    }
                }
                case "C" -> {
                    currentInput = "";
                    firstNum = 0;
                    operator = "";
                    textField.setText(currentInput);
                }
                default -> {
                    if (currentInput.equals("0")) {
                        currentInput = command;
                    } else {
                        currentInput += command;
                    }
                    textField.setText(currentInput);
                }
            }
        }

        private void calculate() {
            if (currentInput.isEmpty()) return;

            double secondNum = Double.parseDouble(currentInput);
            double result = 0;

            switch (operator) {
                case "+" -> result = firstNum + secondNum;
                case "-" -> result = firstNum - secondNum;
                case "*" -> result = firstNum * secondNum;
                case "/" -> {
                    if (secondNum != 0) {
                        result = firstNum / secondNum;
                    } else {
                        textField.setText("Error");
                        return;
                    }
                }
            }

            textField.setText(String.valueOf(result));
        }
    }

    public static void main(String[] args) {
        new Calculator();
    }
}

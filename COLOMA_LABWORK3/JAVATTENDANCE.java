import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JAVATTENDANCE {
    public static void main(String[] args) {
        // Create the main window
        JFrame frame = new JFrame("LAB CALCULATOR (Auto-Save Enabled)");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // Create a panel for inputs using a Grid layout (Rows, Cols, H-gap, V-gap)
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        
        // UI Components
        JTextField attField = new JTextField();
        JTextField lab1Field = new JTextField();
        JTextField lab2Field = new JTextField();
        JTextField lab3Field = new JTextField();
        JButton calcButton = new JButton("Calculate Grade");
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);

        // Add components to the panel
        inputPanel.add(new JLabel(" Attendance Score:"));
        inputPanel.add(attField);
        inputPanel.add(new JLabel(" Lab Work 1:"));
        inputPanel.add(lab1Field);
        inputPanel.add(new JLabel(" Lab Work 2:"));
        inputPanel.add(lab2Field);
        inputPanel.add(new JLabel(" Lab Work 3:"));
        inputPanel.add(lab3Field);
        inputPanel.add(new JLabel("")); // Empty spacer
        inputPanel.add(calcButton);

        // Logic for the button
        calcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double attendance = Double.parseDouble(attField.getText());
                    double lab1 = Double.parseDouble(lab1Field.getText());
                    double lab2 = Double.parseDouble(lab2Field.getText());
                    double lab3 = Double.parseDouble(lab3Field.getText());

                    double labAvg = (lab1 + lab2 + lab3) / 3.0;
                    double classStanding = (attendance * 0.40) + (labAvg * 0.60);
                    double reqPass = (75.0 - (classStanding * 0.70)) / 0.30;

                    String remarks = (reqPass <= 0) ? "You've already passed!" : "Keep studying!";
                    
                    resultArea.setText(String.format(
                        "Class Standing: %.2f\nRequired Exam Score: %.2f\nRemarks: %s",
                        classStanding, reqPass, remarks
                    ));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid numbers!");
                }
            }
        });

        // Add everything to the frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(resultArea), BorderLayout.CENTER);
        
        frame.setVisible(true);
    }
}
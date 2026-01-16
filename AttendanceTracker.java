import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import javax.swing.*;

public class AttendanceTracker {

    public static void main(String[] args) {
        // ===== FRAME SETUP =====
        JFrame frame = new JFrame("Attendance Tracker (Auto-Save Enabled)");
        frame.setSize(750, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // ===== FORM PANEL (LEFT) =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField nameField = new JTextField(15);
        JTextField courseField = new JTextField(15);
        JTextField timeField = new JTextField(15);
        timeField.setEditable(false);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        timeField.setText(LocalDateTime.now().format(formatter));

        JTextField sigField = new JTextField(15);
        sigField.setEditable(false);
        sigField.setText(UUID.randomUUID().toString().substring(0, 8));

        JButton submitButton = new JButton("Submit & Save Attendance");
        submitButton.setBackground(new Color(70, 130, 180));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);

        // Adding components to the form
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1; formPanel.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Course/Year:"), gbc);
        gbc.gridx = 1; formPanel.add(courseField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Timestamp:"), gbc);
        gbc.gridx = 1; formPanel.add(timeField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(new JLabel("E-Signature:"), gbc);
        gbc.gridx = 1; formPanel.add(sigField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        formPanel.add(submitButton, gbc);

        // ===== LIST PANEL (RIGHT) =====
        JPanel sidePanel = new JPanel(new BorderLayout());
        sidePanel.setBorder(BorderFactory.createTitledBorder("People Who Attended"));
        sidePanel.setPreferredSize(new Dimension(320, 0));

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> attendanceList = new JList<>(listModel);
        attendanceList.setFont(new Font("Monospaced", Font.PLAIN, 12));
        sidePanel.add(new JScrollPane(attendanceList), BorderLayout.CENTER);

        // ===== SUBMIT ACTION & FILE LOGGING =====
        submitButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String course = courseField.getText().trim();
            String timestamp = timeField.getText();
            String signature = sigField.getText();

            if (name.isEmpty() || course.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Error: Name and Course cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // The data format for the text file
            String logEntry = String.format("%s | %-20s | %-15s | Sig: %s", timestamp, name, course, signature);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("attendance_log.txt", true))) {
                // 1. Write to the text file
                writer.write(logEntry);
                writer.newLine();
                
                // 2. Add to the UI List for visual confirmation
                listModel.addElement(name + " [" + course + "]");
                
                // 3. Clear and refresh fields
                nameField.setText("");
                courseField.setText("");
                sigField.setText(UUID.randomUUID().toString().substring(0, 8));
                timeField.setText(LocalDateTime.now().format(formatter));
                nameField.requestFocus();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "File Error: Could not save data.\n" + ex.getMessage());
            }
        });

        // ===== FINAL ASSEMBLY =====
        frame.add(formPanel, BorderLayout.CENTER);
        frame.add(sidePanel, BorderLayout.EAST);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
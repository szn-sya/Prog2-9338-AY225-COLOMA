import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.swing.*;

/**
 * Attendance Tracker Application
 * Now includes a Save button to store records in a text file
 */
public class AttendanceTracker {

    public static void main(String[] args) {

        // Create the main frame
        JFrame frame = new JFrame("Attendance Tracker");
        frame.setSize(450, 330);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Labels
        JLabel nameLabel = new JLabel("Name:");
        JLabel courseLabel = new JLabel("Course / Year:");
        JLabel timeInLabel = new JLabel("Time In:");
        JLabel signatureLabel = new JLabel("E-Signature:");

        // Text fields
        JTextField nameField = new JTextField();
        JTextField courseField = new JTextField();
        JTextField timeInField = new JTextField();
        JTextField signatureField = new JTextField();

        // System date and time
        String timeIn = LocalDateTime.now().toString();
        timeInField.setText(timeIn);
        timeInField.setEditable(false);

        // Generate E-Signature
        String eSignature = UUID.randomUUID().toString();
        signatureField.setText(eSignature);
        signatureField.setEditable(false);

        // Save button
        JButton saveButton = new JButton("Save Attendance");

        // Save button action
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String course = courseField.getText();
            String time = timeInField.getText();
            String signature = signatureField.getText();

            // Basic validation
            if (name.isEmpty() || course.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Please fill in all required fields.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (FileWriter writer = new FileWriter("attendance.txt", true)) {
                writer.write("Name: " + name + "\n");
                writer.write("Course/Year: " + course + "\n");
                writer.write("Time In: " + time + "\n");
                writer.write("E-Signature: " + signature + "\n");
                writer.write("-----------------------------------\n");

                JOptionPane.showMessageDialog(frame,
                        "Attendance saved successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                // Clear input fields and regenerate values
                nameField.setText("");
                courseField.setText("");
                timeInField.setText(LocalDateTime.now().toString());
                signatureField.setText(UUID.randomUUID().toString());

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame,
                        "Error saving attendance.",
                        "File Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add components to panel
        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(courseLabel);
        panel.add(courseField);

        panel.add(timeInLabel);
        panel.add(timeInField);

        panel.add(signatureLabel);
        panel.add(signatureField);

        // Empty label for spacing
        panel.add(new JLabel());
        panel.add(saveButton);

        // Add panel to frame
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

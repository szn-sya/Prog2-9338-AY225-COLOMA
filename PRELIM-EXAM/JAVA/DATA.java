import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.*;

public class DATA extends JFrame {
    private DefaultTableModel model;
    private JTable table;
    private JTextField txtID, txtName, txtL1, txtL2, txtL3, txtSearch;
    private JButton btnAdd, btnDelete;

    // --- CYBER-MIDNIGHT COLOR PALETTE ---
    private final Color BG_NAVY = new Color(13, 17, 23);
    private final Color PANEL_SURFACE = new Color(22, 27, 34);
    private final Color ELECTRIC_BLUE = new Color(88, 166, 255);
    private final Color TEXT_PRIMARY = new Color(201, 209, 217);

    public DATA() {
        setupWindow();
        initHeader();
        initTable();
        initInputPanel();
        loadData("MOCK_DATA.csv");
    }

    private void setupWindow() {
        this.setTitle("Student Database Console - Cyber Edition");
        this.setSize(1000, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(BG_NAVY);
        this.setLayout(new BorderLayout(0, 0));
    }

    private void initHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PANEL_SURFACE);
        header.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 1, 0, new Color(48, 54, 61)),
            new EmptyBorder(20, 25, 20, 25)
        ));

        JLabel title = new JLabel("STUDENT ARCHIVE");
        title.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 24));
        title.setForeground(ELECTRIC_BLUE);

        JPanel searchWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchWrapper.setOpaque(false);
        
        txtSearch = createStyledField(18);
        
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                String query = txtSearch.getText().toLowerCase();
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
                table.setRowSorter(sorter);
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
            }
        });

        searchWrapper.add(new JLabel("🔍  "));
        searchWrapper.add(txtSearch);
        header.add(title, BorderLayout.WEST);
        header.add(searchWrapper, BorderLayout.EAST);
        this.add(header, BorderLayout.NORTH);
    }

    private void initTable() {
        // Updated columns: Removed Prelim/Final Grade
        String[] columns = {"ID", "Full Name", "Lab 1", "Lab 2", "Lab 3"};
        model = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        
        table = new JTable(model);
        table.setBackground(BG_NAVY);
        table.setForeground(TEXT_PRIMARY);
        table.setGridColor(new Color(48, 54, 61));
        table.setSelectionBackground(new Color(31, 111, 235, 100));
        table.setSelectionForeground(Color.WHITE);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setShowVerticalLines(false);
        
        JTableHeader header = table.getTableHeader();
        header.setBackground(PANEL_SURFACE);
        header.setForeground(ELECTRIC_BLUE);
        header.setFont(new Font("Segoe UI Bold", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new EmptyBorder(0, 0, 0, 0));
        scroll.getViewport().setBackground(BG_NAVY);
        this.add(scroll, BorderLayout.CENTER);
    }

    private void initInputPanel() {
        JPanel footer = new JPanel(new GridBagLayout());
        footer.setBackground(PANEL_SURFACE);
        footer.setBorder(new CompoundBorder(
            new MatteBorder(1, 0, 0, 0, new Color(48, 54, 61)),
            new EmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtID = createStyledField(12); 
        txtName = createStyledField(12);
        txtL1 = createStyledField(6);  
        txtL2 = createStyledField(6);
        txtL3 = createStyledField(6);

        // Grid Layout for Inputs (Removed Prelim Grade field)
        addLabeledField(footer, "STUDENT ID", txtID, gbc, 0, 0);
        addLabeledField(footer, "FULL NAME", txtName, gbc, 2, 0);
        addLabeledField(footer, "LAB WORK 1", txtL1, gbc, 0, 1);
        addLabeledField(footer, "LAB WORK 2", txtL2, gbc, 2, 1);
        addLabeledField(footer, "LAB WORK 3", txtL3, gbc, 0, 2);

        // Fixed Button Panel with proper FlowLayout to prevent overlap
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        buttonPanel.setOpaque(false);

        btnAdd = new JButton("ADD ENTRY");
        btnDelete = new JButton("REMOVE SELECTED");
        
        styleButton(btnAdd, new Color(35, 134, 54), Color.WHITE);
        styleButton(btnDelete, new Color(166, 68, 68), Color.WHITE); // Solid red for clarity

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);

        // Place buttons on a new row spanning the width
        gbc.gridx = 0; 
        gbc.gridy = 3; 
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        footer.add(buttonPanel, gbc);

        btnAdd.addActionListener(e -> handleAdd());
        btnDelete.addActionListener(e -> handleDelete());

        this.add(footer, BorderLayout.SOUTH);
    }

    private void handleAdd() {
        if (txtID.getText().isEmpty() || txtName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID and Name are required!");
            return;
        }
        // Removed the 6th parameter (Grade)
        model.addRow(new Object[]{
            txtID.getText(), txtName.getText(), txtL1.getText(), 
            txtL2.getText(), txtL3.getText()
        });
        clearFields();
    }

    private void handleDelete() {
        int row = table.getSelectedRow();
        if (row != -1) {
            model.removeRow(table.convertRowIndexToModel(row));
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student to remove.");
        }
    }

    private JTextField createStyledField(int cols) {
        JTextField f = new JTextField(cols);
        f.setBackground(BG_NAVY);
        f.setForeground(TEXT_PRIMARY);
        f.setCaretColor(ELECTRIC_BLUE);
        f.setBorder(new CompoundBorder(
            new LineBorder(new Color(48, 54, 61), 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        return f;
    }

    private void addLabeledField(JPanel p, String label, JTextField f, GridBagConstraints gbc, int x, int y) {
        JLabel l = new JLabel(label);
        l.setForeground(new Color(139, 148, 158));
        l.setFont(new Font("Segoe UI Bold", Font.PLAIN, 11));
        gbc.gridx = x; gbc.gridy = y; gbc.gridwidth = 1;
        p.add(l, gbc);
        gbc.gridx = x + 1;
        p.add(f, gbc);
    }

    private void styleButton(JButton b, Color bg, Color fg) {
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI Bold", Font.PLAIN, 13));
        b.setBorder(new EmptyBorder(12, 30, 12, 30));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void clearFields() {
        txtID.setText(""); txtName.setText(""); txtL1.setText("");
        txtL2.setText(""); txtL3.setText("");
    }

    private void loadData(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine(); 
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                // Expecting at least 6 columns in CSV (ID, FName, LName, L1, L2, L3)
                if (d.length >= 6) {
                    model.addRow(new Object[]{d[0], d[1]+" "+d[2], d[3], d[4], d[5]});
                }
            }
        } catch (Exception e) { System.out.println("Data Load Error: " + e.getMessage()); }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DATA().setVisible(true));
    }
}
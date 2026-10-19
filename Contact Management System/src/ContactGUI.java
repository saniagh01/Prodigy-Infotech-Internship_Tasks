
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ContactGUI extends JFrame {

    private final ContactManager contactManager;

    private JTable contactTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JLabel contactCountLabel;

    private static final Color PRIMARY_COLOR = new Color(55, 48, 163);
    private static final Color PRIMARY_DARK = new Color(49, 46, 129);
    private static final Color BACKGROUND_COLOR = new Color(248, 250, 252);
    private static final Color TEXT_COLOR = new Color(30, 41, 59);
    private static final Color BORDER_COLOR = new Color(203, 213, 225);
    private static final Color DELETE_COLOR = new Color(220, 38, 38);

    public ContactGUI() {

        contactManager = new ContactManager();

        setTitle("Contact Management System");
        setSize(950, 650);
        setMinimumSize(new Dimension(800, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeUI();
    }

    private void initializeUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(
                new EmptyBorder(25, 30, 25, 30)
        );

        // =====================================================
        // HEADER
        // =====================================================

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(
                new BoxLayout(titlePanel, BoxLayout.Y_AXIS)
        );

        JLabel titleLabel =
                new JLabel("Contact Management System");

        titleLabel.setFont(
                new Font("SansSerif", Font.BOLD, 28)
        );

        titleLabel.setForeground(TEXT_COLOR);

        JLabel subtitleLabel =
                new JLabel(
                        "Organize and manage your contacts efficiently"
                );

        subtitleLabel.setFont(
                new Font("SansSerif", Font.PLAIN, 14)
        );

        subtitleLabel.setForeground(
                new Color(100, 116, 139)
        );

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(6));
        titlePanel.add(subtitleLabel);

        headerPanel.add(
                titlePanel,
                BorderLayout.WEST
        );

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // CENTER PANEL
        // =====================================================

        JPanel centerPanel =
                new JPanel(new BorderLayout(15, 15));

        centerPanel.setOpaque(false);

        // =====================================================
        // SEARCH PANEL
        // =====================================================

        JPanel searchPanel =
                new JPanel(new BorderLayout(10, 10));

        searchPanel.setOpaque(false);

        searchField = new JTextField();

        searchField.setFont(
                new Font("SansSerif", Font.PLAIN, 15)
        );

        searchField.setPreferredSize(
                new Dimension(0, 42)
        );

        searchField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR
                        ),
                        BorderFactory.createEmptyBorder(
                                5, 12, 5, 12
                        )
                )
        );

        searchField.setToolTipText(
                "Search by name, phone number or email"
        );

        JButton searchButton =
                createButton(
                        "Search",
                        PRIMARY_COLOR
                );

        searchButton.setPreferredSize(
                new Dimension(110, 42)
        );

        searchPanel.add(
                searchField,
                BorderLayout.CENTER
        );

        searchPanel.add(
                searchButton,
                BorderLayout.EAST
        );

        centerPanel.add(
                searchPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // TABLE
        // =====================================================

        String[] columns = {
                "Name",
                "Phone Number",
                "Email Address"
        };

        tableModel =
                new DefaultTableModel(columns, 0) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        contactTable =
                new JTable(tableModel);

        contactTable.setRowHeight(38);

        contactTable.setFont(
                new Font("SansSerif", Font.PLAIN, 14)
        );

        contactTable.setForeground(TEXT_COLOR);

        contactTable.setSelectionBackground(
                new Color(224, 231, 255)
        );

        contactTable.setSelectionForeground(
                TEXT_COLOR
        );

        contactTable.setGridColor(
                new Color(226, 232, 240)
        );

        contactTable.setShowGrid(true);

        contactTable.setIntercellSpacing(
                new Dimension(0, 1)
        );

        contactTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );
        contactTable.setSelectionMode(
        ListSelectionModel.SINGLE_SELECTION
);

contactTable.getInputMap(
        JComponent.WHEN_FOCUSED
).put(
        KeyStroke.getKeyStroke("DELETE"),
        "deleteContact"
);

contactTable.getActionMap().put(
        "deleteContact",
        new AbstractAction() {

            @Override
            public void actionPerformed(
                    java.awt.event.ActionEvent e) {

                deleteSelectedContact();
            }
        }
);

        // Table header

        contactTable.getTableHeader().setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        contactTable.getTableHeader().setBackground(
                PRIMARY_COLOR
        );

        contactTable.getTableHeader().setForeground(
                Color.WHITE
        );

        contactTable.getTableHeader().setPreferredSize(
                new Dimension(0, 40)
        );

        // Center table content

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        contactTable.getColumnModel()
                .getColumn(0)
                .setCellRenderer(centerRenderer);

        contactTable.getColumnModel()
                .getColumn(1)
                .setCellRenderer(centerRenderer);

        contactTable.getColumnModel()
                .getColumn(2)
                .setCellRenderer(centerRenderer);

        // Column widths

        contactTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(220);

        contactTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(220);

        contactTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(350);

        JScrollPane tableScrollPane =
                new JScrollPane(contactTable);

        tableScrollPane.setBorder(
                BorderFactory.createLineBorder(
                        BORDER_COLOR
                )
        );

        centerPanel.add(
                tableScrollPane,
                BorderLayout.CENTER
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        // =====================================================
        // BOTTOM PANEL
        // =====================================================

        JPanel bottomPanel =
                new JPanel(new BorderLayout());

        bottomPanel.setOpaque(false);

        // Buttons

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                10,
                                0
                        )
                );

        buttonPanel.setOpaque(false);

        JButton addButton =
                createButton(
                        "+ Add Contact",
                        PRIMARY_COLOR
                );

        JButton editButton =
                createButton(
                        "Edit Contact",
                        new Color(15, 118, 110)
                );

        JButton deleteButton =
                createButton(
                        "Delete Contact",
                        DELETE_COLOR
                );

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Contact counter

        contactCountLabel =
                new JLabel("Total Contacts: 0");

        contactCountLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        contactCountLabel.setForeground(
                new Color(71, 85, 105)
        );

        bottomPanel.add(
                buttonPanel,
                BorderLayout.WEST
        );

        bottomPanel.add(
                contactCountLabel,
                BorderLayout.EAST
        );

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        // =====================================================
        // BUTTON EVENTS
        // =====================================================

        addButton.addActionListener(
                e -> showAddContactDialog()
        );

        editButton.addActionListener(
                e -> showEditContactDialog()
        );

        deleteButton.addActionListener(
                e -> deleteSelectedContact()
        );

        searchButton.addActionListener(
                e -> searchContacts()
        );

        searchField.addActionListener(
                e -> searchContacts()
        );
        searchField.getDocument().addDocumentListener(
        new javax.swing.event.DocumentListener() {

            @Override
            public void insertUpdate(
                    javax.swing.event.DocumentEvent e) {

                searchContacts();
            }

            @Override
            public void removeUpdate(
                    javax.swing.event.DocumentEvent e) {

                searchContacts();
            }

            @Override
            public void changedUpdate(
                    javax.swing.event.DocumentEvent e) {

                searchContacts();
            }
        }
);

        add(mainPanel);

        refreshTable();
    }

    // =========================================================
    // CREATE BUTTON
    // =========================================================

    private JButton createButton(
            String text,
            Color background
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        button.setForeground(Color.WHITE);

        button.setBackground(background);

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 18, 10, 18
                )
        );

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        return button;
    }

    // =========================================================
    // ADD CONTACT
    // =========================================================

    private void showAddContactDialog() {

        JTextField nameField =
                new JTextField();

        JTextField phoneField =
                new JTextField();

        JTextField emailField =
                new JTextField();

        JPanel panel =
                createContactForm(
                        nameField,
                        phoneField,
                        emailField
                );

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Add New Contact",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result == JOptionPane.OK_OPTION) {

            String name =
                    nameField.getText().trim();

            String phone =
                    phoneField.getText().trim();

            String email =
                    emailField.getText().trim();

            if (!validateInput(
                    name,
                    phone,
                    email
            )) {
                return;
            }

            Contact contact =
        new Contact(
                name,
                phone,
                email
        );

boolean added =
        contactManager.addContact(contact);

if (!added) {

    JOptionPane.showMessageDialog(
            this,
            "A contact with this phone number or email already exists.",
            "Duplicate Contact",
            JOptionPane.WARNING_MESSAGE
    );

    return;
}

refreshTable();

JOptionPane.showMessageDialog(
        this,
        "Contact added successfully!",
        "Success",
        JOptionPane.INFORMATION_MESSAGE
);
        }
    }

    // =========================================================
    // EDIT CONTACT
    // =========================================================

    private void showEditContactDialog() {

        int selectedRow =
                contactTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a contact first.",
                    "No Contact Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Contact contact =
                contactManager
                        .getContacts()
                        .get(selectedRow);

        JTextField nameField =
                new JTextField(
                        contact.getName()
                );

        JTextField phoneField =
                new JTextField(
                        contact.getPhone()
                );

        JTextField emailField =
                new JTextField(
                        contact.getEmail()
                );

        JPanel panel =
                createContactForm(
                        nameField,
                        phoneField,
                        emailField
                );

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Edit Contact",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result == JOptionPane.OK_OPTION) {

            String name =
                    nameField.getText().trim();

            String phone =
                    phoneField.getText().trim();

            String email =
                    emailField.getText().trim();

            if (!validateInput(
                    name,
                    phone,
                    email
            )) {
                return;
            }

            contact.setName(name);
            contact.setPhone(phone);
            contact.setEmail(email);

            refreshTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Contact updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // =========================================================
    // CONTACT FORM
    // =========================================================

    private JPanel createContactForm(
            JTextField nameField,
            JTextField phoneField,
            JTextField emailField
    ) {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                3,
                                2,
                                12,
                                15
                        )
                );

        panel.setBorder(
                new EmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        panel.add(
                new JLabel("Name:")
        );

        panel.add(nameField);

        panel.add(
                new JLabel("Phone Number:")
        );

        panel.add(phoneField);

        panel.add(
                new JLabel("Email Address:")
        );

        panel.add(emailField);

        return panel;
    }

    // =========================================================
    // VALIDATION
    // =========================================================

    private boolean validateInput(
            String name,
            String phone,
            String email
    ) {

        if (name.isEmpty()
                || phone.isEmpty()
                || email.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "All fields are required.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );

            return false;
        }

        if (!phone.matches("\\d{10}")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Phone number must contain exactly 10 digits.",
                    "Invalid Phone Number",
                    JOptionPane.WARNING_MESSAGE
            );

            return false;
        }

        if (!email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        )) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid email address.",
                    "Invalid Email",
                    JOptionPane.WARNING_MESSAGE
            );

            return false;
        }

        return true;
    }

    // =========================================================
    // DELETE CONTACT
    // =========================================================

    private void deleteSelectedContact() {

        int selectedRow =
                contactTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a contact first.",
                    "No Contact Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Contact contact =
                contactManager
                        .getContacts()
                        .get(selectedRow);

        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Delete contact \"" +
                                contact.getName() +
                                "\"?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (confirmation ==
                JOptionPane.YES_OPTION) {

            contactManager.deleteContact(
                    selectedRow
            );

            refreshTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Contact deleted successfully.",
                    "Deleted",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // =========================================================
    // SEARCH
    // =========================================================

    private void searchContacts() {

        String keyword =
                searchField.getText().trim();

        if (keyword.isEmpty()) {

            refreshTable();

            return;
        }

        List<Contact> results =
                contactManager.searchContacts(
                        keyword
                );

        tableModel.setRowCount(0);

        for (Contact contact : results) {

            tableModel.addRow(
                    new Object[]{
                            contact.getName(),
                            contact.getPhone(),
                            contact.getEmail()
                    }
            );
        }

        contactCountLabel.setText(
                "Search Results: " +
                        results.size()
        );
    }

    // =========================================================
    // REFRESH TABLE
    // =========================================================

    private void refreshTable() {

        tableModel.setRowCount(0);

        for (Contact contact :
                contactManager.getContacts()) {

            tableModel.addRow(
                    new Object[]{
                            contact.getName(),
                            contact.getPhone(),
                            contact.getEmail()
                    }
            );
        }

        contactCountLabel.setText(
                "Total Contacts: " +
                        contactManager
                                .getContacts()
                                .size()
        );
    }
}


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;

public class TemperatureGUI extends JFrame {

    // Colors
    private static final Color BACKGROUND = new Color(13, 15, 25);
    private static final Color CARD_COLOR = new Color(25, 29, 45);
    private static final Color INPUT_COLOR = new Color(18, 21, 34);
    private static final Color PRIMARY = new Color(124, 92, 255);
    private static final Color PRIMARY_LIGHT = new Color(157, 135, 255);
    private static final Color TEXT = new Color(245, 245, 255);
    private static final Color SECONDARY_TEXT = new Color(167, 171, 194);
    private static final Color MUTED_TEXT = new Color(112, 117, 142);
    private static final Color ERROR = new Color(255, 100, 120);

    // Components
    private JTextField temperatureField;
    private JComboBox<String> unitComboBox;

    private JLabel celsiusResult;
    private JLabel fahrenheitResult;
    private JLabel kelvinResult;

    private JLabel errorLabel;

    private final DecimalFormat decimalFormat =
            new DecimalFormat("0.00");


    public TemperatureGUI() {

        setTitle("TempConvert - Temperature Converter");

        setSize(650, 720);

        setMinimumSize(new Dimension(500, 650));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setResizable(false);

        createInterface();
    }


    private void createInterface() {

        // Main panel
        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new BorderLayout());

        mainPanel.setBackground(BACKGROUND);

        mainPanel.setBorder(
                new EmptyBorder(25, 30, 25, 30)
        );


        // ==============================
        // HEADER
        // ==============================

        JPanel headerPanel = new JPanel(
                new BorderLayout()
        );

        headerPanel.setOpaque(false);


        JLabel logoLabel = new JLabel("°");

        logoLabel.setForeground(Color.WHITE);

        logoLabel.setFont(
                new Font("Arial", Font.BOLD, 32)
        );


        JPanel titlePanel = new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setOpaque(false);


        JLabel titleLabel =
                new JLabel("TempConvert");

        titleLabel.setForeground(TEXT);

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 23)
        );


        JLabel subtitleLabel =
                new JLabel("Temperature Conversion Tool");

        subtitleLabel.setForeground(MUTED_TEXT);

        subtitleLabel.setFont(
                new Font("Arial", Font.PLAIN, 11)
        );


        titlePanel.add(titleLabel);

        titlePanel.add(
                Box.createVerticalStrut(3)
        );

        titlePanel.add(subtitleLabel);


        JPanel logoPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        10,
                        0
                )
        );

        logoPanel.setOpaque(false);

        logoPanel.add(logoLabel);

        logoPanel.add(titlePanel);


        JLabel statusLabel =
                new JLabel("●  READY");

        statusLabel.setForeground(
                new Color(74, 222, 128)
        );

        statusLabel.setFont(
                new Font("Arial", Font.BOLD, 11)
        );


        headerPanel.add(
                logoPanel,
                BorderLayout.WEST
        );

        headerPanel.add(
                statusLabel,
                BorderLayout.EAST
        );


        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );


        // ==============================
        // CENTER CONTENT
        // ==============================

        JPanel contentPanel = new JPanel();

        contentPanel.setLayout(
                new BoxLayout(
                        contentPanel,
                        BoxLayout.Y_AXIS
                )
        );

        contentPanel.setBackground(CARD_COLOR);

        contentPanel.setBorder(
                new EmptyBorder(
                        30,
                        30,
                        30,
                        30
                )
        );


        // Heading

        JLabel smallHeading =
                new JLabel("TEMPERATURE");

        smallHeading.setForeground(
                PRIMARY_LIGHT
        );

        smallHeading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        11
                )
        );


        JLabel heading =
                new JLabel(
                        "<html>Convert temperatures<br>" +
                        "<font color='#A7ABC2'>" +
                        "with precision.</font></html>"
                );

        heading.setForeground(TEXT);

        heading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );


        contentPanel.add(smallHeading);

        contentPanel.add(
                Box.createVerticalStrut(8)
        );

        contentPanel.add(heading);

        contentPanel.add(
                Box.createVerticalStrut(30)
        );


        // ==============================
        // TEMPERATURE INPUT
        // ==============================

        JLabel inputLabel =
                createLabel("Enter Temperature");

        contentPanel.add(inputLabel);

        contentPanel.add(
                Box.createVerticalStrut(8)
        );


        temperatureField =
                new JTextField();

        styleTextField(
                temperatureField
        );

        temperatureField.setToolTipText(
                "Enter a temperature value"
        );


        contentPanel.add(
                temperatureField
        );


        errorLabel =
                new JLabel(" ");

        errorLabel.setForeground(ERROR);

        errorLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );


        contentPanel.add(
                Box.createVerticalStrut(5)
        );

        contentPanel.add(errorLabel);

        contentPanel.add(
                Box.createVerticalStrut(15)
        );


        // ==============================
        // UNIT DROPDOWN
        // ==============================

        JLabel unitLabel =
                createLabel("Original Unit");

        contentPanel.add(unitLabel);

        contentPanel.add(
                Box.createVerticalStrut(8)
        );


        String[] units = {

                "Celsius (°C)",

                "Fahrenheit (°F)",

                "Kelvin (K)"

        };


        unitComboBox =
                new JComboBox<>(units);

        styleComboBox(unitComboBox);


        contentPanel.add(
                unitComboBox
        );


        contentPanel.add(
                Box.createVerticalStrut(20)
        );


        // ==============================
        // CONVERT BUTTON
        // ==============================

        JButton convertButton =
                new JButton("CONVERT  →");

        stylePrimaryButton(
                convertButton
        );


        convertButton.addActionListener(
                this::convertTemperature
        );


        contentPanel.add(
                convertButton
        );


        contentPanel.add(
                Box.createVerticalStrut(8)
        );


        // ==============================
        // RESET BUTTON
        // ==============================

        JButton resetButton =
                new JButton("↻  Reset");

        styleResetButton(
                resetButton
        );


        resetButton.addActionListener(
                e -> resetApplication()
        );


        contentPanel.add(resetButton);

        contentPanel.add(
                Box.createVerticalStrut(25)
        );


        // ==============================
        // RESULTS HEADING
        // ==============================

        JLabel resultsHeading =
                new JLabel("CONVERTED VALUES");

        resultsHeading.setForeground(
                PRIMARY_LIGHT
        );

        resultsHeading.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        10
                )
        );


        contentPanel.add(
                resultsHeading
        );

        contentPanel.add(
                Box.createVerticalStrut(10)
        );


        // ==============================
        // RESULT PANEL
        // ==============================

        JPanel resultPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                10,
                                0
                        )
                );

        resultPanel.setOpaque(false);


        JPanel celsiusCard =
                createResultCard(
                        "°C",
                        "Celsius"
                );

        JPanel fahrenheitCard =
                createResultCard(
                        "°F",
                        "Fahrenheit"
                );

        JPanel kelvinCard =
                createResultCard(
                        "K",
                        "Kelvin"
                );


        celsiusResult =
                getResultLabel(
                        celsiusCard
                );

        fahrenheitResult =
                getResultLabel(
                        fahrenheitCard
                );

        kelvinResult =
                getResultLabel(
                        kelvinCard
                );


        resultPanel.add(celsiusCard);

        resultPanel.add(fahrenheitCard);

        resultPanel.add(kelvinCard);


        contentPanel.add(resultPanel);


        // ==============================
        // ADD CONTENT
        // ==============================

        JScrollPane scrollPane =
                new JScrollPane(
                        contentPanel
                );

        scrollPane.setBorder(null);

        scrollPane.setBackground(CARD_COLOR);

        scrollPane.setHorizontalScrollBarPolicy(
                ScrollPaneConstants
                        .HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(15);


        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        setContentPane(mainPanel);


        // Enter key
        getRootPane()
                .setDefaultButton(
                        convertButton
                );
    }


    // =========================================
    // LABEL CREATOR
    // =========================================

    private JLabel createLabel(String text) {

        JLabel label =
                new JLabel(text);

        label.setForeground(
                SECONDARY_TEXT
        );

        label.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        return label;
    }


    // =========================================
    // TEXT FIELD STYLE
    // =========================================

    private void styleTextField(
            JTextField field
    ) {

        field.setPreferredSize(
                new Dimension(
                        0,
                        50
                )
        );

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        50
                )
        );

        field.setBackground(INPUT_COLOR);

        field.setForeground(TEXT);

        field.setCaretColor(TEXT);

        field.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        16
                )
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(

                        BorderFactory.createLineBorder(
                                new Color(
                                        55,
                                        60,
                                        82
                                )
                        ),

                        new EmptyBorder(
                                5,
                                14,
                                5,
                                14
                        )
                )
        );
    }


    // =========================================
    // COMBO BOX STYLE
    // =========================================

    private void styleComboBox(
            JComboBox<String> comboBox
    ) {

        comboBox.setPreferredSize(
                new Dimension(
                        0,
                        50
                )
        );

        comboBox.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        50
                )
        );

        comboBox.setBackground(INPUT_COLOR);

        comboBox.setForeground(TEXT);

        comboBox.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );
    }


    // =========================================
    // PRIMARY BUTTON
    // =========================================

    private void stylePrimaryButton(
            JButton button
    ) {

        button.setPreferredSize(
                new Dimension(
                        0,
                        52
                )
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        52
                )
        );

        button.setBackground(PRIMARY);

        button.setForeground(Color.WHITE);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        13
                )
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createEmptyBorder()
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }


    // =========================================
    // RESET BUTTON
    // =========================================

    private void styleResetButton(
            JButton button
    ) {

        button.setForeground(
                MUTED_TEXT
        );

        button.setBackground(
                CARD_COLOR
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        10,
                        5,
                        10
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }


    // =========================================
    // RESULT CARD
    // =========================================

    private JPanel createResultCard(
            String symbol,
            String unitName
    ) {

        JPanel panel =
                new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setBackground(
                INPUT_COLOR
        );

        panel.setBorder(
                BorderFactory.createCompoundBorder(

                        BorderFactory.createLineBorder(
                                new Color(
                                        45,
                                        49,
                                        68
                                )
                        ),

                        new EmptyBorder(
                                13,
                                13,
                                13,
                                13
                        )
                )
        );


        JLabel symbolLabel =
                new JLabel(symbol);

        symbolLabel.setForeground(
                PRIMARY_LIGHT
        );

        symbolLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );


        JLabel unitLabel =
                new JLabel(unitName);

        unitLabel.setForeground(
                MUTED_TEXT
        );

        unitLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        10
                )
        );


        JLabel resultLabel =
                new JLabel("—");

        resultLabel.setForeground(TEXT);

        resultLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        19
                )
        );


        panel.add(symbolLabel);

        panel.add(
                Box.createVerticalStrut(8)
        );

        panel.add(unitLabel);

        panel.add(
                Box.createVerticalStrut(4)
        );

        panel.add(resultLabel);


        return panel;
    }


    // =========================================
    // GET RESULT LABEL
    // =========================================

    private JLabel getResultLabel(
            JPanel card
    ) {

        Component[] components =
                card.getComponents();

        for (Component component :
                components) {

            if (component instanceof JLabel) {

                JLabel label =
                        (JLabel) component;

                if (label.getText()
                        .equals("—")) {

                    return label;
                }
            }
        }

        return null;
    }


    // =========================================
    // CONVERSION
    // =========================================

    private void convertTemperature(
            ActionEvent event
    ) {

        errorLabel.setText(" ");


        String input =
                temperatureField
                        .getText()
                        .trim();


        if (input.isEmpty()) {

            showError(
                    "Please enter a temperature value."
            );

            temperatureField.requestFocus();

            return;
        }


        double value;


        try {

            value =
                    Double.parseDouble(input);

        }

        catch (NumberFormatException e) {

            showError(
                    "Please enter a valid number."
            );

            temperatureField.requestFocus();

            return;
        }


        String selectedUnit =
                (String) unitComboBox
                        .getSelectedItem();


        double celsius;

        double fahrenheit;

        double kelvin;


        // ==============================
        // CELSIUS
        // ==============================

        if (selectedUnit.startsWith(
                "Celsius"
        )) {

            if (value < -273.15) {

                showError(
                        "Celsius cannot be below -273.15 °C."
                );

                return;
            }


            celsius = value;

            fahrenheit =
                    TemperatureConverter
                            .celsiusToFahrenheit(
                                    value
                            );

            kelvin =
                    TemperatureConverter
                            .celsiusToKelvin(
                                    value
                            );
        }


        // ==============================
        // FAHRENHEIT
        // ==============================

        else if (
                selectedUnit.startsWith(
                        "Fahrenheit"
                )
        ) {

            if (value < -459.67) {

                showError(
                        "Fahrenheit cannot be below -459.67 °F."
                );

                return;
            }


            fahrenheit = value;

            celsius =
                    TemperatureConverter
                            .fahrenheitToCelsius(
                                    value
                            );

            kelvin =
                    TemperatureConverter
                            .fahrenheitToKelvin(
                                    value
                            );
        }


        // ==============================
        // KELVIN
        // ==============================

        else {

            if (value < 0) {

                showError(
                        "Kelvin cannot be below 0 K."
                );

                return;
            }


            kelvin = value;

            celsius =
                    TemperatureConverter
                            .kelvinToCelsius(
                                    value
                            );

            fahrenheit =
                    TemperatureConverter
                            .kelvinToFahrenheit(
                                    value
                            );
        }


        // ==============================
        // DISPLAY RESULTS
        // ==============================

        celsiusResult.setText(
                decimalFormat.format(celsius)
        );

        fahrenheitResult.setText(
                decimalFormat.format(fahrenheit)
        );

        kelvinResult.setText(
                decimalFormat.format(kelvin)
        );
    }


    // =========================================
    // ERROR MESSAGE
    // =========================================

    private void showError(
            String message
    ) {

        errorLabel.setText(message);
    }


    // =========================================
    // RESET
    // =========================================

    private void resetApplication() {

        temperatureField.setText("");

        unitComboBox.setSelectedIndex(0);

        celsiusResult.setText("—");

        fahrenheitResult.setText("—");

        kelvinResult.setText("—");

        errorLabel.setText(" ");

        temperatureField.requestFocus();
    }


    // =========================================
    // MAIN METHOD
    // =========================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            TemperatureGUI application =
                    new TemperatureGUI();

            application.setVisible(true);
        });
    }
}
package View;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;
import java.awt.*;
import java.sql.*;

/**
 * A utility method used throughout the View package.
 *
 * @author Dillon Crookshank
 * @version 1.0
 */
public final class ViewUtility {
    /** The default font of the UI. */
    public static final Font FONT_DEFAULT = new Font("Trebuchet MS", Font.PLAIN, 15);

    /** The default title font of the UI. */
    public static final Font FONT_TITLE = new Font("Trebuchet MS", Font.BOLD, 50);

    /** The default italic font of the UI. */
    public static final Font FONT_ITALIC = new Font("Trebuchet MS", Font.ITALIC, 15);

    /** The default bold font of the UI. */
    public static final Font FONT_BOLD = new Font("Trebuchet MS", Font.BOLD, 15);

    /** The default background color of the UI. */
    public static final Color COLOR_BACKGROUND = new Color(56, 56, 56);

    /** The default text color of the UI, when text is on the default background color*/
    public static final Color COLOR_TEXT_WHITE = new Color(255, 255, 255);

    /** The default text color of the UI, when text is on the default sub-background color. */
    public static final Color COLOR_TEXT_BLACK = new Color(0, 0, 0);

    /** The default sub-background color. Used when a background is needed in a sub-panel. */
    public static final Color COLOR_SUB_BACKGROUND = new Color(80, 124, 54);

    /** The default border color. Used in table cell borders and in other places that some contrast is needed. */
    public static final Color COLOR_BORDERS = new Color(66, 173, 74);

    /**
     * Creates a large font label.
     * @param theText The text of the label.
     * @return The label.
     */
    public static JLabel createTitle(final String theText) {
        JLabel title = new JLabel(theText);
        title.setBackground(COLOR_BACKGROUND);
        title.setForeground(COLOR_TEXT_WHITE);
        title.setFont(FONT_TITLE);

        return title;
    }

    /**
     * Creates an italic font label.
     * @param theText The text of the label.
     * @return The label.
     */
    public static JLabel createWarningLabel(final String theText) {
        JLabel label = new JLabel(theText);
        label.setBackground(COLOR_BACKGROUND);
        label.setForeground(COLOR_TEXT_WHITE);
        label.setFont(FONT_ITALIC);

        return label;
    }

    /**
     * Creates a default font label.
     * @param theText The text of the label.
     * @return The label.
     */
    public static JLabel createLabel(final String theText) {
        JLabel label = new JLabel(theText);
        label.setBackground(COLOR_BACKGROUND);
        label.setForeground(COLOR_TEXT_WHITE);
        label.setFont(FONT_BOLD);

        return label;
    }

    /**
     * Creates a button.
     * @param theText The text inside the button.
     * @return The button.
     */
    public static JButton createButton(final String theText) {
        JButton button = new JButton(theText);
        button.setBackground(COLOR_SUB_BACKGROUND);
        button.setForeground(COLOR_TEXT_BLACK);
        button.setFont(FONT_DEFAULT);

        return button;
    }

    /**
     * Creates a text field that only accepts strings that match the following regex: [A-Za-z0-9.' -]+
     * @return The text field
     */
    public static JTextField createBaseStatTextField() {
        final JTextField textField = new JTextField();

        PlainDocument document = new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str == null || str.isEmpty())
                    return;
                if (str.matches("\\d+"))
                    super.insertString(offs, str, a);
            }
        };
        textField.setDocument(document);

        textField.setBackground(COLOR_SUB_BACKGROUND);
        textField.setForeground(COLOR_TEXT_BLACK);
        textField.setFont(FONT_DEFAULT);
        textField.setColumns(3);

        return textField;
    }

    /**
     * Creates a text field that only accepts strings that match the following regex: [A-Za-z0-9.' -]+
     * //@param theMaxCharCount The maximum amount of characters that should be accepted in the textField
     * @return The text field
     */
    public static JTextField createWordTextField() {
        final JTextField textField = new JTextField();

        PlainDocument document = new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str == null || str.isEmpty())
                    return;
                if (str.length() > 20)
                    return;
                if (str.matches("[A-Za-z0-9.' -]+"))
                    super.insertString(offs, str, a);
            }
        };
        textField.setDocument(document);

        textField.setBackground(COLOR_SUB_BACKGROUND);
        textField.setForeground(COLOR_TEXT_BLACK);
        textField.setFont(FONT_DEFAULT);
        textField.setColumns(20);

        return textField;
    }


    /**
     * Creates a combo box with the following options:
     * -
     * Normal
     * Fire
     * Water
     * Electric
     * Grass
     * Ice
     * Fighting
     * Poison
     * Ground
     * Flying
     * Psychic
     * Bug
     * Rock
     * Ghost
     * Dragon
     * Dark
     * Steel
     * Fairy
     * @return The combo box.
     */
    public static JComboBox<String> createTypeComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();

        comboBox.addItem("-");
        comboBox.addItem("Normal");
        comboBox.addItem("Fire");
        comboBox.addItem("Water");
        comboBox.addItem("Electric");
        comboBox.addItem("Grass");
        comboBox.addItem("Ice");
        comboBox.addItem("Fighting");
        comboBox.addItem("Poison");
        comboBox.addItem("Ground");
        comboBox.addItem("Flying");
        comboBox.addItem("Psychic");
        comboBox.addItem("Bug");
        comboBox.addItem("Rock");
        comboBox.addItem("Ghost");
        comboBox.addItem("Dragon");
        comboBox.addItem("Dark");
        comboBox.addItem("Steel");
        comboBox.addItem("Fairy");

        comboBox.setBackground(COLOR_SUB_BACKGROUND);
        comboBox.setForeground(COLOR_TEXT_BLACK);
        comboBox.setFont(FONT_DEFAULT);

        return comboBox;
    }

    /**
     * Creates a combo box with the following options:
     * -
     * Physical
     * Special
     * Status
     * @return The combo box
     */
    public static JComboBox<String> createMoveCategoryComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();

        comboBox.addItem("-");
        comboBox.addItem("Physical");
        comboBox.addItem("Special");
        comboBox.addItem("Status");

        comboBox.setBackground(COLOR_SUB_BACKGROUND);
        comboBox.setForeground(COLOR_TEXT_BLACK);
        comboBox.setFont(FONT_DEFAULT);

        return comboBox;
    }

    public static JComboBox<String> createEggGroupComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();

        comboBox.addItem("-");
        comboBox.addItem("Amorphous");
        comboBox.addItem("Bug");
        comboBox.addItem("Dragon");
        comboBox.addItem("Fairy");
        comboBox.addItem("Field");
        comboBox.addItem("Flying");
        comboBox.addItem("Grass");
        comboBox.addItem("Human-Like");
        comboBox.addItem("Mineral");
        comboBox.addItem("Monster");
        comboBox.addItem("Water 1");
        comboBox.addItem("Water 2");
        comboBox.addItem("Water 3");
        comboBox.addItem("Ditto");
        comboBox.addItem("No Eggs Discovered");
        comboBox.addItem("Gender Unknown");

        comboBox.setBackground(COLOR_SUB_BACKGROUND);
        comboBox.setForeground(COLOR_TEXT_BLACK);
        comboBox.setFont(FONT_DEFAULT);

        return comboBox;
    }

    /**
     * Creates a basic checkbox.
     * @param theLabel The label to be displayed next to the checkbox.
     * @return The checkbox.
     */
    public static JCheckBox createCheckBox(final String theLabel) {
        JCheckBox checkBox = new JCheckBox(theLabel);

        checkBox.setBackground(COLOR_BACKGROUND);
        checkBox.setForeground(COLOR_TEXT_WHITE);
        checkBox.setFont(FONT_DEFAULT);

        return checkBox;
    }

    public static JTextArea createTextArea() {
        final JTextArea textArea = new JTextArea();

        PlainDocument document = new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str == null || str.isEmpty())
                    return;
                if (str.length() > 20)
                    return;
                if (str.matches("[A-Za-z0-9.' -]+"))
                    super.insertString(offs, str, a);
            }
        };
        textArea.setDocument(document);

        textArea.setBackground(COLOR_SUB_BACKGROUND);
        textArea.setForeground(COLOR_TEXT_BLACK);
        textArea.setFont(FONT_DEFAULT);
        textArea.setColumns(20);

        return textArea;
    }

    /**
     * Creates a JOptionPane error popup.
     * @param theMessage The message inside the JOptionPane.
     * @param theTitle The title of the popup window.
     */
    public static void createErrorPopup(final String theMessage, final String theTitle) {
        JOptionPane.showMessageDialog(null, theMessage, theTitle, JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Creates a JOptionPane info popup.
     * @param theMessage The message inside the JOptionPane.
     * @param theTitle The title of the popup window.
     */
    public static void createInfoPopup(final String theMessage, final String theTitle) {
        JOptionPane.showMessageDialog(null, theMessage, theTitle, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Takes a ResultSet from an SQL query and dynamically creates a table
     * @param theQueryResults The ResultSet from an SQL query.
     * @return A dynamically created table.
     */
    public static JScrollPane createTable(final ResultSet theQueryResults) {
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);

        table.setEnabled(false);

        int columnCount = 0;
        int[] columnWidths = null;

        try {
            ResultSetMetaData metaData = theQueryResults.getMetaData();
            columnCount = metaData.getColumnCount();

            columnWidths = new int[columnCount];

            //Retrieve column labels
            for (int i = 0; i < columnCount; i++) {
                String columnLabel = metaData.getColumnLabel(i + 1);
                tableModel.addColumn(columnLabel);

                columnWidths[i] = columnLabel.length() * 10;
            }

            // Add rows to the table model
            while (theQueryResults.next()) {
                Object[] rowData = new Object[columnCount];

                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = theQueryResults.getObject(i + 1);

                    //Add a placeholder if the data is null
                    if (rowData[i] == null)
                        rowData[i] = "-";

                    //Calculate the necessary row width if not null
                    else if (metaData.getColumnType(i + 1) == Types.INTEGER)
                        columnWidths[i] = Math.max(columnWidths[i], ((int) Math.log10((int) rowData[i])) * 12);

                    else if (metaData.getColumnType(i + 1) == Types.REAL)
                        columnWidths[i] = Math.max(columnWidths[i], ((int)Math.log10((float)rowData[i])) * 12);

                    else
                        columnWidths[i] = Math.max(columnWidths[i], ((String)rowData[i]).length() * 12);
                }

                tableModel.addRow(rowData);
            }
            theQueryResults.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        //Format each column
        for (int i = 0; i < table.getColumnCount(); i++) {
            TableColumn tableColumn = table.getColumnModel().getColumn(i);

            tableColumn.setPreferredWidth(columnWidths[i]);
            tableColumn.setMinWidth(columnWidths[i]);
        }

        //Format the table
        table.setBackground(COLOR_BACKGROUND);
        table.setForeground(COLOR_TEXT_WHITE);
        table.setFont(FONT_DEFAULT);

        table.setGridColor(COLOR_BORDERS);

        // Set custom header renderer to change colors
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setDefaultRenderer(new CustomHeaderRenderer(tableHeader));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setForeground(COLOR_BACKGROUND);

        //Find the total table width to determine if the table should resize itself
        int tableWidth = 0;
        for (int w : columnWidths) {
            tableWidth += w;
        }

        if (tableWidth < scrollPane.getPreferredSize().getWidth()) {
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        } else {
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }

        return scrollPane;
    }

    /** Used to format the header of a table to match with the theme of the application. */
    private static class CustomHeaderRenderer extends DefaultTableCellRenderer {
        public CustomHeaderRenderer(JTableHeader header) {
            setOpaque(true);
            setForeground(COLOR_TEXT_BLACK);
            setBackground(COLOR_SUB_BACKGROUND); // Change the background color of the header
            setFont(FONT_BOLD);
        }
    }
}
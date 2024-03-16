package View.Panels;

import View.ViewUtility;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Creates a Pokemon search panel that has retrievable data from a form.
 * @author Dillon Crookshank
 * @version 1.0
 */
public class SearchPokemonPanel extends GridPanel {
    /** The 'Return to Main Menu' button. */
    private final JButton myReturnButton;

    /** The 'Submit' button. */
    private final JButton mySubmitButton;

    /** The 'Clear Table' Button. */
    private final JButton myClearTableButton;

    /** The 'Clear Search Queries' button. */
    private final JButton myClearSearchQueriesButton;

    /**
     * <pre>
     * The Scroll pane that holds the table of results.
     * See ViewUtility.createTable(...)
     * </pre>
     */
    private JScrollPane myScrollableTable = null;

    /** The 'Pokemon Name' text field. */
    private final JTextField myPokemonNameField;

    /** The 'Ability Name' text field. */
    private final JTextField myAbilityNameField;

    /** The 'Egg Group' text field. */
    private final JComboBox<String> myEggGroupField;

    /** The 'Learns Move' text field. */
    private final JTextField myLearnsMoveField;

    /** The 'Max HP' text field. */
    private final JTextField myMaxHPField;

    /** The 'Min HP' text field. */
    private final JTextField myMinHPField;

    /** The 'Max Atk' text field. */
    private final JTextField myMaxAtkField;

    /** The 'Min Atk' text field. */
    private final JTextField myMinAtkField;

    /** The 'Max Def' text field. */
    private final JTextField myMaxDefField;

    /** The 'Min Def' text field. */
    private final JTextField myMinDefField;

    /** The 'Max SpAtk' text field. */
    private final JTextField myMaxSpAtkField;

    /** The 'Min SpAtk' text field. */
    private final JTextField myMinSpAtkField;

    /** The 'Max SpDef' text field. */
    private final JTextField myMaxSpDefField;

    /** The 'Min SpDef' text field. */
    private final JTextField myMinSpDefField;

    /** The 'Max Spd' text field. */
    private final JTextField myMaxSpdField;

    /** The 'Min Spd' text field. */
    private final JTextField myMinSpdField;

    /** The 'Primary Type' type combo box selector. */
    private final JComboBox<String> myPrimaryTypeSelector;

    /** The 'Secondary Type' type combo box selector. */
    private final JComboBox<String> mySecondaryTypeSelector;

    /** The 'Type Resistance' type combo box selector. */
    private final JComboBox<String> myTypeResistanceSelector;

    /** The 'Type Weakness' type combo box selector. */
    private final JComboBox<String> myTypeWeaknessSelector;

    /** The 'Show Learnset' check box. */
    private final JCheckBox myShowLearnsetBox;

    /** Creates a Search Pokemon menu to be placed in a card layout. */
    public SearchPokemonPanel() {
        super("Search a Pokemon");

        //Set panel properties
        this.setBackground(ViewUtility.COLOR_BACKGROUND);
        setGBAnchor(GridBagConstraints.CENTER);
        setGBFill(GridBagConstraints.BOTH);
        setGBInsets(3, 3, 3, 3);

        //Initialize the menuTitle
        JLabel menuTitle = ViewUtility.createTitle("Search Pokemon");
        menuTitle.setHorizontalAlignment(SwingConstants.CENTER);

        //Initialize control buttons
        myReturnButton = ViewUtility.createButton("Return to Main Menu");
        mySubmitButton = ViewUtility.createButton("Submit");
        myClearSearchQueriesButton = ViewUtility.createButton("Clear Search Queries");
        myClearTableButton = ViewUtility.createButton("Clear Table");
        myClearTableButton.setEnabled(false);

        //Initialize fields
        myPokemonNameField = ViewUtility.createWordTextField();
        myAbilityNameField = ViewUtility.createWordTextField();
        myEggGroupField = ViewUtility.createEggGroupComboBox();
        myLearnsMoveField = ViewUtility.createWordTextField();

        myMaxHPField = ViewUtility.createBaseStatTextField();
        myMinHPField = ViewUtility.createBaseStatTextField();
        myMaxAtkField = ViewUtility.createBaseStatTextField();
        myMinAtkField = ViewUtility.createBaseStatTextField();
        myMaxDefField = ViewUtility.createBaseStatTextField();
        myMinDefField = ViewUtility.createBaseStatTextField();
        myMaxSpAtkField = ViewUtility.createBaseStatTextField();
        myMinSpAtkField = ViewUtility.createBaseStatTextField();
        myMaxSpDefField = ViewUtility.createBaseStatTextField();
        myMinSpDefField = ViewUtility.createBaseStatTextField();
        myMaxSpdField = ViewUtility.createBaseStatTextField();
        myMinSpdField = ViewUtility.createBaseStatTextField();

        //Initialize selectors
        myPrimaryTypeSelector = ViewUtility.createTypeComboBox();
        mySecondaryTypeSelector = ViewUtility.createTypeComboBox();
        myTypeResistanceSelector = ViewUtility.createTypeComboBox();
        myTypeWeaknessSelector = ViewUtility.createTypeComboBox();

        //Initialize checkbox
        myShowLearnsetBox = ViewUtility.createCheckBox("View Learnset");

        //Add return button
        setGBLocation(0, 0);
        setGBSize(2, 1);
        this.add(myReturnButton, myGridBagCons);

        //Add menu title
        setGBLocation(0, 1);
        setGBSize(2, 1);
        this.add(menuTitle, myGridBagCons);

        //create a new panel to hold all the search parameters
        JPanel searchDataPanel = new JPanel(new GridBagLayout());
        searchDataPanel.setBackground(ViewUtility.COLOR_BACKGROUND);

        //Insert Text Fields and Labels
        setGBSize(1, 1);

        setGBLocation(0, 0);
        searchDataPanel.add(ViewUtility.createLabel("Name:"), myGridBagCons);
        setGBLocation(1, 0);
        searchDataPanel.add(myPokemonNameField, myGridBagCons);

        setGBLocation(0, 1);
        searchDataPanel.add(ViewUtility.createLabel("Ability:"), myGridBagCons);
        setGBLocation(1, 1);
        searchDataPanel.add(myAbilityNameField, myGridBagCons);

        setGBLocation(0, 2);
        searchDataPanel.add(ViewUtility.createLabel("Egg Group:"), myGridBagCons);
        setGBLocation(1, 2);
        searchDataPanel.add(myEggGroupField, myGridBagCons);

        setGBLocation(0, 3);
        searchDataPanel.add(ViewUtility.createLabel("Learns Move:"), myGridBagCons);
        setGBLocation(1, 3);
        searchDataPanel.add(myLearnsMoveField, myGridBagCons);

        //Add Selectors and Labels
        setGBLocation(0, 4);
        searchDataPanel.add(ViewUtility.createLabel("Type 1:"), myGridBagCons);
        setGBLocation(1, 4);
        searchDataPanel.add(myPrimaryTypeSelector, myGridBagCons);

        setGBLocation(0, 5);
        searchDataPanel.add(ViewUtility.createLabel("Type 2:"), myGridBagCons);
        setGBLocation(1, 5);
        searchDataPanel.add(mySecondaryTypeSelector, myGridBagCons);

        setGBLocation(0, 6);
        searchDataPanel.add(ViewUtility.createLabel("Resists:"), myGridBagCons);
        setGBLocation(1, 6);
        searchDataPanel.add(myTypeResistanceSelector, myGridBagCons);

        setGBLocation(0, 7);
        searchDataPanel.add(ViewUtility.createLabel("Weak To:"), myGridBagCons);
        setGBLocation(1, 7);
        searchDataPanel.add(myTypeWeaknessSelector, myGridBagCons);


        //Create a new panel to hold the base stat Text Fields
        JPanel baseStatPanel = new JPanel(new GridBagLayout());
        baseStatPanel.setBackground(ViewUtility.COLOR_BACKGROUND);

        setGBWeight(0.142, 0.3);
        setGBSize(1, 1);
        setGBLocation(1, 0);
        baseStatPanel.add(ViewUtility.createLabel("HP"), myGridBagCons);

        setGBLocation(2, 0);
        baseStatPanel.add(ViewUtility.createLabel("Attack"), myGridBagCons);

        setGBLocation(3, 0);
        baseStatPanel.add(ViewUtility.createLabel("Defense"), myGridBagCons);

        setGBLocation(4, 0);
        baseStatPanel.add(ViewUtility.createLabel("SpAtk"), myGridBagCons);

        setGBLocation(5, 0);
        baseStatPanel.add(ViewUtility.createLabel("SpDef"), myGridBagCons);

        setGBLocation(6, 0);
        baseStatPanel.add(ViewUtility.createLabel("Speed"), myGridBagCons);

        setGBLocation(0, 1);
        baseStatPanel.add(ViewUtility.createLabel("Max: "), myGridBagCons);

        setGBLocation(0, 2);
        baseStatPanel.add(ViewUtility.createLabel("Min: "), myGridBagCons);

        setGBLocation(1, 1);
        baseStatPanel.add(myMaxHPField, myGridBagCons);

        setGBLocation(1, 2);
        baseStatPanel.add(myMinHPField, myGridBagCons);

        setGBLocation(2, 1);
        baseStatPanel.add(myMaxAtkField, myGridBagCons);

        setGBLocation(2, 2);
        baseStatPanel.add(myMinAtkField, myGridBagCons);

        setGBLocation(3, 1);
        baseStatPanel.add(myMaxDefField, myGridBagCons);

        setGBLocation(3, 2);
        baseStatPanel.add(myMinDefField, myGridBagCons);

        setGBLocation(4, 1);
        baseStatPanel.add(myMaxSpAtkField, myGridBagCons);

        setGBLocation(4, 2);
        baseStatPanel.add(myMinSpAtkField, myGridBagCons);

        setGBLocation(5, 1);
        baseStatPanel.add(myMaxSpDefField, myGridBagCons);

        setGBLocation(5, 2);
        baseStatPanel.add(myMinSpDefField, myGridBagCons);

        setGBLocation(6, 1);
        baseStatPanel.add(myMaxSpdField, myGridBagCons);

        setGBLocation(6, 2);
        baseStatPanel.add(myMinSpdField, myGridBagCons);

        //Reset the weight to 0
        setGBWeight(0, 0);

        //Add the whole base stat search panel
        setGBLocation(0, 8);
        setGBSize(2, 1);
        searchDataPanel.add(baseStatPanel, myGridBagCons);

        //Add the checkbox and a warning
        setGBLocation(0, 9);
        searchDataPanel.add(ViewUtility.createWarningLabel("*Only select when above queries result in one pokemon*"), myGridBagCons);

        setGBLocation(0, 10);
        searchDataPanel.add(myShowLearnsetBox, myGridBagCons);

        //Add the clear search queries button and make room for a submit button on the right
        setGBLocation(0, 11);
        searchDataPanel.add(myClearSearchQueriesButton, myGridBagCons);

        //Add the submit button under the search queries
        setGBLocation(0, 12);
        searchDataPanel.add(mySubmitButton, myGridBagCons);

        //Add the clear table button
        setGBLocation(0, 13);
        searchDataPanel.add(myClearTableButton, myGridBagCons);

        //Add the whole search data panel
        setGBInsets(3,25,3,3);
        setGBLocation(0, 2);
        setGBSize(1, 1);
        this.add(searchDataPanel, myGridBagCons);
    }

    /**
     * <pre>
     * Returns a packet of data containing all the search queries inputted in the pokemon search menu.
     * The data is in the following order:
     * -0:PokemonName
     * -1:AbilityName
     * -2:EggGroup
     * -3:LearnsMove
     * -4:PrimaryType
     * -5:SecondaryType
     * -6:TypeResistance
     * -7:TypeWeakness
     * -8:MaxHP
     * -9:MinHP
     * -10:MaxAtk
     * -11:MinAtk
     * -12:MaxDef
     * -13:MinDef
     * -14:MaxSpAtk
     * -15:MinSpAtk
     * -16:MaxSpDef
     * -17:MinSpDef
     * -18:MaxSpeed
     * -19:MinSpeed
     * -20:ShowLearnset
     * </pre>
     * @return A packet of data in the form of a String array
     */
    public String[] getSearchData() {
        return new String[] {
                myPokemonNameField.getText().trim(),
                myAbilityNameField.getText().trim(),
                ((String) myEggGroupField.getSelectedItem()).trim(),
                myLearnsMoveField.getText().trim(),
                ((String) myPrimaryTypeSelector.getSelectedItem()).trim(),
                ((String) mySecondaryTypeSelector.getSelectedItem()).trim(),
                ((String) myTypeResistanceSelector.getSelectedItem()).trim(),
                ((String) myTypeWeaknessSelector.getSelectedItem()).trim(),
                myMaxHPField.getText().trim(),
                myMinHPField.getText().trim(),
                myMaxAtkField.getText().trim(),
                myMinAtkField.getText().trim(),
                myMaxDefField.getText().trim(),
                myMinDefField.getText().trim(),
                myMaxSpAtkField.getText().trim(),
                myMinSpAtkField.getText().trim(),
                myMaxSpDefField.getText().trim(),
                myMinSpDefField.getText().trim(),
                myMaxSpdField.getText().trim(),
                myMinSpdField.getText().trim(),
                myShowLearnsetBox.isSelected() ? "true" : "false"
        };
    }

    /**
     * Sets all search query fields to their default value
     */
    public void clearSearchQueries() {
        myPrimaryTypeSelector.setSelectedIndex(0);
        mySecondaryTypeSelector.setSelectedIndex(0);
        myTypeResistanceSelector.setSelectedIndex(0);
        myTypeWeaknessSelector.setSelectedIndex(0);

        myPokemonNameField.setText("");
        myAbilityNameField.setText("");;
        myEggGroupField.setSelectedIndex(0);;

        myLearnsMoveField.setText("");;

        myMaxHPField.setText("");
        myMinHPField.setText("");
        myMaxAtkField.setText("");
        myMinAtkField.setText("");
        myMaxDefField.setText("");
        myMinDefField.setText("");
        myMaxSpAtkField.setText("");
        myMinSpAtkField.setText("");
        myMaxSpDefField.setText("");
        myMinSpDefField.setText("");
        myMaxSpdField.setText("");
        myMinSpdField.setText("");
    }


    /**
     * <pre>
     * Gives a table from ViewUtility.createTable(...) to display.
     * If null, removes current table and displays nothing.
     * </pre>
     * @param theTable The table to be displayed.
     */
    public void giveTable(JScrollPane theTable) {
        //Remove the previous table if it exists
        if (myScrollableTable != null)
            this.remove(myScrollableTable);

        myScrollableTable = theTable;

        if (myScrollableTable != null) {
            //Add a table to the menu
            setGBLocation(1, 2);
            setGBSize(1, 1);
            setGBFill(GridBagConstraints.NONE);
            this.add(myScrollableTable, myGridBagCons);
            myClearTableButton.setEnabled(true);
        } else {
            //Make sure you cant clear a table when none exist
            myClearTableButton.setEnabled(false);
        }

        this.revalidate();
    }

    /**
     * Gives Action listeners to the externally controlled function of components in the search panel.
     * @param theReturnButtonListener The action listener attached to the 'return to the main menu' button
     * @param theSubmitButtonListener The action listener attached to the 'Submit' button
     * @param theClearTableButtonListener The action listener attached to the 'clear table' button
     * @param theClearSearchQueriesButtonListener The action listener attached to the 'clear search queries' button
     */
    public void setListeners(final ActionListener theReturnButtonListener,
                             final ActionListener theSubmitButtonListener,
                             final ActionListener theClearTableButtonListener,
                             final ActionListener theClearSearchQueriesButtonListener) {
        myReturnButton.addActionListener(theReturnButtonListener);
        mySubmitButton.addActionListener(theSubmitButtonListener);
        myClearTableButton.addActionListener(theClearTableButtonListener);
        myClearSearchQueriesButton.addActionListener(theClearSearchQueriesButtonListener);
    }
}

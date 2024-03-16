package View.Panels;

import View.ViewUtility;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Creates an add Pokemon panel that has retrievable data from a form.
 * @author Dillon Crookshank
 * @version 1.0
 */
public class AddPokemonPanel extends GridPanel {

    /** The 'Return to Main Menu' button. */
    private final JButton myReturnButton;

    /** The 'Submit' button. */
    private final JButton mySubmitButton;

    /** The 'Clear Form' button. */
    private final JButton myClearFormButton;

    /** The 'DexNum' Field. */
    private final JTextField myDexNumField;

    /** The 'Name' Field. */
    private final JTextField myNameField;

    /** The 'Primary Type' combo box. */
    private final JComboBox<String> myPrimaryTypeSelector;

    /** The 'Secondary Type' combo box. */
    private final JComboBox<String> mySecondaryTypeSelector;

    /** The 'Primary Ability' Field. */
    private final JTextField myPrimaryAbilityNameField;

    /** The 'Secondary Ability' Field. */
    private final JTextField mySecondaryAbilityNameField;

    /** The 'Hidden Ability' Field. */
    private final JTextField myHiddenAbilityNameField;

    /** The 'HP' Field. */
    private final JTextField myHPField;

    /** The 'Atk' Field. */
    private final JTextField myAtkField;

    /** The 'Def' Field. */
    private final JTextField myDefField;

    /** The 'SpAtk' Field. */
    private final JTextField mySpAtkField;

    /** The 'SpDef' Field. */
    private final JTextField mySpDefField;

    /** The 'Speed' Field. */
    private final JTextField mySpeedField;

    /** The 'Egg Group 1' Field. */
    private final JComboBox<String> myEggGroup1Field;

    /** The 'Egg Group 2' Field. */
    private final JComboBox<String> myEggGroup2Field;

    /** Creates an Add a Pokemon menu to be placed in a card layout. */
    public AddPokemonPanel (String theName) {
        super(theName);

        this.setBackground(ViewUtility.COLOR_BACKGROUND);

        setGBFill(GridBagConstraints.BOTH);
        setGBAnchor(GridBagConstraints.CENTER);
        setGBInsets(3, 3, 3, 3);


        JLabel title = ViewUtility.createTitle("Add Pokemon");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        myReturnButton = ViewUtility.createButton("Return to Main Menu");
        mySubmitButton = ViewUtility.createButton("Submit");
        myClearFormButton = ViewUtility.createButton("Clear Form");

        myDexNumField = ViewUtility.createBaseStatTextField();
        myNameField = ViewUtility.createWordTextField();
        myPrimaryTypeSelector = ViewUtility.createTypeComboBox();
        mySecondaryTypeSelector = ViewUtility.createTypeComboBox();
        myPrimaryAbilityNameField = ViewUtility.createWordTextField();
        mySecondaryAbilityNameField = ViewUtility.createWordTextField();
        myHiddenAbilityNameField = ViewUtility.createWordTextField();
        myHPField = ViewUtility.createBaseStatTextField();
        myAtkField = ViewUtility.createBaseStatTextField();
        myDefField = ViewUtility.createBaseStatTextField();
        mySpAtkField = ViewUtility.createBaseStatTextField();
        mySpDefField = ViewUtility.createBaseStatTextField();
        mySpeedField = ViewUtility.createBaseStatTextField();
        myEggGroup1Field = ViewUtility.createEggGroupComboBox();
        myEggGroup2Field = ViewUtility.createEggGroupComboBox();

        setGBSize(2, 1);

        setGBLocation(0, 0);
        this.add(myReturnButton, myGridBagCons);

        setGBLocation(0, 1);
        this.add(title, myGridBagCons);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(ViewUtility.COLOR_BACKGROUND);

        setGBSize(1, 1);

        setGBLocation(0, 0);
        formPanel.add(ViewUtility.createLabel("Dex Number: *"), myGridBagCons);
        setGBLocation(1, 0);
        formPanel.add(myDexNumField, myGridBagCons);

        setGBLocation(0, 1);
        formPanel.add(ViewUtility.createLabel("Name: *"), myGridBagCons);
        setGBLocation(1, 1);
        formPanel.add(myNameField, myGridBagCons);

        setGBLocation(0, 2);
        formPanel.add(ViewUtility.createLabel("Primary Type: *"), myGridBagCons);
        setGBLocation(1, 2);
        formPanel.add(myPrimaryTypeSelector, myGridBagCons);

        setGBLocation(0, 3);
        formPanel.add(ViewUtility.createLabel("Secondary Type: "), myGridBagCons);
        setGBLocation(1, 3);
        formPanel.add(mySecondaryTypeSelector, myGridBagCons);

        setGBLocation(0, 4);
        formPanel.add(ViewUtility.createLabel("Primary Ability: *"), myGridBagCons);
        setGBLocation(1, 4);
        formPanel.add(myPrimaryAbilityNameField, myGridBagCons);

        setGBLocation(0, 5);
        formPanel.add(ViewUtility.createLabel("Secondary Ability: "), myGridBagCons);
        setGBLocation(1, 5);
        formPanel.add(mySecondaryAbilityNameField, myGridBagCons);

        setGBLocation(0, 6);
        formPanel.add(ViewUtility.createLabel("Hidden Ability: "), myGridBagCons);
        setGBLocation(1, 6);
        formPanel.add(myHiddenAbilityNameField, myGridBagCons);

        JPanel baseStatPanel = new JPanel(new GridBagLayout());
        baseStatPanel.setBackground(ViewUtility.COLOR_BACKGROUND);

        setGBWeight(0.5, 0.167);

        setGBLocation(0, 0);
        baseStatPanel.add(ViewUtility.createLabel("HP *"), myGridBagCons);
        setGBLocation(0, 1);
        baseStatPanel.add(myHPField, myGridBagCons);

        setGBLocation(1, 0);
        baseStatPanel.add(ViewUtility.createLabel("Atk *"), myGridBagCons);
        setGBLocation(1, 1);
        baseStatPanel.add(myAtkField, myGridBagCons);

        setGBLocation(2, 0);
        baseStatPanel.add(ViewUtility.createLabel("Def *"), myGridBagCons);
        setGBLocation(2, 1);
        baseStatPanel.add(myDefField, myGridBagCons);

        setGBLocation(3, 0);
        baseStatPanel.add(ViewUtility.createLabel("SpAtk *"), myGridBagCons);
        setGBLocation(3, 1);
        baseStatPanel.add(mySpAtkField, myGridBagCons);

        setGBLocation(4, 0);
        baseStatPanel.add(ViewUtility.createLabel("SpDef *"), myGridBagCons);
        setGBLocation(4, 1);
        baseStatPanel.add(mySpDefField, myGridBagCons);

        setGBLocation(5, 0);
        baseStatPanel.add(ViewUtility.createLabel("Speed *"), myGridBagCons);
        setGBLocation(5, 1);
        baseStatPanel.add(mySpeedField, myGridBagCons);

        //Add the base stat panel
        setGBWeight(0, 0);
        setGBSize(2, 1);

        setGBLocation(0, 7);
        formPanel.add(baseStatPanel, myGridBagCons);

        setGBSize(1, 1);

        setGBLocation(0, 8);
        formPanel.add(ViewUtility.createLabel("Egg Group 1: *"), myGridBagCons);
        setGBLocation(1, 8);
        formPanel.add(myEggGroup1Field, myGridBagCons);

        setGBLocation(0, 9);
        formPanel.add(ViewUtility.createLabel("Egg Group 2: "), myGridBagCons);
        setGBLocation(1, 9);
        formPanel.add(myEggGroup2Field, myGridBagCons);

        //Add the form panel
        setGBLocation(0, 2);
        this.add(formPanel, myGridBagCons);

        setGBLocation(0, 3);
        this.add(ViewUtility.createWarningLabel("A * indicates a required field"), myGridBagCons);

        setGBLocation(0, 4);
        this.add(myClearFormButton, myGridBagCons);

        setGBLocation(0, 5);
        this.add(mySubmitButton, myGridBagCons);
    }

    /**
     * <pre>
     * Returns a packet of data containing all the form queries inputted.
     * The data is in the following order:
     * -DexNum
     * -Name
     * -PrimaryType
     * -SecondaryType
     * -PrimaryAbility
     * -SecondaryAbility
     * -HiddenAbility
     * -HP
     * -Attack
     * -Defense
     * -Special Attack
     * -Special Defense
     * -Speed
     * -Egg Group 1
     * -Egg Group 2
     * </pre>
     * @return A packet of data in the form of a String array
     */
    public String[] getFormData() {
        return new String[] {
                myDexNumField.getText().trim(),
                myNameField.getText().trim(),
                ((String) myPrimaryTypeSelector.getSelectedItem()).trim(),
                ((String) mySecondaryTypeSelector.getSelectedItem()).trim(),
                myPrimaryAbilityNameField.getText().trim(),
                mySecondaryAbilityNameField.getText().trim(),
                myHiddenAbilityNameField.getText().trim(),
                myHPField.getText().trim(),
                myAtkField.getText().trim(),
                myDefField.getText().trim(),
                mySpAtkField.getText().trim(),
                mySpDefField.getText().trim(),
                mySpeedField.getText().trim(),
                ((String) myEggGroup1Field.getSelectedItem()).trim(),
                ((String) myEggGroup2Field.getSelectedItem()).trim()
        };
    }

    /** Sets all the form queries to their default values. */
    public void clearForm() {
        myDexNumField.setText("");
        myNameField.setText("");
        myPrimaryTypeSelector.setSelectedIndex(0);
        mySecondaryTypeSelector.setSelectedIndex(0);
        myPrimaryAbilityNameField.setText("");
        mySecondaryAbilityNameField.setText("");
        myHiddenAbilityNameField.setText("");
        myHPField.setText("");
        myAtkField.setText("");
        myDefField.setText("");
        mySpAtkField.setText("");
        mySpDefField.setText("");
        mySpeedField.setText("");
        myEggGroup1Field.setSelectedIndex(0);
        myEggGroup2Field.setSelectedIndex(0);
    }

    /**
     * Gives Action listeners to the externally controlled function of components in the search panel.
     * @param theReturnButtonListener The action listener attached to the 'Return to Main Menu' button.
     * @param theSubmitButtonListener The action listener attached to the Submit' button.
     * @param theClearFormButtonListener The action listener attached to the 'Clear Form' button.
     */
    public void setListeners(final ActionListener theReturnButtonListener,
                             final ActionListener theSubmitButtonListener,
                             final ActionListener theClearFormButtonListener) {
        myReturnButton.addActionListener(theReturnButtonListener);
        mySubmitButton.addActionListener(theSubmitButtonListener);
        myClearFormButton.addActionListener(theClearFormButtonListener);
    }
}

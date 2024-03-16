package View.Panels;

import View.ViewUtility;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Creates an add move panel that has retrievable data from a form.
 * @author Dillon Crookshank
 * @version 1.0
 */
public class AddMovesPanel extends GridPanel {

    /** The 'Return to Main Menu' button. */
    private final JButton myReturnButton;

    /** The 'Submit' button. */
    private final JButton mySubmitButton;

    /** The 'Clear Form' button. */
    private final JButton myClearFormButton;

    /** The 'Name' Field. */
    private final JTextField myNameField;

    /** The 'Primary Type' combo box. */
    private final JComboBox<String> myTypeSelector;

    /** The 'Secondary Type' combo box. */
    private final JComboBox<String> myCategorySelector;

    /** The 'HP' Field. */
    private final JTextField myPowerField;

    /** The 'Atk' Field. */
    private final JTextField myAccuracyField;

    /** The 'Def' Field. */
    private final JTextField myPPField;

    /** The 'Effect' text area. */
    private final JTextArea myEffectArea;

    /** Creates an Add a Move menu to be placed in a card layout. */
    public AddMovesPanel (String theName) {
        super(theName);

        this.setBackground(ViewUtility.COLOR_BACKGROUND);

        setGBFill(GridBagConstraints.BOTH);
        setGBAnchor(GridBagConstraints.CENTER);
        setGBInsets(3, 3, 3, 3);


        JLabel title = ViewUtility.createTitle("Add Moves");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        myReturnButton = ViewUtility.createButton("Return to Main Menu");
        mySubmitButton = ViewUtility.createButton("Submit");
        myClearFormButton = ViewUtility.createButton("Clear Form");

        myNameField = ViewUtility.createWordTextField();
        myTypeSelector = ViewUtility.createTypeComboBox();
        myCategorySelector = ViewUtility.createMoveCategoryComboBox();
        myPowerField = ViewUtility.createBaseStatTextField();
        myAccuracyField = ViewUtility.createBaseStatTextField();
        myPPField = ViewUtility.createBaseStatTextField();
        myEffectArea = ViewUtility.createTextArea();

        myEffectArea.setPreferredSize(new Dimension(150, 300));
        myEffectArea.setLineWrap(true);

        setGBSize(2, 1);

        setGBLocation(0, 0);
        this.add(myReturnButton, myGridBagCons);

        setGBLocation(0, 1);
        this.add(title, myGridBagCons);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(ViewUtility.COLOR_BACKGROUND);

        setGBSize(1, 1);

        setGBLocation(0, 0);
        formPanel.add(ViewUtility.createLabel("Name: *"), myGridBagCons);
        setGBLocation(1, 0);
        formPanel.add(myNameField, myGridBagCons);

        setGBLocation(0, 1);
        formPanel.add(ViewUtility.createLabel("Type: *"), myGridBagCons);
        setGBLocation(1, 1);
        formPanel.add(myTypeSelector, myGridBagCons);

        setGBLocation(0, 2);
        formPanel.add(ViewUtility.createLabel("Category: *"), myGridBagCons);
        setGBLocation(1, 2);
        formPanel.add(myCategorySelector, myGridBagCons);

        JPanel statPanel = new JPanel(new GridBagLayout());
        statPanel.setBackground(ViewUtility.COLOR_BACKGROUND);

        setGBWeight(0.5, 0.333);

        setGBLocation(0, 0);
        statPanel.add(ViewUtility.createLabel("Power"), myGridBagCons);
        setGBLocation(0, 1);
        statPanel.add(myPowerField, myGridBagCons);

        setGBLocation(1, 0);
        statPanel.add(ViewUtility.createLabel("Accuracy"), myGridBagCons);
        setGBLocation(1, 1);
        statPanel.add(myAccuracyField, myGridBagCons);

        setGBLocation(2, 0);
        statPanel.add(ViewUtility.createLabel("PP"), myGridBagCons);
        setGBLocation(2, 1);
        statPanel.add(myPPField, myGridBagCons);

        //Add the base stat panel
        setGBWeight(0, 0);
        setGBSize(2, 1);

        setGBLocation(0, 3);
        formPanel.add(statPanel, myGridBagCons);

        //Add the text area
        setGBLocation(0, 4);
        formPanel.add(ViewUtility.createLabel("Effect:"), myGridBagCons);

        setGBSize(2, 4);

        setGBLocation(0, 5);
        formPanel.add(myEffectArea, myGridBagCons);

        setGBSize(1, 1);

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
     * -Name
     * -Type
     * -Category
     * -Power
     * -Accuracy
     * -PP
     * -Effect
     * </pre>
     * @return A packet of data in the form of a String array
     */
    public String[] getFormData() {
        return new String[] {
                myNameField.getText(),
                (String) myTypeSelector.getSelectedItem(),
                (String) myCategorySelector.getSelectedItem(),
                myPowerField.getText(),
                myAccuracyField.getText(),
                myPPField.getText(),
                myEffectArea.getText()
        };
    }

    /** Sets all the form queries to their default values. */
    public void clearForm() {
        myNameField.setText("");
        myTypeSelector.setSelectedIndex(0);
        myCategorySelector.setSelectedIndex(0);
        myPowerField.setText("");
        myAccuracyField.setText("");
        myPPField.setText("");
        myEffectArea.setText("");
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

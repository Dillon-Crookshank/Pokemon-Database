package View.Panels;

import View.ViewUtility;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Creates an add item panel that has retrievable data from a form.
 * @author Dillon Crookshank
 * @version 1.0
 */
public class AddItemsPanel extends GridPanel {

    /** The 'Return to Main Menu' button. */
    private final JButton myReturnButton;

    /** The 'Submit' button. */
    private final JButton mySubmitButton;

    /** The 'Clear Form' button. */
    private final JButton myClearFormButton;

    /** The 'Add a regular item' checkbox. */
    private final JCheckBox myItemBox;

    /** The 'Add a machine' checkbox. */
    private final JCheckBox myMachineBox;

    /** The 'Add a pokeball' checkbox. */
    private final JCheckBox myPokeballBox;

    /** The 'Name' Field. */
    private final JTextField myNameField;

    /** The text area label, we keep this so we can change it*/
    private final JLabel myTextAreaLabel;

    /** The 'Effect' text area. */
    private final JTextArea myDescriptionArea;

    /** Creates an Add an Item menu to be placed in a card layout. */
    public AddItemsPanel (String theName) {
        super(theName);

        this.setBackground(ViewUtility.COLOR_BACKGROUND);

        setGBFill(GridBagConstraints.BOTH);
        setGBAnchor(GridBagConstraints.CENTER);
        setGBInsets(3, 3, 3, 3);


        JLabel title = ViewUtility.createTitle("Add an Item");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        myReturnButton = ViewUtility.createButton("Return to Main Menu");
        mySubmitButton = ViewUtility.createButton("Submit");
        myClearFormButton = ViewUtility.createButton("Clear Form");

        myItemBox = ViewUtility.createCheckBox("Add a Regular Item");
        myItemBox.setSelected(true);
        myItemBox.setEnabled(false);
        myMachineBox = ViewUtility.createCheckBox("Add a Technical Machine");
        myPokeballBox = ViewUtility.createCheckBox("Add a Pokeball");

        myNameField = ViewUtility.createWordTextField();
        myDescriptionArea = ViewUtility.createTextArea();

        myDescriptionArea.setPreferredSize(new Dimension(150, 300));
        myDescriptionArea.setLineWrap(true);

        setGBSize(2, 1);

        setGBLocation(0, 0);
        this.add(myReturnButton, myGridBagCons);

        setGBLocation(0, 1);
        this.add(title, myGridBagCons);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(ViewUtility.COLOR_BACKGROUND);

        setGBSize(2, 1);

        setGBLocation(0, 0);
        formPanel.add(myItemBox, myGridBagCons);

        setGBLocation(0, 1);
        formPanel.add(myMachineBox, myGridBagCons);

        setGBLocation(0, 2);
        formPanel.add(myPokeballBox, myGridBagCons);

        setGBSize(1, 1);

        setGBLocation(0, 3);
        formPanel.add(ViewUtility.createLabel("Name: *"), myGridBagCons);
        setGBLocation(1, 3);
        formPanel.add(myNameField, myGridBagCons);

        setGBSize(2, 1);

        //Add the text area
        setGBLocation(0, 4);
        myTextAreaLabel = ViewUtility.createLabel("Item Description: *");
        formPanel.add(myTextAreaLabel, myGridBagCons);

        setGBLocation(0, 5);
        formPanel.add(myDescriptionArea, myGridBagCons);

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

        //Add local listeners
        myItemBox.addActionListener(e -> {
            myItemBox.setEnabled(false);
            myMachineBox.setEnabled(true);
            myPokeballBox.setEnabled(true);

            myMachineBox.setSelected(false);
            myPokeballBox.setSelected(false);

            myTextAreaLabel.setText("Item Description: *");
        });

        myMachineBox.addActionListener(e -> {
            myItemBox.setEnabled(true);
            myMachineBox.setEnabled(false);
            myPokeballBox.setEnabled(true);

            myItemBox.setSelected(false);
            myPokeballBox.setSelected(false);

            myTextAreaLabel.setText("Machine Move Name: *");
        });

        myPokeballBox.addActionListener(e -> {
            myItemBox.setEnabled(true);
            myMachineBox.setEnabled(true);
            myPokeballBox.setEnabled(false);

            myItemBox.setSelected(false);
            myMachineBox.setSelected(false);

            myTextAreaLabel.setText("Catch Rate Multiplier Description: *");
        });
    }

    /**
     * <pre>
     * Returns a packet of data containing all the form queries inputted.
     * The data is in the following order:
     * -Name
     * -Description/MoveName/CatchRateDescription
     * -IsItem
     * -IsMachine
     * -IsPokeball
     * </pre>
     * @return A packet of data in the form of a String array
     */
    public String[] getFormData() {
        return new String[] {
                myNameField.getText(),
                myDescriptionArea.getText(),
                myItemBox.isSelected() ? "true" : "false",
                myMachineBox.isSelected() ? "true" : "false",
                myPokeballBox.isSelected() ? "true" : "false"
        };
    }

    /** Sets all the form queries to their default values. */
    public void clearForm() {
        myNameField.setText("");
        myDescriptionArea.setText("");
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

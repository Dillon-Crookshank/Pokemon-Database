package View.Panels;

import View.ViewUtility;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Creates an add ability panel that has retrievable data from a form.
 * @author Dillon Crookshank
 * @version 1.0
 */
public class AddAbilitiesPanel extends GridPanel {

    /** The 'Return to Main Menu' button. */
    private final JButton myReturnButton;

    /** The 'Submit' button. */
    private final JButton mySubmitButton;

    /** The 'Clear Form' button. */
    private final JButton myClearFormButton;

    /** The 'Name' Field. */
    private final JTextField myNameField;

    /** The 'Effect' text area. */
    private final JTextArea myDescriptionArea;

    /** Creates an Add an Ability menu to be placed in a card layout. */
    public AddAbilitiesPanel (String theName) {
        super(theName);

        this.setBackground(ViewUtility.COLOR_BACKGROUND);

        setGBFill(GridBagConstraints.BOTH);
        setGBAnchor(GridBagConstraints.CENTER);
        setGBInsets(3, 3, 3, 3);


        JLabel title = ViewUtility.createTitle("Add an Ability");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        myReturnButton = ViewUtility.createButton("Return to Main Menu");
        mySubmitButton = ViewUtility.createButton("Submit");
        myClearFormButton = ViewUtility.createButton("Clear Form");

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

        setGBSize(1, 1);

        setGBLocation(0, 0);
        formPanel.add(ViewUtility.createLabel("Name: *"), myGridBagCons);
        setGBLocation(1, 0);
        formPanel.add(myNameField, myGridBagCons);

        //Add the text area
        setGBLocation(0, 1);
        formPanel.add(ViewUtility.createLabel("Description: *"), myGridBagCons);

        setGBSize(2, 4);

        setGBLocation(0, 2);
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
    }

    /**
     * <pre>
     * Returns a packet of data containing all the form queries inputted.
     * The data is in the following order:
     * -Name
     * -Description
     * </pre>
     * @return A packet of data in the form of a String array
     */
    public String[] getFormData() {
        return new String[] {
                myNameField.getText(),
                myDescriptionArea.getText()
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

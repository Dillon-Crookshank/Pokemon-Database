package View.Panels;

import View.ViewUtility;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Creates a Move search panel that has retrievable data from a form.
 * @author Dillon Crookshank
 * @version 1.0
 */
public final class SearchMovesPanel extends GridPanel {

    /** The 'Return to Main Menu' button. */
    private final JButton myReturnButton;

    /** The 'Submit' button. */
    private final JButton mySubmitButton;

    /** The 'Clear Table' button. */
    private final JButton myClearTableButton;

    /** The 'Clear Search Queries' button. */
    private final JButton myClearSearchQueriesButton;

    /** The Scroll Pane that holds the results table. */
    private JScrollPane myScrollableTable = null;

    /** The 'Move Type' combo box selector. */
    private final JComboBox<String> myTypeSelector;

    /** The 'Move Category' combo box selector. */
    private final JComboBox<String> myDiscriminatorSelector;

    /** The 'Move Name' text field. */
    private final JTextField myMoveNameSearch;

    /** The 'Max Power' text field. */
    private final JTextField myMaxPower;

    /** The 'Min Power' text field. */
    private final JTextField myMinPower;

    /** The 'Max Accuracy' text field. */
    private final JTextField myMaxAccuracy;

    /** The 'Min Accuracy' text field. */
    private final JTextField myMinAccuracy;

    /** The 'Max PP' text field. */
    private final JTextField myMaxPP;

    /** The 'Min PP' text field. */
    private final JTextField myMinPP;

    /** Creates a Search Moves menu to be placed in a card layout. */
    public SearchMovesPanel() {
        super("SearchMoves");

        //Set panel properties
        this.setBackground(ViewUtility.COLOR_BACKGROUND);

        //
        setGBAnchor(GridBagConstraints.CENTER);
        setGBFill(GridBagConstraints.BOTH);
        setGBInsets(3, 3, 3, 3);

        //Initialize the swing components
        JLabel title = ViewUtility.createTitle("Search Moves");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        myReturnButton = ViewUtility.createButton("Return to Main Menu");
        mySubmitButton = ViewUtility.createButton("Submit");
        myClearSearchQueriesButton = ViewUtility.createButton("Clear Search Queries");
        myClearTableButton = ViewUtility.createButton("Clear Table");
        myClearTableButton.setEnabled(false);

        myTypeSelector = ViewUtility.createTypeComboBox();
        myDiscriminatorSelector = ViewUtility.createMoveCategoryComboBox();

        myMoveNameSearch = ViewUtility.createWordTextField();

        myMaxPower = ViewUtility.createBaseStatTextField();
        myMinPower = ViewUtility.createBaseStatTextField();
        myMaxAccuracy = ViewUtility.createBaseStatTextField();
        myMinAccuracy = ViewUtility.createBaseStatTextField();
        myMaxPP = ViewUtility.createBaseStatTextField();
        myMinPP = ViewUtility.createBaseStatTextField();

        //Place the swing components on the panel
        setGBLocation(0, 0);
        setGBSize(2, 1);
        this.add(myReturnButton, myGridBagCons);

        setGBLocation(0, 1);
        setGBSize(2, 1);
        this.add(title, myGridBagCons);

        //create a new panel to hold all the search parameters
        JPanel searchDataPanel = new JPanel(new GridBagLayout());
        searchDataPanel.setBackground(ViewUtility.COLOR_BACKGROUND);

        //Insert Text field and labels
        setGBSize(1, 1);

        setGBLocation(0, 0);
        searchDataPanel.add(ViewUtility.createLabel("Name:"), myGridBagCons);
        setGBLocation(1, 0);
        searchDataPanel.add(myMoveNameSearch, myGridBagCons);

        //Add type selectors
        setGBLocation(0, 4);
        searchDataPanel.add(ViewUtility.createLabel("Type:"), myGridBagCons);
        setGBLocation(1, 4);
        searchDataPanel.add(myTypeSelector, myGridBagCons);

        setGBLocation(0, 5);
        searchDataPanel.add(ViewUtility.createLabel("Move Category:"), myGridBagCons);
        setGBLocation(1, 5);
        searchDataPanel.add(myDiscriminatorSelector, myGridBagCons);


        //create a new panel to hold the base stats
        JPanel baseStatPanel = new JPanel(new GridBagLayout());
        baseStatPanel.setBackground(ViewUtility.COLOR_BACKGROUND);

        setGBWeight(0.25, 0.3);
        setGBSize(1, 1);
        setGBLocation(1, 0);
        baseStatPanel.add(ViewUtility.createLabel("Power"), myGridBagCons);

        setGBLocation(2, 0);
        baseStatPanel.add(ViewUtility.createLabel("Accuracy"), myGridBagCons);

        setGBLocation(3, 0);
        baseStatPanel.add(ViewUtility.createLabel("PP"), myGridBagCons);

        setGBLocation(0, 1);
        baseStatPanel.add(ViewUtility.createLabel("Max: "), myGridBagCons);

        setGBLocation(0, 2);
        baseStatPanel.add(ViewUtility.createLabel("Min: "), myGridBagCons);

        setGBLocation(1, 1);
        baseStatPanel.add(myMaxPower, myGridBagCons);

        setGBLocation(1, 2);
        baseStatPanel.add(myMinPower, myGridBagCons);

        setGBLocation(2, 1);
        baseStatPanel.add(myMaxAccuracy, myGridBagCons);

        setGBLocation(2, 2);
        baseStatPanel.add(myMinAccuracy, myGridBagCons);

        setGBLocation(3, 1);
        baseStatPanel.add(myMaxPP, myGridBagCons);

        setGBLocation(3, 2);
        baseStatPanel.add(myMinPP, myGridBagCons);

        setGBWeight(0, 0);

        //Add the whole base stat search panel
        setGBLocation(0, 8);
        setGBSize(2, 1);
        searchDataPanel.add(baseStatPanel, myGridBagCons);

        //Add the clear search queries button and make room for a submit button on the right
        setGBLocation(0, 9);
        searchDataPanel.add(myClearSearchQueriesButton, myGridBagCons);

        //Add the submit button under the search queries
        setGBLocation(0, 10);
        searchDataPanel.add(mySubmitButton, myGridBagCons);

        //Add the clear table button
        setGBLocation(0, 11);
        searchDataPanel.add(myClearTableButton, myGridBagCons);

        //Add the whole search data panel
        setGBInsets(3,25,3,3);
        setGBLocation(0, 2);
        setGBSize(1, 1);
        this.add(searchDataPanel, myGridBagCons);
    }

    /**
     * <pre>
     * Returns a packet of data containing all the search queries inputted in the move search menu.
     * The data is in the following order:
     * -0:MoveName
     * -1:MoveCategory
     * -2:Type
     * -3:MaxPower
     * -4:MinPower
     * -5:MaxAccuracy
     * -6:MinAccuracy
     * -7:MaxPP
     * -8:MinPP
     * </pre>
     * @return A packet of data in the form of a String array
     */
    public String[] getSearchData() {
        return new String[] {
                myMoveNameSearch.getText().trim(),
                ((String) myDiscriminatorSelector.getSelectedItem()).trim(),
                ((String) myTypeSelector.getSelectedItem()).trim(),
                myMaxPower.getText().trim(),
                myMinPower.getText().trim(),
                myMaxAccuracy.getText().trim(),
                myMinAccuracy.getText().trim(),
                myMaxPP.getText().trim(),
                myMinPP.getText().trim()
        };
    }

    /**
     * Sets all the search queries to their default values.
     */
    public void clearSearchQueries() {
        myTypeSelector.setSelectedIndex(0);
        myDiscriminatorSelector.setSelectedIndex(0);

        myMoveNameSearch.setText("");

        myMaxPower.setText("");
        myMinPower.setText("");
        myMaxAccuracy.setText("");
        myMinAccuracy.setText("");
        myMaxPP.setText("");
        myMinPP.setText("");
    }

    /**
     * <pre>
     * Gives a table from ViewUtility.createTable(...) to display.
     * If null, removes current table and displays nothing.
     * </pre>
     * @param theTable The table to be displayed.
     */
    public void giveTable(JScrollPane theTable) {
        if (myScrollableTable != null)
            this.remove(myScrollableTable);

        myScrollableTable = theTable;

        setGBLocation(1, 2);
        setGBSize(1, 1);

        if (myScrollableTable != null) {
            setGBFill(GridBagConstraints.NONE);
            this.add(myScrollableTable, myGridBagCons);
            myClearTableButton.setEnabled(true);
        } else {
            myClearTableButton.setEnabled(false);
        }

        this.revalidate();
    }

    /**
     * Gives Action listeners to the externally controlled function of components in the search panel.
     * @param theReturnButtonListener The 'Return to Main Menu' button listener
     * @param theSubmitButtonListener The 'Submit' button listener
     * @param theClearTableButtonListener The 'Clear Table' button listener
     * @param theClearSearchQueriesButtonListener The 'Clear Search Queries' button listener
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

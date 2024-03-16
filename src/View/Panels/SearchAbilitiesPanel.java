package View.Panels;

import View.ViewUtility;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Creates an Ability search panel that has retrievable data from a form.
 * @author Dillon Crookshank
 * @version 1.0
 */
public class SearchAbilitiesPanel extends GridPanel {
    /** The 'Return to Main Menu' button. */
    private final JButton myReturnButton;

    /** The 'Submit' button. */
    private final JButton mySubmitButton;

    /** The 'Clear Table' button. */
    private final JButton myClearTableButton;

    /** The 'Clear Search Queries' button. */
    private final JButton myClearSearchQueriesButton;

    /** The scroll pane that will hold the results table. */
    private JScrollPane myScrollableTable = null;

    /** The 'Ability Name' text field. */
    private JTextField myAbilityNameSearch;

    /** Creates a Search Abilities menu to be placed in a card layout. */
    public SearchAbilitiesPanel() {
        super("SearchAbilities");

        this.setBackground(ViewUtility.COLOR_BACKGROUND);

        setGBAnchor(GridBagConstraints.CENTER);
        setGBFill(GridBagConstraints.BOTH);
        setGBInsets(3, 3, 3, 3);

        //Initialize the swing components
        JLabel title = ViewUtility.createTitle("Search Abilities");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        myReturnButton = ViewUtility.createButton("Return to Main Menu");
        mySubmitButton = ViewUtility.createButton("Submit");
        myClearSearchQueriesButton = ViewUtility.createButton("Clear Search Queries");
        myClearTableButton = ViewUtility.createButton("Clear Table");
        myClearTableButton.setEnabled(false);

        myAbilityNameSearch = ViewUtility.createWordTextField();

        //Initialize the swing components
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

        setGBLocation(0, 1);
        searchDataPanel.add(ViewUtility.createLabel("Ability Name:"), myGridBagCons);
        setGBLocation(1, 1);
        searchDataPanel.add(myAbilityNameSearch, myGridBagCons);

        setGBSize(2, 1);

        //Add the clear search queries button and make room for a submit button on the right
        setGBLocation(0, 2);
        searchDataPanel.add(myClearSearchQueriesButton, myGridBagCons);

        //Add the submit button under the search queries
        setGBLocation(0, 3);
        searchDataPanel.add(mySubmitButton, myGridBagCons);

        //Add the clear table button
        setGBLocation(0, 4);
        searchDataPanel.add(myClearTableButton, myGridBagCons);

        //Add the whole search data panel
        setGBInsets(3,25,3,3);
        setGBLocation(0, 2);
        setGBSize(1, 1);
        this.add(searchDataPanel, myGridBagCons);
    }

    /**
     * <pre>
     * Returns a packet of data containing all the search queries inputted in the ability search menu.
     * The data is in the following order:
     * -AbilityName
     * </pre>
     * @return A packet of data in the form of a String array
     */
    public String[] getSearchData() {
        return new String[] {
                myAbilityNameSearch.getText().trim()
        };
    }

    /** Sets all the search queries to their default values. */
    public void clearSearchQueries() {
        myAbilityNameSearch.setText("");
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

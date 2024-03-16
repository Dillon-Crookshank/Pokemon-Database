package View.Panels;

import View.ViewUtility;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Creates a Location search panel that has retrievable data from a form.
 * @author Dillon Crookshank
 * @version 1.0
 */
public class SearchLocationsPanel extends GridPanel {
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

    /** The 'Region Name' text field. */
    private final JTextField myRegionNameSearch;

    /** The 'Location Name' text field. */
    private final JTextField myLocationNameSearch;

    /** The 'Show Items' checkbox. */
    private final JCheckBox myShowItems;

    /** The 'Show Technical Machines' checkbox. */
    private final JCheckBox myShowTechnicalMachines;

    /** The 'Show Pokeballs' checkbox. */
    private final JCheckBox myShowPokeballs;

    /** The 'Show Pokemon' checkbox. */
    private final JCheckBox myShowPokemon;

    /** Creates a Search Locations menu to be placed in a card layout. */
    public SearchLocationsPanel() {
        super("SearchLocations");

        this.setBackground(ViewUtility.COLOR_BACKGROUND);

        setGBAnchor(GridBagConstraints.CENTER);
        setGBFill(GridBagConstraints.BOTH);
        setGBInsets(3, 3, 3, 3);

        //Initialize swing components
        JLabel title = ViewUtility.createTitle("Search Locations");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        myReturnButton = ViewUtility.createButton("Return to Main Menu");
        mySubmitButton = ViewUtility.createButton("Submit");
        myClearSearchQueriesButton = ViewUtility.createButton("Clear Search Queries");
        myClearTableButton = ViewUtility.createButton("Clear Table");
        myClearTableButton.setEnabled(false);
        myShowPokemon = ViewUtility.createCheckBox("Show Pokemon That Spawn Here");
        myShowItems = ViewUtility.createCheckBox("Show Items That Spawn Here");
        myShowTechnicalMachines = ViewUtility.createCheckBox("Show Tecnical Machines That Spawn Here");
        myShowPokeballs = ViewUtility.createCheckBox("Show Pokeballs That Spawn Here");

        myRegionNameSearch = ViewUtility.createWordTextField();
        myLocationNameSearch = ViewUtility.createWordTextField();

        //Place swing components on panel
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
        searchDataPanel.add(ViewUtility.createLabel("Region Name:"), myGridBagCons);
        setGBLocation(1, 1);
        searchDataPanel.add(myRegionNameSearch, myGridBagCons);

        setGBLocation(0, 2);
        searchDataPanel.add(ViewUtility.createLabel("Location Name:"), myGridBagCons);
        setGBLocation(1, 2);
        searchDataPanel.add(myLocationNameSearch, myGridBagCons);

        setGBSize(2, 1);

        setGBLocation(0, 6);
        searchDataPanel.add(ViewUtility.createWarningLabel("*Only choose selection if above queries result in one Location*"), myGridBagCons);

        setGBLocation(0, 7);
        searchDataPanel.add(myShowPokemon, myGridBagCons);

        setGBLocation(0, 8);
        searchDataPanel.add(myShowItems, myGridBagCons);

        setGBLocation(0, 9);
        searchDataPanel.add(myShowTechnicalMachines, myGridBagCons);

        setGBLocation(0, 10);
        searchDataPanel.add(myShowPokeballs, myGridBagCons);

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

        //Add internal listeners, so that only one checkbox can be selected at one time
        myShowPokemon.addActionListener(
                ShowPokemonSelected -> {
                    myShowItems.setSelected(false);
                    myShowTechnicalMachines.setSelected(false);
                    myShowPokeballs.setSelected(false);
                }
        );

        myShowItems.addActionListener(
                ShowPokemonSelected -> {
                    myShowPokemon.setSelected(false);
                    myShowTechnicalMachines.setSelected(false);
                    myShowPokeballs.setSelected(false);
                }
        );

        myShowTechnicalMachines.addActionListener(
                ShowPokemonSelected -> {
                    myShowPokemon.setSelected(false);
                    myShowItems.setSelected(false);
                    myShowPokeballs.setSelected(false);
                }
        );

        myShowPokeballs.addActionListener(
                ShowPokemonSelected -> {
                    myShowPokemon.setSelected(false);
                    myShowItems.setSelected(false);
                    myShowTechnicalMachines.setSelected(false);
                }
        );
    }

    /**
     * <pre>
     * Returns a packet of data containing all the search queries inputted in the location search menu.
     * The data is in the following order:
     * -0:RegionName
     * -1:LocationName
     * -2:ShowPokemon
     * -3:ShowGeneralItems
     * -4:ShowMachines
     * -5:ShowPokeballs
     * </pre>
     * @return A packet of data in the form of a String array
     */
    public String[] getSearchData() {
        return new String[] {
                myRegionNameSearch.getText().trim(),
                myLocationNameSearch.getText().trim(),
                myShowPokemon.isSelected() ? "true" : "false",
                myShowItems.isSelected() ? "true" : "false",
                myShowTechnicalMachines.isSelected() ? "true" : "false",
                myShowPokeballs.isSelected() ? "true" : "false"
        };
    }

    /** Sets all the search queries to their default values. */
    public void clearSearchQueries() {
        myRegionNameSearch.setText("");
        myLocationNameSearch.setText("");
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

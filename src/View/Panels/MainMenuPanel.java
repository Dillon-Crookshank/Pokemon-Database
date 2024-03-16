package View.Panels;

import View.ViewUtility;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Creates a Main Menu panel.
 * @author Dillon Crookshank
 * @version 1.0
 */
public final class MainMenuPanel extends GridPanel {
    /** The 'Search Pokemon' button. */
    private final JButton SearchPokemonButton;

    /** The 'Add a Pokemon' button. */
    private final JButton AddPokemonButton;

    /** The 'Search Moves' button. */
    private final JButton SearchMovesButton;

    /** The 'Add a Move' button. */
    private final JButton AddMovesButton;

    /** The 'Search Abilities' button. */
    private final JButton SearchAbilitiesButton;

    /** The 'Add an Ability' button. */
    private final JButton AddAbilitiesButton;

    /** The 'Search Locations' button. */
    private final JButton SearchLocationsButton;

    /** The 'Add a Location' button. */
    private final JButton AddLocationsButton;

    /** The 'Search Items' button. */
    private final JButton SearchItemsButton;

    /** The 'Add an Item' button. */
    private final JButton AddItemsButton;


    /** Creates a Main Menu panel to be placed in a card layout. */
    public MainMenuPanel() {
        super("MainMenu");

        //Set panel properties
        this.setBackground(ViewUtility.COLOR_BACKGROUND);

        setGBAnchor(GridBagConstraints.CENTER);
        setGBFill(GridBagConstraints.BOTH);

        //Initialize Swing Components
        JLabel title = ViewUtility.createTitle("Pokemon Database");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        SearchPokemonButton = ViewUtility.createButton("Search Pokemon");
        SearchMovesButton = ViewUtility.createButton("Search Moves");
        SearchAbilitiesButton = ViewUtility.createButton("Search Abilities");
        SearchLocationsButton = ViewUtility.createButton("Search Locations");
        SearchItemsButton = ViewUtility.createButton("Search Items");
        AddPokemonButton = ViewUtility.createButton("Add Pokemon");
        AddMovesButton = ViewUtility.createButton("Add Moves");
        AddAbilitiesButton = ViewUtility.createButton("Add Abilities");
        AddLocationsButton = ViewUtility.createButton("Add Locations");
        AddItemsButton = ViewUtility.createButton("Add Items");

        //Place Swing Components on panel
        setGBWeight(0.5, 1);
        setGBLocation(0, 0);
        setGBSize(2, 1);
        this.add(title, myGridBagCons);

        setGBInsets(5, 150, 5, 50);
        setGBWeight(0.25, 0.1);
        setGBLocation(0, 1);
        setGBSize(1, 1);
        this.add(SearchPokemonButton, myGridBagCons);

        setGBLocation(0, 2);
        setGBSize(1, 1);
        this.add(SearchMovesButton, myGridBagCons);

        setGBLocation(0, 3);
        setGBSize(1, 1);
        this.add(SearchAbilitiesButton, myGridBagCons);

        setGBLocation(0, 4);
        setGBSize(1, 1);
        this.add(SearchLocationsButton, myGridBagCons);

        setGBInsets(5, 150, 50, 50);
        setGBLocation(0, 5);
        setGBSize(1, 1);
        this.add(SearchItemsButton, myGridBagCons);

        setGBInsets(5, 50, 5, 150);
        setGBLocation(1, 1);
        setGBSize(1, 1);
        this.add(AddPokemonButton, myGridBagCons);

        setGBLocation(1, 2);
        setGBSize(1, 1);
        this.add(AddMovesButton, myGridBagCons);

        setGBLocation(1, 3);
        setGBSize(1, 1);
        this.add(AddAbilitiesButton, myGridBagCons);

        setGBLocation(1, 4);
        setGBSize(1, 1);
        this.add(AddLocationsButton, myGridBagCons);

        setGBInsets(5, 50, 50, 150);
        setGBLocation(1, 5);
        setGBSize(1, 1);
        this.add(AddItemsButton, myGridBagCons);

    }

    /**
     * Gives Action listeners to the externally controlled function of components in the main menu panel.
     * @param SearchPokemon The action listener attached to the 'Search a Pokemon' button
     * @param AddPokemon The action listener attached to the 'Add a Pokemon' button
     * @param SearchMoves The action listener attached to the 'Search Moves' button
     * @param AddMove The action listener attached to the 'Add a Move' button
     * @param SearchAbilities The action listener attached to the 'Search Abilities' button
     * @param AddAbilities The action listener attached to the 'Add an Ability' button
     * @param SearchLocations The action listener attached to the 'Search Locations' button
     * @param AddLocation The action listener attached to the 'Add a Location' button
     * @param SearchItems The action listener attached to the 'Search Items' button
     * @param AddItem The action listener attached to the 'Add an Item' button
     */
    public void setListeners(final ActionListener SearchPokemon,
                             final ActionListener AddPokemon,
                             final ActionListener SearchMoves,
                             final ActionListener AddMove,
                             final ActionListener SearchAbilities,
                             final ActionListener AddAbilities,
                             final ActionListener SearchLocations,
                             final ActionListener AddLocation,
                             final ActionListener SearchItems,
                             final ActionListener AddItem) {

        SearchPokemonButton.addActionListener(SearchPokemon);
        AddPokemonButton.addActionListener(AddPokemon);
        SearchMovesButton.addActionListener(SearchMoves);
        AddMovesButton.addActionListener(AddMove);
        SearchAbilitiesButton.addActionListener(SearchAbilities);
        AddAbilitiesButton.addActionListener(AddAbilities);
        SearchLocationsButton.addActionListener(SearchLocations);
        AddLocationsButton.addActionListener(AddLocation);
        SearchItemsButton.addActionListener(SearchItems);
        AddItemsButton.addActionListener(AddItem);
    }
}

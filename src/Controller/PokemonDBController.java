package Controller;

import Model.*;
import View.*;
import View.Panels.*;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.util.Scanner;
import java.io.File;

//**PROBLEMS**
//Text fields don't limit how many characters you can input.
// Is possible for user to input something too long to store in the pre-allocated VARCHARS()
//Table background color when table is too short
//No way to register many-to-many relationships

/**
 * <pre>
 * A controller to the Pokemon Database application.
 * Controls the initialization of the model and view.
 * Controls the flow of data between the model and the view.
 * </pre>
 * @author Dillon Crookshank
 * @version 1.0
 */
public class PokemonDBController {
    /**
     * Runs the application.
     * @param args Ignored.
     */
    public static void main(String[] args) {
        new PokemonDBController();
    }

    /** The title of the window. */
    private static final String TITLE = "Pokemon Database";

    /** An instance of the database. */
    private final Database myDatabase;

    /** The main window the application is stored in. */
    private final JFrame myWindow;

    /** The main content panel of the window, supports switching between different panels. */
    private final JPanel myCardPanel;

    /** The layout manager of the main panel. Allows for changing the viewed panel to simulate different menus. */
    private final CardLayout myCardPanelLayoutManager;

    /** The panel that holds the 'Main' menu. */
    private final MainMenuPanel myMainMenuPanel;

    /** The panel that holds the 'Search a Pokemon' menu. */
    private final SearchPokemonPanel mySearchPokemonPanel;

    /** The panel that holds the 'Add a Pokemon' menu. */
    private final AddPokemonPanel myAddPokemonPanel;

    /** The panel that holds the 'Search Moves' menu. */
    private final SearchMovesPanel mySearchMovesPanel;

    /** The panel that holds the 'Add a Move' menu. */
    private final AddMovesPanel myAddMovesPanel;

    /** The panel that holds the 'Search Abilities' menu. */
    private final SearchAbilitiesPanel mySearchAbilitiesPanel;

    /** The panel that holds the 'Add an Ability' menu. */
    private final AddAbilitiesPanel myAddAbilitiesPanel;

    /** The panel that holds the 'Search Locations' menu. */
    private final SearchLocationsPanel mySearchLocationsPanel;

    /** The panel that holds the 'Add a Location' menu. */
    private final AddLocationsPanel myAddLocationsPanel;

    /** The panel that holds the 'Search Items' menu. */
    private final SearchItemsPanel mySearchItemsPanel;

    /** The panel that holds the 'Add an Item' menu. */
    private final AddItemsPanel myAddItemsPanel;

    /** Creates an instance of the Pokemon database application. */
    public PokemonDBController() {
        //retrieve the Database URL, Username, and Password from the config file
        Scanner configFile = null;
        try {
            configFile = new Scanner(new File("db_config.txt"));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        String URL = configFile.nextLine().split(":=")[1].trim();
        String username = configFile.nextLine().split(":=")[1].trim();
        String password = configFile.nextLine().split(":=")[1].trim();

        configFile.close();

        //Connect to the database
        myDatabase = new Database(URL, username, password);

        //Initialize the cardPanel
        myCardPanel = new JPanel(new CardLayout());
        myCardPanelLayoutManager = (CardLayout) myCardPanel.getLayout();
        myCardPanel.setBackground(ViewUtility.COLOR_BACKGROUND);

        //Initialize all the sub-panels that will belong to the card layout panel
        myMainMenuPanel = new MainMenuPanel();
        mySearchPokemonPanel = new SearchPokemonPanel();
        myAddPokemonPanel = new AddPokemonPanel("AP");
        mySearchMovesPanel = new SearchMovesPanel();
        myAddMovesPanel = new AddMovesPanel("AM");
        mySearchAbilitiesPanel = new SearchAbilitiesPanel();
        myAddAbilitiesPanel = new AddAbilitiesPanel("AA");
        mySearchLocationsPanel = new SearchLocationsPanel();
        myAddLocationsPanel = new AddLocationsPanel("AL");
        mySearchItemsPanel = new SearchItemsPanel();
        myAddItemsPanel = new AddItemsPanel("AI");

        //Add all the sub-panels to the cardPanel
        myCardPanel.add(myMainMenuPanel, myMainMenuPanel.getName());
        myCardPanel.add(mySearchPokemonPanel, mySearchPokemonPanel.getName());
        myCardPanel.add(myAddPokemonPanel, myAddPokemonPanel.getName());
        myCardPanel.add(mySearchMovesPanel, mySearchMovesPanel.getName());
        myCardPanel.add(myAddMovesPanel, myAddMovesPanel.getName());
        myCardPanel.add(mySearchAbilitiesPanel, mySearchAbilitiesPanel.getName());
        myCardPanel.add(myAddAbilitiesPanel, myAddAbilitiesPanel.getName());
        myCardPanel.add(mySearchLocationsPanel, mySearchLocationsPanel.getName());
        myCardPanel.add(myAddLocationsPanel, myAddLocationsPanel.getName());
        myCardPanel.add(mySearchItemsPanel, mySearchItemsPanel.getName());
        myCardPanel.add(myAddItemsPanel, myAddItemsPanel.getName());

        //Make sure the MainMenu is the first to show
        myCardPanelLayoutManager.show(myCardPanel, myMainMenuPanel.getName());

        //Initialize the window
        myWindow = new JFrame(TITLE);

        //Initialize Listeners
        initListeners();

        //Initialize the window
        EventQueue.invokeLater(() -> {
            //Add the cardPanel to the window
            myWindow.setContentPane(myCardPanel);

            //Set window properties
            //myWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            myWindow.pack();
            myWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
            myWindow.setMinimumSize(new Dimension(myWindow.getWidth(), myWindow.getHeight()));
            myWindow.setResizable(false);

            myCardPanel.setMinimumSize(myWindow.getSize());

            //do a bit of math so that the window opens up in the middle of the screen.
            final Dimension position = Toolkit.getDefaultToolkit().getScreenSize();
            position.setSize((int) (position.getWidth() - myWindow.getWidth()) / 2,
                    (int) (position.getHeight() - myWindow.getHeight()) / 2);
            myWindow.setLocation((int) position.getWidth(), (int) position.getHeight());

            //Show window
            myWindow.setVisible(true);
        });
    }

    /** Initializes every listener of every sub-panel in the controller. */
    private void initListeners() {
        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Main Menu Listeners =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
        myMainMenuPanel.setListeners(
                SearchPokemonPressed -> {
                    //Show the Search a Pokemon menu
                    myCardPanelLayoutManager.show(myCardPanel, mySearchPokemonPanel.getName());
                },
                AddPokemonPressed -> {
                    //Show the Add a Pokemon menu
                    myCardPanelLayoutManager.show(myCardPanel, myAddPokemonPanel.getName());
                },
                SearchMovesPressed -> {
                    //Show the Search Moves menu
                    myCardPanelLayoutManager.show(myCardPanel, mySearchMovesPanel.getName());
                },
                AddMovesPressed -> {
                    //Show the Add a Move menu
                    myCardPanelLayoutManager.show(myCardPanel, myAddMovesPanel.getName());
                },
                SearchAbilitiesPressed -> {
                    //Show the Search Abilities menu
                    myCardPanelLayoutManager.show(myCardPanel, mySearchAbilitiesPanel.getName());
                },
                AddAbilitiesPressed -> {
                    //Show the Add an Ability menu
                    myCardPanelLayoutManager.show(myCardPanel, myAddAbilitiesPanel.getName());
                },
                SearchLocationsPressed -> {
                    //Show the Search Locations menu
                    myCardPanelLayoutManager.show(myCardPanel, mySearchLocationsPanel.getName());
                },
                AddLocationPressed -> {
                    //Show the Add a Location menu
                    myCardPanelLayoutManager.show(myCardPanel, myAddLocationsPanel.getName());
                },
                SearchItemsPressed -> {
                    //Show the Search Items menu
                    myCardPanelLayoutManager.show(myCardPanel, mySearchItemsPanel.getName());
                },
                AddItemsPressed -> {
                    //Show the Add an Item menu
                    myCardPanelLayoutManager.show(myCardPanel, myAddItemsPanel.getName());
                }
        );

        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Search a Pokemon Menu Listeners =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
        mySearchPokemonPanel.setListeners(
                ReturnPressed -> {
                    //Show the main menu
                    myCardPanelLayoutManager.show(myCardPanel, myMainMenuPanel.getName());

                    //Reset state of the menu
                    mySearchPokemonPanel.giveTable(null);
                    mySearchPokemonPanel.clearSearchQueries();
                },
                SubmitPressed -> {
                    //Take data entered in menu,
                    //preform an SQL query on the data,
                    //create, and give the table back to the menu
                    String[] queryData = mySearchPokemonPanel.getSearchData();
                    ResultSet sqlResults = myDatabase.searchPokemon(
                            queryData[0],
                            queryData[1],
                            queryData[2],
                            queryData[3],
                            queryData[4],
                            queryData[5],
                            queryData[6],
                            queryData[7],
                            queryData[8],
                            queryData[9],
                            queryData[10],
                            queryData[11],
                            queryData[12],
                            queryData[13],
                            queryData[14],
                            queryData[15],
                            queryData[16],
                            queryData[17],
                            queryData[18],
                            queryData[19],
                            queryData[20]);

                    JScrollPane table = ViewUtility.createTable(sqlResults);
                    mySearchPokemonPanel.giveTable(table);
                    myWindow.repaint();
                },
                ClearTablePressed -> {
                    //Remove the table from the menu
                    mySearchPokemonPanel.giveTable(null);
                    myWindow.repaint();
                },
                ClearSearchQueriesPressed -> {
                    //Clear the search queries in the menu
                    mySearchPokemonPanel.clearSearchQueries();
                }
        );

        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Search Moves Menu Listeners =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
        mySearchMovesPanel.setListeners(
                ReturnPressed -> {
                    //Show the main menu
                    myCardPanelLayoutManager.show(myCardPanel, myMainMenuPanel.getName());

                    //Reset state of the menu
                    mySearchMovesPanel.giveTable(null);
                    mySearchMovesPanel.clearSearchQueries();
                },
                SubmitPressed -> {
                    //Take data entered in menu,
                    //preform an SQL query on the data,
                    //create, and give the table back to the menu
                    String[] queryData = mySearchMovesPanel.getSearchData();
                    ResultSet sqlResults = myDatabase.searchMove(queryData[0],queryData[1],queryData[2],queryData[3],queryData[4],queryData[5],queryData[6],queryData[7],queryData[8]);
                    JScrollPane table = ViewUtility.createTable(sqlResults);
                    mySearchMovesPanel.giveTable(table);
                    myWindow.repaint();
                },
                ClearTablePressed -> {
                    //Remove the table from the menu
                    mySearchMovesPanel.giveTable(null);
                    myWindow.repaint();
                },
                ClearSearchQueriesPressed -> {
                    //Clear the search queries in the menu
                    mySearchMovesPanel.clearSearchQueries();
                }
        );

        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Search Abilities Menu Listeners =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
        mySearchAbilitiesPanel.setListeners(
                ReturnPressed -> {
                    //Show the main menu
                    myCardPanelLayoutManager.show(myCardPanel, myMainMenuPanel.getName());

                    //Reset state of the menu
                    mySearchAbilitiesPanel.giveTable(null);
                    mySearchAbilitiesPanel.clearSearchQueries();
                },
                SubmitPressed -> {
                    //Take data entered in menu,
                    //preform an SQL query on the data,
                    //create, and give the table back to the menu
                    String[] queryData = mySearchAbilitiesPanel.getSearchData();
                    ResultSet sqlResults = myDatabase.searchAbilities(queryData[0]);
                    JScrollPane table = ViewUtility.createTable(sqlResults);
                    mySearchAbilitiesPanel.giveTable(table);
                    myWindow.repaint();
                },
                ClearTablePressed -> {
                    //Remove the table from the menu
                    mySearchAbilitiesPanel.giveTable(null);
                    myWindow.repaint();
                },
                ClearSearchQueriesPressed -> {
                    //Clear the search queries in the menu
                    mySearchAbilitiesPanel.clearSearchQueries();
                }
        );

        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Search Locations Menu Listeners =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
        mySearchLocationsPanel.setListeners(
                ReturnPressed -> {
                    //Show the main menu
                    myCardPanelLayoutManager.show(myCardPanel, myMainMenuPanel.getName());

                    //Reset state of the menu
                    mySearchLocationsPanel.giveTable(null);
                    mySearchLocationsPanel.clearSearchQueries();
                },
                SubmitPressed -> {
                    //Take data entered in menu,
                    //preform an SQL query on the data,
                    //create, and give the table back to the menu
                    String[] queryData = mySearchLocationsPanel.getSearchData();
                    ResultSet sqlResults = myDatabase.searchLocations(
                            queryData[0],
                            queryData[1],
                            queryData[2],
                            queryData[3],
                            queryData[4],
                            queryData[5]);

                    JScrollPane table = ViewUtility.createTable(sqlResults);
                    mySearchLocationsPanel.giveTable(table);
                    myWindow.repaint();
                },
                ClearTablePressed -> {
                    //Remove the table from the menu
                    mySearchLocationsPanel.giveTable(null);
                    myWindow.repaint();
                },
                ClearSearchQueriesPressed -> {
                    //Clear the search queries in the menu
                    mySearchLocationsPanel.clearSearchQueries();
                }
        );

        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Search Items Menu Listeners =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
        mySearchItemsPanel.setListeners(
                ReturnPressed -> {
                    //Show the main menu
                    myCardPanelLayoutManager.show(myCardPanel, myMainMenuPanel.getName());

                    //Reset state of the menu
                    mySearchItemsPanel.giveTable(null);
                    mySearchItemsPanel.clearSearchQueries();
                },
                SubmitPressed -> {
                    //Take data entered in menu,
                    //preform an SQL query on the data,
                    //create, and give the table back to the menu
                    String[] queryData = mySearchItemsPanel.getSearchData();
                    ResultSet sqlResults = myDatabase.searchItems(queryData[0],queryData[1],queryData[2],queryData[3]);
                    JScrollPane table = ViewUtility.createTable(sqlResults);
                    mySearchItemsPanel.giveTable(table);
                    myWindow.repaint();
                },
                ClearTablePressed -> {
                    //Remove the table from the menu
                    mySearchItemsPanel.giveTable(null);
                    myWindow.repaint();
                },
                ClearSearchQueriesPressed -> {
                    //Clear the search queries in the menu
                    mySearchItemsPanel.clearSearchQueries();
                }
        );

        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Add a Pokemon Menu Listeners =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
        myAddPokemonPanel.setListeners(
                ReturnPressed -> {
                    //Show the main menu
                    myCardPanelLayoutManager.show(myCardPanel, myMainMenuPanel.getName());
                    myAddPokemonPanel.clearForm();
                },
                SubmitPressed -> {
                    //Determine if we should use UPDATE or INSERT, then display message based on success of the SQL operation.
                    String[] formData = myAddPokemonPanel.getFormData();
                    boolean success;
                    if (myDatabase.doesPokemonExist(formData[0])) {
                        success = myDatabase.updatePokemon(
                                formData[0],
                                formData[1],
                                formData[2],
                                formData[3],
                                formData[4],
                                formData[5],
                                formData[6],
                                formData[7],
                                formData[8],
                                formData[9],
                                formData[10],
                                formData[11],
                                formData[12],
                                formData[13],
                                formData[14]
                                );

                    } else {
                        success = myDatabase.insertPokemon(
                                formData[0],
                                formData[1],
                                formData[2],
                                formData[3],
                                formData[4],
                                formData[5],
                                formData[6],
                                formData[7],
                                formData[8],
                                formData[9],
                                formData[10],
                                formData[11],
                                formData[12],
                                formData[13],
                                formData[14]
                        );

                    }

                    if (success)
                        ViewUtility.createInfoPopup("Pokemon Added Successfully!", "Success!");
                    else
                        ViewUtility.createErrorPopup("Pokemon Insert/Update Failed!\nDid you input all required fields?\nIs the input too long?", "Failure!");
                },
                ClearFormPressed -> {
                    myAddPokemonPanel.clearForm();
                }
        );

        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Add a Move Menu Listeners =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
        myAddMovesPanel.setListeners(
                ReturnPressed -> {
                    //Show the main menu
                    myCardPanelLayoutManager.show(myCardPanel, myMainMenuPanel.getName());
                    myAddMovesPanel.clearForm();
                },
                SubmitPressed -> {
                    //Determine if we should use UPDATE or INSERT, then display message based on success of the SQL operation.
                    String[] formData = myAddMovesPanel.getFormData();
                    boolean success;
                    if (myDatabase.doesMoveExist(formData[0])) {
                        success = myDatabase.updateMove(
                                formData[0],
                                formData[1],
                                formData[2],
                                formData[3],
                                formData[4],
                                formData[5],
                                formData[6]
                        );

                    } else {
                        success = myDatabase.insertMove(
                                formData[0],
                                formData[1],
                                formData[2],
                                formData[3],
                                formData[4],
                                formData[5],
                                formData[6]
                        );

                    }

                    if (success)
                        ViewUtility.createInfoPopup("Move Added Successfully!", "Success!");
                    else
                        ViewUtility.createErrorPopup("Move Insert/Update Failed!\nDid you input all required fields?\nIs the input too long?", "Failure!");
                },
                ClearFormPressed -> {
                    myAddMovesPanel.clearForm();
                }
        );

        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Add an Ability Menu Listeners =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
        myAddAbilitiesPanel.setListeners(
                ReturnPressed -> {
                    //Show the main menu
                    myCardPanelLayoutManager.show(myCardPanel, myMainMenuPanel.getName());
                    myAddAbilitiesPanel.clearForm();
                },
                SubmitPressed -> {
                    //Determine if we should use UPDATE or INSERT, then display message based on success of the SQL operation.
                    String[] formData = myAddAbilitiesPanel.getFormData();
                    boolean success;

                    if (myDatabase.doesAbilityExist(formData[0])) {
                        success = myDatabase.updateAbility(formData[0], formData[1]);
                    }
                    else {
                        success = myDatabase.insertAbility(formData[0], formData[1]);
                    }

                    if (success)
                        ViewUtility.createInfoPopup("Ability Added Successfully!", "Success!");
                    else
                        ViewUtility.createErrorPopup("Ability Insert/Update Failed!\nDid you input all required fields?\nIs the input too long?", "Failure!");

                },
                ClearFormPressed -> {
                    myAddAbilitiesPanel.clearForm();
                }
        );

        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Add a Location Menu Listeners =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
        myAddLocationsPanel.setListeners(
                ReturnPressed -> {
                    //Show the main menu
                    myCardPanelLayoutManager.show(myCardPanel, myMainMenuPanel.getName());
                    myAddLocationsPanel.clearForm();
                },
                SubmitPressed -> {
                    //Determine if we should use UPDATE or INSERT, then display message based on success of the SQL operation.
                    String[] formData = myAddLocationsPanel.getFormData();
                    boolean success;

                    success = myDatabase.insertLocation(formData[0], formData[1]);

                    if (success)
                        ViewUtility.createInfoPopup("Location Added Successfully!", "Success!");
                    else
                        ViewUtility.createErrorPopup("Location Insert Failed!\nDid you input all required fields?\nIs the input too long?", "Failure!");
                },
                ClearFormPressed -> {
                    myAddLocationsPanel.clearForm();
                }
        );

        //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Add an Item Menu Listeners =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//
        myAddItemsPanel.setListeners(
                ReturnPressed -> {
                    //Show the main menu
                    myCardPanelLayoutManager.show(myCardPanel, myMainMenuPanel.getName());
                    myAddItemsPanel.clearForm();
                },
                SubmitPressed -> {
                    //Determine if we should use UPDATE or INSERT, then display message based on success of the SQL operation.
                    String[] formData = myAddItemsPanel.getFormData();
                    boolean success;

                    if (myDatabase.doesItemExist(formData[0]) || myDatabase.doesPokeballExist(formData[0])) {
                        success = myDatabase.updateItem(formData[0], formData[1], formData[2], formData[3], formData[4]);
                    } else {
                        success = myDatabase.insertItem(formData[0], formData[1], formData[2], formData[3], formData[4]);
                    }

                    if (success)
                        ViewUtility.createInfoPopup("Item Added Successfully!", "Success!");
                    else
                        ViewUtility.createErrorPopup("Item Insert Failed!\nDid you input all required fields?\nIs the input too long?", "Failure!");
                },
                ClearFormPressed -> {
                    myAddItemsPanel.clearForm();
                }
        );
    }
}
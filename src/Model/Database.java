package Model;

import java.sql.*;
import java.io.File;
import java.io.PrintStream;

/**
 * Manages a connection to a MySQL Database.
 */
public class Database {
    /** The database connection. */
    private final Connection myConnection;

    private final PrintStream myErrorLog;

    /**
     * Initializes a MySQL database connection.
     * @param theURL The URL of the MySQL database
     * @param theUsername The username of the MySQL database.
     * @param thePassword The password of the mySQL database.
     */
    public Database(final String theURL, final String theUsername, final String thePassword) {
        Connection tryConnection = null;
        PrintStream tryErrorLog = null;

        try {
            tryErrorLog = new PrintStream(new File("log.txt"));
        } catch (Exception e) {
            System.exit(-2);
        }

        myErrorLog = tryErrorLog;

        try {
            tryConnection = DriverManager.getConnection(theURL, theUsername, thePassword);

        }  catch (SQLException e) {
            // Handle any SQL errors

            e.printStackTrace(myErrorLog);
        }

        myConnection = tryConnection;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Pokemon Table Manipulation =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//

    /**
     * Executes an SQL query on the database
     * @param theName The Pokemon Name
     * @param theAbilityName The Ability Name
     * @param theEggGroup The Egg Group
     * @param theLearnsMove The Learns Move
     * @param thePrimaryType The Primary Type
     * @param theSecondaryType The Secondary Type
     * @param theTypeResistance The Type Resistance
     * @param theTypeWeakness The Type Weakness
     * @param theMaxHP The Max HP
     * @param theMinHP The Min HP
     * @param theMaxAtk The Max Attack
     * @param theMinAtk The Min Attack
     * @param theMaxDef The Max Defense
     * @param theMinDef The Min Defense
     * @param theMaxSpAtk The Max Special Attack
     * @param theMinSpAtk The Min Special Attack
     * @param theMaxSpDef The Max Special Defense
     * @param theMinSpDef The Min Special Defense
     * @param theMaxSpeed The Max Speed
     * @param theMinSpeed The Min Speed
     * @param theShowLearnset The Show Learnest Flag
     * @return The query results as a ResultSet
     */
    public ResultSet searchPokemon(final String theName,
                                   final String theAbilityName,
                                   final String theEggGroup,
                                   final String theLearnsMove,
                                   final String thePrimaryType,
                                   final String theSecondaryType,
                                   final String theTypeResistance,
                                   final String theTypeWeakness,
                                   final String theMaxHP,
                                   final String theMinHP,
                                   final String theMaxAtk,
                                   final String theMinAtk,
                                   final String theMaxDef,
                                   final String theMinDef,
                                   final String theMaxSpAtk,
                                   final String theMinSpAtk,
                                   final String theMaxSpDef,
                                   final String theMinSpDef,
                                   final String theMaxSpeed,
                                   final String theMinSpeed,
                                   final String theShowLearnset
                                   ) {
        StringBuilder queryBuilder = new StringBuilder();

        //Build a query based on the given arguments

        if (theShowLearnset.equals("true")) {
            queryBuilder.append("""
                    SELECT MoveName,
                    LearnCondition
                    FROM Pokemon JOIN Learnsets ON Pokemon.PokemonDexNumber = Learnsets.PokemonDexNumber
                    """);
        }

        else {
            queryBuilder.append("""
                    SELECT PokemonDexNumber AS DexNum,
                        PokemonName AS Name,
                        PrimaryTypeName AS Type1,
                        SecondaryTypeName AS Type2,
                        PrimaryAbilityName AS Ability1,
                        SecondaryAbilityName AS Ability2,
                        HiddenAbilityName AS Ability3,
                        BaseHitPoints AS HP,
                        BaseAttack AS Attack,
                        BaseDefense AS Defense,
                        BaseSpecialAttack AS Sp_Atk,
                        BaseSpecialDefense AS Sp_Def,
                        BaseSpeed AS Speed,
                        EggGroup1,
                        EggGroup2
                    FROM Pokemon
                    """);
        }



        queryBuilder.append(String.format("WHERE PokemonName LIKE '%%%s%%'\n", theName)); //0:PokemonName
        queryBuilder.append(String.format("AND (PrimaryAbilityName LIKE '%%%s%%' OR SecondaryAbilityName LIKE '%%%s%%' OR HiddenAbilityName LIKE '%%%s%%')\n", theAbilityName, theAbilityName, theAbilityName)); //1:AbilityName

        if (!theEggGroup.equals("-"))
            queryBuilder.append(String.format("AND (EggGroup1 = '%s' OR EggGroup2 = '%s')\n", theEggGroup, theEggGroup)); //2:EggGroup

        if(!theLearnsMove.equals(""))
            queryBuilder.append(String.format("""
                AND Pokemon.PokemonDexNumber IN (
                    SELECT Distinct Pokemon.PokemonDexNumber
                    FROM Pokemon JOIN Learnsets
                    ON Pokemon.PokemonDexNumber = Learnsets.PokemonDexNumber
                    WHERE Learnsets.MoveName LIKE '%%%s%%'
                )\n
                """, theLearnsMove)); //3:learns Move

        if (!thePrimaryType.equals("-"))
            queryBuilder.append(String.format("AND (PrimaryTypeName = '%s' OR SecondaryTypeName = '%s')\n", thePrimaryType, thePrimaryType)); //4:PrimaryType

        if (!theSecondaryType.equals("-"))
            queryBuilder.append(String.format("AND (PrimaryTypeName = '%s' OR SecondaryTypeName = '%s')\n", theSecondaryType, theSecondaryType)); //5:SecondaryType

        if (!theTypeResistance.equals("-"))
            queryBuilder.append(String.format("""
                AND Pokemon.PokemonDexNumber IN (
                    SELECT Pokemon.PokemonDexNumber
                    FROM Pokemon JOIN Type_Attack_Multipliers
                    ON Pokemon.PrimaryTypeName = Type_Attack_Multipliers.DefendingTypeName OR Pokemon.SecondaryTypeName = Type_Attack_Multipliers.DefendingTypeName
                    WHERE Type_Attack_Multipliers.OffendingTypeName = '%s'
                    GROUP BY Pokemon.PokemonDexNumber
                    HAVING AVG(Type_Attack_Multipliers.AttackMultiplier) < 1.0
                )\n
                """, theTypeResistance)); //6:TypeResistance

        if (!theTypeWeakness.equals("-"))
            queryBuilder.append(String.format("""
                AND Pokemon.PokemonDexNumber IN (
                    SELECT Pokemon.PokemonDexNumber
                    FROM Pokemon JOIN Type_Attack_Multipliers
                    ON Pokemon.PrimaryTypeName = Type_Attack_Multipliers.DefendingTypeName OR Pokemon.SecondaryTypeName = Type_Attack_Multipliers.DefendingTypeName
                    WHERE Type_Attack_Multipliers.OffendingTypeName = '%s'
                    GROUP BY Pokemon.PokemonDexNumber
                    HAVING AVG(Type_Attack_Multipliers.AttackMultiplier) > 1.0
                )\n
                """, theTypeWeakness)); //7:TypeWeakness

        if (!theMaxHP.equals(""))
            queryBuilder.append(String.format("AND BaseHitPoints <= %s\n", theMaxHP)); //8:MaxHP
        if (!theMinHP.equals(""))
            queryBuilder.append(String.format("AND BaseHitPoints >= %s\n", theMinHP)); //9:MinHP
        if (!theMaxAtk.equals(""))
            queryBuilder.append(String.format("AND BaseAttack <= %s\n", theMaxAtk)); //10:MaxAtk
        if (!theMinAtk.equals(""))
            queryBuilder.append(String.format("AND BaseAttack >= %s\n", theMinAtk)); //11:MinAtk
        if (!theMaxDef.equals(""))
            queryBuilder.append(String.format("AND BaseDefense <= %s\n", theMaxDef)); //12:MaxDef
        if (!theMinDef.equals(""))
            queryBuilder.append(String.format("AND BaseDefense >= %s\n", theMinDef)); //13:MinDef
        if (!theMaxSpAtk.equals(""))
            queryBuilder.append(String.format("AND BaseSpecialAttack <= %s\n", theMaxSpAtk)); //14:MaxSpAtk
        if (!theMinSpAtk.equals(""))
            queryBuilder.append(String.format("AND BaseSpecialAttack >= %s\n", theMinSpAtk)); //15:MinSpAtk
        if (!theMaxSpDef.equals(""))
            queryBuilder.append(String.format("AND BaseSpecialDefense <= %s\n", theMaxSpDef)); //16:MaxSpDef
        if (!theMinSpDef.equals(""))
            queryBuilder.append(String.format("AND BaseSpecialDefense >= %s\n", theMinSpDef)); //17:MinSpDef
        if (!theMaxSpeed.equals(""))
            queryBuilder.append(String.format("AND BaseSpeed <= %s\n", theMaxSpeed)); //18:MaxSpeed
        if (!theMinSpeed.equals(""))
            queryBuilder.append(String.format("AND BaseSpeed >= %s\n", theMinSpeed)); //19:MinSpeed

        ResultSet results = null;

        try {
            //Execute the SQL Query
            Statement statement = myConnection.createStatement();
            results = statement.executeQuery(queryBuilder.toString());
        } catch(SQLException e) {
            e.printStackTrace(myErrorLog);
        }

        return results;
    }

    /**
     * Inserts a Pokemon database entry
     * @param theDexNum The Pokemon Dex Number (NOT NULL)
     * @param theName The Pokemon name (NOT NULL)
     * @param thePrimaryType The Primary Type (NOT NULL)
     * @param theSecondaryType The Secondary Type
     * @param thePrimaryAbility The Primary Ability (NOT NULL)
     * @param theSecondaryAbility The Secondary Ability
     * @param theHiddenAbility The Hidden Ability
     * @param theHP The Base HP (NOT NULL)
     * @param theAtk The Base Attack (NOT NULL)
     * @param theDef The Base Defense (NOT NULL)
     * @param theSpAtk The Base Special Attack (NOT NULL)
     * @param theSpDef The Base Special Defense (NOT NULL)
     * @param theSpeed The Base Speed (NOT NULL)
     * @param theEggGroup1 The first Egg Group (NOT NULL)
     * @param theEggGroup2 The Second Egg Group
     * @return True if the insert was successful
     */
    public boolean insertPokemon(final String theDexNum,
                              final String theName,
                              final String thePrimaryType,
                              final String theSecondaryType,
                              final String thePrimaryAbility,
                              final String theSecondaryAbility,
                              final String theHiddenAbility,
                              final String theHP,
                              final String theAtk,
                              final String theDef,
                              final String theSpAtk,
                              final String theSpDef,
                              final String theSpeed,
                              final String theEggGroup1,
                              final String theEggGroup2) {

        //Make sure that values labeled as NOT NULL in the database are, in-fact, not null
        if (theDexNum.equals("") ||
                theName.equals("") ||
                thePrimaryType.equals("-") ||
                thePrimaryAbility.equals("") ||
                theHP.equals("") ||
                theAtk.equals("") ||
                theDef.equals("") ||
                theSpAtk.equals("") ||
                theSpDef.equals("") ||
                theSpeed.equals("") ||
                theEggGroup1.equals("-")) {
            return false;
        }

        //Add a stubbed out ability to the database if it dosent exist yet
        if (!doesAbilityExist(thePrimaryAbility)) {
            insertAbility(thePrimaryAbility, "**THIS IS AN AUTOGENERATED ABILITY, PLEASE OVERWRITE**");
        }

        if (!theSecondaryAbility.equals("") && !doesAbilityExist(theSecondaryAbility)) {
            insertAbility(theSecondaryAbility, "**THIS IS AN AUTOGENERATED ABILITY, PLEASE OVERWRITE**");
        }

        if (!theHiddenAbility.equals("") && !doesAbilityExist(theHiddenAbility)) {
            insertAbility(theHiddenAbility, "**THIS IS AN AUTOGENERATED ABILITY, PLEASE OVERWRITE**");
        }

        //Build a query based on the given arguments

        String query = """
        INSERT INTO Pokemon (
            PokemonDexNumber,
            PokemonName,
            PrimaryTypeName,
            SecondaryTypeName,
            PrimaryAbilityName,
            SecondaryAbilityName,
            HiddenAbilityName,
            BaseHitPoints,
            BaseAttack,
            BaseDefense,
            BaseSpecialAttack,
            BaseSpecialDefense,
            BaseSpeed,
            EggGroup1,
            EggGroup2
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try {
            PreparedStatement statement = myConnection.prepareStatement(query);

            statement.setInt(1, Integer.parseInt(theDexNum));
            statement.setString(2, theName);
            statement.setString(3, thePrimaryType);

            if (theSecondaryType.equals("-"))
                statement.setNull(4, Types.VARCHAR);
            else
                statement.setString(4, theSecondaryType);

            statement.setString(5, thePrimaryAbility);

            if (theSecondaryAbility.equals(""))
                statement.setNull(6, Types.VARCHAR);
            else
                statement.setString(6, theSecondaryAbility);

            if (theHiddenAbility.equals(""))
                statement.setNull(7, Types.VARCHAR);
            else
                statement.setString(7, theHiddenAbility);

            statement.setInt(8, Integer.parseInt(theHP));
            statement.setInt(9, Integer.parseInt(theAtk));
            statement.setInt(10, Integer.parseInt(theDef));
            statement.setInt(11, Integer.parseInt(theSpAtk));
            statement.setInt(12, Integer.parseInt(theSpDef));
            statement.setInt(13, Integer.parseInt(theSpeed));
            statement.setString(14, theEggGroup1);

            if (theEggGroup2.equals("-"))
                statement.setNull(15, Types.VARCHAR);
            else
                statement.setString(15, theEggGroup2);

            // Execute the query
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                throw new SQLException("Insert Failed");
            }

        } catch (SQLException e) {
            e.printStackTrace(myErrorLog);
            return false;
        }

        return true;

    }

    /**
     * Updates a Pokemon database entry
     * @param theDexNum The Pokemon Dex Number (NOT NULL)
     * @param theName The Pokemon name (NOT NULL)
     * @param thePrimaryType The Primary Type (NOT NULL)
     * @param theSecondaryType The Secondary Type
     * @param thePrimaryAbility The Primary Ability (NOT NULL)
     * @param theSecondaryAbility The Secondary Ability
     * @param theHiddenAbility The Hidden Ability
     * @param theHP The Base HP (NOT NULL)
     * @param theAtk The Base Attack (NOT NULL)
     * @param theDef The Base Defense (NOT NULL)
     * @param theSpAtk The Base Special Attack (NOT NULL)
     * @param theSpDef The Base Special Defense (NOT NULL)
     * @param theSpeed The Base Speed (NOT NULL)
     * @param theEggGroup1 The first Egg Group (NOT NULL)
     * @param theEggGroup2 The Second Egg Group
     * @return True if the update was successful
     */
    public boolean updatePokemon(final String theDexNum,
                                 final String theName,
                                 final String thePrimaryType,
                                 final String theSecondaryType,
                                 final String thePrimaryAbility,
                                 final String theSecondaryAbility,
                                 final String theHiddenAbility,
                                 final String theHP,
                                 final String theAtk,
                                 final String theDef,
                                 final String theSpAtk,
                                 final String theSpDef,
                                 final String theSpeed,
                                 final String theEggGroup1,
                                 final String theEggGroup2) {

        //Make sure that values labeled as NOT NULL in the database are, in-fact, not null
        if (theDexNum.equals("") ||
                theName.equals("") ||
                thePrimaryType.equals("-") ||
                thePrimaryAbility.equals("") ||
                theHP.equals("") ||
                theAtk.equals("") ||
                theDef.equals("") ||
                theSpAtk.equals("") ||
                theSpDef.equals("") ||
                theSpeed.equals("") ||
                theEggGroup1.equals("-")) {
            return false;
        }

        //Add a stubbed out ability to the database if it dosent exist yet
        if (!doesAbilityExist(thePrimaryAbility)) {
            insertAbility(thePrimaryAbility, "**THIS IS AN AUTOGENERATED ABILITY, PLEASE OVERWRITE**");
        }

        if (!theSecondaryAbility.equals("") && !doesAbilityExist(theSecondaryAbility)) {
            insertAbility(theSecondaryAbility, "**THIS IS AN AUTOGENERATED ABILITY, PLEASE OVERWRITE**");
        }

        if (!theHiddenAbility.equals("") && !doesAbilityExist(theHiddenAbility)) {
            insertAbility(theHiddenAbility, "**THIS IS AN AUTOGENERATED ABILITY, PLEASE OVERWRITE**");
        }

        //Build a query based on the given arguments

        String query = """
        UPDATE Pokemon SET
            PokemonName = ?,
            PrimaryTypeName = ?,
            SecondaryTypeName = ?,
            PrimaryAbilityName = ?,
            SecondaryAbilityName = ?,
            HiddenAbilityName = ?,
            BaseHitPoints = ?,
            BaseAttack = ?,
            BaseDefense = ?,
            BaseSpecialAttack = ?,
            BaseSpecialDefense = ?,
            BaseSpeed = ?,
            EggGroup1 = ?,
            EggGroup2 = ?
        WHERE PokemonDexNumber = ?
        """;
        try {
            PreparedStatement statement = myConnection.prepareStatement(query);

            statement.setString(1, theName);
            statement.setString(2, thePrimaryType);

            if (theSecondaryType.equals("-"))
                statement.setNull(3, Types.VARCHAR);
            else
                statement.setString(3, theSecondaryType);

            statement.setString(4, thePrimaryAbility);

            if (theSecondaryAbility.equals(""))
                statement.setNull(5, Types.VARCHAR);
            else
                statement.setString(5, theSecondaryAbility);

            if (theHiddenAbility.equals(""))
                statement.setNull(6, Types.VARCHAR);
            else
                statement.setString(6, theHiddenAbility);

            statement.setInt(7, Integer.parseInt(theHP));
            statement.setInt(8, Integer.parseInt(theAtk));
            statement.setInt(9, Integer.parseInt(theDef));
            statement.setInt(10, Integer.parseInt(theSpAtk));
            statement.setInt(11, Integer.parseInt(theSpDef));
            statement.setInt(12, Integer.parseInt(theSpeed));
            statement.setString(13, theEggGroup1);

            if (theEggGroup2.equals("-"))
                statement.setNull(14, Types.VARCHAR);
            else
                statement.setString(14, theEggGroup2);

            statement.setInt(15, Integer.parseInt(theDexNum));

            // Execute the query
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted == 0) {
                throw new SQLException("Update Failed");
            }

        } catch (SQLException e) {
            e.printStackTrace(myErrorLog);
            return false;
        }

        return true;
    }

    /**
     * <pre>
     * Given a primary key of the Pokemon table, this method returns weather or not the Pokemon exists in the table,
     * So we know if we need to do an insert or an update.
     * </pre>
     * @param thePokemonDexNum The primary key of the Pokemon table
     * @return True if the Pokemon exists in the table, False otherwise
     */
    public boolean doesPokemonExist(final String thePokemonDexNum) {
        if (thePokemonDexNum.equals("")) {
            return false;
        }

        //Execute a simple query to find a single pokemon
        String query = "SELECT * FROM Pokemon WHERE PokemonDexNumber = ?";

        boolean exists;

        try {
            PreparedStatement statement = myConnection.prepareStatement(query);

            statement.setInt(1, Integer.parseInt(thePokemonDexNum));

            ResultSet queryResults = statement.executeQuery();

            //If at least one row was returned, this should return true
            exists = queryResults.next();

        } catch (SQLException e) {
            e.printStackTrace(myErrorLog);
            return false;
        }

        return exists;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Moves Table Manipulation =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//

    /**
     * Inserts a new move into the Moves table
     * @param theName The move name (NOT NULL)
     * @param theType The move type (NOT NULL)
     * @param theDiscriminator The move Discriminator/Category (NOT NULL)
     * @param thePower The Power
     * @param theAccuracy The Accuracy
     * @param thePP The Power Points
     * @param theEffect The Effect
     * @return True if the move was inserted successfully
     */
    public boolean insertMove(final String theName,
                              final String theType,
                              final String theDiscriminator,
                              final String thePower,
                              final String theAccuracy,
                              final String thePP,
                              final String theEffect) {

        //Make sure values set to NOT NULL in database are in-fact, not null
        if (theName.equals("") || theType.equals("-") || theDiscriminator.equals("-")) {
            return false;
        }

        //Build a query based on the given arguments

        String query = """
        INSERT INTO Moves (
            MoveName,
            MoveDiscriminator,
            MoveTypeName,
            Power,
            Accuracy,
            PowerPoints,
            Effect
        ) VALUES (?, ?, ?, ?, ?, ?, ?)
        """;
        try {
            PreparedStatement statement = myConnection.prepareStatement(query);

            statement.setString(1, theName);
            statement.setString(2, theDiscriminator);
            statement.setString(3, theType);

            //Need to do extra checks for if the values can be null
            if (thePower.equals(""))
                statement.setNull(4, Types.INTEGER);
            else
                statement.setInt(4, Integer.parseInt(thePower));

            if (theAccuracy.equals(""))
                statement.setNull(5, Types.INTEGER);
            else
                statement.setInt(5, Integer.parseInt(theAccuracy));

            if (thePP.equals(""))
                statement.setNull(6, Types.INTEGER);
            else
                statement.setInt(6, Integer.parseInt(thePP));

            if (theEffect.equals(""))
                statement.setNull(7, Types.VARCHAR);
            else
                statement.setString(7, theEffect);

            // Execute the query
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                throw new SQLException("Insert Failed");
            }

        } catch (SQLException e) {
            e.printStackTrace(myErrorLog);
            return false;
        }

        return true;
    }

    /**
     * Updates an existing move in the Moves table
     * @param theName The move name (NOT NULL)
     * @param theType The move type (NOT NULL)
     * @param theDiscriminator The move Discriminator/Category (NOT NULL)
     * @param thePower The Power
     * @param theAccuracy The Accuracy
     * @param thePP The Power Points
     * @param theEffect The Effect
     * @return True if the move was updated successfully
     */
    public boolean updateMove(final String theName,
                              final String theType,
                              final String theDiscriminator,
                              final String thePower,
                              final String theAccuracy,
                              final String thePP,
                              final String theEffect) {
        //Check to make sure that the values set to NOT NULL in the database are in-fact, not null
        if (theName.equals("") || theType.equals("-") || theDiscriminator.equals("-")) {
            return false;
        }

        //Build a query based on the given arguments

        String query = """
        UPDATE Moves SET
            MoveDiscriminator = ?,
            MoveTypeName = ?,
            Power = ?,
            Accuracy = ?,
            PowerPoints = ?,
            Effect = ?
        WHERE MoveName = ?
        """;
        try {
            PreparedStatement statement = myConnection.prepareStatement(query);

            statement.setString(1, theDiscriminator);
            statement.setString(2, theType);

            //Need to do extra checks for if the values can be null
            if (thePower.equals(""))
                statement.setNull(3, Types.INTEGER);
            else
                statement.setInt(3, Integer.parseInt(thePower));

            if (theAccuracy.equals(""))
                statement.setNull(4, Types.INTEGER);
            else
                statement.setInt(4, Integer.parseInt(theAccuracy));

            if (thePP.equals(""))
                statement.setNull(5, Types.INTEGER);
            else
                statement.setInt(5, Integer.parseInt(thePP));

            if (theEffect.equals(""))
                statement.setNull(6, Types.VARCHAR);
            else
                statement.setString(6, theEffect);

            statement.setString(7, theName);

            // Execute the query
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted == 0) {
                throw new SQLException("Update Failed");
            }

        } catch (SQLException e) {
            e.printStackTrace(myErrorLog);
            return false;
        }

        return true;
    }


    /**
     * Executes a query on the MySQL database.
     * @param theMoveName The Move Name.
     * @param theMoveCategory The Move Category/Discriminator
     * @param theType The Move Type
     * @param theMaxPower The Max Power
     * @param theMinPower The Min Power
     * @param theMaxAccuracy The Max Accuracy
     * @param theMinAccuracy The Min Accuracy
     * @param theMaxPP The Max Power Points
     * @param theMinPP The Min Power Points
     * @return The query results as a ResultSet
     */
    public ResultSet searchMove(final String theMoveName,
                                final String theMoveCategory,
                                final String theType,
                                final String theMaxPower,
                                final String theMinPower,
                                final String theMaxAccuracy,
                                final String theMinAccuracy,
                                final String theMaxPP,
                                final String theMinPP) {
        StringBuilder queryBuilder = new StringBuilder();

        //Build a query based on the given arguments

        queryBuilder.append("""
                    SELECT MoveName AS Name,
                        MoveDiscriminator AS Category,
                        MoveTypeName AS Type,
                        Power,
                        Accuracy,
                        PowerPoints AS PP,
                        Effect
                    FROM Moves
                    """);

        queryBuilder.append(String.format("WHERE MoveName LIKE '%%%s%%'\n", theMoveName)); //0:MoveName

        if (!theMoveCategory.equals("-"))
            queryBuilder.append(String.format("AND MoveDiscriminator = '%s'\n", theMoveCategory)); //1:MoveCategory

        if (!theType.equals("-"))
            queryBuilder.append(String.format("AND MoveTypeName = '%s'\n", theType)); //2:Type

        if (!theMaxPower.equals(""))
            queryBuilder.append(String.format("AND (Power IS NULL OR Power <= %s)\n", theMaxPower)); //3:MaxPower
        if (!theMinPower.equals(""))
            queryBuilder.append(String.format("AND (Power IS NULL OR Power >= %s)\n", theMinPower)); //4:MinPower
        if (!theMaxAccuracy.equals(""))
            queryBuilder.append(String.format("AND (Accuracy IS NULL OR Accuracy <= %s)\n", theMaxAccuracy)); //5:MaxAccuracy
        if (!theMinAccuracy.equals(""))
            queryBuilder.append(String.format("AND (Accuracy IS NULL OR Accuracy >= %s)\n", theMinAccuracy)); //6:MinAccuracy
        if (!theMaxPP.equals(""))
            queryBuilder.append(String.format("AND (PowerPoints IS NULL OR PowerPoints <= %s)\n", theMaxPP)); //7:MaxPP
        if (!theMinPP.equals(""))
            queryBuilder.append(String.format("AND (PowerPoints IS NULL OR PowerPoints >= %s)\n", theMinPP)); //8:MinPP

        ResultSet results = null;

        try {
            //Execute the SQL Query
            Statement statement = myConnection.createStatement();
            results = statement.executeQuery(queryBuilder.toString());
        } catch(SQLException e) {
            e.printStackTrace(myErrorLog);
        }

        return results;
    }

    /**
     * <pre>
     * Given a primary key of the Moves table, this method returns weather or not the Move exists in the table,
     * So we know if we need to do an insert or an update.
     * </pre>
     * @param theMoveName The primary key of the Move table
     * @return True if the Move exists in the table, False otherwise
     */
    public boolean doesMoveExist(final String theMoveName) {
        if (theMoveName.equals("")) {
            return false;
        }

        //Perform a simple query to find a single move

        String query = "SELECT MoveName FROM Moves WHERE MoveName = ?";

        boolean exists;

        try {
            PreparedStatement statement = myConnection.prepareStatement(query);

            statement.setString(1, theMoveName);

            ResultSet queryResults = statement.executeQuery();

            //Should return true if a single move was found
            exists = queryResults.next();

        } catch (SQLException e) {
            e.printStackTrace(myErrorLog);
            return false;
        }

        return exists;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Abilities Table Manipulation =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//

    /**
     * Inserts a new ability into the Abilities table
     * @param theName The ability name. (NOT NULL)
     * @param theDescription The ability description. (NOT NULL)
     * @return True if the ability was inserted successfully.
     */
    public boolean insertAbility(final String theName,
                                 final String theDescription) {

        //Check to make sure that the values set to NOT NULL in the database are in-fact, not null
        if (theName.equals("") || theDescription.equals("")) {
            return false;
        }

        //Build a query based on the given arguments

        String query = """
                INSERT INTO Abilities (
                    AbilityName,
                    AbilityDescription
                ) VALUES (?, ?)
                """;
        try {
            PreparedStatement statement = myConnection.prepareStatement(query);

            statement.setString(1, theName);
            statement.setString(2, theDescription);

            // Execute the query
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                throw new SQLException("Insert Failed");
            }

        } catch (SQLException e) {
            e.printStackTrace(myErrorLog);
            return false;
        }

        return true;
    }

    /**
     * Updates an existing ability in the Abilities table
     * @param theName The ability name. (NOT NULL)
     * @param theDescription The ability description (NOT NULL)
     * @return True if the ability updated successfully.
     */
    public boolean updateAbility(final String theName,
                                 final String theDescription) {
        //Check to make sure that the values in the database that are set to NOT NULL are in-fact, not null
        if (theName.equals("") || theDescription.equals("")) {
            return false;
        }

        //Build a query based on the given arguments

        String query = """
        UPDATE Abilities SET
            AbilityDescription = ?
        WHERE AbilityName = ?
        """;
        try {
            PreparedStatement statement = myConnection.prepareStatement(query);

            statement.setString(1, theDescription);
            statement.setString(2, theName);

            // Execute the query
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted == 0) {
                throw new SQLException("Update Failed");
            }

        } catch (SQLException e) {
            e.printStackTrace(myErrorLog);
            return false;
        }

        return true;
    }

    /**
     * Executes an SQL query on a MySQL database
     * @param theAbilityName The Ability Name.
     * @return The ResultSet after executing the SQL query
     */
    public ResultSet searchAbilities(String theAbilityName) {
        StringBuilder queryBuilder = new StringBuilder();

        //Build a query based on the given arguments

        queryBuilder.append("""
                    SELECT AbilityName AS Name,
                        AbilityDescription AS Description
                    FROM Abilities
                    """);

        queryBuilder.append(String.format("WHERE AbilityName LIKE '%%%s%%'\n", theAbilityName)); //0:AbilityName

        ResultSet results = null;

        try {
            //Execute the SQL Query
            Statement statement = myConnection.createStatement();
            results = statement.executeQuery(queryBuilder.toString());
        } catch(SQLException e) {
            e.printStackTrace(myErrorLog);
        }

        return results;
    }

    /**
     * <pre>
     * Given a primary key of the Abilities table, this method returns weather or not the Ability exists in the table,
     * So we know if we need to do an insert or an update.
     * </pre>
     * @param theAbilityName The primary key of the Abilities table
     * @return True if the Ability exists in the table, False otherwise
     */
    public boolean doesAbilityExist(final String theAbilityName) {
        if (theAbilityName.equals("")) {
            return false;
        }

        //Perform a simple query to find a single ability

        String query = "SELECT AbilityName FROM Abilities WHERE AbilityName = ?";

        boolean exists;

        try {
            PreparedStatement statement = myConnection.prepareStatement(query);

            statement.setString(1, theAbilityName);

            ResultSet queryResults = statement.executeQuery();

            //This should return true if a single ability was found
            exists = queryResults.next();

        } catch (SQLException e) {
            e.printStackTrace(myErrorLog);
            return false;
        }

        return exists;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Locations Table Manipulation =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//

    /**
     * Inserts a new location into the Locations table
     * @param theRegionName The Region name. (NOT NULL)
     * @param theLocationName The Location name. (NOT NULL)
     * @return True if the Location was inserted successfully.
     */
    public boolean insertLocation(final String theRegionName,
                                 final String theLocationName) {
        //Check to make sure that NOT NULL values are in-fact, not null
        if (theRegionName.equals("") || theLocationName.equals("")) {
            return false;
        }

        //Build a query based on the given arguments

        String query = """
                INSERT INTO Locations (
                    RegionName,
                    LocationName
                ) VALUES (?, ?)
                """;
        try {
            PreparedStatement statement = myConnection.prepareStatement(query);

            statement.setString(1, theRegionName);
            statement.setString(2, theLocationName);

            // Execute the query
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                throw new SQLException("Insert Failed");
            }

        } catch (SQLException e) {
            e.printStackTrace(myErrorLog);
            return false;
        }

        return true;
    }

    /**
     * Performs a query on a MySQL database
     * @param theRegionName The Region Name
     * @param theLocationName The Location Name
     * @param theShowPokemon The Show Pokemon flag
     * @param theShowGeneralItems The Show General Items flag
     * @param theShowMachines The Show Machines Flag
     * @param theShowPokeballs The Show Pokeballs flag
     * @return Query results in the form of a ResultSet
     */
    public ResultSet searchLocations(final String theRegionName,
                                     final String theLocationName,
                                     final String theShowPokemon,
                                     final String theShowGeneralItems,
                                     final String theShowMachines,
                                     final String theShowPokeballs) {
        StringBuilder queryBuilder = new StringBuilder();

        //Build a query based on the given arguments

        if (theShowPokemon.equals("true")) { //show pokemon
            queryBuilder.append("""
                    SELECT Pokemon.PokemonDexNumber AS DexNum,
                    PokemonName AS Name,
                    SpawnRate,
                    EncounterScenario
                    FROM Locations JOIN Spawnpoints 
                        ON (Locations.RegionName = Spawnpoints.RegionName AND Locations.LocationName = Spawnpoints.LocationName) 
                        JOIN Pokemon ON Spawnpoints.PokemonDexNumber = Pokemon.PokemonDexNumber
                    """);
        }

        else if (theShowGeneralItems.equals("true")) { //show general items
            queryBuilder.append("""
                    SELECT GeneralItemName AS Name,
                    GeneralItemSpawnDescription AS SpawnDescription
                    FROM Locations JOIN General_Item_Locations 
                        ON (Locations.RegionName = General_Item_Locations.RegionName AND Locations.LocationName = General_Item_Locations.LocationName) 
                    """);
        }

        else if (theShowMachines.equals("true")) { //show machines
            queryBuilder.append("""
                    SELECT MachineName AS Name,
                    MachineSpawnDescription AS SpawnDescription
                    FROM Locations JOIN Machine_Locations 
                        ON (Locations.RegionName = Machine_Locations.RegionName AND Locations.LocationName = Machine_Locations.LocationName) 
                    """);
        }

        else if (theShowPokeballs.equals("true")) { //show pokeballs
            queryBuilder.append("""
                    SELECT PokeballName AS Name,
                    PokeballSpawnDescription AS SpawnDescription
                    FROM Locations JOIN Pokeball_Locations 
                        ON (Locations.RegionName = Pokeball_Locations.RegionName AND Locations.LocationName = Pokeball_Locations.LocationName) 
                    """);
        }

        else { //show locations
            queryBuilder.append("""
                    SELECT RegionName AS Region,
                        LocationName AS Location
                    FROM Locations
                    """);
        }

        queryBuilder.append(String.format("WHERE Locations.RegionName LIKE '%%%s%%'\n", theRegionName)); //0:RegionName
        queryBuilder.append(String.format("AND Locations.LocationName LIKE '%%%s%%'\n", theLocationName)); //1:LocationName

        ResultSet results = null;

        try {
            //Execute the SQL Query
            Statement statement = myConnection.createStatement();
            results = statement.executeQuery(queryBuilder.toString());
        } catch(SQLException e) {
            e.printStackTrace(myErrorLog);
        }

        return results;
    }

    /**
     * <pre>
     * Given the primary keys of the Locations table, this method returns weather or not the Location exists in the table,
     * So we know if we need to do an insert or an update.
     * </pre>
     * @param theRegionName The first primary key of the Locations table.
     * @param theLocationName The second primary key of the Locations table
     * @return True if the Location exists in the table, False otherwise
     */
    public boolean doesLocationExist(final String theRegionName, final String theLocationName) {
        String query = "SELECT * FROM Locations WHERE RegionName = ? AND LocationName = ?";

        boolean exists;

        try {
            PreparedStatement statement = myConnection.prepareStatement(query);

            statement.setString(1, theRegionName);
            statement.setString(2, theLocationName);

            ResultSet queryResults = statement.executeQuery();

            exists = queryResults.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return exists;
    }

    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Items (General_Items/Machines/Pokeballs) Table Manipulation =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=//

    /**
     * Updates an existing item in the General_Items/Machines/Pokeballs tables
     * @param theName The item name
     * @param theText The item description/Catch rate description/move name
     * @param isGeneralItem The General Item Flag
     * @param isMachine The Machine flag
     * @param isPokeball The Pokeball Flag
     * @return True if the insert was successful
     */
    public boolean insertItem(final String theName,
                              final String theText,
                              final String isGeneralItem,
                              final String isMachine,
                              final String isPokeball) {

        //Check to make sure that NOT NULL values are, in-fact, not null
        if (theName.equals("") || theText.equals("")) {
            return false;
        }

        //Build a query based on the given arguments

        String query;

        if (isGeneralItem.equals("true")) {
            query = """
                INSERT INTO General_Items (
                    GeneralItemName,
                    GeneralITemDescription
                ) VALUES (?, ?)
                """;

        } else if (isMachine.equals("true")) {
            query = """
                INSERT INTO Machines (
                    MachineName,
                    MachineMoveName
                ) VALUES (?, ?)
                """;

            //Add the move if it doesn't exist in the database yet
            if (!doesMoveExist(theText)) {
                insertMove(theText, "Normal", "Status", "", "", "", "**THIS IS AN AUTOGENERATED MOVE, PLEASE OVERWRITE**");
            }

        } else if (isPokeball.equals("true")) {
            query = """
                INSERT INTO Pokeballs (
                    PokeballName,
                    CatchRateModifierDescription
                ) VALUES (?, ?)
                """;
        } else {
            return false;
        }

        try {
            PreparedStatement statement = myConnection.prepareStatement(query);

            statement.setString(1, theName);
            statement.setString(2, theText);

            // Execute the query
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                throw new SQLException("Insert Failed");
            }

        } catch (SQLException e) {
            e.printStackTrace(myErrorLog);
            return false;
        }

        return true;
    }

    /**
     * Updates an existing item in the General_Items/Machines/Pokeballs tables
     * @param theName The Item Name
     * @param theText The Item Description / Machine Move Name / Catch Rate Multiplier Description
     * @param isGeneralItem The General Item flag
     * @param isMachine The Machine flag
     * @param isPokeball The Pokeball flag
     * @return True if the update was successful
     */
    public boolean updateItem(final String theName,
                              final String theText,
                              final String isGeneralItem,
                              final String isMachine,
                              final String isPokeball) {
        //Check to make sure that NOT NULL values are, in-fact, not null
        if (theName.equals("") || theText.equals("")) {
            return false;
        }

        //Build a query based on the given arguments

        String query;

        if (isGeneralItem.equals("true")) {
            query = """
                UPDATE General_Items SET
                GeneralItemDescription = ?
                WHERE GeneralItemName = ?
            """;
        } else if (isPokeball.equals("true")) {
            query = """
                UPDATE Pokeballs SET
                CatchRateModifierDescription = ?
                WHERE PokeballName = ?
            """;
        } else {
            return false;
        }

        try {
            PreparedStatement statement = myConnection.prepareStatement(query);

            statement.setString(1, theText);
            statement.setString(2, theName);

            // Execute the query
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted == 0) {
                throw new SQLException("Update Failed");
            }

        } catch (SQLException e) {
            e.printStackTrace(myErrorLog);
            return false;
        }

        return true;
    }

    /**
     * Executes a query on a MySQL database.
     * @param theItemName The Item Name
     * @param theGeneralItemSearch The General Item Search flag
     * @param theMachineSearch The Machine Search Flag
     * @param thePokeballSearch The Pokeball Search Flag
     * @return Query results in the form of a ResultSet
     */
    public ResultSet searchItems(final String theItemName,
                                 final String theGeneralItemSearch,
                                 final String theMachineSearch,
                                 final String thePokeballSearch) {
        StringBuilder queryBuilder = new StringBuilder();

        //Build a query based on the given arguments

        if (theGeneralItemSearch.equals("true")) { //General item search
            queryBuilder.append("""
                    SELECT GeneralItemName AS Name,
                    GeneralItemDescription AS Description
                    FROM General_Items
                    """);

            queryBuilder.append(String.format("WHERE GeneralItemName LIKE '%%%s%%'\n", theItemName)); //0:ItemName
        }

        else if (theMachineSearch.equals("true")) { //Machine search
            queryBuilder.append("""
                    SELECT MachineName AS Name,
                    CONCAT('Machine contains the move \\'', MachineMoveName, '\\'') AS Description
                    FROM Machines
                    """);

            queryBuilder.append(String.format("WHERE MachineName LIKE '%%%s%%'\n", theItemName)); //0:ItemName
        }

        else if (thePokeballSearch.equals("true")) { //Pokeball search
            queryBuilder.append("""
                    SELECT PokeballName AS Name,
                    CatchRateModifierDescription AS Description
                    FROM Pokeballs
                    """);

            queryBuilder.append(String.format("WHERE PokeballName LIKE '%%%s%%'\n", theItemName)); //0:ItemName
        }



        ResultSet results = null;

        try {
            //Execute the SQL Query
            Statement statement = myConnection.createStatement();
            results = statement.executeQuery(queryBuilder.toString());
        } catch(SQLException e) {
            e.printStackTrace(myErrorLog);
        }

        return results;
    }

    /**
     * <pre>
     * Given a primary key of the General_Items table, this method returns weather or not the Item exists in the table,
     * So we know if we need to do an insert or an update.
     * </pre>
     * @param theItemName The primary key of the General_Items table
     * @return True if the Item exists in the table, False otherwise
     */
    public boolean doesItemExist(final String theItemName) {
        if (theItemName.equals(""))
            return false;

        //Perform a simple query for a single item

        String query = "SELECT * FROM General_Items WHERE GeneralItemName = ?";

        boolean exists;

        try {
            PreparedStatement statement = myConnection.prepareStatement(query);

            statement.setString(1, theItemName);

            ResultSet queryResults = statement.executeQuery();

            //Should return true if a single item was found
            exists = queryResults.next();

        } catch (SQLException e) {
            e.printStackTrace(myErrorLog);
            return false;
        }

        return exists;
    }

    /**
     * <pre>
     * Given a primary key of the Pokeballs table, this method returns weather or not the Pokeball exists in the table,
     * So we know if we need to do an insert or an update.
     * </pre>
     * @param thePokeballName The primary key of the Pokeballs table
     * @return True if the Pokeball exists in the table, False otherwise
     */
    public boolean doesPokeballExist(final String thePokeballName) {
        if (thePokeballName.equals(""))
            return false;

        //Perform a simple query to find a single pokeball

        String query = "SELECT * FROM Pokeballs WHERE PokeballName = ?";

        boolean exists;

        try {
            PreparedStatement statement = myConnection.prepareStatement(query);

            statement.setString(1, thePokeballName);

            ResultSet queryResults = statement.executeQuery();

            //Should return true if a single pokeball was found
            exists = queryResults.next();

        } catch (SQLException e) {
            e.printStackTrace(myErrorLog);
            return false;
        }

        return exists;
    }
}

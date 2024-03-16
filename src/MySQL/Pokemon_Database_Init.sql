#Drop Database if exists (For testing)
DROP DATABASE IF EXISTS POKEMON_DB;
CREATE DATABASE POKEMON_DB;
USE POKEMON_DB;


#Dropping all tables if exists (For testing)
/*
DROP TABLE IF EXISTS Types;
DROP TABLE IF EXISTS Moves;
DROP TABLE IF EXISTS Locations;
DROP TABLE IF EXISTS Abilities;
DROP TABLE IF EXISTS General_Items;
DROP TABLE IF EXISTS Machines;
DROP TABLE IF EXISTS Pokeballs;
DROP TABLE IF EXISTS Pokemon;
DROP TABLE IF EXISTS Type_Attack_Multipliers;
DROP TABLE IF EXISTS Evolutions;
DROP TABLE IF EXISTS Learnsets;
DROP TABLE IF EXISTS Spawnpoints;
DROP TABLE IF EXISTS Genaral_Item_Locations;
DROP TABLE IF EXISTS Machine_Locations;
DROP TABLE IF EXISTS Pokeball_Locations;
*/

#Define Data Tables
CREATE TABLE Types (
	TypeName	VARCHAR(10)	NOT NULL,
    TypeColor	VARCHAR(10)	NOT NULL,
    CONSTRAINT 	Types_PK	PRIMARY KEY (TypeName)
);

CREATE TABLE Moves (
	MoveName			VARCHAR(50)		NOT NULL,
    MoveDiscriminator	VARCHAR(10)		NOT NULL,
    MoveTypeName		VARCHAR(10)		NOT NULL,
    Power				INT				NULL,
    Accuracy			INT 			NULL,
    PowerPoints			INT				NULL,
    Effect				VARCHAR(250)	NULL,
    CONSTRAINT		Moves_PK		PRIMARY KEY (MoveName),
    CONSTRAINT		Moves_FK		FOREIGN KEY (MoveTypeName)
					REFERENCES		Types 		(TypeName)
);

CREATE TABLE Locations (
	RegionName		VARCHAR(10)		NOT NULL,
    LocationName	VARCHAR(50)		NOT NULL,
    CONSTRAINT	Locations_PK	PRIMARY KEY (RegionName, LocationName)
);

CREATE TABLE Abilities (
	AbilityName			VARCHAR(25) 	NOT NULL,
    AbilityDescription	VARCHAR(150) 	NOT NULL,
    CONSTRAINT	Abilities_PK	PRIMARY KEY (AbilityName) 
);

CREATE TABLE General_Items (
	GeneralItemName			VARCHAR(25) 		NOT NULL,
    GeneralItemDescription	VARCHAR(250)		NOT NULL,
    CONSTRAINT	General_Items_PK	PRIMARY KEY (GeneralItemName) 
);

CREATE TABLE Machines (
	MachineName		VARCHAR(10)	NOT NULL,
    MachineMoveName	VARCHAR(50)	NOT NULL,
    CONSTRAINT	Machines_PK		PRIMARY KEY (MachineName),
    CONSTRAINT	Machines_FK		FOREIGN KEY (MachineMoveName)
				REFERENCES		Moves 		(MoveName)
);

CREATE TABLE Pokeballs (
	PokeballName					VARCHAR(25)		NOT NULL,
    CatchRateModifierDescription	VARCHAR(500) 	NOT NULL,
    CONSTRAINT	Pokeballs_PK	PRIMARY KEY (PokeballName)
);

CREATE TABLE Pokemon (
	PokemonDexNumber		INT			NOT NULL,
    PokemonName				VARCHAR(15)	NOT NULL,
    PrimaryTypeName			VARCHAR(10)	NOT NULL,
    SecondaryTypeName		VARCHAR(10)	NULL,
    PrimaryAbilityName		VARCHAR(25)	NOT NULL,
    SecondaryAbilityName	VARCHAR(25) NULL,
    HiddenAbilityName		VARCHAR(25)	NULL,
    BaseHitPoints			INT			NOT NULL,
    BaseAttack				INT			NOT NULL,
    BaseDefense				INT			NOT NULL,
    BaseSpecialAttack		INT			NOT NULL,
    BaseSpecialDefense		INT			NOT NULL,
    BaseSpeed				INT			NOT NULL,
    EggGroup1				VARCHAR(20)	NOT NULL,
    EggGroup2				VARCHAR(20)	NULL,
    CONSTRAINT	Pokemon_PK	PRIMARY KEY (PokemonDexNumber)
);

CREATE TABLE Type_Attack_Multipliers (
	DefendingTypeName	VARCHAR(10)	NOT NULL,
    OffendingTypeName	VARCHAR(10)	NOT NULL,
    AttackMultiplier	FLOAT		NOT NULL,
    CONSTRAINT	Type_Attack_Multipliers_PK		PRIMARY KEY (DefendingTypeName, OffendingTypeName),
    CONSTRAINT	Type_Attack_Multipliers_FK_0	FOREIGN KEY	(DefendingTypeName)
				REFERENCES						Types		(TypeName),
	CONSTRAINT	Type_Attack_Multipliers_FK_1	FOREIGN KEY (OffendingTypeName)
				REFERENCES						Types		(TypeName)
);

CREATE TABLE Evolutions (
	PreEvolutionDexNumber	INT 			NOT NULL,
    PostEvolutionDexNumber	INT 			NOT NULL,
    EvolutionCondition		VARCHAR(300) 	NOT NULL,
    CONSTRAINT	Evolutions_PK		PRIMARY KEY	(PreEvolutionDexNumber, PostEvolutionDexNumber),
    CONSTRAINT	Evolutions_FK_0		FOREIGN KEY	(PreEvolutionDexNumber)
				REFERENCES			Pokemon 	(PokemonDexNumber),
    CONSTRAINT	Evolutions_FK_1		FOREIGN KEY	(PreEvolutionDexNumber)
				REFERENCES			Pokemon 	(PokemonDexNumber)
);

CREATE TABLE Learnsets (
	MoveName			VARCHAR(50)		NOT NULL,
    PokemonDexNumber	INT				NOT NULL,
    LearnCondition		VARCHAR(100)	NOT NULL,
    CONSTRAINT	Learnsets_PK	PRIMARY KEY	(MoveName, PokemonDexNumber),
    CONSTRAINT	Learnsets_FK_0	FOREIGN KEY	(MoveName)
				REFERENCES 		Moves 		(MoveName),
    CONSTRAINT 	Learnsets_FK_1	FOREIGN KEY (PokemonDexNumber) 
				REFERENCES 		Pokemon 	(PokemonDexNumber)
);

CREATE TABLE Spawnpoints (
	RegionName			VARCHAR(10)		NOT NULL,
    LocationName		VARCHAR(50) 	NOT NULL,
    PokemonDexNumber	INT				NOT NULL,
    SpawnRate			FLOAT 			NOT NULL,
    EncounterScenario	VARCHAR(25)		NOT NULL,
    CONSTRAINT	Spawnpoints_PK		PRIMARY KEY	(RegionName, LocationName, PokemonDexNumber),
    CONSTRAINT	Spawnpoints_FK_0	FOREIGN KEY	(RegionName, LocationName) 
				REFERENCES			Locations	(RegionName, LocationName),
    CONSTRAINT	Spawnpoints_FK_1	FOREIGN KEY	(PokemonDexNumber) 
				REFERENCES			Pokemon		(PokemonDexNumber)
);

CREATE TABLE General_Item_Locations (
	GeneralItemName				VARCHAR(25)		NOT NULL,
    RegionName					VARCHAR(10)		NOT NULL,
    LocationName				VARCHAR(50)		NOT NULL,
    GeneralItemSpawnDescription	VARCHAR(150) 	NOT NULL,
    CONSTRAINT	General_Item_Locations_PK	PRIMARY KEY		(GeneralItemName, RegionName, LocationName),
    CONSTRAINT	General_Item_Locations_FK_0	FOREIGN KEY		(GeneralItemName)
				REFERENCES					General_Items	(GeneralItemName),
    CONSTRAINT	General_Item_Locations_FK_1	FOREIGN KEY		(RegionName, LocationName)
				REFERENCES					Locations		(RegionName, LocationName)
);

CREATE TABLE Machine_Locations (
	MachineName					VARCHAR(10)		NOT NULL,
    RegionName					VARCHAR(10)		NOT NULL,
    LocationName				VARCHAR(50)		NOT NULL,
    MachineSpawnDescription		VARCHAR(150) 	NOT NULL,
    CONSTRAINT	Machine_Locations_PK	PRIMARY KEY	(MachineName, RegionName, LocationName),
    CONSTRAINT	Machine_Locations_FK_0	FOREIGN KEY	(MachineName)
				REFERENCES				Machines	(MachineName),
    CONSTRAINT	Machine_Locations_FK_1	FOREIGN KEY	(RegionName, LocationName)
				REFERENCES				Locations	(RegionName, LocationName)
);

CREATE TABLE Pokeball_Locations (
	PokeballName				VARCHAR(25)		NOT NULL,
    RegionName					VARCHAR(10)		NOT NULL,
    LocationName				VARCHAR(50)		NOT NULL,
    PokeballSpawnDescription	VARCHAR(150) 	NOT NULL,
    CONSTRAINT	Pokeball_Locations_PK	PRIMARY KEY	(PokeballName, RegionName, LocationName),
    CONSTRAINT	Pokeball_Locations_FK_0	FOREIGN KEY	(PokeballName)
				REFERENCES				Pokeballs	(PokeballName),
    CONSTRAINT	Pokeball_Locations_FK_1	FOREIGN KEY	(RegionName, LocationName)
				REFERENCES				Locations	(RegionName, LocationName)
);

-- Inserting data into all tables
INSERT INTO Types
	(TypeName, 		TypeColor) VALUES
    ('Normal', 		'Grey'),
    ('Fire', 		'Red'),
    ('Water', 		'Blue'),
    ('Electric', 	'Yellow'),
    ('Grass', 		'Green'),
    ('Ice', 		'Blue'),
    ('Fighting', 	'Brown'),
    ('Poison', 		'Purple'),
    ('Ground', 		'Brown'),
    ('Flying', 		'Blue'),
    ('Psychic', 	'Pink'),
    ('Bug', 		'Green'),
    ('Rock', 		'Brown'),
    ('Ghost', 		'Blue'),
    ('Dragon', 		'Blue'),
    ('Dark', 		'Brown'),
    ('Steel', 		'Silver'),
    ('Fairy', 		'Pink');
    
INSERT INTO Moves 
	(MoveName, MoveDiscriminator, MoveTypeName, Power, Accuracy, PowerPoints, Effect) VALUES
	('Absorb', 'Special', 'Grass', 20, 100, 25, 'User recovers half the HP inflicted on opponent.'),
    ('Swords Dance', 'Status', 'Normal', NULL, NULL, 20, 'Sharply raises user\'s Attack.'),
    ('Play Nice', 'Status', 'Normal', NULL, NULL, 20, 'Lowers opponent\'s Attack. Always hits.'),
    ('Sweet Kiss', 'Status', 'Fairy', NULL, 75, 10, 'Confuses opponent.'),
    ('Nuzzle', 'Physical', 'Electric', 20, 100, 20, 'Paralyzes opponent.'),
    ('Nasty Plot', 'Status', 'Dark', NULL, NULL, 20, 'Sharply raises user\'s Special Attack.'),
    ('Charm', 'Status', 'Fairy', NULL, 100, 20, 'Sharply lowers opponent\'s Attack.'),
    ('Thunder Shock', 'Special', 'Electric', 40, 100, 30, 'May paralyze opponent.'),
    ('Tail Whip', 'Status', 'Normal', NULL, 100, 30, 'Lowers opponent\'s Defense.'),
    ('Growl', 'Status', 'Normal', NULL, 100, 40, 'Lowers opponent\'s Attack.'),
    ('Quick Attack', 'Physical', 'Normal', 40, 100, 30, 'User attacks first.'),
    ('Thunder Wave', 'Status', 'Electric', NULL, 90, 20, 'Paralyzes opponent.'),
    ('Double Team', 'Status', 'Normal', NULL, NULL, 15, 'Raises user\'s Evasiveness.'),
    ('Electro Ball', 'Special', 'Electric', NULL, 100, 10, 'The faster the user, the stronger the attack.'),
    ('Feint', 'Physical', 'Normal', 30, 100, 10, 'Only hits if opponent uses Protect or Detect in the same turn.'),
    ('Spark', 'Physical', 'Electric', 65, 100, 10, 'May paralyze opponent.'),
    ('Agility', 'Status', 'Psychic', NULL, NULL, 30, 'Sharply raises user\'s Speed.'),
    ('Iron Tail', 'Physical', 'Steel', 100, 75, 15, 'May lower opponent\'s Defense.'),
    ('Discharge', 'Special', 'Electric', 80, 100, 15, 'May paralyze opponent.'),
    ('Thunderbolt', 'Special', 'Electric', 90, 100, 10, 'May paralyze opponent.'),
    ('Light Screen', 'Special', 'Psychic', NULL, NULL, 30, 'Halves damage from Special attacks for 5 turns.'),
    ('Thunder', 'Special', 'Electric', 110, 70, 10, 'May paralyze opponent.'),
    ('Charge', 'Status', 'Electric', NULL, NULL, 20, 'Raises user\'s Special Defense and next Electric move\'s power increases.'),
    ('Disarming Voice', 'Special', 'Fairy', 40, NULL, 15, 'Ignores Accuracy and Evasiveness.'),
    ('Fake Out', 'Physical', 'Normal', 40, 100, 10, 'User attacks first, foe flinches. Only usable on first turn.'),
    ('Flail', 'Physcial', 'Normal', NULL, 100, 15, 'The lower the user\'s HP, the higher the power.'),
    ('Present', 'Physical', 'Normal', NULL, 90, 15, 'Either deals damage or heals.'),
    ('Tickle', 'Status', 'Normal', NULL, 100, 20, 'Lowers opponent\'s Attack and Defense.'),
    ('Volt Tackle', 'Physcial', 'Electric', 120, 100, 15, 'User receives recoil damage. May paralyze opponent.'),
    ('Wish', 'Status', 'Normal', NULL, NULL, 10, 'The user recovers HP in the following turn.'),
    ('Mega Punch', 'Physical', 'Normal', 80, 85, 20, NULL),
    ('Razor Wind', 'Special', 'Normal', 80, 100, 10, 'Charges on first turn, attacks on second. High critical hit ratio.'),
    ('Whirlwind', 'Status', 'Normal', NULL, NULL, 20, 'In battles, the opponent switches. In the wild, the Pokémon runs.'),
    ('Mega Kick', 'Physcial', 'Normal', 120, 75, 5, NULL),
    ('Toxic', 'Status', 'Poison', NULL, 90, 10, 'Badly poisons opponent.'),
    ('Horn Drill', 'Physical', 'Normal', NULL, 30, 5, 'One-Hit-KO, if it hits.'),
    ('Body Slam', 'Physical', 'Normal', 85, 100, 15, 'May paralyze opponent.'),
    ('Take Down', 'Physical', 'Normal', 90, 85, 20, 'User receives recoil damage.'),
    ('Double-Edge', 'Physical', 'Normal', 120, 100, 15, 'User receives recoil damage.'),
    ('BubbleBeam', 'Special', 'Water', 65, 100, 20, 'May lower opponent\'s Speed.'),
    ('Water Gun', 'Special', 'Water', 40, 100, 25, NULL),
    ('Ice Beam', 'Special', 'Ice', 90, 100, 10, 'May freeze opponent.'),
    ('Blizzard', 'Special', 'Ice', 110, 70, 5, 'May freeze opponent.'),
    ('Hyper Beam', 'Special', 'Normal', 150, 90, 5, 'User must recharge next turn.'),
    ('Pay Day', 'Physical', 'Normal', 40, 100, 20, 'Money is earned after the battle.'),
    ('Submission', 'Physical', 'Fighting', 80, 80, 20, 'User receives recoil damage.'),
    ('Counter', 'Physical', 'Fighting', NULL, 100, 20, 'When hit by a Physical Attack, user strikes back with 2x power.'),
    ('Seismic Toss', 'Physical', 'Fighting', NULL, 100, 20, 'Inflicts damage equal to user\'s level.'),
    ('Rage', 'Physical', 'Normal', 20, 100, 20, 'Raises user\'s Attack when hit.'),
    ('Mega Drain', 'Special', 'Grass', 40, 100, 15, 'User recovers half the HP inflicted on opponent.'),
    ('Solar Beam', 'Special', 'Grass', 120, 100, 10,'Charges on first turn, attacks on second.'),
    ('Dragon Rage', 'Special', 'Dragon', NULL, 100, 10, 'Always inflicts 40 HP.');
    
INSERT INTO Locations
	(RegionName, LocationName) VALUES
	('Kanto', 'Route 1'),
    ('Kanto', 'Route 2'),
    ('Kanto', 'Route 3'),
    ('Kanto', 'Route 4'),
    ('Kanto', 'Pallet Town'),
    ('Kanto', 'Viridian City'),
    ('Kanto', 'Pewter City'),
    ('Kanto', 'Mt. Moon'),
    ('Kanto', 'Rocket Hideout'),
    ('Kanto', 'Silph Co.'),
    ('Kanto', 'Victory Road'),
    ('Kanto', 'Fuchsia City'),
    ('Kanto', 'Celadon City'),
    ('Kanto', 'S.S. Anne'),
    ('Kanto', 'Cerulean City'),
    ('Kanto', 'Pokemon Mansion');
    
INSERT INTO Abilities
	(AbilityName, AbilityDescription) VALUES
    ('Overgrow', 'Powers up Grass-type moves when the Pokémon\'s HP is low.'),
    ('Chlorophyll', 'Boosts the Pokémon\'s Speed stat in harsh sunlight.'),
    ('Blaze', 'Powers up Fire-type moves when the Pokémon\'s HP is low.'),
    ('Solar Power', 'In harsh sunlight, the Pokémon\'s Sp. Atk stat is boosted, but its HP decreases every turn.'),
    ('Torrent', 'Powers up Water-type moves when the Pokémon\'s HP is low.'),
    ('Rain Dish', 'The Pokémon gradually regains HP in rain.'),
    ('Sheild Dust', 'Protective dust shields the Pokémon from the additional effects of moves.'),
    ('Run Away', 'Enables a sure getaway from wild Pokémon.'),
    ('Shed Skin', 'The Pokémon may cure its own status conditions by shedding its skin.'),
    ('Compound Eyes', 'The Pokémon\'s compound eyes boost its accuracy.'),
    ('Tinted Lens', 'The Pokémon can use "not very effective" moves to deal regular damage.'),
    ('Swarm', 'Powers up Bug-type moves when the Pokémon\'s HP is low.'),
    ('Sniper', 'If the Pokémon\'s attack lands a critical hit, the attack is powered up even further.'),
    ('Keen Eye', 'The Pokémon\'s keen eyes prevent its accuracy from being lowered.'),
    ('Tangled Feet', 'Boosts the Pokémon\'s evasiveness if it is confused.'),
    ('Big Pecks', 'Prevents the Pokémon from having its Defense stat lowered.'),
    ('Guts', 'It\'s so gutsy that having a status condition boosts the Pokémon\'s Attack stat.'),
    ('Hustle', 'Boosts the Pokémon\'s Attack stat but lowers its accuracy.'),
    ('Intimidate', 'When the Pokémon enters a battle, it intimidates opposing Pokémon and makes them cower, lowering their Attack stats.'),
    ('Unnerve', 'Unnerves opposing Pokémon and makes them unable to eat Berries.'),
    ('Static', 'The Pokémon is charged with static electricity and may paralyze attackers that make direct contact with it.'),
    ('Lightning Rod', 'The Pokémon draws in all Electric-type moves. Instead of taking damage from them, its Sp. Atk stat is boosted.');

INSERT INTO General_Items
	(GeneralItemName, GeneralItemDescription) VALUES
    ('Ability Capsule', 'A capsule that allows a Pokémon with two Abilities to switch between these Abilities when it is used.'),
    ('Potion', 'Restores 20 HP.'),
    ('Antidote', 'Cures a Pokémon of poisoning.'),
    ('Parlyz Heal', 'Cures a Pokémon of paralysis.'),
    ('Burn Heal', 'Cures a Pokémon of a burn.'),
    ('Awakening', 'Wakes up a sleeping Pokémon.'),
    ('Calcium', 'Increases Special Attack EVs by 10.'),
    ('Carbos', 'Increases Speed EVs by 10.'),
    ('Elixir', 'Restores 10 PP to all moves of a Pokémon.'),
    ('Ether', 'Restores 10 PP to one move of a Pokémon.'),
    ('Full Heal', 'Cures a Pokémon of any status condition.'),
    ('Full Restore', 'Fully restores HP and cures any status condition of a Pokémon.'),
    ('HP Up', 'Increases HP EVs by 10.'),
    ('Hyper Potion', 'Restores 120 HP.'),
    ('Ice Heal', 'Cures a Pokémon of freezing.'),
    ('Iron', 'Increases Defense EVs by 10.'),
    ('Max Elixir', 'Fully restores PP to all moves of a Pokémon.'),
    ('Max Ether', 'Fully restores PP to one move of a Pokémon.'),
    ('Max Potion', 'Fully restores HP.'),
    ('Max Revive', 'Revives a fainted Pokémon, fully restoring its HP.'),
    ('PP Max', 'Raises the maximum PP of a move to its maximum.'),
    ('PP Up', 'Raises the maximum PP of a move.'),
    ('Protien', 'Increases Attack EVs by 10.'),
    ('Rare Candy', 'Raises a Pokémon\'s level by 1.'),
    ('Revive', 'Revives a fainted Pokémon, restoring half its HP.'),
    ('Super Potion', 'Restores 60 HP.'),
    ('Zinc', 'Increases Special Defense EVs by 10.');
    
INSERT INTO Machines
	(MachineName, MachineMoveName) VALUES
    ('TM01', 'Mega Punch'),
    ('TM02', 'Razor Wind'),
    ('TM03', 'Swords Dance'),
    ('TM04', 'Whirlwind'),
    ('TM05', 'Mega Kick'),
    ('TM06', 'Toxic'),
    ('TM07', 'Horn Drill'),
    ('TM08', 'Body Slam'),
    ('TM09', 'Take Down'),
    ('TM10', 'Double-Edge'),
    ('TM11', 'BubbleBeam'),
    ('TM12', 'Water Gun'),
    ('TM13', 'Ice Beam'),
    ('TM14', 'Blizzard'),
    ('TM15', 'Hyper Beam'),
    ('TM16', 'Pay Day'),
    ('TM17', 'Submission'),
    ('TM18', 'Counter'),
    ('TM19', 'Seismic Toss'),
    ('TM20', 'Rage'),
    ('TM21', 'Mega Drain'),
    ('TM22', 'Solar Beam'),
    ('TM23', 'Dragon Rage'),
    ('TM24', 'Thunderbolt'),
    ('TM25', 'Thunder');
    
INSERT INTO Pokeballs
	(PokeballName, CatchRateModifierDescription) VALUES
    ('Pokeball', '1x'),
    ('Great Ball', '1.5x'),
    ('Ultra Ball', '2x'),
    ('Master Ball', '255x (Guaranteed capture)'),
    ('Safari Ball', '1x'),
    ('Fast Ball', '4× if used on a Pokémon with a base Speed of at least 100\n1× otherwise'),
    ('Level Ball', '1× if the player\'s Pokémon is the same level as or a lower level than the wild Pokémon\n2× if the player\'s Pokémon is at a higher level than the wild Pokémon but less than double it\n4× if the player\'s Pokémon is more than double but less than four times the level of the wild Pokémon\n8× if the player\'s Pokémon is of a level four times or more than that of the wild Pokémon'),
    ('Lure Ball', '4× if used on a Pokémon encountered while fishing\n1× otherwise'),
    ('Heavy Ball', '-20 if used on a Pokémon weighing 220.2 lbs. (99.9 kg) or less\n0 if used on a Pokémon weighing 220.5 lbs. (100.0 kg) – 440.7 lbs. (199.9 kg)\n+20 if used on a Pokémon weighing 440.9 lbs. (200.0 kg) – 661.2 lbs. (299.9 kg)\n+30 if used on a Pokémon weighing 661.4 lbs. (300.0 kg) or more'),
    ('Love Ball', '8× if used on a Pokémon of the same species but opposite gender of the player\'s Pokémon\n1× otherwise'),
    ('Friend Ball', '1x'),
    ('Moon Ball', '4× if used on a Pokémon that belongs to an evolutionary family which includes a Pokémon that evolves by using a Moon Stone\n1× otherwise'),
    ('Net Ball', '3.5× if used on a Water or Bug-type Pokémon\n1× otherwise'),
    ('Dive Ball', '3.5× if used on a water-dwelling Pokémon\n1× otherwise'),
    ('Nest Ball', '((41 - Pokémon\'s level) ÷ 10)× if Pokémon\'s level is between 1 and 29\n1× otherwise'),
    ('Repeat Ball', '3.5× if used on a Pokémon that is registered in the player\'s Pokédex as caught\n1× otherwise'),
    ('Timer Ball', '(1 + number of turns passed in battle * 1229/4096)×, maximum 4× at 10 turns'),
    ('Luxury Ball', '1x'),
    ('Premier Ball', '1x'),
    ('Dusk Ball', '	3× if used in a cave or at night\n1× otherwise'),
    ('Heal Ball', '1x'),
    ('Quick Ball', '5× if used on the first turn of a battle\n1× otherwise'),
    ('Beast Ball', '5× if used on an Ultra Beast\n0.1× otherwise');
    
INSERT INTO Pokemon
	(PokemonDexNumber, PokemonName, PrimaryTypeName, SecondaryTypeName, PrimaryAbilityName, SecondaryAbilityName, HiddenAbilityName, BaseHitPoints, BaseAttack, BaseDefense, BaseSpecialAttack, BaseSpecialDefense, BaseSpeed, EggGroup1, EggGroup2) VALUES
    (1, 'Bulbasaur', 'Grass', 'Poison', 'Overgrow', NULL, 'Chlorophyll', 45, 49, 49, 65, 65, 45, 'Monster', 'Grass'),
    (2, 'Ivysaur', 'Grass', 'Poison', 'Overgrow', NULL, 'Chlorophyll', 60, 62, 63, 80, 80, 60, 'Monster', 'Grass'),
    (3, 'Venusaur', 'Grass', 'Poison', 'Overgrow', NULL, 'Chlorophyll', 80, 82, 83, 100,100, 80, 'Monster', 'Grass'),
    (4, 'Charmander', 'Fire', NULL, 'Blaze', NULL, 'Solar Power', 39, 52, 43, 60, 50, 65, 'Monster', 'Dragon'),
    (5, 'Charmeleon', 'Fire', NULL, 'Blaze', NULL, 'Solar Power', 58, 64, 58, 80, 65, 80, 'Monster', 'Dragon'),
    (6, 'Charizard', 'Fire', 'Flying', 'Blaze', NULL, 'Solar Power', 78, 84, 78, 109, 85, 100, 'Monster', 'Dragon'),
    (7, 'Squirtle', 'Water', NULL, 'Torrent', NULL, 'Rain Dish', 44, 48, 65, 50, 64, 43, 'Monster', 'Water 1'),
    (8, 'Wartortle', 'Water', NULL, 'Torrent', NULL, 'Rain Dish', 59, 63, 80, 65, 80, 58, 'Monster', 'Water 1'),
    (9, 'Blastoise', 'Water', NULL, 'Torrent', NULL, 'Rain Dish', 79, 83, 100, 85, 105, 78, 'Monster', 'Water 1'),
    (10, 'Caterpie', 'Bug', NULL, 'Shield Dust', NULL, 'Run Away', 45, 30, 35, 20, 20, 45, 'Bug', NULL),
    (11, 'Metapod', 'Bug', NULL, 'Shed Skin', NULL, NULL, 50, 20, 55, 25, 25, 30, 'Bug', NULL),
    (12, 'Butterfree', 'Bug', 'Flying', 'Compound Eyes', NULL, 'Tinted Lens', 60, 45, 50, 90, 80, 70, 'Bug', NULL),
    (13, 'Weedle', 'Bug', 'Poison', 'Sheild Dust', NULL, 'Run Away', 40, 35, 30, 20, 20, 50, 'Bug', NULL),
    (14, 'Kakuna', 'Bug', 'Poison', 'Shed Skin', NULL, NULL, 45, 25, 50, 25, 25, 35, 'Bug', NULL),
    (15, 'Beedrill', 'Bug', 'Poison', 'Swarm', NULL, 'Sniper', 65, 90, 40, 45, 80, 75, 'Bug', NULL),
    (16, 'Pidgey', 'Normal', 'Flying', 'Keen Eye', 'Tangled Feet', 'Big Pecks', 40, 45, 40, 35, 35, 56, 'Flying', NULL),
    (17, 'Pidgeotto', 'Normal', 'Flying', 'Keen Eye', 'Tangled Feet', 'Big Pecks', 63, 60, 55, 50, 50, 71, 'Flying', NULL),
    (18, 'Pidgeot', 'Normal', 'Flying', 'Keen Eye', 'Tangled Feet', 'Big Pecks', 83, 80, 75, 70, 70, 101, 'Flying', NULL),
    (19, 'Rattata', 'Normal', NULL, 'Run Away', 'Guts', 'Hustle', 30, 56, 35, 25, 35, 72, 'Field', NULL),
    (20, 'Raticate', 'Normal', NULL, 'Run Away', 'Guts', 'Hustle', 55, 81, 60, 50, 70, 97, 'Field', NULL),
    (21, 'Spearow', 'Normal', 'Flying', 'Keen Eye', NULL, 'Sniper', 40, 60, 30, 31, 31, 70, 'Flying', NULL),
    (22, 'Fearow', 'Normal', 'Flying', 'Keen Eye', NULL, 'Sniper', 65, 90, 65, 61, 61, 100, 'Flying', NULL),
    (23, 'Ekans', 'Poison', NULL, 'Intimidate', 'Shed Skin', 'Unnerve', 35, 60, 44, 40, 54, 55, 'Field', 'Dragon'),
    (24, 'Arbok', 'Poison', NULL, 'Intimidate', 'Shed Skin', 'Unnerve', 60, 95, 69, 65, 79, 80, 'Field', 'Dragon'),
    (25, 'Pikachu', 'Electric', NULL, 'Static', NULL, 'Lightning Rod', 35, 55, 30, 50, 50, 90, 'Field', 'Fairy'),
    (26, 'Raichu', 'Electric', NULL, 'Static', NULL, 'Lightning Rod', 60, 90, 55, 90, 80, 110, 'Field', 'Fairy');
    


INSERT INTO Type_Attack_Multipliers
	(OffendingTypeName, DefendingTypeName, AttackMultiplier) VALUES
    ('Normal', 'Normal', 1), 	('Normal', 'Fire', 1), 		('Normal', 'Water', 1), 	('Normal', 'Electric', 1), 		('Normal', 'Grass', 1), 	('Normal', 'Ice', 1), 		('Normal', 'Fighting', 1), 		('Normal', 'Poison', 1), 		('Normal', 'Ground', 1), 	('Normal', 'Flying', 1), 	('Normal', 'Psychic', 1), 		('Normal', 'Bug', 1), 		('Normal', 'Rock', 0.5), 	('Normal', 'Ghost', 0), 	('Normal', 'Dragon', 1), 	('Normal', 'Dark', 1), 		('Normal', 'Steel', 0.5), 	('Normal', 'Fairy', 1),
	('Fire', 'Normal', 1), 		('Fire', 'Fire', 0.5), 		('Fire', 'Water', 0.5), 	('Fire', 'Electric', 1), 		('Fire', 'Grass', 2), 		('Fire', 'Ice', 2), 		('Fire', 'Fighting', 1), 		('Fire', 'Poison', 1), 			('Fire', 'Ground', 1), 		('Fire', 'Flying', 1), 		('Fire', 'Psychic', 1), 		('Fire', 'Bug', 2), 		('Fire', 'Rock', 0.5), 		('Fire', 'Ghost', 1), 		('Fire', 'Dragon', 0.5), 	('Fire', 'Dark', 1), 		('Fire', 'Steel', 2), 		('Fire', 'Fairy', 1),
    ('Water', 'Normal', 1), 	('Water', 'Fire', 2), 		('Water', 'Water', 0.5), 	('Water', 'Electric', 1), 		('Water', 'Grass', 0.5), 	('Water', 'Ice', 1), 		('Water', 'Fighting', 1), 		('Water', 'Poison', 1), 		('Water', 'Ground', 2), 	('Water', 'Flying', 1), 	('Water', 'Psychic', 1),		('Water', 'Bug', 1), 		('Water', 'Rock', 2), 		('Water', 'Ghost', 1), 		('Water', 'Dragon', 0.5), 	('Water', 'Dark', 1), 		('Water', 'Steel', 1), 		('Water', 'Fairy', 1),
    ('Electric', 'Normal', 1), 	('Electric', 'Fire', 1), 	('Electric', 'Water', 2), 	('Electric', 'Electric', 1), 	('Electric', 'Grass', 1), 	('Electric', 'Ice', 1), 	('Electric', 'Fighting', 1), 	('Electric', 'Poison', 1), 		('Electric', 'Ground', 0), 	('Electric', 'Flying', 1), 	('Electric', 'Psychic', 1), 	('Electric', 'Bug', 1), 	('Electric', 'Rock', 1), 	('Electric', 'Ghost', 1), 	('Electric', 'Dragon', 1), 	('Electric', 'Dark', 1), 	('Electric', 'Steel', 1), 	('Electric', 'Fairy', 1),
    ('Grass', 'Normal', 1), 	('Grass', 'Fire', 0.5), 	('Grass', 'Water', 2), 		('Grass', 'Electric', 1), 		('Grass', 'Grass', 0.5), 	('Grass', 'Ice', 1), 		('Grass', 'Fighting', 1), 		('Grass', 'Poison', 0.5), 		('Grass', 'Ground', 2), 	('Grass', 'Flying', 0.5), 	('Grass', 'Psychic', 1), 		('Grass', 'Bug', 0.5), 		('Grass', 'Rock', 2), 		('Grass', 'Ghost', 1), 		('Grass', 'Dragon', 0.5),	('Grass', 'Dark', 1), 		('Grass', 'Steel', 0.5), 	('Grass', 'Fairy', 1),
	('Ice', 'Normal', 1), 		('Ice', 'Fire', 0.5), 		('Ice', 'Water', 0.5), 		('Ice', 'Electric', 1),			('Ice', 'Grass', 2), 		('Ice', 'Ice', 0.5), 		('Ice', 'Fighting', 1), 		('Ice', 'Poison', 1), 			('Ice', 'Ground', 2), 		('Ice', 'Flying', 2), 		('Ice', 'Psychic', 1), 			('Ice', 'Bug', 1), 			('Ice', 'Rock', 1), 		('Ice', 'Ghost', 1), 		('Ice', 'Dragon', 2), 		('Ice', 'Dark', 1), 		('Ice', 'Steel', 0.5), 		('Ice', 'Fairy', 1),
	('Fighting', 'Normal', 2), 	('Fighting', 'Fire', 1), 	('Fighting', 'Water', 1), 	('Fighting', 'Electric', 1),	('Fighting', 'Grass', 1),	('Fighting', 'Ice', 2), 	('Fighting', 'Fighting', 1), 	('Fighting', 'Poison', 0.5), 	('Fighting', 'Ground', 1), 	('Fighting', 'Flying', 0.5),('Fighting', 'Psychic', 0.5), 	('Fighting', 'Bug', 0.5), 	('Fighting', 'Rock', 2), 	('Fighting', 'Ghost', 0), 	('Fighting', 'Dragon', 1), 	('Fighting', 'Dark', 2), 	('Fighting', 'Steel', 2), 	('Fighting', 'Fairy', 0.5),
	('Poison', 'Normal', 1), 	('Poison', 'Fire', 1), 		('Poison', 'Water', 1), 	('Poison', 'Electric', 1), 		('Poison', 'Grass', 2), 	('Poison', 'Ice', 1), 		('Poison', 'Fighting', 1), 		('Poison', 'Poison', 0.5), 		('Poison', 'Ground', 0.5), 	('Poison', 'Flying', 1), 	('Poison', 'Psychic', 1), 		('Poison', 'Bug', 1), 		('Poison', 'Rock', 0.5), 	('Poison', 'Ghost', 0.5), 	('Poison', 'Dragon', 1), 	('Poison', 'Dark', 1), 		('Poison', 'Steel', 0), 	('Poison', 'Fairy', 2), 
	('Ground', 'Normal', 1), 	('Ground', 'Fire', 2), 		('Ground', 'Water', 1), 	('Ground', 'Electric', 2), 		('Ground', 'Grass', 0.5), 	('Ground', 'Ice', 1), 		('Ground', 'Fighting', 1), 		('Ground', 'Poison', 2), 		('Ground', 'Ground', 1), 	('Ground', 'Flying', 0), 	('Ground', 'Psychic', 1), 		('Ground', 'Bug', 0.5), 	('Ground', 'Rock', 2), 		('Ground', 'Ghost', 1), 	('Ground', 'Dragon', 1), 	('Ground', 'Dark', 1), 		('Ground', 'Steel', 2), 	('Ground', 'Fairy', 1),
	('Flying', 'Normal', 1), 	('Flying', 'Fire', 1), 		('Flying', 'Water', 1), 	('Flying', 'Electric', 0.5), 	('Flying', 'Grass', 2), 	('Flying', 'Ice', 1), 		('Flying', 'Fighting', 2), 		('Flying', 'Poison', 1), 		('Flying', 'Ground', 1), 	('Flying', 'Flying', 1), 	('Flying', 'Psychic', 1), 		('Flying', 'Bug', 2), 		('Flying', 'Rock', 0.5), 	('Flying', 'Ghost', 1), 	('Flying', 'Dragon', 1), 	('Flying', 'Dark', 1), 		('Flying', 'Steel', 0.5), 	('Flying', 'Fairy', 1),
	('Psychic', 'Normal', 1), 	('Psychic', 'Fire', 1), 	('Psychic', 'Water', 1), 	('Psychic', 'Electric', 1), 	('Psychic', 'Grass', 1), 	('Psychic', 'Ice', 1), 		('Psychic', 'Fighting', 2), 	('Psychic', 'Poison', 2), 		('Psychic', 'Ground', 1), 	('Psychic', 'Flying', 1),	('Psychic', 'Psychic', 0.5), 	('Psychic', 'Bug', 1), 		('Psychic', 'Rock', 1), 	('Psychic', 'Ghost', 1), 	('Psychic', 'Dragon', 1), 	('Psychic', 'Dark', 0), 	('Psychic', 'Steel', 0.5), 	('Psychic', 'Fairy', 1),
	('Bug', 'Normal', 1), 		('Bug', 'Fire', 0.5), 		('Bug', 'Water', 1), 		('Bug', 'Electric', 1), 		('Bug', 'Grass', 2), 		('Bug', 'Ice', 1), 			('Bug', 'Fighting', 0.5), 		('Bug', 'Poison', 0.5), 		('Bug', 'Ground', 1), 		('Bug', 'Flying', 0.5), 	('Bug', 'Psychic', 2), 			('Bug', 'Bug', 1), 			('Bug', 'Rock', 1), 		('Bug', 'Ghost', 0.5), 		('Bug', 'Dragon', 1), 		('Bug', 'Dark', 2), 		('Bug', 'Steel', 0.5), 		('Bug', 'Fairy', 0.5),
	('Rock', 'Normal', 1), 		('Rock', 'Fire', 2), 		('Rock', 'Water', 1), 		('Rock', 'Electric', 1), 		('Rock', 'Grass', 1), 		('Rock', 'Ice', 2), 		('Rock', 'Fighting', 0.5), 		('Rock', 'Poison', 1), 			('Rock', 'Ground', 0.5), 	('Rock', 'Flying', 2), 		('Rock', 'Psychic', 1), 		('Rock', 'Bug', 2), 		('Rock', 'Rock', 1), 		('Rock', 'Ghost', 1), 		('Rock', 'Dragon', 1), 		('Rock', 'Dark', 1), 		('Rock', 'Steel', 0.5), 	('Rock', 'Fairy', 1),
	('Ghost', 'Normal', 0), 	('Ghost', 'Fire', 1), 		('Ghost', 'Water', 1), 		('Ghost', 'Electric', 1), 		('Ghost', 'Grass', 1), 		('Ghost', 'Ice', 1), 		('Ghost', 'Fighting', 1), 		('Ghost', 'Poison', 1), 		('Ghost', 'Ground', 1), 	('Ghost', 'Flying', 1), 	('Ghost', 'Psychic', 2), 		('Ghost', 'Bug', 1), 		('Ghost', 'Rock', 1), 		('Ghost', 'Ghost', 2), 		('Ghost', 'Dragon', 1), 	('Ghost', 'Dark', 0.5), 	('Ghost', 'Steel', 1), 		('Ghost', 'Fairy', 1),
	('Dragon', 'Normal', 1), 	('Dragon', 'Fire', 1),	 	('Dragon', 'Water', 1), 	('Dragon', 'Electric', 1), 		('Dragon', 'Grass', 1), 	('Dragon', 'Ice', 1), 		('Dragon', 'Fighting', 1), 		('Dragon', 'Poison', 1), 		('Dragon', 'Ground', 1), 	('Dragon', 'Flying', 1), 	('Dragon', 'Psychic', 1), 		('Dragon', 'Bug', 1), 		('Dragon', 'Rock', 1), 		('Dragon', 'Ghost', 1), 	('Dragon', 'Dragon', 2), 	('Dragon', 'Dark', 1), 		('Dragon', 'Steel', 0.5), 	('Dragon', 'Fairy', 0),
	('Dark', 'Normal', 1), 		('Dark', 'Fire', 1), 		('Dark', 'Water', 1), 		('Dark', 'Electric', 1), 		('Dark', 'Grass', 1), 		('Dark', 'Ice', 1), 		('Dark', 'Fighting', 0.5), 		('Dark', 'Poison', 1), 			('Dark', 'Ground', 1), 		('Dark', 'Flying', 1), 		('Dark', 'Psychic', 2), 		('Dark', 'Bug', 1), 		('Dark', 'Rock', 1), 		('Dark', 'Ghost', 2), 		('Dark', 'Dragon', 1), 		('Dark', 'Dark', 0.5), 		('Dark', 'Steel', 1), 		('Dark', 'Fairy', 0.5),
	('Steel', 'Normal', 1), 	('Steel', 'Fire', 0.5), 	('Steel', 'Water', 0.5), 	('Steel', 'Electric', 0.5), 	('Steel', 'Grass', 1), 		('Steel', 'Ice', 2), 		('Steel', 'Fighting', 1), 		('Steel', 'Poison', 1), 		('Steel', 'Ground', 1), 	('Steel', 'Flying', 1), 	('Steel', 'Psychic', 1), 		('Steel', 'Bug', 1), 		('Steel', 'Rock', 2), 		('Steel', 'Ghost', 1), 		('Steel', 'Dragon', 1), 	('Steel', 'Dark', 1), 		('Steel', 'Steel', 0.5), 	('Steel', 'Fairy', 2),
	('Fairy', 'Normal', 1), 	('Fairy', 'Fire', 0.5), 	('Fairy', 'Water', 1), 		('Fairy', 'Electric', 1), 		('Fairy', 'Grass', 1), 		('Fairy', 'Ice', 1), 		('Fairy', 'Fighting', 2), 		('Fairy', 'Poison', 0.5), 		('Fairy', 'Ground', 1), 	('Fairy', 'Flying', 1), 	('Fairy', 'Psychic', 1), 		('Fairy', 'Bug', 1), 		('Fairy', 'Rock', 1), 		('Fairy', 'Ghost', 1), 		('Fairy', 'Dragon', 2), 	('Fairy', 'Dark', 2), 		('Fairy', 'Steel', 0.5), 	('Fairy', 'Fairy', 1);

    INSERT INTO Evolutions 
    (PreEvolutionDexNumber, PostEvolutionDexNumber, EvolutionCondition) VALUES
    (1, 2, 'It evolves into Ivysaur starting at level 16'),
    (2, 3, 'It evolves into Venusaur starting at level 32'),
    (4, 5, 'It evolves into Charmeleon starting at level 16'),
    (5, 6, 'It evolves into Charizard starting at level 36'),
    (7, 8, 'It evolves into Wartortle starting at level 16'),
    (8, 9, 'It evolves into Blastoise starting at level 36'),
    (10, 11, 'It evolves into Metapod starting at level 7'),
    (11, 12, 'It evolves into Butterfree starting at level 10'),
    (13, 14, 'It evolves into Kakuna starting at level 7'),
    (14, 15, 'It evolves into Beedrill starting at level 10'),
    (16, 17, 'It evolves into Pidgeotto starting at level 18'),
    (17, 18, 'It evolves into Pidgeot starting at level 36'),
    (19, 20, 'It evolves into Raticate starting at level 20'),
    (21, 22, 'It evolves into Fearow starting at level 20'),
    (23, 24, 'It evolves into Arbok starting at level 22'),
    (25, 26, 'It evolves into Raichu when exposed to a Thunder Stone');
    
INSERT INTO Learnsets
	(MoveName, PokemonDexNumber, LearnCondition) VALUES
    ('Play Nice', 25, 'Learnded at Level 1'),
    ('Sweet Kiss', 25, 'Learnded at Level 1'),
    ('Nuzzle', 25, 'Learnded at Level 1'),
    ('Nasty Plot', 25, 'Learnded at Level 1'),
    ('Charm', 25, 'Learnded at Level 1'),
    ('Thunder Shock', 25, 'Learnded at Level '),
    ('Tail Whip', 25, 'Learnded at Level 1'),
    ('Growl', 25, 'Learnded at Level 1'),
    ('Quick Attack', 25, 'Learnded at Level 1'),
    ('Thunder Wave', 25, 'Learnded at Level 4'),
    ('Double Team', 25, 'Learnded at Level 8'),
    ('Electro Ball', 25, 'Learnded at Level 12'),
    ('Feint', 25, 'Learnded at Level 16'),
    ('Spark', 25, 'Learnded at Level 20'),
    ('Agility', 25, 'Learnded at Level 24'),
    ('Iron Tail', 25, 'Learnded at Level 28'),
    ('Discharge', 25, 'Learnded at Level 32'),
    ('Thunderbolt', 25, 'Learnded at Level 36'),
    ('Light Screen', 25, 'Learnded at Level 40'),
    ('Thunder', 25, 'Learnded at Level 44'),
    ('Charge', 25, 'Egg Move'),
    ('Disarming Voice', 25, 'Egg Move'),
    ('Fake Out', 25, 'Egg Move'),
    ('Flail', 25, 'Egg Move'),
    ('Present', 25, 'Egg Move'),
    ('Tickle', 25, 'Egg Move'),
    ('Volt Tackle', 25, 'Egg Move'),
    ('Wish', 25, 'Egg Move');
    
INSERT INTO Spawnpoints 
	(RegionName, LocationName, PokemonDexNumber, SpawnRate, EncounterScenario) VALUES
    ('Kanto', 'Route 1', 16, 0.5, 'Grass Encounter'),
    ('Kanto', 'Route 1', 19, 0.5, 'Grass Encounter'),
    ('Kanto', 'Route 2', 10, 0.15, 'Grass Encounter'),
    ('Kanto', 'Route 2', 13, 0.15, 'Grass Encounter'),
    ('Kanto', 'Route 2', 16, 0.45, 'Grass Encounter'),
    ('Kanto', 'Route 2', 19, 0.45, 'Grass Encounter'),
    ('Kanto', 'Route 3', 16, 0.45, 'Grass Encounter'),
    ('Kanto', 'Route 3', 21, 0.45, 'Grass Encounter'),
    ('Kanto', 'Route 4', 19, 0.40, 'Grass Encounter'),
    ('Kanto', 'Route 4', 21, 0.35, 'Grass Encounter'),
    ('Kanto', 'Route 4', 23, 0.25, 'Grass Encounter');


INSERT INTO General_Item_Locations
	(GeneralItemName, RegionName, LocationName, GeneralItemSpawnDescription) VALUES
    ('HP Up', 'Kanto', 'Route 2', 'Southeast of the first pair of ledges south of the eastern gate (requires Cut)'),
    ('Ether', 'Kanto', 'Route 2', 'South of the first pair of ledges south of the eastern gate (requires Cut)'),
    ('Parlyz Heal', 'Kanto', 'Route 2', 'South of the second pair of ledges south of the eastern gate (requires Cut)'),
    ('Potion', 'Kanto', 'Route 3', 'Between the ledges south of Youngster Calvin'),
    ('Parlyz Heal', 'Kanto', 'Route 4', 'Southwest of the eastern Mt. Moon entrance');


INSERT INTO Machine_Locations
	(MachineName, RegionName, LocationName, MachineSpawnDescription) VALUES
    ('TM01', 'Kanto', 'Mt. Moon', 'Found on the ground.'),
    ('TM01', 'Kanto', 'Celadon City', 'Purchace at the Celadon Department Store for 3000 PokeYen'),
    ('TM02', 'Kanto', 'Rocket Hideout', 'Found on the ground.'),
    ('TM02', 'Kanto', 'Celadon City', 'Purchace at the Celadon Department Store for 2000 PokeYen'),
    ('TM03', 'Kanto', 'Silph Co.', 'Found on the ground.'),
    ('TM04', 'Kanto', 'Route 4', 'Found on the ground'),
    ('TM05', 'Kanto', 'Victory Road', 'Found on the ground'),
    ('TM05', 'Kanto', 'Celadon City', 'Purchace at the Celadon Department Store for 3000 PokeYen'),
    ('TM06', 'Kanto', 'Fuchsia City', 'Reward for defeating Fuchsia City Gym'),
    ('TM07', 'Kanto', 'Rocket Hideout', 'Found on the ground.'),
    ('TM07', 'Kanto', 'Celadon City', 'Purchace at the Celadon Department Store for 2000 PokeYen'),
    ('TM08', 'Kanto', 'S.S. Anne', 'Found on the ground.'),
    ('TM09', 'Kanto', 'Silph Co.', 'Found on the ground.'),
    ('TM09', 'Kanto', 'Celadon City', 'Purchace at the Celadon Department Store for 3000 PokeYen'),
    ('TM10', 'Kanto', 'Rocket Hideout', 'Found on the ground at B3F'),
    ('TM11', 'Kanto', 'Cerulean City', 'Reward for defeating Cerulean City Gym'),
    ('TM12', 'Kanto', 'Mt. Moon', 'Found on the ground.'),
    ('TM13', 'Kanto', 'Celadon City', 'Found on the ground on the Celadon Department Store rooftop'),
    ('TM14', 'Kanto', 'Pokemon Mansion', 'Found on the ground.');
    

INSERT INTO Pokeball_Locations
	(PokeballName, RegionName, LocationName, PokeballSpawnDescription) VALUES
    ('Great Ball', 'Kanto', 'Route 2', 'North of Diglett\'s Cave (requires Chop Down)'),
    ('Pokeball', 'Kanto', 'Route 3', 'South of the ledges south of Youngster Calvin'),
    ('Great Ball', 'Kanto', 'Route 4', 'In the center of the plateau east of the eastern Mt. Moon entrance (hidden)');
    
#Test Scenarios

#Search for a specific pokemon
USE POKEMON_DB;
BEGIN;
SELECT PokemonDexNumber, PokemonName
FROM Pokemon
WHERE PokemonName LIKE '%char%';

#Insert a new pokemon into the database
#We first need to insert the pokemon's abilities if they are missing from the database
INSERT INTO Abilities
	(AbilityName, AbilityDescription) VALUES
    ('Synchronize', 'If the Pokémon is burned, paralyzed, or poisoned by another Pokémon, that Pokémon will be inflicted with the same status condition.');

#Then we can insert the pokemon
INSERT INTO Pokemon
	(PokemonDexNumber, PokemonName, PrimaryTypeName, SecondaryTypeName, PrimaryAbilityName, SecondaryAbilityName, HiddenAbilityName, BaseHitPoints, BaseAttack, BaseDefense, BaseSpecialAttack, BaseSpecialDefense, BaseSpeed, EggGroup1, EggGroup2) VALUES
	(151, 'Mew', 'Psychic', NULL, 'Synchronize', NULL, NULL, 100, 100, 100, 100, 100, 100, 'No Eggs Discovered', NULL);
    
#Search for locations
SELECT LocationName
FROM Locations
WHERE LocationName LIKE '%Route%';

#Insert a new locaiton
INSERT INTO Locations
	(RegionName, LocationName) VALUES
    ('Unova', 'Victory Road');
    
#Browse a list of items
SELECT *
FROM General_Items;

#View all pokemon in a specific locaion
SELECT Pokemon.PokemonDexNumber, Pokemon.PokemonName, Spawnpoints.SpawnRate, Spawnpoints.EncounterScenario
FROM Pokemon JOIN Spawnpoints
ON Pokemon.PokemonDexNumber = Spawnpoints.PokemonDexNumber
WHERE Spawnpoints.RegionName = 'Kanto' AND Spawnpoints.LocationName = 'Route 1';

#View a pokemons learnset
SELECT Learnset_With_Info.MoveName AS MoveName, Learnset_With_Info.MoveTypeName AS MoveType, Learnset_With_Info.MoveDiscriminator AS Discriminator, Learnset_With_Info.Power AS Power, Learnset_With_Info.Accuracy AS Accuracy, Learnset_With_Info.PP AS PP, Learnset_With_Info.LearnCondition AS LearnCondition
FROM Pokemon JOIN (
	SELECT Moves.MoveName AS MoveName, Moves.MoveTypeName AS MoveTypeName, Moves.MoveDiscriminator AS MoveDiscriminator, Moves.Power AS Power, Moves.Accuracy AS Accuracy, Moves.PowerPoints AS PP, Learnsets.LearnCondition AS LearnCondition, Learnsets.PokemonDexNumber AS PokemonDexNumber
    FROM Learnsets JOIN Moves
    ON Learnsets.MoveName = Moves.MoveName
) AS Learnset_With_Info
ON Pokemon.PokemonDexNumber = Learnset_With_Info.PokemonDexNumber
WHERE Pokemon.PokemonName = 'Pikachu';

#Find all Pokemon who can learn a specific move and have a specific base stat above a certain threshold.
SELECT Pokemon.PokemonDexNumber, Pokemon.PokemonName
FROM Pokemon JOIN Learnsets
ON Pokemon.PokemonDexNumber = Learnsets.PokemonDexNumber
WHERE Pokemon.BaseSpeed >= 50 AND Learnsets.MoveName = 'Quick Attack';

#Find all locations that can spawn a specific Pokemon with a spawn rate above a certain threshold.
SELECT Locations.RegionName, Locations.LocationName, Spawnpoints_With_PokemonName.SpawnRate
FROM Locations JOIN (
	SELECT Spawnpoints.RegionName AS RegionName, Spawnpoints.LocationName AS LocationName, Spawnpoints.SpawnRate AS SpawnRate, Pokemon.PokemonName AS PokemonName
    FROM Spawnpoints JOIN Pokemon
    ON Spawnpoints.PokemonDexNumber = Pokemon.PokemonDexNumber
) AS Spawnpoints_With_PokemonName
ON Locations.RegionName = Spawnpoints_With_PokemonName.RegionName AND Locations.LocationName = Spawnpoints_With_PokemonName.LocationName
WHERE Spawnpoints_With_PokemonName.PokemonName = 'Pidgey' AND Spawnpoints_With_PokemonName.SpawnRate >= 0.25;

#Find all Pokemon who resist attacks from a specific type
SELECT Pokemon.PokemonDexNumber, Pokemon.PokemonName
FROM Pokemon JOIN Type_Attack_Multipliers
ON Pokemon.PrimaryTypeName = Type_Attack_Multipliers.DefendingTypeName OR Pokemon.SecondaryTypeName = Type_Attack_Multipliers.DefendingTypeName
WHERE Type_Attack_Multipliers.OffendingTypeName = 'Ghost'
GROUP BY Pokemon.PokemonDexNumber
HAVING AVG(Type_Attack_Multipliers.AttackMultiplier) < 1.0;

#Find all Pokemon within a specific Egg Group that learn a specific move
SELECT Pokemon.PokemonDexNumber, Pokemon.PokemonName
FROM Pokemon JOIN Learnsets
ON Pokemon.PokemonDexNumber = Learnsets.PokemonDexNumber
WHERE (Pokemon.EggGroup1 = 'Field' OR Pokemon.EggGroup2 = 'Field') AND Learnsets.MoveName = 'Quick Attack';

#Find all Physical moves with a power and an accuracy above a certain threshold
SELECT MoveName, MoveTypeName, MoveDiscriminator, Power, Accuracy, PowerPoints AS PP, Effect
FROM Moves
WHERE MoveDiscriminator = 'Physical' AND Power >= 80 AND Accuracy >= 80;
package Long_Zuul_A4;

/**
 *  This class is the main class of the "World of Zuul" application.
 *  "World of Zuul" is a very simple, text based adventure game.  Users
 *  can walk around some scenery. That's all. It should really be extended
 *  to make it more interesting!
 *
 *  UPDATE 4/3: Player is trapped in a "haunted" house and must escape.  Changed map layout,
 *  names of rooms, and printWelcome to match current game scenario.
 *
 *  UPDATE 4/12: Updated the Long_Zuul_A4.Game class by changing the goRoom(called the getLocationInfo
 *  method) and createRoom(changed to conform with the setExit method) methods and
 *  added the printLocationInfo method.  Updated the Long_Zuul_A4.Room class by adding the getExit,
 *  setExit, getExitString, and getLongDescription methods, and removed the setExits method.
 *
 *  UPDATE 4/17: Added items to the rooms, added a look and pickup command, added a showCommands method
 *  that prints the list of valid commands from the Long_Zuul_A4.CommandWords class.
 *
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 *
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game
{
    private Parser parser;
    private Room currentRoom;

    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        createRooms();
        parser = new Parser();
    }


    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, foyer, garage, livingroom, kitchen, bedroom1, bedroom2, hallway, bathroom;

        // create the rooms
        outside = new Room("outside the house.  You're finnaly out, but as you're walking away you think you hear a voice call out from the entrance, saying 'please come visit again'.  Without bothering to look back, you high tail your ass outta there as fast as you can go.");
        foyer = new Room("inside the foyer.  There is a flashlight to your right hanging on a wall mount used to hang coats off of.  Taking it would be a good idea as the rest of the house is too dark to see.");
        garage = new Room("inside the garage.  After a bit of searching, you find a crowbar and figure it might come in handy later.");
        livingroom = new Room("inside the living room.  After a bit of searching, nothing of use is found.");
        kitchen = new Room("in the kitchen.  A bit of searching turns up nothing of use, besides maybe a chef's knife in case an intruder shows up.  Oh, wait...you are the intruder here.");
        bedroom1 = new Room("in what appears to be the main bedroom.  A wooden box with a glass square on top with a key inside is found.  The glass is pretty sturdy, and the lock is rusted over.  Bashing it against the wood furniture does nothing to break it.");
        bedroom2 = new Room("in a small bedroom.  A quick look around shows nothing useful except a few creepy old children's dolls that make you wonder how kids found those things 'fun' or 'cute'");
        hallway = new Room("in the hallway.  Nothing but old sepia pictures of people with blank expressions on their faces line the walls.  As you walk around the hall, you can almost feel their eyes follow you when you aren't pointing your flashlight at them.");
        bathroom = new Room("in the bathroom.  Luckily, nothing creepy is here.  Your reflection in the mirror stays normal, and there isn't a dead body in the bathtub.  A look inside the cabinets returns nothing of value.");


        // initialise room exits
        foyer.setExit("north", hallway);
        foyer.setExit("east", livingroom);
        foyer.setExit("south", outside);
        foyer.setExit("west", garage);
        garage.setExit("east", foyer);
        livingroom.setExit("east", kitchen);
        livingroom.setExit("west", foyer);
        kitchen.setExit("west", livingroom);
        hallway.setExit("north", bathroom);
        hallway.setExit("east", bedroom2);
        hallway.setExit("south", foyer);
        hallway.setExit("west", bedroom1);
        bedroom1.setExit("east", hallway);
        bedroom2.setExit("west", hallway);
        bathroom.setExit("south", hallway);

        //Add items to the rooms
        foyer.addItem(new Item("Flashlight"));
        garage.addItem(new Item("Crowbar"));
        bedroom1.addItem(new Item("Key box"));

        currentRoom = foyer;  // start game inside the foyer
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    private void play()
    {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("You get an invite from your friends to meet up at a haunted house in the hills.");
        System.out.println("You decide why not, and meet up at the house just after sunset for maximum effect.");
        System.out.println("Your friends decide to 'volunteer' you to be the first.  As you take your first");
        System.out.println("few steps inside, the door suddenly slams shut and locks.  Your friends and you");
        System.out.println("both yelp in suprise and your 'friends' haul ass out of there, with one of them");
        System.out.println("wishing you good luck.");
        System.out.println("You are now stuck inside the house and need to find a way out.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if(commandWord.equals("look")){
            look();
        }
        else if(commandWord.equals("pickup")){
            System.out.println("You picked up a thing");
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp()
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());
    }

    /**
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * Looks around the room(calls the getLongDescription method)
     */
    private void look()
    {
        System.out.println(currentRoom.getLongDescription());
    }
}

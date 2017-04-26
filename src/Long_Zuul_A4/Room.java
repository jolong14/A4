package Long_Zuul_A4;

import java.util.*;
import java.util.HashMap;
/**
 * Class Long_Zuul_A4.Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * A "Long_Zuul_A4.Room" represents one location in the scenery of the game.  It is
 * connected to other rooms via exits.  The exits are labelled north,
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room
{
    private String description;
    private HashMap<String, Room> exits;
    public Room northExit;
    public Room southExit;
    public Room eastExit;
    public Room westExit;
    private ArrayList<Item> items;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description)
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        items = new ArrayList<Item>();
    }

    /**
     * Define an exit from this room
     * @param direction The direction of the exit.
     * @param neighbor The room in the given direction
     */
    public void setExit(String direction, Room neighbor){
        exits.put(direction, neighbor);
    }

    public Room getExit(String direction){
        return exits.get(direction);
    }

    /**
     * Return a description of the room's exits
     * Ex. "Exits: north south".
     * @return A description of the available exits.
     */
    public String getExitString(){
        String returnString = "Exits: ";
        Set<String> keys = exits.keySet();
        for(String exit : keys){
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return a long description of this room, of the form:
     * You are in the kitchen.
     * Exits: north west
     * @return A description of the room, including the exits.
     */
    public String getLongDescription(){
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Add an item to the array of items
     */
    public void addItem(Item thingy){
        items.add(thingy);
    }

}

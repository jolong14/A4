package Long_Zuul_A4;

/**
 * Adds an item to a room
 */
public class Item
{
    private String name;

    public Item(String name){
        this.name = name;
    }

    /**
     * Get the name of an item
     */
    public String getName(){
        return name;
    }
}
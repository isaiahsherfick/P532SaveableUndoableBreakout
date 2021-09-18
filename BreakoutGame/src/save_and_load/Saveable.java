/**
 * @author: Isaiah Sherfick
 * @CreationDate: Sep 16, 2021
 * @editors:
 **/

package save_and_load;

//Interface for save and load functionality
//Every game object needs to implement this
//
//Will get messy with recursive calls in complex objects,
//but hopefully I will figure out a way to make it cleanish
public interface Saveable
{
    //Produce the saveString
    public String save();

    //reconstruct the saveable from the saveString
    //Call this method on a freshly default constructed
    //member of the corresponding class
    public void load(String saveString) throws BadSaveStringException;
}

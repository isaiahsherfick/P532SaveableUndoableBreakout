/**
 * @author: Isaiah Sherfick
 * @CreationDate: Sep 17, 2021
 * @editors:
 **/
package save_and_load;

//Exception to be thrown when a Saveable's load() method
//is given a string that is somehow incorrect or corrupt
public class BadSaveStringException extends Exception 
{
    public BadSaveStringException(String errorMessage)
    {
        super(errorMessage);
    }
}

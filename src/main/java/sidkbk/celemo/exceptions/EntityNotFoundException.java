package sidkbk.celemo.exceptions;

public class EntityNotFoundException extends RuntimeException{


    //this metod needs to be added since im using @NotBlank, @NotNull etc. If some variable is empty while trying to add new data
    //it will throw EntityNotFoundException instead of adding, since that one variable can not be empty.
    public EntityNotFoundException(String message){
        super(message);
    }
}

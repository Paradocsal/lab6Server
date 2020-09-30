package Commands;

import This.Organization;
import This.OrganizationData;

import java.util.NoSuchElementException;

public class Head implements Command{
    public  Head(){
        CommandExecutor.addCommand("head",this);
    }
    public String execute(String arg, OrganizationData data) {
        try {
            Organization sFirst = data.getFirst();
            return ("First element is " + sFirst.getName());
        }
        catch (NoSuchElementException e){
            return ("Collection is empty");
        }
    }
}

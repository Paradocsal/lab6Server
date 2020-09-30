package Commands;

import Server.ServerReceiver;
import Server.ServerSender;
import This.NewElementData;
import This.Organization;
import This.OrganizationData;

public class AddNew implements Command {
    public AddNew(){
        CommandExecutor.addCommand("add",this);
    }
    public String execute(String s, OrganizationData orgData){
        ServerSender.send("",1);
        Organization organization =(Organization) ServerReceiver.receive();
        orgData.addOrganization(organization);
        return (organization.getName() + " was added");

    }
}

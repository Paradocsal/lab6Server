package Commands;

import Server.ServerReceiver;
import Server.ServerSender;
import This.NewElementData;
import This.Organization;
import This.OrganizationData;
import This.ZonedDateTimer;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;

public class UpdateId implements Command {
    public UpdateId(){
        CommandExecutor.addCommand("update_id", this);
    }

    @Override
    public String execute(String s, OrganizationData data) {
        try{
            long id = Long.parseLong(s);


            if (data.getListOfIds().contains(id)) {
                ServerSender.send("",1);
                Organization organization = (Organization) ServerReceiver.receive();
               // organization.setCreationDate(ZonedDateTimer);
                data.updateOrganization(id, organization);
                Organization newOrganization = data.getElementById(id);
                return ("Element with id " + id + " was updated, new one:\n" + newOrganization);
            } else {
                return ("Band with id " + id + " doesn't exist");
            }

        } catch (NumberFormatException e) {
            return ("Wrong id number format");
        }

        }
    }


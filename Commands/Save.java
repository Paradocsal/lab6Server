package Commands;

import This.FileWork;
import This.OrganizationData;

import javax.xml.bind.JAXBException;

public class Save implements Command{
    public Save() {
        CommandExecutor.addCommand("save", this);
    }

    @Override
    public String execute(String arg, OrganizationData data) {

        final String nameOfEnvVar = "Xml";
        String dataPath = System.getenv(nameOfEnvVar);
        dataPath = "org.txt";
        if (dataPath == null) {
            System.out.println("ERROR\nYou need to set environment variable(DATA_PATH) with path to you data file(.xml)");
        } else {
            try {
                FileWork.saveToXml(data, dataPath);
                System.out.println("Data saved to file: " + dataPath);
            } catch (JAXBException e) {
                e.printStackTrace();
                System.out.println("Error while saving");
            }
        }
        return null;
    }
}

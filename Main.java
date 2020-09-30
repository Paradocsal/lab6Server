
import Commands.*;
import Server.ServerReceiver;
import Server.ServerSender;
import This.FileWork;
import This.OrganizationData;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        OrganizationData organizationData = new OrganizationData();
        String dataPath = System.getenv("EnvName");
        if (dataPath == null) {
            System.out.println("No data,exiting...");
            System.exit(0);
        } else {
            System.out.println("Getting data from file " + dataPath + " ...");
            try {
                organizationData = FileWork.readFromXML(dataPath);
                System.out.println("Got data");

            } catch (JAXBException e) {
                e.printStackTrace();
                System.out.println("error while reading data from XML");
                System.out.println("Exit...");
                System.exit(0);
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            ServerSender serverSender = new ServerSender();
            System.out.println("Enter port for server");
            System.out.print("> ");
            String line = bufferedReader.readLine();
            int port = Integer.parseInt(line);
            ServerReceiver serverReceiver = new ServerReceiver("localhost", port);
            checkForSaveCommand(organizationData);
            Help helpCommand = new Help();
            Info infoCommand = new Info();
            Exit exitCommand = new Exit();
            Show showCommand = new Show();
            Save saveCommand = new Save();
            AddNew addNewCommand = new AddNew();
            UpdateId updateCommand = new UpdateId();
            RemoveById removeByIdCommand = new RemoveById();
            Clear clearCommand = new Clear();
            ExecuteScriptFileName executeScriptCommand = new ExecuteScriptFileName();
            Head headCommand = new Head();
            RemoveFirst removeFirstCommand = new RemoveFirst();
            RemoveGreater removeGreaterCommand = new RemoveGreater();
            AverageOfEmployeesCount averageOfEmployeesCountCommand = new AverageOfEmployeesCount();
            PrintFieldDescendingPostalAddress printFieldDescendingPostalAddress = new PrintFieldDescendingPostalAddress();
            FilterStartsWithName filterStartsWithName = new FilterStartsWithName();

            while (true) {
                try {
                    Map<Command, String> commandableStringMap = (Map<Command, String>) ServerReceiver.receive();
                    ServerReceiver.isBusy = true;
                    System.out.println("Received " + commandableStringMap.entrySet().iterator().next().getKey() + " from client.");
                    String answer = commandableStringMap.entrySet().iterator().next().getKey().execute(commandableStringMap.entrySet().iterator().next().getValue(), organizationData);
                    serverSender.send(answer, 0);
                    ServerReceiver.isBusy = false;
                } catch (Exception e) {
                    ServerSender.currentClientSocket.close();
                    ServerReceiver.isBusy = false;
            }
            }
        }
    }

    public static void checkForSaveCommand(OrganizationData organizationData) throws IOException {
        Thread backgroundReaderThread = new Thread(() -> {
            System.out.println("Check for \"save\" and \"exit\" commands started");
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
                while (!Thread.interrupted()) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    if (line.equalsIgnoreCase("save")) {
                        Save s = new Save();
                        s.execute(null, organizationData);
                    }
                    if (line.equalsIgnoreCase("exit")) {
                        Save s = new Save();
                        s.execute(null, organizationData);
                        System.out.println("Exiting...");
                        System.exit(0);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        backgroundReaderThread.start();
    }
}


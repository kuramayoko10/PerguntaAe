package topcom.pergunteai;

import java.util.ArrayList;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;


public class JavaServer implements Runnable
{
    private String ip = "127.0.0.1";
    private int port = 1024;
    private DataOutputStream output;
    private DataInputStream input;
    private ArrayList<Socket> connections;
    private ArrayList<String> prohibited;

    @Override
    public void run()
    {
        boolean running = true;
        connections = new ArrayList<Socket>();

        while(running)
        {
            try
            {
                ServerSocket serverSocket = new ServerSocket(port);
                Socket client = serverSocket.accept();
                input = new DataInputStream(client.getInputStream());
                output = new DataOutputStream(client.getOutputStream());

                connections.add(client);

                //Receive message from client
                String request = input.readLine();  //Ok but what to use instead?
                String[] sections = request.split(" ");
                String response = "";

                //Processing
                if(applyFilter(sections[2]) == true)    //Filter content
                {
                    if(sections[0].compareTo("REFRESH") == 0)
                    {
                        //REFRESH Home Category

                        String category = sections[2];
                        String sql = "SELECT category FROM QUESTION_TABLE WHERE category = " + sections[2] + ";";
                        String dbResponse = accessDB(sql);

                        response = "QUESTION_LIST ";
                        addToResponse(response, dbResponse);
                    }
                    else if(sections[0].compareTo("SUBMIT_QUESTION") == 0)
                    {
                        //SUBMIT_QUESTION User Content Category
                    }
                    else if(sections[0].compareTo("SUBMIT_ANSWER") == 0)
                    {
                        //SUBMIT_ANSWER user content questionID
                    }
                }
                else throw new SecurityException();

                //Response & closing
                output.writeBytes(response);
                output.flush();

                input.close();
                output.close();
                client.close();
                serverSocket.close();

            } catch (IOException ex) {
                ex.printStackTrace();
                running = false;
            }
        }
    }

    private boolean applyFilter(String request)
    {
        //Prohibited vocabulary
        for(int i = 0; i < prohibited.size(); i++)
        {
            if(request.contains(prohibited.get(i)))
                return false;
        }

        //Prohibited terms: SQL commands, Android commands, etc.
        //...

        return true;
    }

    private String accessDB(String sql)
    {
        String str = "DB Response";

        //DB stuff here

        return str;
    }

    private void addToResponse(String response, String str)
    {
        response += str + " ";
    }

    private void addToResponse(String response, int i)
    {
        response += String.valueOf(i) + " ";
    }

    private void addToResponse(String response, double d)
    {
        response += String.valueOf(d) + " ";
    }

    public static void main(String argv[])
    {
        //Read config file
        //Fillup prohibited terms

        //TO DO: escalate server to work with multiple threads accepting requests and dealing with the DB
        JavaServer server = new JavaServer();
        Thread t = new Thread(server);
        t.start();
    }


}

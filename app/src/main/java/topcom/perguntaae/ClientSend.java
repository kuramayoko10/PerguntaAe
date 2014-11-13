package topcom.pergunteai;

import android.os.AsyncTask;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;


public class ClientSend extends AsyncTask<Void, Void, Void>
{
    private Socket socket;
    private String serverAddr = "127.0.0.1";
    private int port = 1024;
    private DataOutputStream output;
    private DataInputStream input;
    private String message, received;

    public ClientSend(String message)
    {
        super();
        this.message = message;
    }

    @Override
    protected Void doInBackground(Void... params)
    {
        try
        {
            socket = new Socket(serverAddr, port);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            //Send message
            output.writeBytes(message);

            //Wait response & save
            received = input.readLine();

            //Closing
            output.flush();
            output.close();
            socket.close();
        }catch(IOException ex)
        {
            ex.printStackTrace();
        }

        return null;
    }



}

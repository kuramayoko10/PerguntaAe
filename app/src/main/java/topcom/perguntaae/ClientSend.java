package topcom.perguntaae;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Vector;
import java.util.concurrent.ExecutionException;


public class ClientSend extends AsyncTask<Void, Void, Void>
{
    private Socket socket;
    //private String serverAddr = "192.168.1.127";
    private String serverAddr = "172.26.5.179";
    private int port = 49152;
    private DataOutputStream output;
    private ObjectInputStream input;
    private String message, received;
    private Vector data;
    private Object submitObj;
    boolean success = false;

    public ClientSend(String message)
    {
        super();
        this.message = message;
        data = new Vector();
    }

    public ClientSend(String message, Object submitObj)
    {
        super();
        this.message = message;
        this.submitObj = submitObj;
    }

    @Override
    protected Void doInBackground(Void... params)
    {
        try
        {
            socket = new Socket(serverAddr, port);
            input = new ObjectInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            //Send message
            output.writeInt(message.length());
            output.write(message.getBytes("UTF-8"));

            //if(submitObj != null)
            //    output.writeObject(submitObj);  //DID: change output here to ObjectOutput and input to InputObject on server
            Log.d("Debug", "Connection established");

            byte[] buffer;  //antes new byte[1024];
            int count;

            //Wait response
            while(input.available() < 4)
            {
                ;
            }

            count = input.readInt();
            Log.d("Debug", "count: " + count);
            buffer = new byte[count+1];
            input.read(buffer, 0, count);
            received = new String(buffer, 0, count, Charset.forName("UTF-8"));
            Log.d("Debug", "Response: " + received);

            if(received.equals("QUESTION_LIST"))
            {
                Vector answerCount, userNames, aux;

                aux = (Vector)input.readObject();
                userNames = (Vector)input.readObject();
                answerCount = (Vector)input.readObject();

                for(int i = 0; i < aux.size(); i++)
                {
                    Vector row = (Vector)aux.get(i);
                    Vector elem1 = (Vector)userNames.get(i), elem2 = (Vector)answerCount.get(i);
                    row.addElement(elem1.get(0));
                    row.addElement(elem2.get(0));
                    data.addElement(row);
                }
                success = true;
            }
            else if(received.equals("INSERTION_OK"))
            {
                success = true;
            }
            else if(received.equals("DENIED"))
            {
                success = false;
            }
            else if(received.equals("ANSWER_DETAILS"))
            {
                Vector userNames, aux;

                aux = (Vector)input.readObject();
                userNames = (Vector)input.readObject();

                for(int i = 0; i < aux.size(); i++)
                {
                    Vector row = (Vector)aux.get(i);
                    row.addElement(userNames.get(i));
                    data.addElement(row);
                }
                success = true;
            }
            else if(received.equals("USER_EXISTS"))
            {
                data = (Vector)input.readObject();
                data = (Vector)data.elementAt(0);
                success = true;
            }

            //Closing
            output.close();
            input.close();
            socket.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void finishQuery()
    {
        try {
            //Wait until the query finishes
            this.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public Vector getData()         { return data; }
    public String getResponse()     { return received; }

}

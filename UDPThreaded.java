import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
class ClientThread implements Runnable{
      public GUI g = new GUI();
    @Override
    public void run()
    {
      try{
        BufferedReader inFromUser =
         new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("10.145.244.193");
              g.send.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                String sentence= g.area.getText();
                g.area.setText("");
                byte[] sendData = new byte[1024];
                sendData = sentence.getBytes();
                g.areane.append("Sent: "+sentence + "\n");
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
                try
                {clientSocket.send(sendPacket);}
                catch (Exception g){
                  g.printStackTrace();
                }
              }
            } );
      }
      catch (Exception f){
        f.printStackTrace();
      }
    }
}
class ServerThread implements Runnable {

    @Override
    public void run()
    {
      ClientThread c=  new ClientThread();
      c.run();
      try{
        DatagramSocket serverSocket = new DatagramSocket(9876);
           byte[] sendData = new byte[1024];
           while(true)
              {
                byte[] receiveData = new byte[1024];
                 DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                 serverSocket.receive(receivePacket);
                 String sentence = new String( receivePacket.getData());
                 System.out.println("RECEIVED: " + sentence);
                 c.g.areane.append("RECEIVED: "+sentence +"\n");
                 InetAddress IPAddress = receivePacket.getAddress();
                 int port = receivePacket.getPort();
                 String capitalizedSentence = sentence.toUpperCase();
                 sendData = capitalizedSentence.getBytes();
                 DatagramPacket sendPacket =
                 new DatagramPacket(sendData, sendData.length, IPAddress, port);
                 serverSocket.send(sendPacket);
              }
      }
      catch (Exception e){
        e.printStackTrace();
      }
    }
}

public class UDPThreaded{
    public static void main(String[] args)
    {
      Thread t2 = new Thread(new ServerThread(), "t2");
      t2.start();
    }
  }

class GUI extends JFrame
{
  JFrame frame;
  JTextArea area,areane;
  JButton send;
  GUI()
  {
    frame =new JFrame("Chat Applet");
    area = new JTextArea(10,30);
    areane = new JTextArea(10,60);
    send = new JButton("Send");
    area.setBackground(Color.white);
    area.setBounds(10,20,200,40);
    send.setBackground(Color.blue);
    send.setBounds(220,20,70,40);
    areane.setBounds(20,70,250,200);
    areane.setBackground(Color.gray);
    areane.setEditable(false);
    frame.getContentPane().setBackground(Color.black);

    frame.setVisible(true);


    frame.setSize(310, 350);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(true);
    frame.getContentPane().setLayout(null);

    frame.add(area);
    frame.add(areane);
    frame.add(send);
  }
}

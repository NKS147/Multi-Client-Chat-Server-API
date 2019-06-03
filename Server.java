package scpbb;

import java.net.*;
import java.io.*;
import java.lang.*;
import java.util.*;



public class Server {
	
	private ServerSocket ss;
	private Socket s;
	
	private static List<String> clients_name;
	private static List<Socket> clients_socket;
	private static List<ObjectOutputStream> clients_outstream;
	private static int clients_cnt=-1;
	
	// constructor
	public Server(int port) throws IOException
	{
		ss = new ServerSocket(port);
		clients_name=new Vector<String>();
		clients_socket= new Vector<Socket>();
		clients_outstream = new Vector<ObjectOutputStream>();
	}
	
	private class ClientHandler implements Runnable
	{
		private Socket sa;
		private ObjectInputStream is;
		private int selfosind,frndosind;
		
		//constructor
		public ClientHandler(Socket sb, int osind) {
			sa=sb;
			selfosind=osind;
			frndosind=osind;
		}
		
		//methods
		private void setConnectionStreams() throws IOException
		{
			is= new ObjectInputStream(sa.getInputStream());
		}
		
		private void nsetConnectionStreams() throws IOException
		{
			is= null;
		}
						//classes
						class WhileChatting implements Runnable
						{
							private String msg;
							
							@Override
							public void run()
							{
								try {
									// client will first send its name then it is added to cln.
									clients_name.add(is.readUTF());
								
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
							
								while(true)
								{
									try {
										msg=is.readUTF();
										if(msg.equals("giveonlinelist"))
										{
											clients_outstream.get(selfosind).writeUTF("haveonlinelist");
											clients_outstream.get(selfosind).flush();
											Iterator<String> itr = clients_name.iterator();
											for(;itr.hasNext();)
											{
												String t=itr.next();
												System.out.println(t+" ");
												clients_outstream.get(selfosind).writeUTF(t);
												clients_outstream.get(selfosind).flush();
											}
											clients_outstream.get(selfosind).writeUTF("endonlinelist");
											clients_outstream.get(selfosind).flush();
											
											
											System.out.println(msg + " List send");
											
											
								
										}
										else
										{
											if(msg.equals("#ID"))
											{
												clients_outstream.get(selfosind).writeUTF("$ Enter ID: $");
												clients_outstream.get(selfosind).flush();
												String id = is.readUTF();
												frndosind= Integer.parseInt(id);

												clients_outstream.get(selfosind).writeUTF("$ Connected to: "+clients_name.get(frndosind)+ " $");
												clients_outstream.get(selfosind).flush();
												
												
												
											}
											else
											{
												clients_outstream.get(frndosind).writeUTF(clients_name.get(selfosind)+": "+ msg);
												clients_outstream.get(frndosind).flush();
												
												
											}
										}
										
										
									} catch (Exception e) {
										e.printStackTrace();
									}
									System.out.println(msg);
								}
			
				
							  }
						}
		@Override
		public void run() 
		{
			try {
				setConnectionStreams();
				
				Thread wr = new Thread(new WhileChatting());
				System.out.println("wr-1");
				
				wr.start();
				System.out.println("wr 0 ");
					
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
	};
	
	
	public static void main(String[] args) throws IOException
	{
		Server main_server =new Server(5001);
		// client_num indicating number of clients, that can be online at the same time.
		int client_num=100;
		while(client_num!=0)
		{
			System.out.println("Waiting for connection");
			main_server.s=main_server.ss.accept();
			System.out.println("Connected");
			
			//Entering into cls
			clients_socket.add(main_server.s);
			clients_cnt=clients_cnt+1;
			clients_outstream.add(new ObjectOutputStream( (main_server.s.getOutputStream()) ) );
			
			//for checking
			clients_outstream.get(clients_cnt).writeUTF("CHECK");
			clients_outstream.get(clients_cnt).flush();
			
			
			Thread ch= new Thread(main_server.new ClientHandler(main_server.s,clients_cnt));
			ch.start();
			long cp=main_server.s.getPort();
			String chname=ch.getName();
			long chid=ch.getId();
			
			
	
			System.out.println("CHT:INFO " );
			System.out.println("CHT: "+ chname  + "idd "+ chid + " cp"+ cp+ " "+ main_server.s.getInetAddress());
			
			
			
			client_num--;
		}
		
			
	}

}

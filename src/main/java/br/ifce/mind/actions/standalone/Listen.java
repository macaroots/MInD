/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.standalone;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import br.ifce.mind.actions.AbstractAction;

public class Listen extends AbstractAction {

	@Override
	public void act(Object args, Object callback) {
		try {
			int portNumber = Integer.parseInt(((String[]) args)[1]);
			System.out.println("Listening to port #" + portNumber);
			ServerSocket serverSocket = new ServerSocket(portNumber);
			Socket clientSocket = serverSocket.accept();
			PrintWriter out =
					new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));


			String inputLine;
			// Initiate conversation with client
			while ((inputLine = in.readLine()) != null) {
				if (inputLine.equals("exit"))
					break;
				this.getAgent().see("callAgent", inputLine.split(" "), new AbstractAction() {
					public void act(Object target, Object callback) {
						out.println(this.getAgent() + " says: " + target);	
					}
				});
			}
			serverSocket.close();
		}
		catch (Exception e) {
			System.out.println("Error binding agent to socket: " + e.getMessage());
		}

	}

}

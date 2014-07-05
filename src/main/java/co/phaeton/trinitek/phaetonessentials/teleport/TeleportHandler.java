package co.phaeton.trinitek.phaetonessentials.teleport;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportHandler {
	
	private ArrayList<TeleportRequest> requestQueue;
	
	public TeleportHandler() {
		// dynamically expanding array of open requests
		this.requestQueue = new ArrayList<TeleportRequest>();
	}
	
	/**
	 * Get the number of currently open requests
	 * @return number of requests
	 */
	public int getNumberOfOpenRequests() {
		return requestQueue.size();
	}
	
	/**
	 * Helper method for creating a formatted String representation of the type of request
	 * @param request
	 * @return ">>" if 'tpa', or "<<" if 'tphere'
	 */
	private String requestTypeToString(TeleportRequest request) {
		if (request.getRequestType()) { // 'tpa'
			return ">>";
		} else { // 'tphere'
			return "<<";
		}
	}
	
	public void displayRequestList(CommandSender cmdSender) {
		cmdSender.sendMessage(ChatColor.GRAY + "There are " + Integer.toString(getNumberOfOpenRequests()) + " open teleport requests");
		
		if (getNumberOfOpenRequests() > 0) {
			for (TeleportRequest request : requestQueue) {
				cmdSender.sendMessage(ChatColor.GRAY + request.getSender().getName() + " " + requestTypeToString(request) + " " + request.getRecipient().getName());
			}
		}
	}
	
	/**
	 * Creates a TeleportRequest object and adds it to the queue
	 * @param requestType - true if 'tpa', false if 'tphere'
	 * @param sender - Player that sent the request
	 * @param recipient - Player that received the request
	 * @param overridePrompt - flag that determines if the request prompt should not be displayed
	 */
	public void createRequest(boolean requestType, Player sender, Player recipient, boolean overridePrompt) {
		if (overridePrompt) {
			if (requestType) { // 'tpa'
				sender.teleport(recipient);
			} else { // 'tphere'
				recipient.teleport(sender);
			}
		} else {
			/* 
			 * If overridePrompt is not set, check for any pending requests on the receiver's side, and for
			 * an already existing request on the sender's side, before creating a new one
			 */
			for (int index = 0; index < requestQueue.size(); index++) {
				if (requestQueue.get(index).getRecipient() == recipient) {
					sender.sendMessage(ChatColor.RED + recipient.getName() + " already has a pending teleport request");
					return;
				} else if (requestQueue.get(index).getSender() == sender) {
					sender.sendMessage(ChatColor.RED + requestQueue.get(index).getRecipient().getName() + " hasn't answered your previous request yet. Use /tpcancel to abort.");
					return;
				}
			}
			
			// add the request to the queue and tell the two players that it went through
			requestQueue.add(new TeleportRequest(requestType, sender, recipient));
			sender.sendMessage(ChatColor.GRAY + "Teleport request sent to " + recipient.getName());
			
			if (requestType) { // 'tpa'
				recipient.sendMessage(ChatColor.YELLOW + sender.getName() + " wants to teleport to you. /tpaccept or /tpdeny");
			} else { // 'tphere'
				recipient.sendMessage(ChatColor.YELLOW + sender.getName() + " wants you to teleport to him. /tpaccept or /tpdeny");
			}
		}
	}
	
	/**
	 * Process the /tpaccept command. The sender of this command is the recipient of the request.
	 * @param Player that sent the command
	 */
	public void acceptRequest(Player commandSender) {
		
		// file through the requestQueue and look for commandSender == requestRecipient
		for (TeleportRequest request : requestQueue) {
			Player requestSender = request.getSender();
			Player requestRecipient = request.getRecipient();
			
			// send each of them messages saying that the request was confirmed
			requestSender.sendMessage(ChatColor.YELLOW + requestRecipient.getName() + " accepted your teleport request");
			requestRecipient.sendMessage(ChatColor.YELLOW + "Request accepted. Teleporting...");
			
			if (request.getRequestType()) { // 'tpa' - sender >> recipient
				// write the teleport record to the sender's history
				TeleportHistoryHandler.add(requestSender, new TeleportHistory(requestSender.getLocation(), TeleportDirection.OUTGOING));
				TeleportHistoryHandler.add(requestSender, new TeleportHistory(requestRecipient.getLocation(), TeleportDirection.INCOMING));
				
				// and teleport
				requestSender.teleport(requestRecipient);
				
			} else { // 'tphere' - sender << recipient
				// write the teleport record to the recipient's history
				TeleportHistoryHandler.add(requestRecipient, new TeleportHistory(requestRecipient.getLocation(), TeleportDirection.OUTGOING));
				TeleportHistoryHandler.add(requestRecipient, new TeleportHistory(requestSender.getLocation(), TeleportDirection.INCOMING));
				
				// and teleport
				requestRecipient.teleport(requestSender);
			}
			
			// and remove that request from the queue
			requestQueue.remove(request);
			
			return;
		}
		
		// otherwise, there must not have been a request waiting
		commandSender.sendMessage(ChatColor.RED + "There are no teleport requests waiting for you");
	}
	
	/**
	 * Process the /tpdeny command. The sender of this command is the recipient of the request.
	 * @param Player that sent the command
	 */
	public void denyRequest(Player commandSender) {
		
		// file through the requestQueue and look for commandSender == requestRecipient
		for (TeleportRequest request : requestQueue) {
			Player requestSender = request.getSender();
			Player requestRecipient = request.getRecipient();
			
			// send each of them messages saying that the request was denied
			requestSender.sendMessage(ChatColor.RED + requestRecipient.getName() + " denied your teleport request");
			requestRecipient.sendMessage(ChatColor.RED + requestSender.getName() + "'s teleport request was denied");
			
			// and remove that request from the queue
			requestQueue.remove(request);
			
			return;
		}
		
		for (int index = 0; index < requestQueue.size(); index++) {
			if (requestQueue.get(index).getRecipient() == commandSender) {
				
			}
		}
		
		// otherwise, there must not have been a request waiting
		commandSender.sendMessage(ChatColor.RED + "There are no teleport requests waiting for you");
	}

	public void cancelRequest(Player commandSender) {
		// file through the requestQueue and look for commandSender == sender
		for (TeleportRequest request : requestQueue) {
			if (request.getSender() == commandSender) {
				Player requestSender = request.getSender();
				Player requestRecipient = request.getRecipient();
				
				// send each o f them messages saying that the request was cancelled
				requestSender.sendMessage(ChatColor.RED + "Your teleport request to " + requestRecipient.getName() + " has been cancelled");
				requestRecipient.sendMessage(ChatColor.RED + requestSender.getName() + " cancelled the teleport request");
				
				// and remove that request from the queue
				requestQueue.remove(request);
				
				return;
			}
		}
		
		// otherwise, the sender must not have sent a request
		commandSender.sendMessage(ChatColor.RED + "You do not have any pending outgoing requests");
		
	}

}

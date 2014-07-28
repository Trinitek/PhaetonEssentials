package co.phaeton.trinitek.phaetonessentials.teleport;

import org.bukkit.entity.Player;

/**
 * Teleport request containing the request type, its sender, and its recipient
 */
public class TeleportRequest {

	private boolean requestType;
	private Player sender;
	private Player recipient;
	
	/**
	 * Constructor for the TeleportRequest object
	 * @param requestType true if 'tpa', false if 'tphere'
	 */
	public TeleportRequest(boolean requestType, Player sender, Player recipient) {
		this.requestType = requestType;
		this.sender = sender;
		this.recipient = recipient;
	}
	
	/**
	 * Get the type of request
	 * @return true if 'tpa', false if 'tphere'
	 */
	public boolean getRequestType() {
		return this.requestType;
	}
	
	/**
	 * Get the sender of the request
	 * @return sender
	 */
	public Player getSender() {
		return this.sender;
	}
	
	/**
	 * Get the recipient of the request
	 * @return recipient
	 */
	public Player getRecipient() {
		return this.recipient;
	}
	
}

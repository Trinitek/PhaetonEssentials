package co.phaeton.trinitek.phaetonessentials.teleport;

import java.util.Calendar;

import org.bukkit.Location;

/**
 * Teleport history containing the Player's history of locations he has teleported to and from
 */
public class TeleportHistory {
	
	private Location location;
	private TeleportDirection direction;
	private Calendar timestamp;
	
	/**
	 * Constructor for the TeleportHistory object
	 * @param location
	 * @param direction - OUTGOING if the location was the point of departure, INCOMING if the location was the destination
	 */
	public TeleportHistory(Location newLocation, TeleportDirection newDirection) {
		this.location = newLocation;
		this.direction = newDirection;
		this.timestamp = Calendar.getInstance();
	}
	
	/**
	 * Get the Location associated with the teleportation
	 */
	public Location getLocation() {
		return this.location;
	}
	
	/**
	 * @return OUTGOING if the location was the point of departure, INCOMING if the location was the destination
	 */
	public TeleportDirection getDirection() {
		return this.direction;
	}
	
	/**
	 * Get the timestamp of when this object was created
	 * @return timestamp Calendar
	 */
	public Calendar getTimestamp() {
		return this.timestamp;
	}
	
}

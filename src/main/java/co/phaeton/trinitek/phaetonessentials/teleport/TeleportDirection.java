package co.phaeton.trinitek.phaetonessentials.teleport;

/**
 * Specifies the direction of a teleport in respect to a location
 * 
 * For example, if a player teleports to pointA and then leaves to go to pointB, in a teleportation
 * log file, first, an INCOMING would be marked for pointA as he arrived, and then an OUTGOING as
 * he left for pointB.
 */
public enum TeleportDirection {

	OUTGOING, INCOMING
	
}
package co.phaeton.trinitek.phaetonessentials.teleport;

import java.util.*;

import co.phaeton.trinitek.phaetonessentials.generic.DateMath;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TeleportHistoryHandler {

	private static Map<Player, ArrayList<TeleportHistory>> playerTeleportList = new HashMap<>();

	/**
	 * Assign a new teleport history listing for the given Player
	 * @param player Player that the list is to be assigned to
     * @return false if player's list already exists
	 */
	public static boolean create(Player player) {
        // If that player already exists, do nothing
        return create(player, new ArrayList<TeleportHistory>());
	}
	
	/**
	 * Assign a given teleport history listing for the given Player
	 * @param player Player that the list is to be assigned to
	 * @param historyList ArrayList of TeleportHistory objects to assign to the Player
     * @return false if player's list already exists
	 */
	public static boolean create(Player player, ArrayList<TeleportHistory> historyList) {
        // If that player already exists, do nothing
        if (!(playerTeleportList.containsKey(player))) {
            playerTeleportList.put(player, historyList);
            Bukkit.getServer().getLogger().info("[PhaetonEssentials] " + player.getPlayerListName() + " added to TeleportHistory hashmap");
            return true;
        } else return false;
	}
	
	/**
	 * Add a new TeleportHistory entry to the given Player's history list
	 * @param player Player that holds the list to be modified
	 * @param newEntry New TeleportHistory entry to be added
	 */
	public static void add(Player player, TeleportHistory newEntry) {
		playerTeleportList.get(player).add(newEntry);
        Bukkit.getServer().getLogger().info("[PhaetonEssentials] New TeleportHistory entry added for " + player.getPlayerListName());
	}
	
	/**
	 * Get the list of the specified Player's TeleportHistory entries
	 * @param player Player that holds the list to be retrieved
	 * @return ArrayList of TeleportHistory elements
	 */
	public static ArrayList<TeleportHistory> getEntry(Player player) {
		return playerTeleportList.get(player);
	}
	
	/**
	 * Remove the teleport history listing for the given Player
	 * @param player - Player that holds the list to be modified
	 */
	public static void remove(Player player) {
        Bukkit.getServer().getLogger().info("[PhaetonEssentials] " + player.getPlayerListName() + " removed from TeleportHistory hashmap");
		playerTeleportList.remove(player);
	}

    /**
     * Get an array containing string representations of all of the TeleportHistory elements associated with the
     * provided player
     * @param player Player that holds the list to be retrieved
     * @return array of Strings
     */
    public static String[] historyListToString(Player player) {
        long now = Calendar.getInstance().getTimeInMillis();
        ArrayList<String> historyList = new ArrayList<>();
        String entry;
        int index = playerTeleportList.get(player).size() - 1;

        for (TeleportHistory teleportHistory : playerTeleportList.get(player)) {
            entry = index + " : "; index--;
            entry = entry.concat(DateMath.differenceToString(now, teleportHistory.getTimestamp().getTimeInMillis()) + " ago - ");
            if (teleportHistory.getDirection() == TeleportDirection.OUTGOING) entry = entry.concat("teleported away from ");
            else entry = entry.concat("teleported to ");
            entry = entry.concat
                    (teleportHistory.getLocation().getBlockX() + ", " +
                    teleportHistory.getLocation().getBlockY() + ", " +
                    teleportHistory.getLocation().getBlockZ());
            historyList.add(entry);
        }

        // Sort the list from newest to oldest
        Collections.reverse(historyList);
        return historyList.toArray(new String[historyList.size()]);
    }
}

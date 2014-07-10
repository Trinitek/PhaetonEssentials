package co.phaeton.trinitek.phaetonessentials.teleport;

import java.util.ArrayList;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TeleportHistoryHandler {

	private static Map<Player, ArrayList<TeleportHistory>> playerTeleportList;
	
	/**
	 * Assign a new teleport history listing for the given Player
	 * @param player - Player that the list is to be assigned to
	 */
	public static void create(Player player) {
		playerTeleportList.put(player, new ArrayList<TeleportHistory>());
	}
	
	/**
	 * Assign a given teleport history listing for the given Player
	 * @param player - Player that the list is to be assigned to
	 * @param historyList - ArrayList of TeleportHistory objects to assign to the Player
	 */
	public static void create(Player player, ArrayList<TeleportHistory> historyList) {
		playerTeleportList.put(player, historyList);
	}
	
	/**
	 * Add a new TeleportHistory entry to the given Player's history list
	 * @param player - Player that holds the list to be modified
	 * @param newEntry - New TeleportHistory entry to be added
	 */
	public static void add(Player player, TeleportHistory newEntry) {
		playerTeleportList.get(player).add(newEntry);
	}
	
	/**
	 * Get the list of the specified Player's TeleportHistory entries
	 * @param player - Player that holds the list to be retrieved
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
		playerTeleportList.remove(player);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		create(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		remove(event.getPlayer());
	}
}

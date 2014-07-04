/*package co.phaeton.trinitek.phaetonessentials;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

public class CommandListener implements Listener {
	
	@SuppressWarnings("unused")
	private Plugin plugin = null;
	
	/**
	 * constructor that creates the CommandListener and registers this class and the plugin
	 * with the Bukkit event manager
	 * @param plugin
	 * /
	public CommandListener(Plugin plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	/**
	 * the actual method that will be listening for commands.
	 * because the CommandPreprocessEvent is triggered before Bukkit normally parses the command,
	 * this will give us a chance to take control of the command and overwrite any plugin/default
	 * functionality.
	 * @param event
	 * /
	@EventHandler
	public void onPreCommand(PlayerCommandPreprocessEvent event) {
		String command = event.getMessage().toLowerCase(); // text of raw message sent to the server
		Player player = event.getPlayer(); // player object that refers to the player that sent the message
		
		// ensure that the command has spaces before we split it
		if (command.contains(" ")) {
			String[] commandArgs = command.split(" ");
			
			// check for the /tp command
			if (commandArgs[0].equals("/tp")) {
				if (commandArgs[1].equals("to") || commandArgs[1].equals("there")) {
					//TODO
				} else if (commandArgs[1].equals("here")) {
					//TODO
				} else if (commandArgs[1].equals("player")) {
					//TODO
				}
			}
		} // otherwise, stay quiet and let Bukkit handle the rest
	}

}

*/

package co.phaeton.trinitek.phaetonessentials;

import co.phaeton.trinitek.phaetonessentials.generic.AfkHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import co.phaeton.trinitek.phaetonessentials.teleport.TeleportCommandHandler;
import co.phaeton.trinitek.phaetonessentials.teleport.TeleportHandler;
import co.phaeton.trinitek.phaetonessentials.teleport.TeleportHistoryHandler;

/* THE BIG TO-DO LIST!
TODO add /back functionality
 */

public final class Main extends JavaPlugin implements Listener {
	
	private TeleportHandler teleportHandler;
    private AfkHandler afkHandler;

	@Override
	public void onEnable() {
        // Register event listeners
        getServer().getPluginManager().registerEvents(this, this);

		this.teleportHandler = new TeleportHandler();
        this.afkHandler = new AfkHandler(this);

		// Associate a new TeleportHistory list for each online Player
		for (Player player : getServer().getOnlinePlayers()) {
			TeleportHistoryHandler.create(player);
		}
	}
	
	@Override
	public void onDisable() {
		this.teleportHandler = null;
	}

    @EventHandler
    public void onPlayerJoin(PlayerLoginEvent event) {
        TeleportHistoryHandler.create(event.getPlayer());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        TeleportHistoryHandler.remove(event.getPlayer());
    }

	@Override
	public boolean onCommand(CommandSender cmdSender, Command cmd, String rawCmd, String[] args) {
		String command = cmd.getName().toLowerCase();
		
		/*
		 * Check for the teleport request commands.
		 */
        switch (command) {
            case "tpa":
            case "tphere":
            case "tpaccept":
            case "tpdeny":
            case "tplist":
            case "tpcancel":
            case "back":
                return TeleportCommandHandler.TeleportCommand(cmdSender, command, args, this.teleportHandler);
            case "afk":
                return this.afkHandler.toggleAfk(cmdSender);
            default:
                // no valid command entered, display 'Usage:' message
                return false;
        }
	}
	
	/**
	 * Ensure that the sender did not issue the command from the console
	 * @param cmdSender - entity that sent the command
	 * @return true if sent from the console
	 */
	public static boolean denyConsoleSender(CommandSender cmdSender) {
		if (!(cmdSender instanceof Player)) {
			cmdSender.sendMessage(ChatColor.RED + "Only players can run this command");
			return true;
		} else return false;
	}
	
}

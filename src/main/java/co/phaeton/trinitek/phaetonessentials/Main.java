package co.phaeton.trinitek.phaetonessentials;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import co.phaeton.trinitek.phaetonessentials.teleport.TeleportCommandHandler;
import co.phaeton.trinitek.phaetonessentials.teleport.TeleportHandler;
import co.phaeton.trinitek.phaetonessentials.teleport.TeleportHistoryHandler;

public final class Main extends JavaPlugin {
	
	private TeleportHandler teleportHandler;

	@Override
	public void onEnable() {
		this.teleportHandler = new TeleportHandler();
		
		// Associate a new TeleportHistory list for each online Player
		for (Player player : getServer().getOnlinePlayers()) {
			TeleportHistoryHandler.create(player);
		}
	}
	
	@Override
	public void onDisable() {
		this.teleportHandler = null;
	}

	@Override
	public boolean onCommand(CommandSender cmdSender, Command cmd, String rawCmd, String[] args) {
		String command = cmd.getName().toLowerCase();
		
		/*
		 * Check for the teleport request commands.
		 */
		if ((command.equals("tpa")) ||
			(command.equals("tphere")) ||
			(command.equals("tpaccept")) ||
			(command.equals("tpdeny")) ||
			(command.equals("tplist")) ||
			(command.equals("tpcancel")) ||
			(command.equals("back"))) {
			
			return TeleportCommandHandler.TeleportCommand(cmdSender, command, args, this.teleportHandler);

		}
		
		// no valid command entered, display 'Usage:' message
		return false;
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

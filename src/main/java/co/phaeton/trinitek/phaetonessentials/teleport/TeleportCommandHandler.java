package co.phaeton.trinitek.phaetonessentials.teleport;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.phaeton.trinitek.phaetonessentials.Main;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class TeleportCommandHandler {
	
	public static boolean TeleportCommand(CommandSender cmdSender, String command, String[] args, TeleportHandler teleportHandler) {
		
		Player sender = null;
		boolean overridePrompt = false;
		
		// Check for 'back'
		if (command.equals("back")) return backCommand(cmdSender, args);
		
		// Check for 'tplist'
		if (command.equals("tplist")) {
			teleportHandler.displayRequestList(cmdSender);
			return true;
		}
		
		/*
		 * Make sure that the sender didn't send the command from the console
		 */
		if (Main.denyConsoleSender(cmdSender)) {
			return true;
		} else {
			sender = (Player) cmdSender;
		}
		
		/*
		 * The 'tpaccept' and 'tpdeny' commands don't need the special treatment ahead.
		 */
		if (command.equals("tpaccept")) {
			teleportHandler.acceptRequest(sender);
			return true;
		} else if (command.equals("tpdeny")) {
			teleportHandler.denyRequest(sender);
			return true;
		} else if (command.equals("tpcancel")) {
			teleportHandler.cancelRequest(sender);
			return true;
		}
		
		/*
		 * Check for the right number of arguments (either 1 or 2)
		 */
		if ((args.length < 1) || (args.length > 2)) {
			sender.sendMessage(ChatColor.RED + "Wrong number of arguments");
			return false;
		}
		
		/*
		 * Look up the player by the given username			
		 */
		Player recipient = Bukkit.getServer().getPlayer(args[0]);
		
		/*
		 * If the player isn't in the list, then abort.
		 */
		if (recipient == null) {
			sender.sendMessage(ChatColor.RED + args[0] + " is not online!");
			return false;
		}
		
		/*
		 * Verify that the sender isn't the same player as the specified recipient
		 */
		if (recipient == sender) {
			sender.sendMessage(ChatColor.RED + "You can't teleport to yourself");
			return true;
		}
		
		/*
		 * Verify that the sender has permission to override the confirmation prompt if the 'force' flag is specified
		 */
		if (args.length > 1) {
			if (args[1].equals("force")) {
				if (PermissionsEx.getUser(sender).has("phaetonessentials.tp.confirm.override")) {
					overridePrompt = true;
				} else {
					sender.sendMessage(ChatColor.RED + "You do not have permission to override the confirmation prompt");
					return true;
				}
			}
		}
		
		
		if (command.equals("tpa")) {
			teleportHandler.createRequest(true, sender, recipient, overridePrompt);
		} else if (command.equals("tphere")) {
			teleportHandler.createRequest(false, sender, recipient, overridePrompt);
		}
		
		// the command completed successfully
		return true;
		
	}
	
	private static boolean backCommand(CommandSender cmdSender, String[] args) {
		Player sender;
		
		// The following commands require at least one argument
		if (args.length > 0) {
			// Check for 'list'
			if (args[0].equalsIgnoreCase("list")) {
				// If a player is specified, display the TeleportHistory list for that player
				if (args.length > 1) {
					// Either a console user or a Player with permissions can execute this command
					if (!(Main.denyConsoleSender(cmdSender))) {
						sender = (Player) cmdSender;
						if (PermissionsEx.getUser(sender).has("phaetonessentials.tp.back.listplayer")) {
							// TODO print specified player's TeleportHistory
						}
					} else {
						// TODO print specified player's TeleportHistory
					}
				} else {
					// TODO print player's own TeleportHistory
					// Only players can execute this command
					if (Main.denyConsoleSender(cmdSender)) {
						return true;
					} else {
						sender = (Player) cmdSender;
					}
				}
			}
		}
		
		return false;
	}
	
}

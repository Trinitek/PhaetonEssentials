package co.phaeton.trinitek.phaetonessentials.teleport;

import co.phaeton.trinitek.phaetonessentials.chat.PageBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import co.phaeton.trinitek.phaetonessentials.Main;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class TeleportCommandHandler {
	
	public static boolean TeleportCommand(CommandSender cmdSender, String command, String[] args, TeleportHandler teleportHandler) {
		
		Player sender;
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
        switch (command) {
            case "tpaccept":
                teleportHandler.acceptRequest(sender);
                return true;
            case "tpdeny":
                teleportHandler.denyRequest(sender);
                return true;
            case "tpcancel":
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

    /**
     * Process the provided arguments for the /back command.
     *
     * Usage: /back [numberOfLocations] or [<list> [playerName] [pageNumber]]
     *
     * numberOfLocations is the number of steps to backtrack, where 1 would be the previous location, 2 would be the
     * location previous to that, and so forth.
     *
     * @param commandSender - Player or console user that sent the command
     * @param args - array of argument strings
     * @return false if the syntax is incorrect
     */
	private static boolean backCommand(CommandSender commandSender, String[] args) {

        // The following commands require at least one argument
		if (args.length >= 1) {
			// Check for 'list'
			if (args[0].equalsIgnoreCase("list")) {

                // If there are no other arguments specified, display the teleport history for the command sender
                if (args.length <= 1) {
                    // Only a Player can access his own teleport history
                    if (Main.denyConsoleSender(commandSender)) return true;

                    // Display to the command sender the first page of his own teleport history list
                    showTeleportHistoryList(commandSender, (Player)commandSender, 1, false);
                    return true;
                }

                // The second argument can be either a player's name or a page number
                // Requires at least two arguments
                else if (args.length >= 2) {
                    try {
                        // Try to parse the argument as an integer
                        int pageNumber = Integer.parseInt(args[1]);

                        // If it works, then the command sender wants to look at a page of his own teleport history list
                        showTeleportHistoryList(commandSender, (Player)commandSender, pageNumber, false);
                        return true;
                    } catch (NumberFormatException e) {
                        // If the argument could not be parsed as an integer, then try to match it to a player's name
                        Player player = Bukkit.getServer().getPlayer(args[1]);
                        if (player == null) {
                            // If the player does not exist, then say that the player is not online
                            commandSender.sendMessage(ChatColor.RED + args[1] + " is not online!");
                            return true;
                        } else {
                            // The third argument, if it exists, can only be a page number
                            if (args.length >= 3) {
                                try {
                                    // Try to parse the argument as an integer
                                    int pageNumber = Integer.parseInt(args[2]);

                                    // If it works, then send the command sender that page of the specified player's
                                    // teleport history list
                                    showTeleportHistoryList(commandSender, player, pageNumber, true);
                                    return true;
                                } catch (NumberFormatException f) {
                                    return false;
                                }
                            }
                            // If a page number is not specified, then display the first page of the specified player's
                            // teleport history list
                            showTeleportHistoryList(commandSender, player, 1, true);
                            return true;
                        }
                    }
                }

			}
            // Check for an integer, which will be the number of teleportation steps to backtrack
            else try {
                int backtrack = Integer.parseInt(args[0]);
                // TODO teleport player to that location in the teleport history list, where it says "teleported from"
                return true;
            } catch (NumberFormatException e) {
                // If parsing the argument as an integer fails, then the command syntax was not correct
                return false;
            }
		} else {
            // If no arguments are specified, then backtrack by only one step
            // TODO teleport player to the first location in the teleport history list, where it says "teleported from"
        }
		
		return false;
	}

    /**
     * Display a formatted page of the teleport history for the specified player
     * @param commandSender command sender to whom the list should be sent
     * @param player player who's teleport history list should be shown
     * @param pageNumber page number of the results to be displayed
     * @param customPlayer true if a player was specified in the /back command, false if the command sender is
     *                     requesting his own history list
     */
    private static void showTeleportHistoryList(CommandSender commandSender, Player player, int pageNumber, boolean customPlayer) {
        PageBuilder page = new PageBuilder();
        page.setHeader(new String[]{ChatColor.GOLD + "----- Teleport History Results -----"});
        page.setBody(TeleportHistoryHandler.historyListToString(player));
        if (customPlayer)
            page.setFooter(new String[]{ChatColor.GRAY + "Page " + pageNumber + "/" + page.getNumberOfPages() + ", use \"/back list <playerName> <pageNumber>\" for more"});
        else
            page.setFooter(new String[]{ChatColor.GRAY + "Page " + pageNumber + "/" + page.getNumberOfPages() + ", use \"/back list <pageNumber>\" for more"});
        page.showPage(commandSender, pageNumber);
    }
	
}

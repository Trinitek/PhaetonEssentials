package co.phaeton.trinitek.phaetonessentials.generic;

import co.phaeton.trinitek.phaetonessentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

public class AfkHandler implements Listener {

    private ArrayList<Player> afkList;

    /**
     * Constructor for the AfkHandler object
     */
    public AfkHandler(Main plugin) {
        // Register the event listener
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        // Initialize the list that will contain Players that are AFK
        this.afkList = new ArrayList<>();
    }

    /**
     * Sets the Player's AFK status to false whenever he interacts with an object in the game
     * @param event - PlayerInteractEvent
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        this.setAfk(event.getPlayer(), false);
    }

    /**
     * Sets the Player's AFK status to false whenever he moves to another location
     * @param event - PlayerMoveEvent
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        // If the Player's block location changed, then set AFK flag to false
        if (!event.getFrom().getBlock().equals(event.getTo().getBlock()))
            this.setAfk(event.getPlayer(), false);
    }

    /**
     * Process any additional arguments for this command
     * @param commandSender - CommandSender that issued the command
     * @param args - argument list
     * @return true if successful
     */
    public boolean processCommand(CommandSender commandSender, String[] args) {
        if (args.length > 0)
            if (args[0].equalsIgnoreCase("list")) {
                commandSender.sendMessage(ChatColor.GRAY + "There are " + afkList.size() + " players AFK:");

                String playerList = ChatColor.GRAY + "";
                for (int index = 0; index < afkList.size(); index++) {
                    playerList = playerList.concat(afkList.get(index).getName());

                    if (index + 1 < afkList.size())
                        playerList = playerList.concat(", ");
                }

                commandSender.sendMessage(playerList);

                return true;
            }

        return toggleAfk(commandSender);
    }
    /**
     * Toggle the AFK status for the given Player.
     * If the player is AFK, then set the flag to false. Otherwise, set the flag to true.
     * @param commandSender - CommandSender object to apply AFK status to. Must be an instance of Player.
     * @return - true if successful
     */
    public boolean toggleAfk(CommandSender commandSender) {
        // This command can only be run by a player
        if (Main.denyConsoleSender(commandSender)) return true;
        Player player = (Player) commandSender;

        // Set AFK to true; if that fails, set to false
        if (!setAfk(player, true)) setAfk(player, false);

        return true;
    }

    /**
     * Set the AFK status for the given Player
     * @param player - Player to set flag for
     * @param flag - true if AFK, false if not
     * @return true if successful
     */
    public boolean setAfk(Player player, boolean flag) {

        if (flag) {
            if (!afkList.contains(player)) {
                afkList.add(player);
                Bukkit.getServer().broadcastMessage(ChatColor.GRAY + player.getDisplayName() + " is now AFK");
                return true;
            } else return false;
        } else {
            if (afkList.contains(player)) {
                afkList.remove(player);
                Bukkit.getServer().broadcastMessage(ChatColor.GRAY + player.getDisplayName() + " is no longer AFK");
                return true;
            } else return false;
        }
    }

}

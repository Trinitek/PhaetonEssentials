package co.phaeton.trinitek.phaetonessentials.generic;

import co.phaeton.trinitek.phaetonessentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InventoryHandler {

    /**
     * Opens the Enderchest interface for the provided Player
     *
     * This method is provided to allow a command processor method to pass arguments from the given command string
     * directly without having to parse and sanitize each one. This method performs that transparently.
     *
     * Usage: /enderchest [player]
     *
     * @param viewer the Player for whom the interface will be opened
     * @param args argument list
     * @return false if the provided arguments do not adhere to the command format
     */
    public static boolean openEnderChest(CommandSender viewer, String[] args) {
        // This command can not be issued by the console
        if (Main.denyConsoleSender(viewer)) return true;

        // If the inventoryHolder's name is empty, display the viewer's own Enderchest interface
        String inventoryHolderName = null;
        Player inventoryHolder;
        if (args.length >= 1) inventoryHolderName = args[0];
        if (inventoryHolderName == null) {
            inventoryHolder = (Player) viewer;
        } else {
            inventoryHolder = Bukkit.getPlayer(inventoryHolderName);
            if (inventoryHolder == null) {
                viewer.sendMessage(ChatColor.RED + inventoryHolderName + " is not online!");
                return true;
            }
        }

        // Everything checks out, perform the action
        openEnderChest((Player) viewer, inventoryHolder);
        return true;
    }

    /**
     * Opens an Enderchest interface for the provided Player
     * @param viewer the Player for whom the interface will be opened
     * @param inventoryHolder the Player that holds the Enderchest interface
     */
    public static void openEnderChest(Player viewer, Player inventoryHolder) {
        viewer.openInventory(inventoryHolder.getEnderChest());
    }

    /**
     * Opens the Enderchest interface for the provided Player
     *
     * This method is provided to allow a command processor method to pass arguments from the given command string
     * directly without having to parse and sanitize each one. This method performs that transparently.
     *
     * Usage: /inventory <player>
     *
     * @param viewer the Player for whom the interface will be opened
     * @param args argument list
     * @return false if the provided arguments do not adhere to the command format
     */
    public static boolean openPlayerInventory(CommandSender viewer, String[] args) {
        // This command can not be issued by the console
        if (Main.denyConsoleSender(viewer)) return true;

        // The inventory holder's name must be provided
        String inventoryHolderName = null;
        Player inventoryHolder;
        if (args.length >= 1) inventoryHolderName = args[0];
        if (inventoryHolderName == null) {
            viewer.sendMessage(ChatColor.RED + "Wrong number of arguments");
            return false;
        } else {
            inventoryHolder = Bukkit.getPlayer(inventoryHolderName);
            if (inventoryHolder == null) {
                viewer.sendMessage(ChatColor.RED + inventoryHolderName + " is not online!");
                return true;
            }
        }

        // Everything checks out, perform the action
        openPlayerInventory((Player) viewer, inventoryHolder);
        return true;
    }

    /**
     * Opens a Player inventory interface for the provided Player
     * @param viewer the Player for whom the interface will be opened
     * @param inventoryHolder the Player that holds the Player inventory interface
     */
    public static void openPlayerInventory(Player viewer, Player inventoryHolder) {
        viewer.openInventory(inventoryHolder.getInventory());
    }

    /**
     * Opens the Enderchest interface for the provided Player
     *
     * This method is provided to allow a command processor method to pass arguments from the given command string
     * directly without having to parse and sanitize each one. This method performs that transparently.
     *
     * Usage: /workbench
     *
     * @param viewer the Player for whom the interface will be opened
     * @return false if the provided arguments do not adhere to the command format
     */
    public static boolean openWorkbench(CommandSender viewer) {
        // This command can not be issued by the console
        if (Main.denyConsoleSender(viewer)) return true;

        // Everything checks out, perform the action
        openWorkbench((Player) viewer);
        return true;
    }

    /**
     * Opens a Workbench interface for the provided player
     * @param viewer the Player for whom the interface will be opened
     */
    public static void openWorkbench(Player viewer) {
        viewer.openWorkbench(null, true);
    }

}

package co.phaeton.trinitek.phaetonessentials.chat;

import org.bukkit.command.CommandSender;

public class PageBuilderTester {

    private PageBuilder pageBuilder;

    public PageBuilderTester(CommandSender commandSender) {
        this.pageBuilder = new PageBuilder();
        String[] header = {"Header #1", "Header #2"};
        String[] body = {"Body #1", "Body #2", "Body #3"};
        String[] footer = {"Footer #1"};
        this.pageBuilder.setHeader(header);
        this.pageBuilder.setBody(body);
        this.pageBuilder.setFooter(footer);
        this.pageBuilder.showPage(commandSender, 1);
    }

}

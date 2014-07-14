package co.phaeton.trinitek.phaetonessentials.chat;

import org.bukkit.command.CommandSender;

public class PageBuilderTester {

    private PageBuilder pageBuilder;

    public PageBuilderTester(CommandSender commandSender) {
        this.pageBuilder = new PageBuilder();

        commandSender.sendMessage(new String[]{"", "TEST #1"});
        String[] header = {"Header #1", "Header #2"};
        String[] body = {"Body #1", "Body #2", "Body #3"};
        String[] footer = {"Footer #1"};
        this.pageBuilder.setHeader(header);
        this.pageBuilder.setBody(body);
        this.pageBuilder.setFooter(footer);
        this.pageBuilder.showPage(commandSender, 1);

        commandSender.sendMessage(new String[]{"", "TEST #2"});
        body = new String[]{"Body #1", "Body #2", "Body #3", "Body #4", "Body #5", "Body #6", "Body #7"};
        this.pageBuilder.setBody(body);
        this.pageBuilder.showPage(commandSender, 1);

        commandSender.sendMessage(new String[]{"", "TEST #3"});
        body = new String[]{"Body #1", "Body #2", "Body #3", "Body #4", "Body #5", "Body #6", "Body #7", "Body #8",
                "Body #9", "Body #10", "Body #11"};
        this.pageBuilder.setBody(body);
        this.pageBuilder.showPage(commandSender, 1);
        this.pageBuilder.showPage(commandSender, 2);

        commandSender.sendMessage(new String[]{"", "TEST #4"});
        header = new String[]{"Header #1", "Header #2", "Header #3", "Header #4", "Header #5", "Header #6", "Header #7", "Header #8",
                "Header #9", "Header #10", "Header #11"};
        this.pageBuilder.setHeader(header);
        this.pageBuilder.showPage(commandSender, 1);
        this.pageBuilder.showPage(commandSender, 2);
    }

}

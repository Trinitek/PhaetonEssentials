package co.phaeton.trinitek.phaetonessentials.chat;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class PageBuilder {

    private String[] header;
    private String[] body;
    private String[] footer;
    private int sizeOfPage;
    private int numberOfPages;
    private int numberOfBodyLinesPerPage;

    private static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * Constructor to create a new PageBuilder
     * Does not accept arguments
     */
    public PageBuilder() {
        this.sizeOfPage = DEFAULT_PAGE_SIZE;
        this.numberOfPages = 1;
        this.numberOfBodyLinesPerPage = 0;
    }

    /**
     * Set the header to the given ArrayList of Strings
     * @param stringList - array of Strings that constitute the header
     */
    public void setHeader(String[] stringList) {
        this.header = stringList;
        adjustPageSize();
    }

    /**
     * Set the body to the given ArrayList of Strings
     * @param stringList - array of Strings that constitute the header
     */
    public void setBody(String[] stringList) {
        this.body = stringList;
        adjustPageSize();
    }

    /**
     * Set the footer to the given ArrayList of Strings
     * @param stringList - array of Strings that constitute the footer
     */
    public void setFooter(String[] stringList) {
        this.footer = stringList;
        adjustPageSize();
    }

    /**
     * Adjust all the object's variables to account for changes in the size of the message's string arrays
     */
    private void adjustPageSize() {
        int headerLength = 0, bodyLength = 0, footerLength = 0;

        if (this.header != null) headerLength = this.header.length;
        if (this.body != null) bodyLength = this.body.length;
        if (this.footer != null) footerLength = this.footer.length;

        // If the number of header and footer strings alone hits the page size limit, size accordingly
        if (headerLength + footerLength >= DEFAULT_PAGE_SIZE) {
            this.sizeOfPage = headerLength + footerLength;
            if (bodyLength > 0) this.sizeOfPage++;
        }

        // Otherwise if the header and footer are within the page limit, calculate the size left for the body
        else {
            this.sizeOfPage = DEFAULT_PAGE_SIZE;
        }

        // Calculate the number of body lines that can fit on a page
        this.numberOfBodyLinesPerPage = this.sizeOfPage - headerLength - footerLength;

        // Calculate the number of pages
        this.numberOfPages = bodyLength / this.numberOfBodyLinesPerPage;
        if (bodyLength % this.numberOfBodyLinesPerPage > 0) this.numberOfPages++;
    }

    /**
     * Builds a page of information using the object's header, body, and footer in a form that can be displayed to a
     * chat console.
     *
     * Tries to fit the given header and footer strings into the default page size, and allocates the remaining lines
     * to the body strings. If the header and footer strings combined matches or exceeds the default page size, the
     * page size is adjusted accordingly to fit both, in addition to one body string.
     *
     * @param pageNumber - page number to generate
     * @return String array of the page's contents
     */
    public String[] buildPage(int pageNumber) {

        // If the page number is out of range, then set it to either the first or last page
        if (0 > pageNumber) pageNumber = 0;
        else if (pageNumber > this.numberOfPages) pageNumber = this.numberOfPages;

        // Calculate the number of body lines to show to find out how large the page should be
        int bodyIndex = pageNumber * this.numberOfBodyLinesPerPage - this.numberOfBodyLinesPerPage;

        int bodyLinesToShow;
        if (this.body.length - bodyIndex + 1 < this.numberOfBodyLinesPerPage)
            bodyLinesToShow = this.body.length - bodyIndex;
        else
            bodyLinesToShow = this.numberOfBodyLinesPerPage;

        // Initialize the destination string array
        String[] returnString;
        if (bodyLinesToShow < DEFAULT_PAGE_SIZE - this.header.length - this.footer.length)
            returnString = new String[bodyLinesToShow + this.header.length + this.footer.length];
        else
            returnString = new String[DEFAULT_PAGE_SIZE];

        int currentIndex = 0;

        Bukkit.getLogger().info("pageNumber=" + pageNumber);
        Bukkit.getLogger().info("bodyIndex=" + bodyIndex);
        Bukkit.getLogger().info("bodyLinesToShow=" + bodyLinesToShow);
        Bukkit.getLogger().info("this.numberOfPages=" + this.numberOfPages);
        Bukkit.getLogger().info("this.numberOfBodyLinesPerPage=" + this.numberOfBodyLinesPerPage);
        Bukkit.getLogger().info("this.body.length=" + this.body.length);
        Bukkit.getLogger().info("returnString.length=" + returnString.length);

        // Append the header
        System.arraycopy(this.header, 0, returnString, currentIndex, this.header.length);
        currentIndex += this.header.length;

        // Append the body
        System.arraycopy(this.body, bodyIndex, returnString, currentIndex, bodyLinesToShow);
        currentIndex += bodyLinesToShow;

        // Append the footer
        System.arraycopy(this.footer, 0, returnString, currentIndex, this.footer.length);

        return returnString;
    }

    /**
     * Builds and sends a specified page to the given CommandSender
     * @param commandSender
     * @param pageNumber
     */
    public void showPage(CommandSender commandSender, int pageNumber) {
        commandSender.sendMessage(buildPage(pageNumber));
    }

    /**
     * Get the number of pages in the PageBuilder object
     * @return number of pages
     */
    public int getNumberOfPages() {
        return this.numberOfPages;
    }

}

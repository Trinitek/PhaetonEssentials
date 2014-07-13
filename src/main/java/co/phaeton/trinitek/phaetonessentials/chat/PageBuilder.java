package co.phaeton.trinitek.phaetonessentials.chat;

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
        int headerLength = this.header.length;
        int bodyLength = this.body.length;
        int footerLength = this.footer.length;

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
     * @param pageNumber - page number to generate
     * @return String array of the page's contents
     */
    private String[] buildPage(int pageNumber) {
        String[] returnString = new String[this.sizeOfPage];
        int currentIndex = 0;

        // If the page number is out of range, then set it to either the first or last page
        if (0 > pageNumber) pageNumber = 0;
        else if (pageNumber > this.numberOfPages) pageNumber = this.numberOfPages;

        // Append the header
        System.arraycopy(this.header, 0, returnString, currentIndex, this.header.length);
        currentIndex += this.header.length;

        // Append the body
        int bodyIndex = pageNumber * this.numberOfBodyLinesPerPage - this.numberOfBodyLinesPerPage;
        int bodyLinesToShow;

        // If the body array has less than numberOfBodyLinesPerPage left, then only append the remaining strings
        if ((this.body.length - bodyIndex + 1 < this.numberOfBodyLinesPerPage))
            bodyLinesToShow = this.body.size() - bodyIndex + 1;
        else
            bodyLinesToShow = this.numberOfBodyLinesPerPage;
        System.arraycopy(this.body, bodyIndex, returnString, currentIndex, bodyLinesToShow);

        // Append the footer
        System.arraycopy(this.footer, 0, returnString, currentIndex, this.footer.length);

        return returnString;
    }

    /**
     * Builds and sends a specified page to the given CommandSender
     * @param commandSender - person to whom the message shall be sent
     * @param pageNumber - page number to build and send
     */
    public void showPage(CommandSender commandSender, int pageNumber) {
        commandSender.sendMessage(buildPage(pageNumber));
    }

}

package co.phaeton.trinitek.phaetonessentials.chat;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;

public class PageBuilder {

    private ArrayList<String> header;
    private ArrayList<String> body;
    private ArrayList<String> footer;
    private int sizeOfPage;
    private int numberOfPages;
    private int numberOfBodyLinesPerPage;

    private static final int DEFAULT_PAGE_SIZE = 10;

    public PageBuilder() {
        this.sizeOfPage = DEFAULT_PAGE_SIZE;
        this.numberOfPages = 1;
        this.numberOfBodyLinesPerPage = 0;
    }

    public void setHeader(ArrayList<String> stringList) {
        if (stringList == null) stringList = new ArrayList<>();
        this.header = stringList;
        adjustPageSize();
    }

    public void setBody(ArrayList<String> stringList) {
        if (stringList == null) stringList = new ArrayList<>();
        this.body = stringList;
        adjustPageSize();
    }

    public void setFooter(ArrayList<String> stringList) {
        if (stringList == null) stringList = new ArrayList<>();
        this.footer = stringList;
        adjustPageSize();
    }

    private void adjustPageSize() {
        int headerLength = this.header.size();
        int bodyLength = this.body.size();
        int footerLength = this.body.size();

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
        System.arraycopy(this.header.toArray(), 0, returnString, currentIndex, this.header.size());
        currentIndex += this.header.size();

        // Append the body
        int bodyIndex = pageNumber * this.numberOfBodyLinesPerPage - this.numberOfBodyLinesPerPage;
        int bodyLinesToShow;

        // If the body array has less than numberOfBodyLinesPerPage left, then only append the remaining strings
        if ((this.body.size() - bodyIndex + 1 < this.numberOfBodyLinesPerPage))
            bodyLinesToShow = this.body.size() - bodyIndex + 1;
        else
            bodyLinesToShow = this.numberOfBodyLinesPerPage;
        System.arraycopy(this.body.toArray(), bodyIndex, returnString, currentIndex, bodyLinesToShow);

        // Append the footer
        System.arraycopy(this.footer.toArray(), 0, returnString, currentIndex, this.footer.size());

        return returnString;
    }

    public void showPage(CommandSender commandSender, int pageNumber) {
        commandSender.sendMessage(buildPage(pageNumber));
    }

}

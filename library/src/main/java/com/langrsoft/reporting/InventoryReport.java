package com.langrsoft.reporting;

import com.langrsoft.external.MaterialType;
import com.langrsoft.domain.Catalog;
import com.langrsoft.domain.Holding;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class InventoryReport {
    private static final String NEWLINE = System.getProperty("line.separator");
    private static final int SPACING = 2;
    private static final int TITLE_LENGTH = 20;
    private static final int BRANCH_LENGTH = 20;
    private static final int AUTHOR_LENGTH = 30;
    private static final int YEAR_LENGTH = 6;
    private static final int ISBN_LENGTH = 10;
    private static final String OUTPUT_FILENAME = "./InventoryReport.txt";
    private final Catalog catalog;
    private final LibraryOfCongress congress;
    private static final int RECORD_LIMIT = 100;

    public InventoryReport(Catalog catalog) {
        this.catalog = catalog;
        this.congress = new LibraryOfCongress();
    }

    public void allBooks() throws IOException {
        List<ReportRecord> records = new ArrayList<>();
        for (Holding holding : catalog) {
            if (holding.getMaterial().getFormat() == MaterialType.BOOK) {
                records.add(new ReportRecord(holding));
            }
        }

        Collections.sort(records);

        var buffer = new StringBuilder();
        buffer.append(ReportUtil.transform("Title",
                TITLE_LENGTH + SPACING, TITLE_LENGTH + SPACING - "Title".length(), ReportUtil.StringOp.PAD));
        buffer.append(ReportUtil.transform("Branch",
                BRANCH_LENGTH + SPACING, BRANCH_LENGTH + SPACING - "Branch".length(), ReportUtil.StringOp.PAD));
        buffer.append(ReportUtil.transform("Author",
                AUTHOR_LENGTH + SPACING, AUTHOR_LENGTH + SPACING - "Author".length(), ReportUtil.StringOp.PAD));
        buffer.append(ReportUtil.transform("Year", YEAR_LENGTH,
                YEAR_LENGTH - "Year".length(), ReportUtil.StringOp.PAD));
        buffer.append(ReportUtil.transform("ISBN", ISBN_LENGTH,
                ISBN_LENGTH - "ISBN".length(), ReportUtil.StringOp.PAD));
        buffer.append(NEWLINE);

        buffer.append(ReportUtil.transform("", TITLE_LENGTH, SPACING, ReportUtil.StringOp.UNDER));
        buffer.append(ReportUtil.transform("", BRANCH_LENGTH, SPACING, ReportUtil.StringOp.UNDER));
        buffer.append(ReportUtil.transform("", AUTHOR_LENGTH, SPACING, ReportUtil.StringOp.UNDER));
        buffer.append(ReportUtil.transform("", YEAR_LENGTH, SPACING, ReportUtil.StringOp.UNDER));
        buffer.append(NEWLINE);

        int i = 0;
        for (var reportRecord : records) {
            if (i == RECORD_LIMIT) {
                buffer.append("... (only 1st " + RECORD_LIMIT + " records shown) ...");
                break;
            }
            buffer.append(ReportUtil.transform(reportRecord.title, TITLE_LENGTH,
                    TITLE_LENGTH - reportRecord.title.length(), ReportUtil.StringOp.PAD));
            buffer.append(ReportUtil.transform("", SPACING, SPACING - "".length(), ReportUtil.StringOp.PAD));
            buffer.append(ReportUtil.transform(reportRecord.branch, BRANCH_LENGTH,
                    BRANCH_LENGTH - reportRecord.branch.length(), ReportUtil.StringOp.PAD));
            buffer.append(ReportUtil.transform("", SPACING, SPACING - "".length(), ReportUtil.StringOp.PAD));
            buffer.append(ReportUtil.transform(reportRecord.author, AUTHOR_LENGTH,
                    AUTHOR_LENGTH - reportRecord.author.length(), ReportUtil.StringOp.PAD));
            buffer.append(ReportUtil.transform("", SPACING, SPACING - "".length(), ReportUtil.StringOp.PAD));
            buffer.append(ReportUtil.transform(reportRecord.year, YEAR_LENGTH,
                    YEAR_LENGTH - reportRecord.year.length(), ReportUtil.StringOp.PAD));
            buffer.append(ReportUtil.transform("", SPACING, SPACING - "".length(), ReportUtil.StringOp.PAD));
            buffer.append(ReportUtil.transform(reportRecord.isbn, ISBN_LENGTH,
                    ISBN_LENGTH - reportRecord.isbn.length(), ReportUtil.StringOp.PAD));
            buffer.append(NEWLINE);
            i++;
        }

        String result = buffer.toString();

        try (var writer = new BufferedWriter(new FileWriter(OUTPUT_FILENAME))) {
            writer.write("Inventory Report");
            writer.write(NEWLINE);
            writer.write(result, 0, result.length());
        }
    }

    class ReportRecord implements Comparable<ReportRecord> {
        String title;
        String branch;
        String author;
        String year;
        String isbn;

        public ReportRecord(Holding holding) {
            this.title = holding.getMaterial().getTitle();
            this.branch = holding.getBranch().getName();
            this.author = holding.getMaterial().getAuthor();
            this.year = holding.getMaterial().getYear();
            this.isbn = congress.getISBN(holding.getMaterial().getClassification());
        }

        @Override
        public String toString() {
            return this.title + " " + this.isbn;
        }

        @Override
        public int compareTo(ReportRecord that) {
            return this.title.compareTo(that.title);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            var reportRecord = (ReportRecord) o;
            return Objects.equals(title, reportRecord.title) && Objects.equals(branch, reportRecord.branch) && Objects.equals(author, reportRecord.author) && Objects.equals(year, reportRecord.year) && Objects.equals(isbn, reportRecord.isbn);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, branch, author, year, isbn);
        }
    }
}

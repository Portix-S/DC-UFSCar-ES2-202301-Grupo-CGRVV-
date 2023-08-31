package org.jabref.logic.importer.fileformat;

import java.io.BufferedReader;
import java.io.IOException;
// import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.types.EntryTypeFactory;
import org.jabref.model.entry.field.StandardField;

import org.jabref.logic.importer.Importer;
import org.jabref.logic.importer.ParserResult;
import org.jabref.logic.util.StandardFileType;

public class CsvImporter extends Importer {
    
    @Override
    public String getName() {
        return "CSV";
    }

    @Override
    public StandardFileType getFileType() {
        return StandardFileType.CSV;
    }

    @Override 
    public String getId() {
        return "csv";
    }

    @Override
    public String getDescription() {
        return "Importer for the CSV format";
    }

    @Override
    public boolean isRecognizedFormat(BufferedReader reader) throws IOException{
        // String str;
        // int i = 0;
        // while(((str = reader.readLine()) != null) && (i<50)) {
        //     if(str.trim().isEmpty()) {
        //         continue;
        //     }
        //     String[] fields = str.split(",");
        //     if(fields.length != 6) {
        //         return false;
        //     }
        //     i++;
        // }
        return true;
    }

    @Override
    public ParserResult importDatabase(BufferedReader reader) throws IOException{
        List<BibEntry> bibEntries = new ArrayList<>();

        String line = reader.readLine();
        int iter = 0;
        while(line != null) {
            // NOTE: This is a hack to skip the first line of the CSV file
            iter++;

            if(!line.trim().isEmpty()) {
                String[] fields = line.split(";");
                // if(fields.length != 7) {
                //     throw new IOException("Invalid CSV file. Expected 7 fields per line: type, title, publisher, year, author, editor, bibtexkey");
                // }               
                
                // Skip the first line, which is the header
                if (iter == 1) {
                    line = reader.readLine();
                    continue;
                }
                
                BibEntry be = new BibEntry();
                be.setType(EntryTypeFactory.parse(fields[0].trim()));
                be.setField(StandardField.TITLE, fields[1].trim());
                be.setField(StandardField.PUBLISHER, fields[2].trim());
                be.setField(StandardField.YEAR, fields[3].trim());
                be.setField(StandardField.AUTHOR, fields[4].trim());
                be.setField(StandardField.EDITOR, fields[5].trim());
                be.setField(StandardField.KEY, fields[6].trim());
                bibEntries.add(be);
                line = reader.readLine();
            }
        }
        return new ParserResult(bibEntries);
    }
}
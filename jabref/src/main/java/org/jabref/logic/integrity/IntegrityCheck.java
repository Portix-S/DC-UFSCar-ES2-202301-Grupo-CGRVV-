package org.jabref.logic.integrity;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jabref.logic.citationkeypattern.CitationKeyPatternPreferences;
import org.jabref.logic.journals.JournalAbbreviationRepository;
import org.jabref.model.database.BibDatabase;
import org.jabref.model.database.BibDatabaseContext;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.field.StandardField;
import org.jabref.preferences.FilePreferences;

public class IntegrityCheck {

    private final BibDatabaseContext bibDatabaseContext;
    private final FieldCheckers fieldCheckers;
    public final List<EntryChecker> entryCheckers;
    public List<IntegrityMessage> result;

    public IntegrityCheck(BibDatabaseContext bibDatabaseContext,
                          FilePreferences filePreferences,
                          CitationKeyPatternPreferences citationKeyPatternPreferences,
                          JournalAbbreviationRepository journalAbbreviationRepository,
                          boolean allowIntegerEdition) {
        this.bibDatabaseContext = bibDatabaseContext;

        fieldCheckers = new FieldCheckers(bibDatabaseContext,
                filePreferences,
                journalAbbreviationRepository,
                allowIntegerEdition);

        entryCheckers = new ArrayList<>(List.of(
                new CitationKeyChecker(),
                new TypeChecker(),
                new BibStringChecker(),
                new HTMLCharacterChecker(),
                new EntryLinkChecker(bibDatabaseContext.getDatabase()),
                new CitationKeyDeviationChecker(bibDatabaseContext, citationKeyPatternPreferences),
                new CitationKeyDuplicationChecker(bibDatabaseContext.getDatabase()),
                new AmpersandChecker()
                ));
        if (bibDatabaseContext.isBiblatexMode()) {
            entryCheckers.addAll(List.of(
                    new JournalInAbbreviationListChecker(StandardField.JOURNALTITLE, journalAbbreviationRepository),
                    new UTF8Checker(bibDatabaseContext.getMetaData().getEncoding().orElse(StandardCharsets.UTF_8))
            ));
        } else {
            entryCheckers.addAll(List.of(
                    new JournalInAbbreviationListChecker(StandardField.JOURNAL, journalAbbreviationRepository),
                    new ASCIICharacterChecker(),
                    new NoBibtexFieldChecker(),
                    new BibTeXEntryTypeChecker())
            );
        }
        //System.out.println("resultado " + entryCheckers + "\n   field" + fieldCheckers);
    }

    public IntegrityCheck(BibDatabaseContext bibDatabaseContext) {
        this.bibDatabaseContext = Objects.requireNonNull(bibDatabaseContext);
        this.fieldCheckers = null;
        this.entryCheckers = null;
    }

    List<IntegrityMessage> check() {
        result = new ArrayList<>();

        BibDatabase database = bibDatabaseContext.getDatabase();

        for (BibEntry entry : database.getEntries()) {
            result.addAll(checkEntry(entry));
        }
        result.addAll(checkDatabase(database));

        return result;
    }

    public List<IntegrityMessage> checkEntry(BibEntry entry) {
        result = new ArrayList<>();
        if (entry == null) {
            return result;
        }

        for (FieldChecker fieldChecker : fieldCheckers.getAll()) {
            result.addAll(fieldChecker.check(entry));
        }

        for (EntryChecker entryChecker : entryCheckers) {
            result.addAll(entryChecker.check(entry));
        }
        //System.out.println(result + " = resultado\n");

        return result;
    }

    public List<IntegrityMessage> checkDatabase(BibDatabase database) {
        System.out.println("checando... " + database + " erros: " + new DoiDuplicationChecker().check(database));
        return new DoiDuplicationChecker().check(database);
    }
}

package org.jabref.logic.integrity;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import org.jabref.logic.l10n.Localization;
import org.jabref.model.strings.StringUtil;

import java.util.regex.*;

public class YearChecker implements ValueChecker {

    private static final Predicate<String> CONTAINS_FOUR_DIGIT = Pattern.compile("([^0-9]|^)[0-9]{4}([^0-9]|$)")
                                                                        .asPredicate();
    private static final Predicate<String> ENDS_WITH_FOUR_DIGIT = Pattern.compile("[0-9]{4}$").asPredicate();
    private static final String PUNCTUATION_MARKS = "[(){},.;!?<>%&$]";


    /**
     * Checks, if the number String contains a four digit year and ends with it.
     * Official bibtex spec:
     * Generally it should consist of four numerals, such as 1984, although the standard styles
     * can handle any year whose last four nonpunctuation characters are numerals, such as ‘(about 1984)’.
     * Source: http://ftp.fernuni-hagen.de/ftp-dir/pub/mirrors/www.ctan.org/biblio/bibtex/base/btxdoc.pdf
     */
    @Override
    public Optional<String> checkValue(String value) {



        if (StringUtil.isBlank(value)) {
            return Optional.empty();
        }

        String regex = "[0-9]+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);

        /*
        if (!isValid(value.trim())) {
            return Optional.of(Localization.lang("should contain a four digit number"));
        }
        //*/

        if (!(m.matches())) {
            return Optional.of(Localization.lang("should contain a four positive digit number"));
        }
        int today = LocalDate.now().getYear() + 10;
        if (((value.length() > 4) || ((Integer.parseInt(value) < 0))) && !StringUtil.isBlank(value)) {
            return Optional.of(Localization.lang("should contain a four positive digit number"));
        }

        if ((Integer.parseInt(value) > (today)) && !StringUtil.isBlank(value)) {
            return Optional.of(Localization.lang("should be a year between 0 - " + today));
        }


        return Optional.empty();
    }
}

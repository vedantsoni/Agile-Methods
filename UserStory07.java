import com.VedantSoni.FormatGEDCOM;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class UserStory07 {

    private FormatGEDCOM formatGEDCOM = new FormatGEDCOM();

    public void ageLessThan150_US07() throws ParseException {
        formatGEDCOM.GedcomTable();
        HashMap<String, String[]> Individual = formatGEDCOM.getIndividualMap();
        String[] indiValues = new String[100];
        long differenceInDays, differenceInYears;
        Date birthdate, deathdate, tdate;
        tdate = new Date();


        for (String s : Individual.keySet()) {
            indiValues = Individual.get(s);


            if (!indiValues[2].equalsIgnoreCase("NA")) {
                birthdate = formatGEDCOM.StringtoDate(indiValues[2]);

                if (!indiValues[5].equalsIgnoreCase("NA")) {
                    deathdate = formatGEDCOM.StringtoDate(indiValues[5]);

                    long duration = deathdate.getTime() - birthdate.getTime();
                    differenceInDays = TimeUnit.MILLISECONDS.toDays(duration);
                    differenceInYears = differenceInDays / 365;

                    if (differenceInYears > 150) {
                        System.out.println("\n ERROR in the Input GEDCOM File: The age is more than 150 for the Individual ID " + s);
                    }
                }
                else if(indiValues[5].equalsIgnoreCase("NA")){

                    long duration1= tdate.getTime() - birthdate.getTime();
                    differenceInDays = TimeUnit.MILLISECONDS.toDays(duration1);
                    differenceInYears = differenceInDays/365;

                    if(differenceInYears > 150){
                        System.out.println("\n ERROR in the Input GEDCOM File: The age is more than 150 for the Individual ID " + s);
                    }
                }

            }

        }
    }
}

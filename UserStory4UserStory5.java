import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

public class UserStory4UserStory5 {
    private FormatGEDCOM formatGEDCOM = new FormatGEDCOM();

    public UserStory4UserStory5() {
        formatGEDCOM.GedcomTable();

    }

    public void MarriageBeforeDivorce_US4() throws ParseException {


        HashMap<String, String[]> Family = formatGEDCOM.getFamilyMap();//Copying familymap values to new hashmap
        String[] familyValues = new String[100];
        Date marriedDate, divorceDate, tdate;
        tdate = new Date();//Setting date to today's date.
        for (String s : Family.keySet()) {
            familyValues = Family.get(s);//Getting values of family map for each key value
            try {
                if ((!familyValues[0].equalsIgnoreCase("NA")) && (!familyValues[1].equalsIgnoreCase("NA"))) {

                    if ((!familyValues[0].equalsIgnoreCase("")) || (!familyValues[1].equalsIgnoreCase(""))) {
                        marriedDate = formatGEDCOM.StringtoDate(familyValues[0]);
                        divorceDate = formatGEDCOM.StringtoDate(familyValues[1]);
                        if (marriedDate.after(divorceDate) || marriedDate.equals(divorceDate)) {
                            System.out.println("\nError in GEDCOM File:Marriage Date is after Divorce date " +
                                    " for the family with family ID " + s);
                        }
                        else if(marriedDate.equals(divorceDate))
                        {
                            System.out.println("\nError in GEDCOM File:Marriage Date and Divorce date are same" +
                                    " for the family with family ID " + s);
                        }
                    } else {
                        System.out.println("\nError in GEDCOM File:In family with family ID " + s + " Marriage date and Divorce date is not mentioned");
                    }


                } else if ((!familyValues[0].equalsIgnoreCase("NA")) && (familyValues[1].equalsIgnoreCase("NA")))

                {
                    marriedDate = formatGEDCOM.StringtoDate(familyValues[0]);
                    if (marriedDate.after(tdate)) {
                        System.out.println("\nError in GEDCOM File:Marriage date is mentioned in the family with family ID  " + s + " is a future date");
                    }
                } else if ((familyValues[0].equalsIgnoreCase("NA")) && (!familyValues[1].equalsIgnoreCase("NA"))) {
                    System.out.println("\nError in GEDCOM File:In family with family ID " + s + " Divorce date is mentioned but Marriage Date not mentioned");

                }
            }
            catch (Exception e)
            {
                e.fillInStackTrace();
            }
        }
    }
    public void MarriageBeforeDeath_US5() throws ParseException {

        HashMap<String, String[]> Family = formatGEDCOM.getFamilyMap();
        HashMap<String, String[]> Individual = formatGEDCOM.getIndividualMap();
        String familyValues[]=new String[100];
        String individualValues[]=new String[100];
        Date marriedDate,deathDate;
        for(String key: Family.keySet())
        {
            familyValues=Family.get(key);
            try {
                marriedDate=formatGEDCOM.StringtoDate(familyValues[0]);
            }
            catch(Exception e)
            {
                continue;
            }
            for(String iKey:Individual.keySet())
            {
                if(familyValues[2].trim().equalsIgnoreCase(iKey)||familyValues[4].trim().equalsIgnoreCase(iKey))
                {
                    individualValues=Individual.get(iKey);
                    try {
                        deathDate = formatGEDCOM.StringtoDate(individualValues[5]);
                        if(deathDate.before(marriedDate))
                        {
                            if(individualValues[1].trim().equalsIgnoreCase("M"))
                            {
                                System.out.println("\nError in GEDCOM File: Husband's death date with individual ID " +iKey+" is before marriage date for" +
                                        "the family with family ID "+key);
                            }
                            else if(individualValues[1].trim().equalsIgnoreCase("F"))
                            {
                                System.out.println("\nError in GEDCOM File: Wife's death date with individual ID " +iKey+" is before marriage date for" +
                                        "the family with family ID "+key);
                            }
                            else
                            {
                                System.out.println("\nError in GEDCOM File: Wife/Husband's(Gender not mentioned) death date with individual ID " +iKey+" is before marriage date for" +
                                        "the family with family ID "+key);
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        continue;
                    }
                }


            }

        }
    }



}

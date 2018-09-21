import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class FormatGEDCOM {
    public static void main(String[] args) throws IOException {

        String filedata = "";
        String filePath;
        filePath = "F:\\Stevens_Masters\\Fall 18\\Agile\\Week 1\\Deepu_Variyangattil.ged";
        CharSequence charSequenceIndividual = "INDI";
        CharSequence charSequenceFamily = "FAM";
        //CharSequence charSequenceChild = "FAMC";
        HashMap<String, String[]> individualMap = new HashMap<>();
        String[] lineArray = new String[1000];
        String[] lineArray2 = new String[1000];
        String[] familyArray1=new String[100];
        String[] familyArray2=new String[100];
        String[] values;
        String individualID = null, date, name, sex=null;
        String birthDate="";
        String deathDate="";
        String alive="False";
        String individualID_Old="";
        String spouse="";
        String child="";
        int count = 0;
        int familyCount=0;
        String familyID=null;
        int dateField = 0;
        boolean keywordMatch_2;
        date = "";
        name = "";
        Date dateBir;
        Date dateDet;
        int age=0;
        dateBir=new Date();
        dateDet=new Date();

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        try {
            while ((filedata = bufferedReader.readLine()) != null) {
                //System.out.println(filedata);


                if (filedata.contains(charSequenceIndividual)) {

                   values=new String[]{name,sex,birthDate,Integer.toString(age),alive,deathDate,child,spouse};
                   if(individualID!=null)
                   {
                       individualMap.put(individualID.replace("@","").trim(),values);
                   }

                    individualID_Old = individualID;
                    individualID = "";
                    name="";
                    sex="";
                    birthDate="";
                    age=0;
                    alive="";
                    deathDate="";
                    count=0;
                    dateField = 0;
                    dateBir=new Date();
                    date="";
                    spouse="";
                    child="";

                    dateDet=new Date();
                    lineArray = filedata.split(" ");
                    individualID = lineArray[1];

                    count = 1;


                } else if (filedata.contains(charSequenceIndividual) == false && count == 1) {
                    lineArray2 = filedata.split(" ");

                    if (lineArray2[0].equalsIgnoreCase("2")) {
                        keywordMatch_2 = keywordMatcher(lineArray2[0], lineArray2[1]);
                        if (keywordMatch_2) {

                            for (int i = 2; i <= lineArray2.length - 1; i++)
                                date += lineArray2[i];
                            if (dateField == 1) {
                                birthDate = date;
                                date="";
                                dateBir = new SimpleDateFormat("ddMMMyyyy").parse(birthDate);
                                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                birthDate = dateFormat.format(dateBir);
                                int birthYear = dateBir.getYear();
                                int currentYear = dateDet.getYear();
                                age = currentYear - birthYear;
                                alive="True";
                                deathDate="NA";


                            } else if (dateField == 2) {
                                deathDate = date;
                                //System.out.println(deathDate);
                                dateDet = new SimpleDateFormat("ddMMMyyyy").parse(deathDate);
                                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                deathDate = dateFormat.format(dateDet);
                                int birthYear = dateBir.getYear();
                                int deathYear = dateDet.getYear();
                                age = deathYear - birthYear;
                                alive="False";


                            }


                        }
                    } else if (lineArray2[0].equalsIgnoreCase("1")) {
                        keywordMatch_2 = keywordMatcher(lineArray2[0], lineArray2[1]);
                        if (keywordMatch_2) {
                            if (lineArray2[1].equalsIgnoreCase("BIRT")) {
                                dateField = 1;
                            } else if (lineArray2[1].equalsIgnoreCase("DEAT")) {
                                dateField = 2;
                            } else if (lineArray2[1].equalsIgnoreCase("NAME")) {
                                for (int i = 2; i < lineArray2.length; i++) {
                                    name = name + lineArray2[i];
                                }
                                //System.out.println("Name is " + name);
                            } else if (lineArray2[1].equalsIgnoreCase("SEX")) {
                                sex = lineArray2[2];
                                //System.out.println("Sex is " + sex);
                            }
                            else if(lineArray2[1].equalsIgnoreCase("FAMS"))
                            {
                                String spouseArray[]=new String[100];
                                spouseArray=filedata.split(" ");
                                spouse=spouse+" "+spouseArray[2].replace("@","").trim();
                               // System.out.println("Spouse is : "+spouse);

                            }
                            else if(lineArray2[1].equalsIgnoreCase("FAMC"))
                            {
                                String childArray[]=new String[100];
                                childArray=filedata.split(" ");
                                child=child+" "+childArray[2].replace("@","").trim();
                            }

                        }
                    }
                }
                else if(filedata.contains(charSequenceFamily))
                {
                    familyArray1=filedata.split(" ");
                    if(familyArray1[0].equalsIgnoreCase("0"))
                    {
                        familyCount=1;
                        familyID=familyArray1[1].replace("@","").trim();
                    }

                }
                else if(filedata.contains(charSequenceFamily)==false && familyCount==1)
                {

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        fileReader.close();
        bufferedReader.close();
        String[]temp=new String[100];
        String[] set=new String[1000];
        set= individualMap.keySet().toArray(new String[0]);
        //System.out.println(set[2
          //      ]+set[1]);
        System.out.println("ID       NAME               GENDER  BIRTHDAY    AGE  ALIVE     DEATH      CHILD  SPOUSE");
        System.out.println("==       ====               ======  ========    ===  =====    ========    =====  ======");

        for(String iterator_Key: set)
        {
            if(iterator_Key!=null) {
                temp = individualMap.get(iterator_Key);
                System.out.println(String.format("%1s%18s%10s%14s%4s%8s%12s%10s%8s",iterator_Key,temp[0],temp[1],temp[2],temp[3],
                        temp[4],temp[5],temp[6],temp[7]));

            }
        }

        //System.out.println(temp.length);

       //System.out.println(temp[0]);
    }

    private static boolean keywordMatcher(String level, String tag) {

        String[] validKeyWords_2 = new String[100];
        if (level.equalsIgnoreCase("1")) {
            validKeyWords_2 = new String[]{"NAME", "BIRT", "SEX", "DEAT","FAMS","FAMC"};
        } else {
            validKeyWords_2 = new String[]{"DATE"};
        }
        boolean result = true;

        for (String iterate : validKeyWords_2) {
            if (iterate.equalsIgnoreCase(tag)) {
                result = true;
                break;
            } else {
                result = false;
            }


        }
        return result;
    }
}

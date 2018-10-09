import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

public class US31_US32{
    private FormatGEDCOM formatGEDCOM = new FormatGEDCOM();
    

public void StillSingle_US31() throws ParseException
{
	
        formatGEDCOM.GedcomTable();
        HashMap<String, String[]> Indi = formatGEDCOM.getIndividualMap();
        String[] indiValues = new String[100];
        
        
        for (String s : Indi.keySet()) 
        {
            indiValues = Indi.get(s);
            if(indiValues[5]=="NA")
           if(indiValues[7]=="")
            {
        	   int age;
            age=Integer.parseInt(indiValues[3]);
        	   if(age>30)
        	   {System.out.println("The person living and single is "+indiValues[0]+"with id as "+s);}
        	   
            }
        }



}





public void MultipleBirths_US32() throws ParseException

{
	formatGEDCOM.GedcomTable();
    
    
    HashMap<String, String[]> Indi = formatGEDCOM.getIndividualMap();
    String[] indiValues = new String[100];
    String[] indiValues1 = new String[100];
    
    
    for (String s : Indi.keySet())
    {
    	indiValues = Indi.get(s);
    	if(indiValues[5]=="NA")
    	for(String s1: Indi.keySet())
    	{
    		
    		indiValues1=Indi.get(s1);
    		if(indiValues1[5]=="NA")

    		if(indiValues1[2].equals(indiValues[2])&&indiValues1[6].equals(indiValues[6])&&!(indiValues1[0].equals(indiValues[0])))
    			
    		{System.out.println(indiValues[0]+" and "+indiValues1[0]+" are a multiple birth");}
    		
    		
    		
    	}
   }
	
    
    
}


















}


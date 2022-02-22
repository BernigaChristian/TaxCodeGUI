import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FiscalCode {
    private String fiscalCode="";

    public FiscalCode(String[] data,Town[] italianTowns ){
        firstPart(data[0]);     //surname
        secondPart(data[1]);            //name
        thirdPart(data[2]);             //year
        fourthPart(data[2]);            //month
        fifthPart(data[2],data[5]);     //day
        sixthPart(data[3],data[4],italianTowns);     //town
        seventhPart();                  //checkchar
    }

    private boolean vocalCheck(char c){
        char[] vocals={'A','E','I','O','U'};
        for(char x:vocals)
            if(Character.compare(x,c)==0)    return true;
        return false;
    }


    private void firstPart(String surname){
        int counter=0;
        for(int i=0;i<surname.length();i++)
            if(counter<3&&!vocalCheck((surname.charAt(i)))) {
                fiscalCode += surname.charAt(i);
                counter++;
            }
        if(counter<3)
            for(int i=0;i<surname.length();i++)
                if(counter<3&&vocalCheck(surname.charAt(i))) {
                    fiscalCode += surname.charAt(i);
                    counter++;
                }
        for(int i=counter;i<3;i++)
            fiscalCode+="X";
    }

    private void secondPart(String name){
        int counter=0;
        for(int i=0;i<name.length();i++)
            if(counter<4&&!vocalCheck(name.charAt(i))) {
                if(counter!=1)
                    fiscalCode += name.charAt(i);
                counter++;
            }
        if(counter<4)
            for(int i=0;i<name.length();i++)
                if(counter<4&&vocalCheck(name.charAt(i))) {
                    fiscalCode += name.charAt(i);
                    counter++;
                }
        for(int i=counter;i<3;i++)
            fiscalCode+="X";
    }

    private void thirdPart(String year){
        String[] data=year.split("/");
        fiscalCode+=(data[2].substring(2,4));
        System.out.println(data[2]);
    }

    private char getMonthChar(int month){
        char[] vocals={'A','B','C','D','E','H','L','M','P','R','S','T'};
        return vocals[month-1];
    }

    private void fourthPart(String month){
        String[] data=month.split("/");
        fiscalCode+=getMonthChar(Integer.parseInt(data[1]));
    }

    private void fifthPart(String day,String gender){
        String[] data=day.split("/");
        int d=Integer.parseInt(data[0]);
        fiscalCode+=(gender.equalsIgnoreCase("women"))?d+40:((d<10)?"0"+d:d);
    }

    private void sixthPart(String town,String province,Town[] italianTowns){
        for(Town t:italianTowns)
            if(t.getName().equalsIgnoreCase(town)&&t.getProvince().equalsIgnoreCase(province.substring(0,2)))  fiscalCode+=t.getFisco();
    }

    private int evenCharSum(){
        int sum=0;
        for(int i=1;i<fiscalCode.length();i+=2)
            sum+=(fiscalCode.charAt(i)<65)?(fiscalCode.charAt(i)-48):(fiscalCode.charAt(i)-65);
        return sum;
    }

    private int oddCharSum(){
        int[] oddValues={1,0,5,7,9,13,15,17,19,21,2,4,18,20,11,3,6,8,12,14,16,10,22,25,24,23};
        int sum=0;
        for(int i=0;i<fiscalCode.length();i+=2)
            sum+=oddValues[(fiscalCode.charAt(i)<65)?(fiscalCode.charAt(i)-48):(fiscalCode.charAt(i)-65)];
        return sum;
    }

    private void seventhPart(){
        fiscalCode+=(char)(65+((evenCharSum()+oddCharSum())%26));
    }

    public String toString(){return this.fiscalCode;}
}

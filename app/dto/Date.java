public class Date {
    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year){
        setDay(day);
        setMonth(month);
        setYear(year);
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setDay(int day) {
        if(day>=1 && day<=31){
            this.day = day;
        }
        else {
            System.out.println("Invalid date");
            System.exit(1);
        }
    }

    public void setMonth(int month) {
        if(month>=1 && month<=12){
            this.month = month;
        }else{
            System.out.println("Invalid month");
            System.exit(1);
        }
    }

    public void setYear(int year) {
        if(year>=1940 && year<=2100){
            this.year = year;
        }else{
            System.out.println("Invalid year range");
            System.exit(1);
        }
    }

    public String getDate(){
        String  day = (this.getDay()<10)?"0"+this.getDay():Integer.toString(this.getDay());
        String  month = (this.getMonth()<10)?"0"+this.getMonth():Integer.toString(this.getMonth());

        return day+"/"+month+"/"+year;
    }
}

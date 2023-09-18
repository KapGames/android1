package com.example.mathchampionsleague;

import android.telephony.mbms.StreamingServiceInfo;

public class Language {
    public boolean heb;

    public Language(boolean heb){
        this.heb = heb;
    }
    public String getHighScore(int x ){
        if (heb) return  "שיא : " + String.valueOf(x);
        else return "High Score : " + String.valueOf(x) ;
    }
    public String getShareByEmail(){
        if (heb) return "שתף באימייל";
        else return "Email Share";
    }

    public String getShareBySMS(){
        if (heb) return "שתף בסמס";
        else return "SMS Share";
    }
    public String getFirstMessage(){
        if (heb) return "לא חייב לתת שם, אתם יכולים \nללחוץ על הכפתור ולהתחיל לשחק";
        else return "You don't have to insert a name...\nYou can just tap PLAY to start Playing.";
    }
    public String getChooseDiff(){
        if (heb) return "בחר רמת קושי";
        else return "Choose Difficulty";
    }

    public String getShareBySMStoDev(){
        if (heb) return "סמס למפתח";
        else return "SMS developer";
    }

    public String getEmailtoDev(){
        if (heb) return "מייל למפתח";
        else return "Email Devs";
    }
    public String getQuotes(){
        if (heb) return "ציטוטים של מתמטיקאים";
        else return "Math Quotes";
    }

    public String getPlay(){
        if (heb) return "שחק";
        else return "PLAY";
    }
    public String getHowToPlay(){
        if (heb) return "איך משחקים?";
        else return "How to Play?";
    }

    public String getUsernameHint(){
        if (heb) return "הכנס את שמך כאן...";
        else return "Insert your name here...";
    }
    public String getInstructions(){
        if (heb) return "1.בחר את רמת המשחק.\n\n" +
                "2.פתור את המשוואה.\n\n" +
                "3.לחץ על הכדור עם התשובה הנכונה.\n\n" +
                "4.אם הכדור נכנס לשער זה אומר שענית נכון!";
        else{
            return "1.Choose Difficulty.\n\n" +
                    "2.Solve the equation.\n\n" +
                    "3.Tap the ball with the right answer on it.\n\n" +
                    "4.If it's a goat that means you answered correctly!";
        }
    }

    public String getBack(){
        if (heb) return "חזור";
        else return "Back";
    }

    public String getDefinition(){
        if (heb) return "המשחק הזה הוא השילוב המושלם בין כדורגל למתמטיקה!";
        else return "This game is the ultimate combination between math and football!";
    }

    public String getTitle(){
        if (heb) return "ליגת האלופות למתמטיקה";
        else return "Math Champions League";
    }
    public String getEasy(){
        if (heb) return "קל";
        else return "Easy";
    }

    public String getMedium(){
        if (heb) return "בינוני";
        else return "Medium";
    }

    public String getHard(){
        if (heb) return "קשה";
        else return "Hard";
    }
    public String getTime(){
        if (heb) return "זמן";
        else return "Time";
    }
    public String getExit(){
        if (heb) return "יציאה";
        else return "Exit";
    }
    public String getRestart(){
        if (heb) return "שחק שוב";
        else return "Restart";
    }
    public String getThanks(String name){
        if (heb) return "תודה ששיחקת " + name;
        else return "Thanks for Playing " + name ;
    }

    public String getScoreSummary(int x , String d){
        if (heb) {d = translateDiff(d); return "הצלחת להכניס " + x + " גולים " + "ברמה " + d;}
        else return "You Scored " + x + " Goals " + "On difficulty " +d;
    }

    public static String translateDiff(String d){
        if(d.equals("hard")){
            return "קשה";}
        else if(d.equals("medium")){
            return "בינוני";
        }else{return "קל";}
    }

    public String getTopData(String name , String diff , String score ){
        if (heb) return "שם : " + name + "\nרמה :"  + diff +"\nגולים: " + score ;
        else return "Your Name : " + name + "\nDifficulty :"  + diff +"\nScore: " + score;
    }

    public String getGoal( ){
        if (heb) return "גוללל!!!";
        else return "GOALLL!!!";
    }
    public String getMiss( ){
        if (heb) return "טעות...";
        else return "Miss...";
    }

    public String getHelloName(String name ){
        if (heb) return  "שלום " + name + ",";
        else return "Hello " + name +",";
    }
    public String getHelloNoName( ){
        if (heb) return "ברוכים הבאים,";
        else return "Welcome,";
    }
    public String getGameOver( ){
        if (heb) return "המשחק נגמר";
        else return "Game Over";
    }

}

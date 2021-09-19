package rob.myappcompany.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TICKET_SYSTEM_TABLE = "TICKET_SYSTEM";
    public static final String COLUM_ID = "ID";

    public static final String COLUM_CREATOR_NAME = "CREATOR_NAME";
    public static final String COLUM_QUESTION_TICKET = "QUESTION_TICKET";

    public static final String COLUM_TICKET_NAME = "TICKET_NAME";
    public static final String COLUM_TITLE_CREATED = "TITLE_CREATED";
    public static final String COLUM_SITUATION_TICKET = "SITUATION_TICKET";
    public static final String COLUM_PRIORITY_TICKET = "PRIORITY_TICKET";
    public static final String COLUM_CREATED_DATE = "CREATED_DATE";
    public static final String COLUM_UPDATED_DATE = "UPDATED_DATE";

    public static final String DATABASE_NAME = "TICKETSYSTEM_DB";

    public static final String ANSWER_TICKET_TABLE = "ANSWER_TICKET";
    public static final String COLUM_RESPONSE_ID = "RESPONSE_ID";
    public static final String COLUM_RESPONSE_TICKET = "RESPONSE_TICKET";
    public static final String COLUM_RESPONSE_NAME = "RESPONSE_NAME";
    public static final String COLUM_FOREIGN_ID = "FOREIGN_ID";
    public static final String COLUM_UPDATED_DATE_RESPONSE = "UPDATED_DATE_RESPONSE";
    private static final String ID = "ID";

    Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) throws SQLiteException{

        try{
            String createTicketTableStatment = "CREATE TABLE IF NOT EXISTS  " + TICKET_SYSTEM_TABLE + " (" + COLUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUM_CREATOR_NAME + " TEXT, " +
                    COLUM_QUESTION_TICKET + " TEXT, " + COLUM_TICKET_NAME + " TEXT, " + COLUM_TITLE_CREATED + " TEXT, " + COLUM_SITUATION_TICKET + " BOOL, " + COLUM_PRIORITY_TICKET + " TEXT, " + COLUM_CREATED_DATE + " DATE, " + COLUM_UPDATED_DATE + " DATE)";

            //Response Ticket
            String createAnswerTicketStatment = "CREATE TABLE IF NOT EXISTS " + ANSWER_TICKET_TABLE + " (" + ID +" INTEGER PRIMARY KEY AUTOINCREMENT , " + COLUM_FOREIGN_ID + " INT , " +
                    COLUM_RESPONSE_NAME + " TEXT , " + COLUM_RESPONSE_TICKET + " TEXT , " +  COLUM_UPDATED_DATE_RESPONSE + " DATE)";

            db.execSQL(createTicketTableStatment);
            db.execSQL(createAnswerTicketStatment);
        }catch (SQLiteException e){
            try {
                throw new IOException(e);
            }catch (IOException e1){
                e1.printStackTrace();
            }
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TICKET_SYSTEM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ ANSWER_TICKET_TABLE);
        onCreate(db);
    }



    //WORKING HERE
    public boolean deleteItem(String idInput){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TICKET_SYSTEM_TABLE, "id=0", new String[]{idInput});
            db.close();
            Log.i("TAG", "DELETED");
            return true;
        }catch (SQLiteException e){
            e.printStackTrace();
            return false;
        }
    }
    public void getResponseTicket(){
        Log.i("TAG", "Response");
    }

    public boolean insertResponse(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues conVal = new ContentValues();

        conVal.put(COLUM_FOREIGN_ID, 11);
        conVal.put(COLUM_RESPONSE_TICKET, "Hallo mr.Matheo");
        conVal.put(COLUM_RESPONSE_NAME, "Matheo");
        conVal.put(COLUM_UPDATED_DATE_RESPONSE, "11.03.1991");
        long insertResponse = db.insert(ANSWER_TICKET_TABLE, null, conVal);
        if (insertResponse == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean insertOnItem(TicketSystemValueModel ticketModel){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues insertValues = new ContentValues();
        insertValues.put(COLUM_CREATOR_NAME, ticketModel.getCreator_name());
        insertValues.put(COLUM_QUESTION_TICKET,ticketModel.getQuestion_ticket());
        insertValues.put(COLUM_TITLE_CREATED,ticketModel.getTitle_created());
        insertValues.put(COLUM_SITUATION_TICKET,ticketModel.getSituation_ticket());
        insertValues.put(COLUM_TICKET_NAME, ticketModel.getTicket_name_ticket());
        insertValues.put(COLUM_PRIORITY_TICKET, ticketModel.getPriority_ticket());
        insertValues.put(COLUM_CREATED_DATE, ticketModel.getCreated_date());
        insertValues.put(COLUM_UPDATED_DATE, ticketModel.getUpdated_date());
        long insert = db.insert(TICKET_SYSTEM_TABLE, null, insertValues);
        if (insert == -1){
            return false;
        }else {
            return true;
        }
    }


    public String getDataTime(String choose){
        LocalDateTime localDateTime;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            localDateTime = LocalDateTime.now();

            if (choose.equals("Data")){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                return localDateTime.format(formatter);
            }else if(choose == "Time"){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                return localDateTime.format(formatter);
            }
        }
        return choose;
    }

    public List<TicketSystemValueModel> getEveryTicket(){
        List<TicketSystemValueModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+ TICKET_SYSTEM_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();

        //Cursor return type
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do{
                int TicketID = cursor.getInt(0);
                String creator_name = cursor.getString(1);
                String question_ticket = cursor.getString(2);
                String ticket_name = cursor.getString(3);
                String title_ticket = cursor.getString(4);
                String area = cursor.getString(5);
                String priority = cursor.getString(6);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    TicketSystemValueModel newTicket = new TicketSystemValueModel(creator_name,question_ticket, title_ticket, area, ticket_name, priority, getDataTime("Data"),"14.04.2022");
                    returnList.add(newTicket);
                }
            }while (cursor.moveToNext());
        }
        else {
            //failed
        }
        //cursor.close();
        //db.close();
        return  returnList;
    }

    /*public ArrayList getAllTicket(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM "+ TICKET_SYSTEM_TABLE , null);

        int id;
        String creator_name;
        String question_ticket;
        String ticket_name;
        String title_created;
        String situation_ticket;
        String priority_ticket;
        String created_date;
        String updated_date;


        ArrayList<StruckDB> valueReturn = new ArrayList<StruckDB>();

        //id , creator_name, quastion_ticket, ticket_name, title_created, situation_ticket, priority_ticket, created_date, updated_date

        result.moveToFirst();
        while (result.isAfterLast() == false){
            id = result.getInt(result.getColumnIndex("id"));
            creator_name = result.getString(result.getColumnIndex("creator_name"));
            question_ticket = result.getString(result.getColumnIndex("quastion_ticket"));
            ticket_name = result.getString(result.getColumnIndex("ticket_name"));
            title_created = result.getString(result.getColumnIndex("title_created"));
            situation_ticket = result.getString(result.getColumnIndex("situation_ticket"));
            priority_ticket = result.getString(result.getColumnIndex("priority_ticket"));
            created_date = result.getString(result.getColumnIndex("created_date"));
            updated_date = result.getString(result.getColumnIndex("updated_date"));

            valueReturn.add(new StruckDB(id, creator_name, question_ticket, ticket_name, title_created, situation_ticket, priority_ticket, created_date, updated_date));

            result.moveToNext();
        }
        
        return valueReturn;
    }*/

}

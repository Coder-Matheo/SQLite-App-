package rob.myappcompany.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseHelper databaseHelper;
    Spinner spinner;
    EditText createrEditText;
    EditText questionEditText;
    Button addButton;
    private String priority_text;
    private InputMethodManager inputMethodManager;
    private ListView ticketListView;
    private ArrayAdapter arrayAdapter;
    private ArrayList ticketArrayList;
    public String tag = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialization Layout Element
        spinner = findViewById(R.id.spinner);
        createrEditText = findViewById(R.id.createrEditText);
        questionEditText = findViewById(R.id.questionEditText);
        addButton = findViewById(R.id.addButton);
        ticketListView = findViewById(R.id.ticketListView);
        //initialization Object
        databaseHelper = new DatabaseHelper(this);


        //Spinner Adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.priority_option, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //Hide Keyboard while Add Button pressed
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        createrEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s1, int start, int before, int count) {

                questionEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                    //if fill all Input Value, then enable Button
                    @Override
                    public void onTextChanged(CharSequence s2, int start, int before, int count) {
                        if (s1.length() == 0 || s2.length() == 0){
                            addButton.setEnabled(false);
                        }else if (s1.length() != 0 && s2.length() != 0){
                            addButton.setEnabled(true);
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable s) { }
                });
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    //get Time and Date Local
    public String getDataTime(String choose){
        LocalDateTime localDateTime;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            localDateTime = LocalDateTime.now();

            if (choose.equals("Date")){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                return localDateTime.format(formatter);
            }else if(choose == "Time"){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                return localDateTime.format(formatter);
            }
        }
        return choose;
    }


    public void addOnItem(View view){
        if (createrEditText.getText().length() > 0 && questionEditText.getText().length() > 0 && priority_text != "priority" && spinner.isEnabled()){

            TicketSystemValueModel ticketSystemValueModel = new TicketSystemValueModel(createrEditText.getText().toString(),questionEditText.getText().toString(), "Response", "Manegment", "Sofort", priority_text, getDataTime("Date"),"14.04.2020");
            databaseHelper.insertOnItem(ticketSystemValueModel);

            //Hide Keyboard while press Button
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }else {
            addButton.setEnabled(false);
        }
    }

    public void showAllTicket(View view){
        List<TicketSystemValueModel> newlistTicket = new ArrayList<>();

        for (int i = 0; i < databaseHelper.getEveryTicket().size(); i++){
                newlistTicket.add(new TicketSystemValueModel(
                        databaseHelper.getEveryTicket().get(i).getCreator_name(),
                        databaseHelper.getEveryTicket().get(i).getQuestion_ticket(),
                        databaseHelper.getEveryTicket().get(i).getTitle_created(),
                        databaseHelper.getEveryTicket().get(i).getSituation_ticket(),
                        databaseHelper.getEveryTicket().get(i).getTicket_name_ticket(),
                        databaseHelper.getEveryTicket().get(i).getPriority_ticket(),
                        databaseHelper.getEveryTicket().get(i).getCreated_date(),
                        databaseHelper.getEveryTicket().get(i).getUpdated_date()));
        }
        Log.i(tag, databaseHelper.getEveryTicket().get(0).getCreator_name());

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, newlistTicket);
        ticketListView.setAdapter(arrayAdapter);

    }
}
package com.example.financialassistant.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.financialassistant.App;
import com.example.financialassistant.DB.DatabaseHelper;
import com.example.financialassistant.DB.Model.Document;
import com.example.myroom1.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private static final String MY_SETTINGS = "my_settings";
    // Идентификатор уведомления
    private static final int NOTIFY_ID = 101;
    // Идентификатор канала
    private static String CHANNEL_ID = "Date document";

    private List<Document> documentModels = new ArrayList<>();
   // private List<DocumentPeriod> documentperiodModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = App.getInstance().getDatabaseInstance();
        setTitle("Financial assistant");


        SharedPreferences sp = getSharedPreferences(MY_SETTINGS,
                Context.MODE_PRIVATE);
        // проверяем, первый ли раз открывается программа
        boolean hasVisited = sp.getBoolean("hasVisited", false);

        if (!hasVisited) {
            databaseHelper.run_category_income();
            databaseHelper.run_category_cost();
            databaseHelper.run_document_period();
            SharedPreferences.Editor e = sp.edit();
            e.putBoolean("hasVisited", true);
            e.commit();
           // f();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DATE, +6);
        int m_now = now.get(Calendar.MONTH);
        int y_now = now.get(Calendar.YEAR);
        int d_now = now.get(Calendar.DAY_OF_MONTH);
        now.set(y_now,m_now,d_now,0,0);
        long timeMilli_Notification = now.getTimeInMillis(); //дата сегодняшняя, до какой даты отсчет

        Calendar now1 = Calendar.getInstance();
        now1.add(Calendar.DATE, +4);
        now.set(y_now,m_now,d_now,0,0);
        long timeMilli =  now1.getTimeInMillis();
        documentModels = databaseHelper.getDocumentDao().getAllDocumentDateFinish(timeMilli,timeMilli_Notification);

        String fDate1 = sdf.format(timeMilli_Notification);
        String fDate2 = sdf.format(timeMilli);
        int size =  0 ;
        for (Document c: documentModels){
            size = size +1;
            String fDate = sdf.format(c.finish_date);
            Not(c.name, fDate, size);
        }

}

  /*  public void f(){
        DocumentPeriod documentPeriod = new DocumentPeriod();
        documentperiodModels=databaseHelper.getDocumentPeriodDao().getAllDocumentPeriod();
        String[] ONE ={"Справка", "Гарантия", "Другое"};
        String[] MONTH ={"Квартплата", "Коммуникации", "Кредит", "Обучение","Другое"};
        String[] YEAR ={"Страховка", "Обучение", "Другое"};

        for (DocumentPeriod c: documentperiodModels){
            if (c.name == "Одноразовые"){
                for (int j = 0; j < ONE.length; j++){
                    c.category_period = ONE[j];
                    databaseHelper.getDocumentPeriodDao().updateDocumentPeriod(c);
                }}
            if (c.name == "Ежемесячные"){
                for (int j = 0; j < MONTH.length; j++){
                    c.category_period = MONTH[j];
                    databaseHelper.getDocumentPeriodDao().updateDocumentPeriod(c);                }}
            if (c.name == "Ежегодные"){
                for (int j = 0; j < YEAR.length; j++){
                    c.category_period = YEAR[j];
                    databaseHelper.getDocumentPeriodDao().updateDocumentPeriod(c);                }}
        }
    }-*/
    public void Not (String name, String date, int id) {
        String str2 = "Истекает срок действия документа";
        String str4 = ". Дата истечения срока действия/оплаты документа: ";
       // String str3 = String.join(" ", str2, name);
        String str3 = str2 + " " + name + str4 + " " + date;
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("Напоминание")
                        .setContentText(str3)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_more)) // большая картинка

                .setStyle(new NotificationCompat.BigTextStyle().bigText(str3))
                .setAutoCancel(true); // автоматически закрыть уведомление после нажатия

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(MainActivity.this);
        notificationManager.notify(id, builder.build());
    }
    public void addi(View view) {
        Intent intent1 = new Intent(this, IncomeActivity.class);
        startActivity(intent1);
    }

    public void adds(View view) {
        Intent intent = new Intent(this, CostActivity.class);
        startActivity(intent);
    }

   /* public void doc(View view) {
        Intent intent1 = new Intent(this, DocumentActivity.class);
        startActivity(intent1);
    }*/
   public void doc(View view) {
       Intent intent1 = new Intent(this, DocumentPeriodActivity.class);
       startActivity(intent1);}

    public void statistics(View view) {
        Intent intent1 = new Intent(this, StatisticsActivity.class);
        startActivity(intent1);
    }
}
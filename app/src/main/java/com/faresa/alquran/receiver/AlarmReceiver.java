package com.faresa.alquran.receiver;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.faresa.alquran.MainActivity;
import com.faresa.alquran.R;
import com.faresa.alquran.db.AdzanHelper;
import com.faresa.alquran.db.QuranHelper;
import com.faresa.alquran.model.QuranModel;
import com.faresa.alquran.model.muslimsalat.Jadwal;
import com.faresa.alquran.model.muslimsalat.ResponseAdzan;
import com.faresa.alquran.preference.AppPreference;
import com.faresa.alquran.rest.ApiClient;
import com.faresa.alquran.rest.ApiService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

import static android.content.Context.ALARM_SERVICE;
import static com.faresa.alquran.DailyActivity.HALAMAN;
import static com.faresa.alquran.DailyActivity.HALF_JUZ_MESSAGE;
import static com.faresa.alquran.DailyActivity.JUZ_MESSAGE;
import static com.faresa.alquran.DailyActivity.SURAH_MESSAGE;

public class AlarmReceiver extends BroadcastReceiver {
    public static String CHANNEL_ID = "channel_01";
    public static CharSequence CHANNEL_NAME = "Notifikasi Adzan";
    public static String NOTIFICATION_ID = "notification-id";
    private static String PASSED_ID = "passed-id";
    private static String TIME_NAME = "time-name";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int id = intent.getIntExtra(NOTIFICATION_ID, 1);
        String[] time = intent.getStringArrayExtra(PASSED_ID);

        AppPreference preference = new AppPreference(context);
        QuranHelper quranHelper = new QuranHelper(context);
        QuranModel quranModel = quranHelper.getAllDataById(preference.getLastRead());
        String surah = quranHelper.getSurahById(quranModel.getSuratId()).getSurahText();
        String verse = String.valueOf(quranModel.getVerseId());
        Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + R.raw.adzan);

        if (id == 10) {
            Log.e("daily", "daily: received");

            Date date = Calendar.getInstance().getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = format.format(date);

            if (!formattedDate.equals(preference.getDataDate())) {
                loadData(context, preference);
            }

            if (!formattedDate.equals(preference.getDate())) {
                preference.setDate(formattedDate);
                preference.setAdzanTrigger(1);

                switch (preference.getDaily()) {
                    case "setengah juz":
                        preference.setDailyMessage(HALF_JUZ_MESSAGE);
                        break;
                    case "surah":
                        preference.setDailyMessage(SURAH_MESSAGE);
                        break;
                    case "juz":
                        preference.setDailyMessage(JUZ_MESSAGE);
                        break;
                    case "halaman":
                        preference.setDailyMessage(HALAMAN);
                        break;
                }
            }

        } else {
            if (preference.getFirstTrigger()) {
                preference.setAdzanTrigger(id);
                preference.setFirstTrigger(false);
            }

            Intent myIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                AudioAttributes attributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .build();

                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                channel.setSound(sound, attributes);

                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(channel);
                }
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher_new_round)
                    .setContentTitle("Terakhir dibaca: Q.S. " + surah + ": " + verse)
                    .setSound(sound)
                    .setContentText(preference.getDailyMessage())
                    .setSubText(intent.getStringExtra(TIME_NAME))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            int hour = Integer.parseInt(time[0]);
            int minute = Integer.parseInt(time[1]) + 2;

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);

            Log.e("notif", "notif: " + id);
            Log.e("trigger", "trigger: " + preference.getAdzanTrigger());
            if (preference.getAdzanTrigger() <= id) {
                preference.setAdzanTrigger(id + 1);

                if (calendar.after(Calendar.getInstance())) {
                    if (notificationManager != null) {
                        notificationManager.notify(id, builder.build());
                    }
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    public void registerNotification(Context context) throws ParseException {
        AdzanHelper adzanHelper = new AdzanHelper(context);
        AppPreference preference = new AppPreference(context);
        Jadwal model = adzanHelper.getAdzanDataById(preference.getAdzanId());

        Log.e("data", "dataget: " + model.getSubuh());

        SimpleDateFormat formatBefore = new SimpleDateFormat("h:mm a");
        SimpleDateFormat formatAfter = new SimpleDateFormat("HH:mm");

        Date before = formatBefore.parse(model.getSubuh());
        setNotification(context, formatAfter.format(before), "Adzan Subuh", 1);
        before = formatBefore.parse(model.getDzuhur());
        setNotification(context, formatAfter.format(before), "Adzan Dzuhur", 2);
        before = formatBefore.parse(model.getAshar());
        setNotification(context, formatAfter.format(before), "Adzan Ashar", 3);
        before = formatBefore.parse(model.getMaghrib());
        setNotification(context, formatAfter.format(before), "Adzan Maghrib", 4);
        before = formatBefore.parse(model.getIsya());
        setNotification(context, formatAfter.format(before), "Adzan Isya", 5);
        setNotification(context, "00:00", "Daily Reset", 10);

    }

    private void setNotification(Context context, String time, String timeName, int id) {
        String[] split = time.split(":");
        Log.e("waktu", "waktu: " + Arrays.toString(split));

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(split[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(split[1]));
        calendar.set(Calendar.SECOND, 0);

        Log.e("calendar", "calendar: " + id);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(NOTIFICATION_ID, id);
        intent.putExtra(PASSED_ID, split);
        intent.putExtra(TIME_NAME, timeName);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    @SuppressLint("SimpleDateFormat")
    private void loadData(Context context, AppPreference preference) {
        AppPreference appPreference = new AppPreference(context);
        AdzanHelper adzanHelper = new AdzanHelper(context);
        String city = appPreference.getCityId();
        String cityName = appPreference.getCity();

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = format.format(date);

        ApiService apiService = ApiClient.getClient(false).create(ApiService.class);
        Call<ResponseAdzan> callAdzan = apiService.getJadwalShalat(cityName);

        callAdzan.enqueue(new Callback<ResponseAdzan>() {
                    @Override
                    public void onResponse(Call<ResponseAdzan> call, retrofit2.Response<ResponseAdzan> response) {
                        if (response.body() != null) {
                            Log.e("empty", "msg: " + response.body().getItems().get(0).getTanggal());
                            Jadwal adzanModel = response.body().getItems().get(0);
                            Log.e("data", "dataload: " + adzanModel.getTanggal());
                            adzanHelper.insertAdzanData(adzanModel);
                            preference.setDataDate(formattedDate);

                            AlarmReceiver receiver = new AlarmReceiver();
                            try {
                                receiver.registerNotification(context);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseAdzan> call, Throwable t) {
                        Toast.makeText(context, "Pastikan koneksi internet aktif", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void daily_setcancel(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context,AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(),101,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Objects.requireNonNull(alarmManager).cancel(pendingIntent);

    }
}

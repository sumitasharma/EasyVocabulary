package com.example.sumitasharma.easyvocabulary.wordui;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.sumitasharma.easyvocabulary.R;
import com.example.sumitasharma.easyvocabulary.fragments.DictionaryFragment;
import com.example.sumitasharma.easyvocabulary.fragments.ProgressFragment;
import com.example.sumitasharma.easyvocabulary.fragments.WordMainFragment;
import com.example.sumitasharma.easyvocabulary.fragments.WordPracticeFragment;
import com.example.sumitasharma.easyvocabulary.services.NotificationPublisher;
import com.example.sumitasharma.easyvocabulary.services.WordDbPopulatorJobService;
import com.example.sumitasharma.easyvocabulary.util.NotificationHelper;
import com.example.sumitasharma.easyvocabulary.util.WordsDbUtil;
import com.facebook.stetho.Stetho;

import java.util.HashMap;

import timber.log.Timber;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.DICTIONARY_CARD_VIEW_IDENTIFIER;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.NOTIFICATION;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.NOTIFICATION_CHANNEL;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.NOTIFICATION_CHANNEL_NAME;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.NOTIFICATION_ID;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.PROGRESS_CARD_VIEW_IDENTIFIER;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.QUIZ_CARD_VIEW_IDENTIFIER;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.WORD_MEANING_CARD_VIEW_IDENTIFIER;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.isOnline;

public class MainActivity extends AppCompatActivity implements WordMainFragment.PassCardViewInformation {
    public static final String TAG = MainActivity.class.getSimpleName();
    HashMap<Long, Boolean> mUserAnswer = new HashMap<>();
    HashMap<String, String> mCorrectAnswers = new HashMap<>();
    long interval;

    private int numberOfWordsForPractice;
    private String frequencyOfWordsForPractice;
    private String levelOfWordsForPractice;
    private boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Initialize stetho
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());

        Log.i(TAG, "Inside onCreate");
        setContentView(R.layout.activity_main);

        /* Delete the table */
        //  Uri loaderUri = WordContract.WordsEntry.CONTENT_URI;
        //  getContentResolver().delete(loaderUri,null,null);
        /* Insert the table */
        //new WordsDbUtil(this).readWordsFromAssets();
        WordsDbUtil wordsDbUtil = new WordsDbUtil(this);
        if (!wordsDbUtil.isDatabasePopulated()) {
            Timber.i("Database is not populated, populating it");
            wordsDbUtil.populateDatabase();
        }

        setupSharedPreference();


        // this.sendBroadcast(new Intent("android.intent.action.BOOT_COMPLETED"));
        Bundle bundle = this.getIntent().getExtras();

        //Checking if it is a tablet
        mTwoPane = findViewById(R.id.words_tablet_linear_layout) != null;

        WordMainFragment wordMainFragment = new WordMainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.word_main_fragment, wordMainFragment).commit();


        if (mTwoPane) {
            WordPracticeFragment wordPracticeFragment = new WordPracticeFragment();
            fragmentManager = getSupportFragmentManager();
            // Add the fragment to its container using a FragmentManager and a Transaction
            fragmentManager.beginTransaction().replace(R.id.word_main_choice_fragment, wordPracticeFragment).commit();
        }
        //Schedule job to populate Database in the background
        scheduleDbPopulatorJob(this);

        //Schedule Notification via alarm manager
        scheduleNotificationAlarm(this);

    }


    private void setupSharedPreference() {
        Log.i(TAG, "Inside setupSharedPreference");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        numberOfWordsForPractice = Integer.parseInt(sharedPreferences.getString(getResources().getString(R.string.number_of_words_key), "4"));
        frequencyOfWordsForPractice = (sharedPreferences.getString(getResources().getString(R.string.frequency_of_words_key), "Daily"));
        levelOfWordsForPractice = (sharedPreferences.getString(getResources().getString(R.string.level_of_words_for_practice_key), "Easy"));
        Log.i(TAG, "Inside setupSharedPreference after getDefaultSharedPreferences");
        Log.i(TAG, "Inside setupSharedPreference" + numberOfWordsForPractice + frequencyOfWordsForPractice + levelOfWordsForPractice);

    }

    // schedule the start of the service every 10 - 30 seconds
    private void scheduleDbPopulatorJob(Context context) {
        Timber.i("Inside scheduleJob");
        //Set job scheduling based on user preference
        ComponentName serviceComponent = new ComponentName(context, WordDbPopulatorJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(1234, serviceComponent)
                .setMinimumLatency(1 * 60 * 60 * 1000) // wait at least
                .setOverrideDeadline(24 * 60 * 60 * 1000) // maximum delay
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build();
        JobScheduler jobService = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobService.schedule(jobInfo);
    }

    private void scheduleNotificationAlarm(Context context) {

        //Start the Word Notification Service

        switch (frequencyOfWordsForPractice) {
            case "Daily":
                scheduleNotification(getNotification(numberOfWordsForPractice), 60 * 1000);
                break;
            case "Weekly":
                scheduleNotification(getNotification(numberOfWordsForPractice), 7 * 24 * 60 * 60 * 1000);
                break;
            case "Monthly":
                scheduleNotification(getNotification(numberOfWordsForPractice), 7 * 24 * 60 * 60 * 1000);
                break;
            default:
                scheduleNotification(getNotification(numberOfWordsForPractice), 24 * 60 * 60 * 1000);
        }
    }

    private void scheduleNotification(Notification notification, long delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(int notification) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL);

        Intent intentToRepeat = new Intent(this, MainActivity.class);
        //set flag to restart/relaunch the app
        intentToRepeat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //Pending intent to handle launch of Activity in intent above
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, NotificationHelper.ALARM_TYPE_RTC, intentToRepeat, PendingIntent.FLAG_UPDATE_CURRENT);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder.setChannelId(NOTIFICATION_CHANNEL);
        }
        //Notification Channel
        CharSequence channelName = NOTIFICATION_CHANNEL_NAME;
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL, NOTIFICATION_CHANNEL_NAME, importance);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});


        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);


        builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.notification_text) + " Your " + notification + " words are ready.")
                .setSmallIcon(R.drawable.books)
                .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                .setSound(null)
                .setChannelId(NOTIFICATION_CHANNEL)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false);


//        builder.setContentTitle(getString(R.string.app_name));
//        builder.setContentText(getString(R.string.notification_text)+" Your "+ notification + " words are ready.");
//        builder.setSmallIcon(R.drawable.easyvocabulary);
//        builder.setChannelId("easyVocabChannel");
        return builder.build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu, menu);
        Log.i(TAG, "Inside onCreateOptionsMenu");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "Inside onOptionsItemSelected");
        int id = item.getItemId();

        if (id == R.id.settings) {
            Intent intentMenuSetting = new Intent(this, PreferenceActivity.class);
            Log.i(TAG, "Intent started");
            startActivity(intentMenuSetting);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onResume() {
        super.onResume();
        if (!isOnline(this)) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.word_main_fragment), R.string.internet_connectivity,
                    Snackbar.LENGTH_SHORT);
            snackbar.show();
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(Color.BLUE);
            //Toast.makeText(mContext, "Kindly check your Internet Connectivity", Toast.LENGTH_LONG).show();
            return;
        }
    }

    @Override
    public void cardViewInformation(String cardViewNumber) {
        if (mTwoPane) { // Handle Tablet devices
            FragmentManager fragmentManager = getSupportFragmentManager();
            WordPracticeFragment wordPracticeFragment = new WordPracticeFragment();
            switch (cardViewNumber) {
                case WORD_MEANING_CARD_VIEW_IDENTIFIER:

                    // FragmentManager fragmentManager = getSupportFragmentManager();
                    // Add the fragment to its container using a FragmentManager and a Transaction
                    fragmentManager.beginTransaction().replace(R.id.word_main_choice_fragment, wordPracticeFragment).commit();
                    break;
                case QUIZ_CARD_VIEW_IDENTIFIER:
//                    WordQuizFragment wordQuizFragment = new WordQuizFragment();
//                    // FragmentManager fragmentManager = getSupportFragmentManager();
//                    // Add the fragment to its container using a FragmentManager and a Transaction
//                    fragmentManager.beginTransaction().replace(R.id.word_main_choice_fragment, wordQuizFragment).commit();
                    Intent intent = new Intent();
                    intent.setClass(this, WordQuizPracticeActivity.class);
                    Timber.i("Inside WordQuizPractice selected");
                    startActivity(intent);
                    break;
                case PROGRESS_CARD_VIEW_IDENTIFIER:
                    ProgressFragment progressFragment = new ProgressFragment();
                    // FragmentManager fragmentManager = getSupportFragmentManager();
                    // Add the fragment to its container using a FragmentManager and a Transaction
                    Timber.i("Entering Progress Fragment");
                    fragmentManager.beginTransaction().replace(R.id.word_main_choice_fragment, progressFragment).commit();
                    break;
                case DICTIONARY_CARD_VIEW_IDENTIFIER:
                    DictionaryFragment dictionaryFragment = new DictionaryFragment();
                    fragmentManager.beginTransaction().replace(R.id.word_main_choice_fragment, dictionaryFragment).commit();
                    break;
                default:
                    fragmentManager.beginTransaction().replace(R.id.word_main_choice_fragment, wordPracticeFragment).commit();
                    break;

            }
        } else {  // Handle mobile devices
            if (cardViewNumber.equals(WORD_MEANING_CARD_VIEW_IDENTIFIER)) {
                Intent intent = new Intent();
                intent.setClass(this, PracticeWordsActivity.class);
                startActivity(intent);
            } else if (cardViewNumber.equals(QUIZ_CARD_VIEW_IDENTIFIER)) {
                Intent intent = new Intent();
                intent.setClass(this, WordQuizPracticeActivity.class);
                Timber.i("Inside WordQuizPractice selected");
                startActivity(intent);
            } else if (cardViewNumber.equals(PROGRESS_CARD_VIEW_IDENTIFIER)) {
                Intent intent = new Intent();
                intent.setClass(this, ProgressActivity.class);
                startActivity(intent);
            } else if (cardViewNumber.equals(DICTIONARY_CARD_VIEW_IDENTIFIER)) {
                Intent intent = new Intent();
                intent.setClass(this, DictionaryActivity.class);
                startActivity(intent);
            }

        }
    }

}

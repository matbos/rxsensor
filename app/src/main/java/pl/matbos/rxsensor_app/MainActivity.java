package pl.matbos.rxsensor_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import pl.matbos.rxsensor.RxSensor;
import pl.matbos.rxsensor.data.EventData;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    private RxSensor sensorManager;
    private int i;
    private CompositeSubscription subscription;
    private Observable<EventData> accelerometerObservable;
    private Observable<EventData> gyroscopeObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = new RxSensor(this);
        findViewById(R.id.btn).setOnClickListener((view) -> {
            subscription.unsubscribe();
            subscription = new CompositeSubscription();
        });
        findViewById(R.id.btn2).setOnClickListener((view) -> resubscribe(++i));
        findViewById(R.id.btn3).setOnClickListener((view) -> subscribeRe());
    }

    private void subscribeRe() {
        final CompositeSubscription cs = new CompositeSubscription();
        cs.add(accelerometerObservable.subscribe(ed -> {
            Log.d("GATT_ACCE_CS", "  " + ed.xValue + " " + ed.yValue + " " + ed.xValue + " " + ed.accuracy + " " + ed.timestamp);
        }));
        cs.add(gyroscopeObservable.subscribe(ed -> {
            Log.d("GATT_GYRO_CS", "  " + ed.xValue + " " + ed.yValue + " " + ed.xValue + " " + ed.accuracy + " " + ed.timestamp);
        }));

        Observable.just(1).delay(3, TimeUnit.SECONDS).subscribe(t -> cs.unsubscribe());
    }

    @Override
    protected void onPause() {
        super.onPause();
        subscription.unsubscribe();
    }

    @Override
    protected void onResume() {
        super.onResume();
        i = 0;
        subscription = new CompositeSubscription();
        accelerometerObservable = sensorManager.getAccelerometerObservable(10);
        gyroscopeObservable = sensorManager.getGyroscopeObservable(10);
        resubscribe(i);
    }

    private void resubscribe(int t) {
        subscription.add(accelerometerObservable.subscribe(ed -> {
            Log.d("GATT_ACCE", t + "  " + ed.xValue + " " + ed.yValue + " " + ed.xValue + " " + ed.accuracy + " " + ed.timestamp);
        }));
        subscription.add(gyroscopeObservable.subscribe(ed -> {
            Log.d("GATT_GYRO", t + "  " + ed.xValue + " " + ed.yValue + " " + ed.xValue + " " + ed.accuracy + " " + ed.timestamp);
        }));
    }
}

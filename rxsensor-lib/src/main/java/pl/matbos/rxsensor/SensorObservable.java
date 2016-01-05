package pl.matbos.rxsensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.util.Log;

import pl.matbos.rxsensor.data.EventData;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Smart sensor listener, registers itself for sensor updates after first observer connects
 * and unregisters itself from updates after the last one disconnects.
 *
 * @author Mateusz Boś
 */
public class SensorObservable implements SensorEventListener {

    private final Observable<EventData> eventDataObservable;
    private PublishSubject<EventData> subject = PublishSubject.create();

    public SensorObservable(final int type, final int samplingPeriod, final SensorManager sensorManager) {
        eventDataObservable = subject.asObservable()
                                     .doOnSubscribe(() -> {
                                         Log.d("GATT_SUB", "Subscribing for sensor updates");
                                         sensorManager.registerListener(this, sensorManager.getDefaultSensor(type), samplingPeriod);
                                     })

                                     .doOnUnsubscribe(() -> {
                                         Log.d("GATT_SUB_UN", "Unsubscribed from sensor updates");
                                         sensorManager.unregisterListener(this);
                                     })
                                     .share();
    }

    public Observable<EventData> getObservable() {
        return eventDataObservable;
    }

    @Override
    public void onSensorChanged(@NonNull SensorEvent event) {
        subject.onNext(new EventData(event.values[0], event.values[1], event.values[2], event.accuracy, event.timestamp));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

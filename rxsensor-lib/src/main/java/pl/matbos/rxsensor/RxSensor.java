package pl.matbos.rxsensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import pl.matbos.rxsensor.data.EventData;
import rx.Observable;

/**
 * Provides means of retrieving observables for required sensor.
 *
 * @author Mateusz Boś
 */
public class RxSensor {

    private final SensorManager sensorManager;

    public RxSensor(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    /**
     * @param type           one of {@link android.hardware.Sensor}.TYPE_ constants
     * @param samplingPeriod sampling period used by this observable
     * @return observable
     */
    public Observable<EventData> getSensorObservable(int type, int samplingPeriod) {
        return new SensorObservable(type, samplingPeriod, sensorManager).getObservable();
    }

    /**
     * @param samplingPeriod sampling period used by this observable
     * @return observable
     */
    public Observable<EventData> getAccelerometerObservable(int samplingPeriod) {
        return getSensorObservable(Sensor.TYPE_ACCELEROMETER, samplingPeriod);
    }

    /**
     * @param samplingPeriod sampling period used by this observable
     * @return observable
     */
    public Observable<EventData> getGyroscopeObservable(int samplingPeriod) {
        return getSensorObservable(Sensor.TYPE_GYROSCOPE, samplingPeriod);
    }
}

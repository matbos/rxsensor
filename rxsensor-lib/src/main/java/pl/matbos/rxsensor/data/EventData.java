package pl.matbos.rxsensor.data;

/**
 * Simply passes data retrieved from sensors
 *
 * @author Mateusz Boś
 */
public class EventData {

    public final float xValue;
    public final float yValue;
    public final float zValue;

    public final int accuracy;
    public final long timestamp;

    public EventData(float xValue, float yValue, float zValue, int accuracy, long timestamp) {
        this.xValue = xValue;
        this.yValue = yValue;
        this.zValue = zValue;
        this.accuracy = accuracy;
        this.timestamp = timestamp;
    }
}
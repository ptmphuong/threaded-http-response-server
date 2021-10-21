package view;

import api.RequestTimeList;

import java.time.Duration;
import java.time.LocalDateTime;

public class ApiCallStats {
    private int inLastMin = 0;
    private int inLastHr = 0;
    private int inLast24Hr = 0;
    private int inLifetime = 0;

    public ApiCallStats(RequestTimeList timeList, LocalDateTime timeNow) {
        this.setCount(timeList, timeNow);
    }

    private void setCount(RequestTimeList timeList, LocalDateTime timeNow) {
        for (LocalDateTime requestTime: timeList.get()) {
            Duration duration = Duration.between(requestTime, timeNow);
            long min = duration.toMinutes();
            this.inLifetime++;
            if (min < 60*24) this.inLast24Hr++;
            if (min < 60) this.inLastHr++;
            if (min < 1) this.inLastMin++;
        }
    }

    public int getInLastMin() {
        return inLastMin;
    }

    public int getInLastHr() {
        return inLastHr;
    }

    public int getInLast24Hr() {
        return inLast24Hr;
    }

    public int getInLifetime() {
        return inLifetime;
    }

    @Override
    public String toString() {
        return "view.ApiCallNum{" +
                "inLastMin=" + inLastMin +
                ", inLastHr=" + inLastHr +
                ", inLast24Hr=" + inLast24Hr +
                ", inLifetime=" + inLifetime +
                '}';
    }

}

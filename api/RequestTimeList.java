package api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RequestTimeList {
    List<LocalDateTime> ldtList = new ArrayList<>();

    public RequestTimeList() {
    }

    public List<LocalDateTime> get() {
        return this.ldtList;
    }

    public synchronized void add(LocalDateTime ldt) {
        this.ldtList.add(ldt);
    }
}

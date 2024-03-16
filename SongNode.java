package player;

import java.io.File;

public class SongNode {
    File data;
    SongNode next;
    SongNode previous;

    public SongNode(File data) {
        this.data = data;
        this.next = null;
        this.previous = null;
    }
}

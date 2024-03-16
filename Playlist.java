package player;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFrame;
import javax.swing.JFileChooser;

public class Playlist {
    
    JFileChooser fc = new JFileChooser();
    SongNode head;

    void add(JFrame frame) {
        fc.setMultiSelectionEnabled(true);
        int fileValid = fc.showOpenDialog(frame);

        if (fileValid == javax.swing.JFileChooser.CANCEL_OPTION) {
            return;

        } else if (fileValid == javax.swing.JFileChooser.APPROVE_OPTION) {
            File[] files = fc.getSelectedFiles();
            for (File file : files) {
                addSong(file);
            }
        }
    }

    private void addSong(File file) {
        SongNode newNode = new SongNode(file);
        if (head == null) {
            head = newNode;
        } else {
            SongNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
            newNode.previous = current;
        }
    }

    SongNode getListSong() {
        return head;
    }

    // save playlist

    public void saveAsPlaylist(JFrame frame) {
        fc.setMultiSelectionEnabled(false);

        int Valid = fc.showSaveDialog(frame);

        if (Valid == javax.swing.JFileChooser.CANCEL_OPTION) {
            return;

        } else if (Valid == javax.swing.JFileChooser.APPROVE_OPTION) {
            File pls = fc.getSelectedFile();

            try (FileOutputStream fos = new FileOutputStream(pls + ".tgr");
                    ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                SongNode current = head;
                while (current != null) {
                    oos.writeObject(current.data);
                    current = current.next;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void openPls(JFrame frame) {
        fc.setMultiSelectionEnabled(false);
        int Valid = fc.showOpenDialog(frame);

        if (Valid == javax.swing.JFileChooser.CANCEL_OPTION) {
            return;
        } else {
            File pls = fc.getSelectedFile();

            try (FileInputStream fis = new FileInputStream(pls);
                    ObjectInputStream ois = new ObjectInputStream(fis)) {
                head = null;
                Object tmp;
                while ((tmp = ois.readObject()) != null) {
                    if (tmp instanceof File) {
                        addSong((File) tmp);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

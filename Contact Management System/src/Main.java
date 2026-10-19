
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            ContactGUI application =
                    new ContactGUI();

            application.setVisible(true);
        });
    }
}
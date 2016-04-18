import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

public class TimestampCellRenderer extends DefaultTableCellRenderer {

    DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    
    public TimestampCellRenderer() {
        super();
        this.setHorizontalAlignment(JLabel.CENTER);
    }

    public void setValue(Object value) {
        if (formatter == null) {
            formatter = DateFormat.getDateInstance();
        }
        setText((value == null) ? "" : formatter.format(value));
    }
}
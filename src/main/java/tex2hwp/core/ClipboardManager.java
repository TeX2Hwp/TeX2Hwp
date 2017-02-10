package tex2hwp.core;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

public class ClipboardManager {
    private Clipboard clipboard;

    public ClipboardManager() {
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    public void copyString(String string) {
        StringSelection contents = new StringSelection(string);
        clipboard.setContents(contents, null);
    }

    public String readString() throws IOException, UnsupportedFlavorException {
        Transferable contents = clipboard.getContents(clipboard);

        if (contents == null) {
            return null;
        } else {
            return (String) contents.getTransferData(DataFlavor.stringFlavor);
        }
    }
}

package tex2hwp.core;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

/**
 * Small class for managing the clipboard.
 */
public class ClipboardManager {
    private Clipboard clipboard;

    public ClipboardManager() {
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    /**
     * Add a string on the clipboard.
     *
     * @param string string to copy
     */
    public void copyString(String string) {
        StringSelection contents = new StringSelection(string);
        clipboard.setContents(contents, null);
    }

    /**
     * Read a string from the clipboard.
     *
     * @return result string
     * @throws IOException                if the data is no longer available
     * @throws UnsupportedFlavorException if the data flavor is not supported
     */
    public String readString() throws IOException, UnsupportedFlavorException {
        Transferable contents = clipboard.getContents(clipboard);

        if (contents == null) {
            return null;
        } else {
            return (String) contents.getTransferData(DataFlavor.stringFlavor);
        }
    }
}

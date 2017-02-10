package tex2hwp.ui;

import net.sourceforge.jeuclid.swing.JMathComponent;
import tex2hwp.core.ClipboardManager;
import tex2hwp.core.HwpConverter;
import tex2hwp.core.TeXParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;

public class MainFrame extends JFrame {
    ClipboardManager clipboardManager;
    TeXParser parser;
    HwpConverter converter;

    JMathComponent mathRenderer;
    JTextArea mlText;
    JTextArea hwpText;
    JTextArea inputField;
    JButton convertButton;
    JButton copyButton;

    ControlListener controlListener;

    Dimension mathRendererSize = new Dimension(200, 200);
    Dimension resultTextSize = new Dimension(200, 100);
    Dimension inputFieldSize = new Dimension(400, 50);
    Dimension buttonSize = new Dimension(200, 30);
    Dimension dialogTextSize = new Dimension(300, 200);


    public MainFrame(String title) {
        super(title);

        initUI();
        initCore();
        initCallbacks();
    }

    private void initUI() {
        // init panels
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(1, 2));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(2, 1));

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 2));

        // init widgets
        mathRenderer = new JMathComponent();

        JScrollPane mathScroll = new JScrollPane(mathRenderer);
        mathScroll.setPreferredSize(mathRendererSize);
        mathScroll.setMinimumSize(mathRendererSize);

        mlText = new JTextArea("Math ML이 표시됩니다.");
        mlText.setLineWrap(true);
        mlText.setEditable(false);

        JScrollPane mlScroll = new JScrollPane(mlText);
        mlScroll.setPreferredSize(resultTextSize);
        mlScroll.setMinimumSize(resultTextSize);

        hwpText = new JTextArea("Hwp 수식이 표시됩니다.");
        hwpText.setLineWrap(true);
        hwpText.setEditable(false);

        JScrollPane hwpScroll = new JScrollPane(hwpText);
        hwpScroll.setPreferredSize(resultTextSize);
        hwpScroll.setMinimumSize(resultTextSize);

        inputField = new JTextArea("\\int_{0}^{\\infty} e^{-x^2} dx = \\frac{\\sqrt{\\pi}}{2}");

        JScrollPane inputScroll = new JScrollPane(inputField);
        inputScroll.setPreferredSize(inputFieldSize);
        inputScroll.setMinimumSize(inputFieldSize);

        convertButton = new JButton("Convert");
        convertButton.setPreferredSize(buttonSize);
        convertButton.setMinimumSize(buttonSize);

        copyButton = new JButton("Copy");
        copyButton.setPreferredSize(buttonSize);
        copyButton.setMinimumSize(buttonSize);

        // set textPanel
        textPanel.add(mlScroll);
        textPanel.add(hwpScroll);

        // set resultPanel
        resultPanel.add(mathScroll);
        resultPanel.add(textPanel);

        // set controlPanel
        controlPanel.add(convertButton);
        controlPanel.add(copyButton);

        // set mainPanel
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        mainPanel.add(resultPanel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        mainPanel.add(inputScroll, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        mainPanel.add(controlPanel, c);

        getContentPane().add(mainPanel);
    }

    private void initCore() {
        clipboardManager = new ClipboardManager();
        parser = new TeXParser();
        converter = new HwpConverter();
    }

    private void initCallbacks() {
        convertButton.setActionCommand("convert");
        copyButton.setActionCommand("copy");

        controlListener = new ControlListener();
        convertButton.addActionListener(controlListener);
        copyButton.addActionListener(controlListener);
    }

    private void onConvertButtonClicked() {
        try {
            // read input
            String tex = inputField.getText();

            // parse tex -> generate ml
            parser.parse("\\[" + tex + "\\]");

            // parse ml -> generate hwp
            String hwp = converter.convertNode(parser.getMlDom());

            // show the results
            mathRenderer.setDocument(parser.getMlDom());
            mlText.setText(parser.getMlString());
            hwpText.setText(hwp);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            popMsg("에러가 발생했습니다.\n\n- 에러 메시지 :\n" + e.getMessage() +
                    "\n\n- 스택 추적 :\n" + sw.toString());
        }
    }

    private void onCopyButtonClicked() {
        String hwp = hwpText.getText();
        clipboardManager.copyString(hwp);
        popMsg("Hwp 수식이 클립보드에 복사되었습니다.");
    }

    private void popMsg(String msg) {
        JTextArea msgText = new JTextArea(msg);
        msgText.setEditable(false);

        JScrollPane msgScroll = new JScrollPane(msgText);
        msgScroll.setMinimumSize(dialogTextSize);
        msgScroll.setPreferredSize(dialogTextSize);

        JOptionPane.showMessageDialog(null, msgScroll);
    }

    private class ControlListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "convert":
                    onConvertButtonClicked();
                    break;
                case "copy":
                    onCopyButtonClicked();
                    break;
            }
        }
    }
}

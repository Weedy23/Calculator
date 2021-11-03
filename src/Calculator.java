import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

public class Calculator extends javax.swing.JFrame {

    static JFrame jWindow = new JFrame("Calculator");

    private javax.swing.JLabel jLabel;

    private String Action = null;
    private int NUM1 = 0;
    private int pointPosition1 = 0;
    private int symbol1 = 1;
    private int NUM2 = 0;
    private int pointPosition2 = 0;
    private int symbol2 = 1;

    public Calculator() {
        init();
    }

    public void init() {
        jWindow.setSize(350, 439);
        jWindow.setLayout(null);
        jWindow.setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel jPanelDigits = new JPanel();
        jPanelDigits.setLayout(null);
        jPanelDigits.setBounds(0, 135, 200, 265);
        jPanelDigits.setBackground(Color.LIGHT_GRAY);

        JButton[] jButtonsDigits = new JButton[11];
        for (int i = 0; i < 10; i++) {
            jButtonsDigits[i] = new JButton();
            if (i == 0) {
                jButtonsDigits[0].setText("0");
                jButtonsDigits[0].setBounds(5, 200, 125, 60);
            } else if (i < 4) {
                jButtonsDigits[i].setText(i + "");
                jButtonsDigits[i].setBounds((i - 1)*65 + 5, 135, 60, 60 );
            } else if (i < 7) {
                jButtonsDigits[i].setText(i + "");
                jButtonsDigits[i].setBounds((i - 4)*65 + 5, 70, 60, 60 );

            } else {
                jButtonsDigits[i].setText(i + "");
                jButtonsDigits[i].setBounds((i - 7)*65 + 5, 5, 60, 60 );
            }
            jPanelDigits.add(jButtonsDigits[i]);
            int finalI = i;
            jButtonsDigits[i].addActionListener(evt -> jButtonsDigitsActionPerformed(finalI));
        }

        jButtonsDigits[10] = new JButton();
        jButtonsDigits[10].setText(".");
        jButtonsDigits[10].setBounds(135, 200, 60, 60);
        jPanelDigits.add(jButtonsDigits[10]);
        jButtonsDigits[10].addActionListener(evt -> jButtonsPointActionPerformed());

        jWindow.add(jPanelDigits);

        JPanel jPanelActions = new JPanel();
        jPanelActions.setLayout(null);
        jPanelActions.setBounds(200, 70, 135, 330);
        jPanelActions.setBackground(Color.DARK_GRAY);

        JButton[] jButtonsActions = new JButton[8];
        String[] actions = {"/", "*", "+", "√", "^", "-", "=", "-num"};
        for (int i = 0; i < 8; i++) {
            jButtonsActions[i] = new JButton();
            if (i < 3) {
                jButtonsActions[i].setBounds(5, i * 65 + 70, 60, 60);
            } else if (i < 6) {
                jButtonsActions[i].setBounds(70, (i - 3)* 65 + 70, 60, 60);
            } else if (i < 7) {
                jButtonsActions[i].setBounds(5, 265, 125, 60);
            } else {
                jButtonsActions[i].setBounds(5, 5, 125, 60);
            }
            jButtonsActions[i].setText(actions[i]);
            jPanelActions.add(jButtonsActions[i]);
            int finalI = i;
            jButtonsActions[i].addActionListener(evt -> jButtonsActionsActionPerformed(actions[finalI]));
        }

        jWindow.add(jPanelActions);

        JPanel jPanelDelete = new JPanel();
        jPanelDelete.setLayout(null);
        jPanelDelete.setBounds(0, 70, 200, 70);
        jPanelDelete.setBackground(Color.LIGHT_GRAY);

        JButton[] jButtonsDelete = new JButton[2];
        String[] deletes = {"Clear", "BackSpace"};
        for (int i  = 0; i < 2; i++) {
            jButtonsDelete[i] = new JButton();
            if (i == 0) {
                jButtonsDelete[i].setBounds(5, 5, 60, 60);
            } else {
                jButtonsDelete[i].setBounds(70, 5, 125, 60);
            }
            jButtonsDelete[i].setText(deletes[i]);
            jPanelDelete.add(jButtonsDelete[i]);
            int finalI = i;
            jButtonsDelete[i].addActionListener(evt -> jButtonsDeleteActionPerformed(deletes[finalI]));
        }

        jWindow.add(jPanelDelete);

        JPanel jPanelLabel = new JPanel();
        jPanelLabel.setLayout(null);
        jPanelLabel.setBounds(0, 0, 335, 70);
        jPanelLabel.setBackground(Color.GRAY);

        jLabel = new JLabel();
        jLabel.setBounds(35, 5, 265, 60);
        jLabel.setFont(new Font("", Font.BOLD, 45));
        jLabel.setText(text(NUM1));

        jPanelLabel.add(jLabel);

        jWindow.add(jPanelLabel);
    }

    private void jButtonsDigitsActionPerformed(int num) {
        if (isNum1()) {
            NUM1 = parseInt(String.format("%d%d", NUM1, num));
            setTextInLabel(NUM1);
        } else {
            NUM2 = parseInt(String.format("%d%d", NUM2, num));
            setTextInLabel(NUM2);
        }
    }

    private boolean isNum1() {
        return Action == null;
    }

    private void jButtonsPointActionPerformed() {
        if (!havePoint()) {
            if (isNum1()) {
                pointPosition1 = (NUM1 + "").length();
            } else {
                pointPosition2 = (NUM2 + "").length();
            }
        }
    }

    private void jButtonsActionsActionPerformed(String action) {
        if (Action == null || action.equals("-num") || action.equals("√")) {
            if (Action == null) {
                Action = action;
                setTextInLabel(0);
            }
            switch (action) {
                case "=" -> {
                    setTextInLabel(NUM1);
                    Action = null;
                    setTextInLabel(NUM1);
                }
                case "-num" -> {
                    changeSymbol();
                    changeNumSymbol();
                    setTextInLabel(NUM1);
                }
                case "√" -> {
                    NUM1 = (int) Math.sqrt(NUM1);
                    Action = null;
                    setTextInLabel(NUM1);
                }
            }
        } else {
            switch (Action) {
                case "+" -> NUM1 += NUM2;
                case "-" -> NUM1 -= NUM2;
                case "*" -> NUM1 *= NUM2;
                case "/" -> NUM1 /= NUM2;
                case "^" -> NUM1 = (int) Math.pow(NUM1, NUM2);
            }
            NUM2 = 0;
            if (action.equals("=")) {
                Action = null;
            } else {
                Action = action;
            }
            setTextInLabel(NUM1);
        }
    }

    private void changeSymbol() {
        if (isNum1()) {
            symbol1 = -symbol1;
        } else {
            symbol2 = -symbol2;
        }
    }

    private void changeNumSymbol() {
        if (isNum1()) {
            NUM1 *= symbol1;
        } else {
            NUM2 *= symbol2;
        }
    }

    private void jButtonsDeleteActionPerformed(String delete) {
        switch (delete) {
            case "Clear":
                clearData();
                setTextInLabel(NUM1);
            case "BackSpace":
                backSpaceNum();
                if (isNum1()) {
                    setTextInLabel(NUM1);
                } else {
                    setTextInLabel(NUM2);
                }
        }
    }

    private void clearData() {
        Action = null;
        NUM1 = 0;
        pointPosition1 = 0;
        symbol1 = 1;
        NUM2 = 0;
        pointPosition2 = 0;
        symbol2 = 1;
    }

    private void backSpaceNum() {
        if (isNum1()) {
            NUM1 /= 10;
            if (havePoint()) {
                pointPosition1 -= 1;
            }
        } else {
            NUM2 /= 10;
            if (havePoint()) {
                pointPosition2 -= 1;
            }
        }
    }

    private boolean havePoint() {
        if (isNum1()) {
            return pointPosition1 > 0;
        }
        return pointPosition2 > 0;
    }

    private void setTextInLabel(int num) {
        jLabel.setText(text(num));
    }

    private String text(int num) {
        int len = Integer.toString(num).length();
        if (len < 10) {
            String zeros = "0".repeat(9 - len);
            String symbol;
            if (isNum1()) {
                symbol = convertSymbolToString(symbol1);
                if (!havePoint()) {
                    return symbol + zeros + num;
                } else {
                    return symbol + zeros + ((double) num) / parseInt("1" + "0".repeat(len - pointPosition1));
                }
            } else {
                symbol = convertSymbolToString(symbol2);
                if (!havePoint()) {
                    return symbol + zeros + num;
                } else {
                    return symbol + zeros + ((double) num) / parseInt("1" + "0".repeat(len - pointPosition2));
                }
            }
        }
        return null;
    }

    private String convertSymbolToString(int sym) {
        if (sym == 1) {
            return "+";
        }
        return "-";
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Calculator.class.getName()).log(Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(Calculator::new);
    }
}

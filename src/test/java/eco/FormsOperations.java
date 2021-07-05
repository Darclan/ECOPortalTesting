package eco;

import javax.swing.*;

public class FormsOperations {
    public static String[] modifyDataBasedOnFormInput(String[] credentialsTable)
    {
        JTextField tfUrl = new JTextField();
        JTextField tfUsername = new JTextField();
        JTextField tfPassword = new JPasswordField();
        JTextField tfLanguage = new JTextField();
        JTextField tfStp = new JTextField();

        //TODO: get data from the file to fill the fields
        tfUrl.setText(credentialsTable[0]);
        tfUsername.setText(credentialsTable[1]);
        tfPassword.setText(credentialsTable[2]);
        tfLanguage.setText(credentialsTable[3]);
        tfStp.setText(credentialsTable[4]);

        Object[] message = {
                "Url: ", tfUrl,
                "Username", tfUsername,
                "Password", tfPassword,
                "Language", tfLanguage,
                "Sold To Party ID", tfStp
        };
        int option = JOptionPane.showConfirmDialog(null,
                message,
                "Provide url, user, password, language (optional), STP (optional)",
                JOptionPane.OK_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            credentialsTable[0] = tfUrl.getText();
            credentialsTable[1] = tfUsername.getText();
            credentialsTable[2] = tfPassword.getText();
            credentialsTable[3] = tfLanguage.getText();
            credentialsTable[4] = tfStp.getText();
        }
        return credentialsTable;
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class gui {
    static Town[] italianTowns= new Town[8092];

    public static void setTowns(){
        try {
            int i=0;
            Scanner scanner=new Scanner(new File("listacomuni.txt"));
            while(scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(";");
                italianTowns[i++]=new Town(data[0],data[1],data[2],data[3],data[4],data[5],data[6],Integer.parseInt(data[7]));
            }
        }catch(IOException e) {System.out.println(e);}
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame("Fiscal code computation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        JPanel panel1 = new JPanel();
        JPanel panel = new JPanel();
        GridLayout CFLayout = new GridLayout(0, 2);
        panel.setLayout(CFLayout);
        JLabel lCognome = new JLabel("Cognome");
        JTextField tCognome = new JTextField(20);
        panel.add(lCognome);
        panel.add(tCognome);
        JLabel lNome = new JLabel("Nome");
        JTextField tNome = new JTextField(20);
        panel.add(lNome);
        panel.add(tNome);
        JLabel lDataN = new JLabel("Data di Nascita (GG/MM/AAAA)");
        JTextField tDataN = new JTextField(10);
        panel.add(lDataN);
        panel.add(tDataN);
        JLabel lLuogo = new JLabel("Luogo di Nascita");
        JTextField tLuogo = new JTextField(20);
        panel.add(lLuogo);
        panel.add(tLuogo);
        JLabel lProvincia = new JLabel("Provincia:");
        JTextField tProvincia = new JTextField(20);
        panel.add(lProvincia);
        panel.add(tProvincia);
        JLabel lSesso = new JLabel("Sesso Femminile:");
        JCheckBox CSesso = new JCheckBox();
        panel.add(lSesso);
        panel.add(CSesso);
        JButton calcola = new JButton("Calcola");
        JButton cancella = new JButton("Cancella");
        panel.add(calcola);
        panel.add(cancella);
        JTextField tCF = new JTextField(16);
        JLabel lCF = new JLabel("CODICE FISCALE:");
        FlowLayout Flow = new FlowLayout();
        panel1.setLayout(Flow);
        panel1.add(lCF);
        panel1.add(tCF);

        calcola.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTowns();
                String data=tCognome.getText().toUpperCase()+";"+tNome.getText().toUpperCase()+";"+tDataN.getText()+";"+tLuogo.getText().toUpperCase()+";"+tProvincia.getText().toUpperCase()+";"+((CSesso.isSelected())?"WOMEN":"MEN");
                String[] info=data.split(";");
                FiscalCode fiscalCode=new FiscalCode(info,italianTowns);
                tCF.setText(fiscalCode.toString());
            }
        });

        cancella.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tCognome.setText("");
                tNome.setText("");
                tDataN.setText("");
                tLuogo.setText("");
                tProvincia.setText("");
                tCF.setText("");
                CSesso.setSelected(false);
            }
        });
        frame.getContentPane().add(BorderLayout.NORTH, panel1);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setVisible(true);
    }
}

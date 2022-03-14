import java.awt.*;
import javax.swing.*;
class MultiTimer {

    //pomodoro
    private JLabel pomLabel;
    private JButton pomStartBtn;
    private JButton pomResetBtn;

    //general
    private JLabel genLabel;
    private JButton genStartBtn;
    private JButton genResetBtn;

    //global
    private JButton globalStartBtn;
    private JButton globalResetBtn;

    public MultiTimer(){
        createJFrame();
    }

    private void createJFrame() {
        JFrame frame = new JFrame("Study Timer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,300);
        
        frame.add(createPanel());

        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 0;

        //Pomodoro
        pomLabel = new JLabel();
        pomLabel.setText("Pomodoro: 25:00");
        pomLabel.setBounds(50, 50, 100, 30);
        pomLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
        pomLabel.setForeground(Color.RED);
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(pomLabel, c);

        pomStartBtn = new JButton("Start/Pause");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        panel.add(pomStartBtn, c);

        pomResetBtn = new JButton("Reset");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        panel.add(pomResetBtn, c);

        //General
        genLabel = new JLabel();
        genLabel.setText("General Timer: 30:00");
        genLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
        c.insets = new Insets(10,0,0,0);  //top padding
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(genLabel, c);

        genStartBtn = new JButton("Start/Pause");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,0,0,0);  //top padding
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        panel.add(genStartBtn, c);

        genResetBtn = new JButton("Reset");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,0,0,0);  //top padding
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 1;
        panel.add(genResetBtn, c);

        globalStartBtn = new JButton("Start/Pause All");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(0,0,0,0);  //top padding
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 1;   //2 columns wide
        c.gridy = 2;       //third row
        panel.add(globalStartBtn, c);

        globalResetBtn = new JButton("Reset All");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(0,0,0,0);  //top padding
        c.gridx = 2;       //aligned with button 2
        c.gridwidth = 1;   //2 columns wide
        c.gridy = 2;       //third row
        panel.add(globalResetBtn, c);
        
        return panel;
    }

    public static void main (String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MultiTimer t = new MultiTimer();
            }
        });
    }
}
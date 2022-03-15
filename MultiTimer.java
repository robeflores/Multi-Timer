import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.SwingWorker; 
class MultiTimer implements ActionListener, PropertyChangeListener{

    class ToggleTimerTask extends SwingWorker<Void, Void>{
        @Override
        public Void doInBackground() {
            firePropertyChange("label", "Pomodoro: 25:00", "Pomodoro: 10");
            return null;
        }
        @Override
        public void done() {}
    }

    //pomodoro
    private JLabel pomLabel;
    private JButton pomStartBtn;
    private JButton pomResetBtn;
    private ToggleTimerTask pomTask;

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
        JFrame frame = new JFrame("Multi Timer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700,200);
        
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
        pomLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
        pomLabel.setForeground(Color.RED);
        c.insets = new Insets(10,10,0,0);
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(pomLabel, c);

        pomStartBtn = new JButton("Start/Pause");
        pomStartBtn.addActionListener(this);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,0,0,0);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        panel.add(pomStartBtn, c);

        pomResetBtn = new JButton("Reset");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,0,0,10);
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        panel.add(pomResetBtn, c);

        //General
        genLabel = new JLabel();
        genLabel.setText("General Timer: 30:00");
        genLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
        c.insets = new Insets(10,10,0,0);
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(genLabel, c);

        genStartBtn = new JButton("Start/Pause");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,0,0,0);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        panel.add(genStartBtn, c);

        genResetBtn = new JButton("Reset");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,0,0,10); 
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 1;
        panel.add(genResetBtn, c);

        //Global
        globalStartBtn = new JButton("Start/Pause All");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;    
        c.weighty = 1.0;   
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(0,0,10,0);
        c.gridx = 1;     
        c.gridwidth = 1;   
        c.gridy = 2;       
        panel.add(globalStartBtn, c);

        globalResetBtn = new JButton("Reset All");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;   
        c.weighty = 1.0; 
        c.anchor = GridBagConstraints.PAGE_END; 
        c.insets = new Insets(0,0,10,10);
        c.gridx = 2;   
        c.gridwidth = 1; 
        c.gridy = 2; 
        panel.add(globalResetBtn, c);
        
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == pomStartBtn){
            System.out.println("Pom start button pressed!");
            pomTask = new ToggleTimerTask();
            pomTask.addPropertyChangeListener(this);
            pomTask.execute();
        }
    }
    
    public void propertyChange(PropertyChangeEvent e) {
        if (e.getPropertyName() == "label") {
            System.out.println((String)e.getNewValue());
        } 
    }

    public static void main (String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MultiTimer timer = new MultiTimer();
            }
        });
    }
}
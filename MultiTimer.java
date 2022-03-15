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
    private enum Timer {
        POMODORO, GENERAL
    }

    int pomTime = 1459;
    int genTime = 2000;

    class TimerTask extends SwingWorker<Void, Void>{
        Timer t;
        int timeLeftInSec;
        TimerTask(Timer t, int timeLeftInSec){
            this.t = t;
            this.timeLeftInSec = timeLeftInSec;
        }

        @Override
        public Void doInBackground() {
            int oldTime = timeLeftInSec;
            while(timeLeftInSec > 0){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignore) {}
                timeLeftInSec--;
                switch(t){
                    case POMODORO:
                        firePropertyChange("pomTime", oldTime, timeLeftInSec);
                        break;
                    default:
                        System.out.println("ERROR.");
                }
                oldTime = timeLeftInSec;
            }
            return null;
        }

        @Override
        public void done() {
            System.out.println("Timer finished!");
        }
    }

    //pomodoro
    private JLabel pomLabel;
    private JButton pomStartBtn;
    private JButton pomResetBtn;
    private TimerTask pomTask;

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
            pomTask = new TimerTask(Timer.POMODORO, pomTime);
            pomTask.addPropertyChangeListener(this);
            pomTask.execute();
        }
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        if (e.getPropertyName() == "pomTime") {
            pomLabel.setText("Pomodoro: " + (int)e.getNewValue()/60 + ":");
            if((int)e.getNewValue()%60 < 10)
                pomLabel.setText(pomLabel.getText() + "0");
            pomLabel.setText(pomLabel.getText() + (int)e.getNewValue()%60);
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
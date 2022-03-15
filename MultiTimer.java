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

    final int POMTIME = 5;
    final int GENTIME = 1800;

    int pomTimeLeft = POMTIME;
    int genTimeLeft = GENTIME;

    class TimerWorker extends SwingWorker<Integer, Void>{
        Timer t;
        int timeLeftInSec;
        TimerWorker(Timer t, int timeLeftInSec){
            this.t = t;
            this.timeLeftInSec = timeLeftInSec;
        }

        @Override
        public Integer doInBackground() {
            int oldTime = timeLeftInSec;
            while(timeLeftInSec > 0){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignore) {break;}
                timeLeftInSec--;
                switch(t){
                    case POMODORO:
                        firePropertyChange("pomTimeLeft", oldTime, timeLeftInSec);
                        break;
                    case GENERAL:
                        firePropertyChange("genTimeLeft", oldTime, timeLeftInSec);
                        break;
                    default:
                        System.out.println("ERROR.");
                }
                oldTime = timeLeftInSec;
            }
            return timeLeftInSec;
        }

        @Override
        public void done() {
        }
    }

    //pomodoro
    private JLabel pomLabel;
    private JButton pomStartBtn;
    private JButton pomResetBtn;
    private TimerWorker pomWorker;

    //general
    private JLabel genLabel;
    private JButton genStartBtn;
    private JButton genResetBtn;
    private TimerWorker genWorker;

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
        pomLabel.setText("Pomodoro: " + pomTimeLeft/60 + ":");
        if(pomTimeLeft%60 < 10)
            pomLabel.setText(pomLabel.getText() + "0");
        pomLabel.setText(pomLabel.getText() + pomTimeLeft%60);
        pomLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
        pomLabel.setForeground(Color.RED);
        c.insets = new Insets(10,10,0,0);
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(pomLabel, c);

        pomStartBtn = new JButton("Start");
        pomStartBtn.addActionListener(this);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,0,0,0);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        panel.add(pomStartBtn, c);

        pomResetBtn = new JButton("Reset");
        pomResetBtn.addActionListener(this);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,0,0,10);
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        panel.add(pomResetBtn, c);

        //General
        genLabel = new JLabel();
        genLabel.setText("General: " + genTimeLeft/60 + ":");
        if(genTimeLeft%60 < 10)
            genLabel.setText(genLabel.getText() + "0");
        genLabel.setText(genLabel.getText() + genTimeLeft%60);
        genLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
        c.insets = new Insets(10,10,0,0);
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(genLabel, c);

        genStartBtn = new JButton("Start");
        genStartBtn.addActionListener(this);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,0,0,0);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        panel.add(genStartBtn, c);

        genResetBtn = new JButton("Reset");
        genResetBtn.addActionListener(this);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,0,0,10); 
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 1;
        panel.add(genResetBtn, c);

        //Global
        globalStartBtn = new JButton("Start/Pause All");
        globalStartBtn.addActionListener(this);
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
        globalResetBtn.addActionListener(this);
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

    void StartPomodoro(){
        if(pomWorker != null && !pomWorker.isDone()){
            pomWorker.cancel(true);
            pomStartBtn.setText("Start");
            return;
        }
        pomWorker = new TimerWorker(Timer.POMODORO, pomTimeLeft);
        pomWorker.addPropertyChangeListener(this);
        pomWorker.execute();
        pomStartBtn.setText("Pause");
    }
    void ResetPomodoro(){
        if(pomWorker != null && !pomWorker.isDone()){
            pomWorker.cancel(true);
        }
        pomTimeLeft = POMTIME;
        pomLabel.setText("Pomodoro: " + pomTimeLeft/60 + ":");
        if(pomTimeLeft%60 < 10)
            pomLabel.setText(pomLabel.getText() + "0");
        pomLabel.setText(pomLabel.getText() + pomTimeLeft%60);
    }
    void StartGeneral(){
        if(genWorker != null && !genWorker.isDone()){
            genWorker.cancel(true);
            genStartBtn.setText("Start");
            return;
        }
        genWorker = new TimerWorker(Timer.GENERAL, genTimeLeft);
        genWorker.addPropertyChangeListener(this);
        genWorker.execute();
        genStartBtn.setText("Pause");
    }
    void ResetGeneral(){
        if(genWorker != null && !genWorker.isDone()){
            genWorker.cancel(true);
        }
        genTimeLeft = GENTIME;
        genLabel.setText("General: " + genTimeLeft/60 + ":");
        if(genTimeLeft%60 < 10)
            genLabel.setText(genLabel.getText() + "0");
        genLabel.setText(genLabel.getText() + genTimeLeft%60);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == pomStartBtn)
            StartPomodoro();
        else if(e.getSource() == pomResetBtn)
            ResetPomodoro();
        else if(e.getSource() == genStartBtn)
            StartGeneral();
        else if(e.getSource() == genResetBtn)
            ResetGeneral();
        else if(e.getSource() == globalStartBtn){
            StartPomodoro();
            StartGeneral();
        }
        else if(e.getSource() == globalResetBtn){
            ResetPomodoro();
            ResetGeneral();
        }
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        if (e.getPropertyName() == "pomTimeLeft") {
            pomTimeLeft = (int)e.getNewValue();
            pomLabel.setText("Pomodoro: " + pomTimeLeft/60 + ":");
            if(pomTimeLeft%60 < 10)
                pomLabel.setText(pomLabel.getText() + "0");
            pomLabel.setText(pomLabel.getText() + pomTimeLeft%60);
        }
        else if (e.getPropertyName() == "genTimeLeft") {
            genTimeLeft = (int)e.getNewValue();
            genLabel.setText("General: " + genTimeLeft/60 + ":");
            if(genTimeLeft%60 < 10)
                genLabel.setText(genLabel.getText() + "0");
            genLabel.setText(genLabel.getText() + genTimeLeft%60);
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
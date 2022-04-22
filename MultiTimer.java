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

class MultiTimer implements ActionListener{

    private JFrame frame;
    public JPanel panel;

    //global
    private JButton globalStartBtn;
    private JButton globalResetBtn;

    Timer pom;
    Timer gen;

    public MultiTimer(){
        createJFrame();
        pom = new Timer(this, 0, "Pomodoro", 1500);
        gen = new Timer(this, 1, "General", 1800);
    }

    private void createJFrame() {
        frame = new JFrame("Multi Timer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,175);
        
        panel = createPanel();
        frame.add(panel);

        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private JPanel createPanel() {
        panel = new JPanel(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 0;

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

    public void showWindow(){
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.setVisible(true);
                frame.setAlwaysOnTop(true);
                frame.toFront();
                frame.requestFocus();
                frame.setAlwaysOnTop(false);
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == globalStartBtn){
            pom.StartTimer();
            gen.StartTimer();
        }
        else if(e.getSource() == globalResetBtn){
            pom.ResetTimer();
            gen.ResetTimer();
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


class Timer implements ActionListener, PropertyChangeListener{
    private JLabel label;
    private JButton startBtn;
    private JButton resetBtn;
    private TimerWorker worker;

    MultiTimer t;

    int time;
    int timeLeft;

    String labelText;

    class TimerWorker extends SwingWorker<Integer, Void>{
        int timeLeftInSec;
        TimerWorker(int timeLeftInSec){
            this.timeLeftInSec = timeLeftInSec;
        }
    
        @Override
        public Integer doInBackground() {
            int oldTime = timeLeftInSec;
            while(timeLeftInSec > 0){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {break;}
                timeLeftInSec--;
                firePropertyChange("timeLeft", oldTime, timeLeftInSec);
                oldTime = timeLeftInSec;
            }
            return timeLeftInSec;
        }
    
        @Override
        public void done() {
            t.showWindow();
        }
    }


    Timer(MultiTimer t, int y, String labelText, int time){
        this.t = t;
        this.labelText = labelText;
        this.time = time;

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 0;

        label = new JLabel();
        timeLeft = this.time;
        label.setText(this.labelText + ": " + timeLeft/60 + ":");
        if(timeLeft%60 < 10)
            label.setText(label.getText() + "0");
        label.setText(label.getText() + timeLeft%60);
        label.setFont(new Font("Verdana", Font.PLAIN, 18));
        label.setForeground(Color.RED);
        c.insets = new Insets(10,10,0,0);
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = y;
        t.panel.add(label, c);

        startBtn = new JButton("Start");
        startBtn.addActionListener(this);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,0,0,0);
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = y;
        t.panel.add(startBtn, c);

        resetBtn = new JButton("Reset");
        resetBtn.addActionListener(this);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,0,0,10);
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = y;
        t.panel.add(resetBtn, c);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startBtn)
            StartTimer();
        else if(e.getSource() == resetBtn)
            ResetTimer();
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {
        if (e.getPropertyName() == "timeLeft") {
            timeLeft = (int)e.getNewValue();
            label.setText(labelText + ": " + timeLeft/60 + ":");
            if(timeLeft%60 < 10)
                label.setText(label.getText() + "0");
            label.setText(label.getText() + timeLeft%60);
        }
    }

    public void StartTimer(){
        if(worker != null && !worker.isDone()){
            worker.cancel(true);
            startBtn.setText("Start");
            return;
        }
        worker = new TimerWorker(timeLeft);
        worker.addPropertyChangeListener(this);
        worker.execute();
        startBtn.setText("Pause");
    }
    public void ResetTimer(){
        if(worker != null && !worker.isDone()){
            worker.cancel(true);
        }
        timeLeft = time;
        label.setText(labelText + ": " + timeLeft/60 + ":");
        if(timeLeft%60 < 10)
            label.setText(label.getText() + "0");
        label.setText(label.getText() + timeLeft%60);
        startBtn.setText("Start");
    }
}
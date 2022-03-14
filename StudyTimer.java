import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
class StudyTimer{
    public static void main ( String [] arguments ){
		JFrame frame = new JFrame("Study Timer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,300);

        Container pane = frame.getContentPane();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 0;

        JLabel label1 = new JLabel();
        label1.setText("Pomodoro: 25:00");
        label1.setBounds(50, 50, 100, 30);
        label1.setFont(new Font("Verdana", Font.PLAIN, 18));
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(label1, c);

        JButton button1 = new JButton("Start/Pause/Resume");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(button1, c);

        JButton button2 = new JButton("Reset");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        pane.add(button2, c);

        JLabel label2 = new JLabel();
        label2.setText("General Timer: 30:00");
        label2.setFont(new Font("Verdana", Font.PLAIN, 18));
        c.weightx = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(label2, c);

        JButton button3 = new JButton("Start/Pause/Resume");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        pane.add(button3, c);

        JButton button4 = new JButton("Reset");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 1;
        pane.add(button4, c);

        JButton button5 = new JButton("Start/Pause/Resume All");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(0,0,0,0);  //top padding
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 1;   //2 columns wide
        c.gridy = 2;       //third row
        pane.add(button5, c);

        JButton button6 = new JButton("Reset All");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(0,0,0,0);  //top padding
        c.gridx = 2;       //aligned with button 2
        c.gridwidth = 1;   //2 columns wide
        c.gridy = 2;       //third row
        pane.add(button6, c);
		
        frame.setVisible(true);
    }
}
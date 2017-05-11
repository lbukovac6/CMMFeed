import java.awt.*;
import java.awt.event.*;

/**
 * Created by lbukovac on 5/10/2017.
 */


class MessageBox extends Dialog implements ActionListener
{

    public MessageBox(Frame frame, String title, String messageString)
    {
        super(frame, title, true);

        Rectangle bounds = frame.getBounds();
        setLocation(bounds.x+bounds.width/3, bounds.y+bounds.height/3);

        Panel messagePane = new Panel();
        Label message = new Label(messageString);
        messagePane.add(message);
        add(messagePane, BorderLayout.CENTER);

        Panel buttonPane = new Panel();
        Button button = new Button(" OK ");
        buttonPane.add(button);
        button.addActionListener(this);
        add(buttonPane, BorderLayout.SOUTH);
        pack();
    }

    public void actionPerformed(ActionEvent e)
    {
        setVisible(false);
    }
}

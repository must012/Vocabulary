import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameController implements ActionListener{
	GUI frame;
	
	public FrameController(GUI other) {
		this.frame = other;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == frame.buts[0]) {
			frame.iw.setVisible(true);
			frame.sw.setVisible(false);
			frame.tw.setVisible(false);
			frame.card.show(frame.showP, "input");
			}
		if(e.getSource() == frame.buts[1]) {
			frame.sw.setVisible(true);
			frame.iw.setVisible(false);
			frame.tw.setVisible(false);
			frame.card.show(frame.showP, "select");
			}
		if(e.getSource() == frame.buts[2]) {
			frame.tw.setVisible(true);
			frame.sw.setVisible(false);
			frame.iw.setVisible(false);
			frame.card.show(frame.showP, "test");
			}
		}
	}

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class GUI extends JFrame{
//�� �������� �׼Ǹ����ʿ��� ���Ǳ� ������ ��� ������ ����
	
	CardLayout card = new CardLayout();
	
	JPanel mainP = new JPanel(); // ��ü �г�
	JPanel showP = new JPanel(); // ���� �г�(ī�巹�̾ƿ�)
	JPanel butP = new JPanel(); // ��ư �г�
	JButton [] buts = new JButton[3];
	
	inputWord iw;
	selectWord sw;
	testWord tw;
		
	public GUI() {
		//��Ʈ(�۾�ü, ����(BOLD, ITALIC, PLAIN), ũ��)
		Font font = new Font("����", Font.BOLD,30);
	
		//�׵θ�(����)
		BevelBorder border = new BevelBorder(BevelBorder.RAISED);

		mainP.setLayout(new BorderLayout(0,10));
		mainP.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
		//�� ���� ����(�е�)���� ž ����Ʈ ���� ����Ʈ
		
		buts[0] = new JButton("�ܾ� �Է�");
		iw = new inputWord();
		iw.setVisible(false);
		showP.add(iw, "input");
		
		buts[1] = new JButton("�ܾ� �˻�");
		sw = new selectWord();
		sw.setVisible(false);
		showP.add(sw, "select");
		
		buts[2] = new JButton("�ܾ� ����");
		tw = new testWord();
		tw.setVisible(false);
		showP.add(tw, "test");
		
		showP.setLayout(card);
		
		butP.setLayout(new GridLayout(1, 3, 20, 20));
		
		for(int i = 0;i<3; i++) {
			butP.add(buts[i]);
			buts[i].setFont(font);
			buts[i].addActionListener(new FrameController(this));
		}
		
		mainP.add(butP, BorderLayout.NORTH);
		mainP.add(showP, BorderLayout.CENTER);
		
		add(mainP);
		
		setSize(1200, 800);
		setLocationRelativeTo(null);
		setTitle("�ܾ���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);	
	}
}
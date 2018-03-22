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
//이 변수들이 액션리스너에도 사용되기 때문에 멤버 변수로 선언
	
	CardLayout card = new CardLayout();
	
	JPanel mainP = new JPanel(); // 전체 패널
	JPanel showP = new JPanel(); // 내용 패널(카드레이아웃)
	JPanel butP = new JPanel(); // 버튼 패널
	JButton [] buts = new JButton[3];
	
	inputWord iw;
	selectWord sw;
	testWord tw;
		
	public GUI() {
		//폰트(글씨체, 굵음(BOLD, ITALIC, PLAIN), 크기)
		Font font = new Font("돋움", Font.BOLD,30);
	
		//테두리(보더)
		BevelBorder border = new BevelBorder(BevelBorder.RAISED);

		mainP.setLayout(new BorderLayout(0,10));
		mainP.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
		//빈 내부 공백(패딩)생성 탑 레프트 바텀 라이트
		
		buts[0] = new JButton("단어 입력");
		iw = new inputWord();
		iw.setVisible(false);
		showP.add(iw, "input");
		
		buts[1] = new JButton("단어 검색");
		sw = new selectWord();
		sw.setVisible(false);
		showP.add(sw, "select");
		
		buts[2] = new JButton("단어 시험");
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
		setTitle("단어장");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);	
	}
}
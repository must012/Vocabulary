import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class inputWord extends JPanel{
	boolean panelC = true;

	CardLayout icard = new CardLayout(10, 10);
	static MainDB db;
	
	JPanel buttonP;
	JPanel resultP;
	JPanel firstInsertP;
	JPanel secondInsertP;
	JPanel insertResultP;
	
	JButton [] buts;
	
	JTextField [] firstInsertT;
	JTextField [] secondInsertT;
	
	JLabel [] firstInsertL;
	JLabel [] secondInsertL;
	JLabel resultL;
	
	MainDB dao = new MainDB();
	DTO dto = new DTO();
	
	inputWord(){
		
		Font font = new Font("����", Font.BOLD, 30);
		
		//�г�, ���̾ƿ�	
		buttonP = new JPanel();
		resultP = new JPanel();
		firstInsertP = new JPanel();
		secondInsertP = new JPanel();
		insertResultP = new JPanel();
		
		setLayout(new BorderLayout(0, 100));	//���� ���̾ƿ�
		
		buttonP.setLayout(new GridLayout(1, 3, 30, 50));	//��ư�г�
		buttonP.setBorder(BorderFactory.createEmptyBorder(100, 30, 10, 30));
		
		resultP.setLayout(icard);	//���â
		
		firstInsertP.setLayout(new GridLayout(2, 5, 10, 10));
		firstInsertP.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		secondInsertP.setLayout(new GridLayout(2, 6, 10, 10));
		secondInsertP.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		insertResultP.setBorder(BorderFactory.createEmptyBorder(50, 10, 50, 10));
		
		//��ư
		buts = new JButton[3];
		
		firstInsertT = new JTextField[5];
		secondInsertT = new JTextField[6];
		
		buts[0] = new JButton("�ܼ� �Է�");
		buts[1] = new JButton("NOTE ���");
		buts[2] = new JButton("������");
		
		for(int i = 0; i<3; i++) {
			buts[i].setFont(font);
		}
		
		//��, �ؽ�Ʈ
		firstInsertL = new JLabel[5];
		secondInsertL = new JLabel[6];
		resultL = new JLabel("�̰��� INSERT ����� ���", 0);
		
		resultL.setFont(font);
		
		firstInsertL[0] = new JLabel("����", 0);
		firstInsertL[1] = new JLabel("�Ϻ���", 0);
		firstInsertL[2] = new JLabel("�� 1", 0);
		firstInsertL[3] = new JLabel("�� 2", 0);
		firstInsertL[4] = new JLabel("�� 3", 0);
		
		secondInsertL[0] = new JLabel("����", 0);
		secondInsertL[1] = new JLabel("�Ϻ���", 0);
		secondInsertL[2] = new JLabel("�� 1", 0);
		secondInsertL[3] = new JLabel("�� 2", 0);
		secondInsertL[4] = new JLabel("�� 3", 0);
		secondInsertL[5] = new JLabel("��Ʈ", 0);	
		
		//�󺧰� �ؽ�Ʈ�� ��Ʈ ���� / ���� �гο� �߰�
		for(int i = 0; i<5; i++) {
			firstInsertT[i] = new JTextField();
			firstInsertT[i].setHorizontalAlignment(JTextField.CENTER);
			firstInsertT[i].setFont(new Font("���", Font.BOLD, 20));

			secondInsertT[i] = new JTextField();
			secondInsertT[i].setHorizontalAlignment(JTextField.CENTER);
			secondInsertT[i].setFont(new Font("���", Font.BOLD, 20));

			firstInsertP.add(firstInsertL[i]);
			firstInsertL[i].setFont(font);
			
			secondInsertP.add(secondInsertL[i]);
			secondInsertL[i].setFont(font);
		} 
		secondInsertT[5] = new JTextField();
		secondInsertT[5].setHorizontalAlignment(JTextField.CENTER);
		secondInsertT[5].setFont(new Font("���", Font.BOLD, 20));
		
		secondInsertP.add(secondInsertL[5]);
		secondInsertL[5].setFont(font);
		
		//�ؽ�Ʈ �ʵ带 �гο� �߰�
		for(int i = 0; i<5; i++) {
			firstInsertP.add(firstInsertT[i]);
			secondInsertP.add(secondInsertT[i]);
		}
		secondInsertP.add(secondInsertT[5]);
		
		//�̺�Ʈ
		buts[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				icard.show(resultP, "first");
				panelC = true;
			}
		});
		
		buts[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				icard.show(resultP, "second");
				panelC = false;
			}
		});
		
		buts[2].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String [] inputWord = new String[6];
				int result;
				
				if(panelC == true) {
					for(int i = 0; i<5; i++) {
						if(firstInsertT[i].getText().isEmpty())
							firstInsertT[i].setText("");
					}
						dto.setCN(firstInsertT[0].getText());
						dto.setJP(firstInsertT[1].getText());
						dto.setKR1(firstInsertT[2].getText());
						dto.setKR2(firstInsertT[3].getText());
						dto.setKR3(firstInsertT[4].getText());
						dto.setNOTE("");	
				}
				
				if(panelC == false) {
					for(int i = 0; i<6; i++) {
						if(secondInsertT[i].getText().isEmpty())
							secondInsertT[i].setText("");
					}
					dto.setCN(secondInsertT[0].getText());
					dto.setJP(secondInsertT[1].getText());
					dto.setKR1(secondInsertT[2].getText());
					dto.setKR2(secondInsertT[3].getText());
					dto.setKR3(secondInsertT[4].getText());
					dto.setNOTE(secondInsertT[5].getText());
				}
					result = dao.insertWord(dto);
					
					if(result == 1) resultL.setText("�Է� ����");
					else resultL.setText("�Է� ����");
				
				//�Է� �� �ʵ带 �ʱ�ȭ(NOTE�� �ʵ�� ��ĥ �ʿ䰡 ����)
				for(int i = 0; i<5; i++) {
					firstInsertT[i].setText(null);
					secondInsertT[i].setText(null);
				}
				
				//���ͷ� �Է��� Ŀ���� �ٽ� ���ϴ� �ؽ�Ʈ�ʵ�� �̵�
				if(panelC == true)
					firstInsertT[0].requestFocus();
				if(panelC == false)
					secondInsertT[0].requestFocus();
			}
		});
		
		for(int i = 2; i<5;i++) {
			firstInsertT[i].addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent e) {}
				public void keyReleased(KeyEvent e) {}
			
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == 10)
						buts[2].doClick();
				}
			});
			secondInsertT[i].addKeyListener(new KeyListener() {
			
				public void keyTyped(KeyEvent e) {}
				public void keyReleased(KeyEvent e) {}
			
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == 10)
						buts[2].doClick();
				}
			});
		}
		
		//�߰�
		resultP.add(firstInsertP, "first");//�Է� â1
		resultP.add(secondInsertP, "second");//�Է� â2
	
		buttonP.add(buts[0]);
		buttonP.add(buts[1]);
		buttonP.add(buts[2]); // ��ư�� ���
		
		insertResultP.add(resultL);
		
		this.add(buttonP, BorderLayout.NORTH);
		this.add(resultP, BorderLayout.CENTER);
		this.add(insertResultP, BorderLayout.SOUTH);

		icard.show(resultP, "first");
	}
}

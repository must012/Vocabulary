import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class testWord extends JPanel{
	
	JPanel buttonP;
	JPanel showP;
	JScrollPane scrollP1;
	JScrollPane scrollP2;
	
	JButton questionB;
	JButton submitB;
	
	JLabel noteL;
	
	JTextField noteT;
	
	JTable table1;
	JTable table2;
	DefaultTableModel model1;
	DefaultTableModel model2;
	
	String[] columnName1 = {"����","����","���󰡳�","O/X"};
	String[] columnName2 = {"����","���󰡳�","��1","��2","��3"};
	String[][] submitT = new String[20][4];
	String[][] answerT = new String[20][5];
	
	DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
	TableColumnModel tcm;
	
	MainDB dao = new MainDB();
	ArrayList <DTO> list = null;
	DTO dtoForNote = new DTO();
	
	public testWord() {

		//��ư
		questionB = new JButton("���� ����");
		questionB.setFont(new Font("����", Font.BOLD, 30));
		submitB = new JButton("��� ����");
		submitB.setFont(new Font("����", Font.BOLD, 30));
		
		//��, �ؽ�Ʈ
		noteL = new JLabel("NOTE");
		noteL.setFont(new Font("����", Font.BOLD, 30));
		noteL.setHorizontalAlignment(JLabel.CENTER);
		noteT = new JTextField(20);
		noteT.setFont(new Font("����", Font.BOLD, 30));
		noteT.setHorizontalAlignment(JTextField.CENTER);
		
		//��
		model1 = new DefaultTableModel(submitT, columnName1);
		model2 = new DefaultTableModel(answerT, columnName2) {
			//������ ����
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		//���̺�
		table1 = new JTable(model1);
		table2 = new JTable(model2);
		table1.setFont(new Font("���", Font.BOLD, 22));
		table1.setRowHeight(28);
		table2.setFont(new Font("���", Font.BOLD, 22));
		table2.setRowHeight(28);
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		
		//�г�
		setLayout(new BorderLayout(10, 10));
		buttonP = new JPanel(new GridLayout(1, 4, 50, 0));
		buttonP.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));
		scrollP1 = new JScrollPane(table1);
		scrollP2 = new JScrollPane(table2);
		
		//�̺�Ʈ
		questionB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model1 = (DefaultTableModel)table1.getModel();
				model1.setRowCount(0);
				DefaultTableModel model2 = (DefaultTableModel)table2.getModel();
				model2.setRowCount(0);
				
				if(noteT.getText().isEmpty()) {
					dtoForNote.setNOTE("9999");
					list = dao.testWord(dtoForNote.getNOTE());
					}
				else {
					dtoForNote.setNOTE(noteT.getText());
					list = dao.testWord(dtoForNote.getNOTE());
					}
				
				//������ ������ �游�� ���
				//���߿� �������� Ȥ�� �������� ��OR���� �� �ϳ��� ������� ��ü
				for(int i = 0; i<20; i++) {
					answerT[i][0] = list.get(i).getCN();
					answerT[i][1] = list.get(i).getJP();
					answerT[i][2] = list.get(i).getKR1();
					answerT[i][3] = list.get(i).getKR2();
					answerT[i][4] = list.get(i).getKR3();
					
					submitT[i][0] = answerT[i][2];
					//���� ���̺��� 
					model2.addRow(answerT[i]);
					model1.addRow(submitT[i]);
				}
				
				//model1.fireTableDataChanged();
				table2.setVisible(false);
				
				//���� ������� �߾� ���� �ϱ� ����
				tcm = table1.getColumnModel();
				for(int i = 0; i<4; i++) {
					tcm.getColumn(i).setCellRenderer(dtcr);
				}
				
				tcm = table2.getColumnModel();
				for(int i = 0; i<5; i++) {
					tcm.getColumn(i).setCellRenderer(dtcr);
				}
			}
		});

		submitB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//���ڸ� ����� ���󰡳��� ���� �Է�
			/*	for(int i = 0; i<20; i++) {
					if(table1.getValueAt(i, 1) == null) table1.setValueAt("", i, 1);
					if(table1.getValueAt(i, 2) == null) table1.setValueAt("", i, 2);
					if((table1.getValueAt(i, 1).equals(table2.getValueAt(i, 1))) && (table1.getValueAt(i, 2).equals(table2.getValueAt(i, 2))))
						table1.setValueAt("O", i, 3);
					else
						table1.setValueAt("X", i, 3);	*/
					
				//������ ���� ����� ����/���󰡳��� �Է�
					for(int i = 0; i<20; i++) {
						if(table1.getValueAt(i, 1) == null) table1.setValueAt("", i, 1);
						if(table1.getValueAt(i, 2) == null) table1.setValueAt("", i, 2);
						if((table1.getValueAt(i, 1).equals(table2.getValueAt(i, 0))) && (table1.getValueAt(i, 2).equals(table2.getValueAt(i, 1))))
							table1.setValueAt("O", i, 3);
						else
							table1.setValueAt("X", i, 3);
						
							/*
					if((table1.getValueAt(i, 1) == null) || !(table1.getValueAt(i, 1).equals(table2.getValueAt(i, 1)))) table1.setValueAt("X", i, 3);
					
					if((table1.getValueAt(i, 2) == null) || !(table1.getValueAt(i, 2).equals(table2.getValueAt(i, 2)))) table1.setValueAt("X", i, 3);
					else table1.setValueAt("O", i, 3);
			*/
				}
				model1.fireTableDataChanged();
				table2.setVisible(true);
				}
			});
		
		//�г� �߰�
		buttonP.add(questionB);
		buttonP.add(submitB);
		buttonP.add(noteL);
		buttonP.add(noteT);
		
		this.add(buttonP, BorderLayout.NORTH);
		this.add(scrollP1, BorderLayout.WEST);
		this.add(scrollP2, BorderLayout.CENTER);
	}
	}

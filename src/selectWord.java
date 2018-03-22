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
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class selectWord extends JPanel{
//	GUI swin;
	
	JLabel label;
	JButton submitB;
	JTextField selectT;
	
	JScrollPane scrollP;
	JPanel buttonP;
	
	DefaultTableModel model;
	JTable table;
	String [] head = {"����", "�Ϻ���", "��1", "��2", "��3"};
	String [][] body = null;
	
	//���̺��� �߾������� ���� ���� ���̺� ���� ������ ��ü, ���̺��� �÷����� ������ ��ü
	DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();	
	TableColumnModel tcm;
	
	MainDB dao = new MainDB();
	DTO dto = new DTO();
	ArrayList<DTO> list = new ArrayList<DTO>();
	
	public selectWord() {

		//�ؽ�Ʈ
		selectT = new JTextField(20);
		
		//��ư
		submitB = new JButton("������");
		submitB.setFont(new Font("����", Font.BOLD, 30));
		
		//��
		label = new JLabel("NOTE", 0);
		label.setFont(new Font("����", Font.BOLD, 30));
		
		//���̺�
		model = new DefaultTableModel(body, head){
			//������ ����
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		//���� ��ü�� �߾����� ����
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		
		table = new JTable(model);
		table.setFont(new Font("���", Font.BOLD, 25));
		table.setRowHeight(30);
		
		//�г�
		setLayout(new BorderLayout(0, 10));
		buttonP = new JPanel(new GridLayout(1, 3, 20, 20));
		buttonP.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
		scrollP = new JScrollPane(table);
		
		//�̺�Ʈ
		submitB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//���̺� �ʱ�ȭ�� ���� ����
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				model.setRowCount(0);	 
				
				if(selectT.getText().isEmpty())
					list = dao.selectWord(1, dto);
				else {
					dto.setNOTE(selectT.getText());
					list = dao.selectWord(0, dto);
				}
				
				//body �迭�� ���� ����
				body = new String[list.size()][5];
				
				for(int i = 0; i<list.size(); i++) {
						body[i][0] = list.get(i).getCN();
						body[i][1] = list.get(i).getJP();
						body[i][2] = list.get(i).getKR1();
						body[i][3] = list.get(i).getKR2();
						body[i][4] = list.get(i).getKR3();
						
					for(int j =0; j<5; j++) {
						if(body[i][j] == null) {
							body[i][j] = "-";
						}
					}
					model.addRow(body[i]);
				}
				
				//���� ���� �߾� ����
				tcm = table.getColumnModel();
				for(int i = 0; i<5; i++) {
					tcm.getColumn(i).setCellRenderer(dtcr);
				}	
				RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
				table.setRowSorter(sorter);
			}
		});
		
		//�г� �߰�
		buttonP.add(label);
		buttonP.add(selectT);
		buttonP.add(submitB);
			
		this.add(buttonP, BorderLayout.NORTH);
		this.add(scrollP, BorderLayout.CENTER);
		}
	}
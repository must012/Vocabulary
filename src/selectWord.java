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
	String [] head = {"한자", "일본어", "뜻1", "뜻2", "뜻3"};
	String [][] body = null;
	
	//테이블의 중앙정렬을 위한 변수 테이블 셀을 조정할 객체, 테이블의 컬럼들을 가져올 객체
	DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();	
	TableColumnModel tcm;
	
	MainDB dao = new MainDB();
	DTO dto = new DTO();
	ArrayList<DTO> list = new ArrayList<DTO>();
	
	public selectWord() {

		//텍스트
		selectT = new JTextField(20);
		
		//버튼
		submitB = new JButton("보내기");
		submitB.setFont(new Font("돋움", Font.BOLD, 30));
		
		//라벨
		label = new JLabel("NOTE", 0);
		label.setFont(new Font("돋움", Font.BOLD, 30));
		
		//테이블
		model = new DefaultTableModel(body, head){
			//열수정 금지
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		//정렬 객체를 중앙으로 설정
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		
		table = new JTable(model);
		table.setFont(new Font("고딕", Font.BOLD, 25));
		table.setRowHeight(30);
		
		//패널
		setLayout(new BorderLayout(0, 10));
		buttonP = new JPanel(new GridLayout(1, 3, 20, 20));
		buttonP.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
		scrollP = new JScrollPane(table);
		
		//이벤트
		submitB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//테이블 초기화를 위한 생성
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				model.setRowCount(0);	 
				
				if(selectT.getText().isEmpty())
					list = dao.selectWord(1, dto);
				else {
					dto.setNOTE(selectT.getText());
					list = dao.selectWord(0, dto);
				}
				
				//body 배열의 길이 설정
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
				
				//셀의 내용 중앙 정렬
				tcm = table.getColumnModel();
				for(int i = 0; i<5; i++) {
					tcm.getColumn(i).setCellRenderer(dtcr);
				}	
				RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
				table.setRowSorter(sorter);
			}
		});
		
		//패널 추가
		buttonP.add(label);
		buttonP.add(selectT);
		buttonP.add(submitB);
			
		this.add(buttonP, BorderLayout.NORTH);
		this.add(scrollP, BorderLayout.CENTER);
		}
	}
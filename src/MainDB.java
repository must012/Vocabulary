import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.UIManager;
import javax.swing.text.html.parser.DTD;

public class MainDB {
	
	
	String selectQuery = 
		"SELECT CN, JP, KR1, KR2, KR3 FROM WORDPAD WHERE NOTE = ?";
	String countQuery = 
		"SELECT COUNT(*) FROM WORDPAD WHERE NOTE = ?";
	
	int row;
	
	public static Connection connect() {
		String userID = "s1301204";
		String PW = "p1301204";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		
		Connection con = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url, userID, PW);
			
		} catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException: ");
			System.out.println("검색 실패"); // DB 로드 실패
			e.printStackTrace();
		}
		catch (SQLException e) {
			System.out.println("그 외의 오류");
			e.printStackTrace();
		}finally {
			if(rs != null) try{ rs.close();}
			catch(Exception e) {e.getMessage();}
			if(stmt != null) try{ stmt.close();}
			catch(Exception e) {e.getMessage();}
		}
		return con;
	}
	
	public int insertWord(DTO dto){
		int resultCheck = 0;
		String inputQuery = "INSERT INTO WORDPAD (CN, JP, KR1, KR2, KR3, NOTE, ID) VALUES (?,?,?,?,?,?,?)";
		Connection con = connect();
		Statement stmt = null;
		PreparedStatement rtmt = null;
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT COUNT(*) FROM WORDPAD");
			rs.next();
			row = Integer.parseInt(rs.getString(1));
			stmt.close();
			
			rtmt = con.prepareStatement(inputQuery);
			rtmt.setString(1, dto.getCN());
			rtmt.setString(2, dto.getJP());
			rtmt.setString(3, dto.getKR1());
			rtmt.setString(4, dto.getKR2());
			rtmt.setString(5, dto.getKR3());
			rtmt.setString(6, dto.getNOTE());
			rtmt.setInt(7, (row+1));
		
			resultCheck = rtmt.executeUpdate(); // 적용한 행 (1개)가 반환되어서, 정상적으로 처리되면 i 에 1이 저장
		
			stmt = con.createStatement();
			stmt.executeUpdate("commit");
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("입력실패");
			System.out.println(e.getMessage());
			System.exit(0);
		}finally {
			if(rs != null)	{
				try {	rs.close();	} 
				catch (SQLException e2) {}
			}
			if(rtmt != null) { 
				try {	rtmt.close();	}
				catch (SQLException e2) {}
			}
			if(con != null) { 
				try {	con.close();	}
				catch (SQLException e2) {}
			}
		}
		return resultCheck;
	}

	public ArrayList<DTO> selectWord(int noteNullCheck, DTO inputDto) {
		Connection con = connect();
		Statement stmt = null;
		PreparedStatement rtmt = null;
		ResultSet rs = null;
		
		String [][] table = null;
		
		ArrayList <DTO> list = new ArrayList<DTO>();
		DTO dto = null;
		
		try {	
			//SELECT 쿼리
			if(noteNullCheck == 1)
				rtmt = con.prepareStatement("SELECT CN, JP, KR1, KR2, KR3 FROM WORDPAD");
			
			else {
			rtmt = con.prepareStatement(selectQuery);
			rtmt.setString(1, inputDto.getNOTE());
			}
			
			rs = rtmt.executeQuery();
			
			while (rs.next()) {
				dto = new DTO();
				dto.setCN(rs.getString(1));
				dto.setJP(rs.getString(2));;
				dto.setKR1(rs.getString(3));
				dto.setKR2(rs.getString(4));
				dto.setKR3(rs.getString(5));
				list.add(dto);
				}
			
		} catch (SQLException e) {
			System.out.println("SELECT문 오류");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}finally {
			if(rs != null) {try { rs.close(); }
			catch(Exception e) {e.getMessage();}
			}
			if(rtmt != null) {try {rtmt.close(); }
			catch(Exception e) {e.getMessage(); }
			}
		}
		return list;
		
	}
	
	public ArrayList<DTO> testWord(String NOTE) {
		Random rd = new Random();
		
		int num = 0;
		Connection con = connect();
		Statement stmt = null;
		PreparedStatement rtmt = null;
		ResultSet rs = null;
		
		String [][] word = new String[20][5];
		boolean checkOut = false;

		ArrayList <Integer> numForNote = new ArrayList<Integer>();
		ArrayList <Integer> redundancy = new ArrayList<Integer>();
		
		DTO dto = null;
		ArrayList <DTO> list = new ArrayList<DTO>();
		
		try {
			stmt = con.createStatement();
			
			//노트에 입력되는 값 없음
			if(NOTE == "9999") {
			rs = stmt.executeQuery("SELECT COUNT(*) FROM WORDPAD");
			rs.next();
			
			row = Integer.parseInt(rs.getString(1));
			
			for(int i = 0;i<20;i++) {
				dto = new DTO();
				
				num = rd.nextInt((row + 1));
				
				if(i >= 1) {
					while(checkOut == false) {
						num = rd.nextInt((row + 1));
				
						for(int j = 0; j<redundancy.size(); j++) {
							if(num != redundancy.get(j)) checkOut = true;
							else {
								checkOut = false;
								break;
							}	
						}
					}
				}
				
				rs = stmt.executeQuery("SELECT CN, JP, KR1, KR2, KR3 FROM WORDPAD WHERE ID =" + num);
				
				if(rs.next()) {
					if(rs.getString(1) == null){
						dto.setCN("-");
					}else
						dto.setCN(rs.getString(1));
				
				dto.setJP(rs.getString(2));
				dto.setKR1(rs.getString(3));

				if(rs.getString(4) == null){
					dto.setKR2("-");
				}else
					dto.setKR2(rs.getString(4));
				if(rs.getString(5) == null){
					dto.setKR3("-");
				}else
					dto.setKR3(rs.getString(5));
				}
				redundancy.add(num);
				checkOut = false;
				list.add(dto);
			}
		}
			//NOTE 검색 값 있음
			else {
			rs = stmt.executeQuery("SELECT ID FROM WORDPAD WHERE NOTE = '" + NOTE +"'");
			rs.next();
			
			while(rs.next()) {
				numForNote.add(rs.getInt(1));
			}
			
			for(int i = 0; i<20; i++) {
				dto = new DTO();
				
				num = rd.nextInt(numForNote.size());
				row = numForNote.get(num);
				
				if(i >= 1) {
					while(checkOut == false) {
						num = rd.nextInt(numForNote.size());
						
						for(int j = 0; j<redundancy.size(); j++) {
							if(num != redundancy.get(j)) checkOut = true;
							else {
								checkOut = false;
								break;
							}
						}
					}
				row = numForNote.get(num);
				}
				
				rs = stmt.executeQuery("SELECT CN, JP, KR1, KR2, KR3 FROM WORDPAD WHERE ID = " + row);
				if(rs.next()) {
				if(rs.getString(1) == null){
					dto.setCN("-");
				}else
					dto.setCN(rs.getString(1));
				
					dto.setJP(rs.getString(2));
				
					dto.setKR1(rs.getString(3));
				
				if(rs.getString(4) == null){
					dto.setKR2("-");
				}else
					dto.setKR2(rs.getString(4));
				
				if(rs.getString(5) == null){
					dto.setKR3("-");
				}else
					dto.setKR3(rs.getString(5));
				}
				redundancy.add(num);
				checkOut = false;
				list.add(dto);
			}
			}
		
		}catch(SQLException e) {
			System.out.println("SQL 문 오류");
		}catch(Exception e) {
			System.out.println("그 외의 오류");
		}finally {
			if(rs != null){
				try {	rs.close();	} 
				catch (SQLException e2) {}	}
			if(rtmt != null) { 
				try {	rtmt.close();	}
				catch (SQLException e2) {}	}
			if(con != null) { 
				try {	con.close();	}
				catch (SQLException e2) {}	}
		}
		return list;
	}

	public static void main(String[] args){
		  try {
			  UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");			  
		    }  catch (Exception e) { }
		GUI f = new GUI();	}
	
} // end class

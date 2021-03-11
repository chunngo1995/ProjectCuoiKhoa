package pk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import com.mysql.cj.jdbc.JdbcConnection;
import com.mysql.cj.xdevapi.PreparableStatement;

public class MAIN {
	// attributes:
	public static Scanner sc = new Scanner(System.in);
	
//	public static Map<String, Integer> mapId = new TreeMap<String, Integer>();

	//

	// methods:
	// clear console
	public static void clearScreen() {
		for (int i = 0; i < 50; ++i)
			System.out.println();
		System.out.flush();
	}

//	check id
	public static boolean checkId(String id) throws SQLException {
		/*
		 * Check ID sinh viên <b>Return:</b> Trả về <b>true</b> nếu ID tồn tại
		 */
		Connection con = JDBCConnection.getConnection();

		try {
			PreparedStatement state = con.prepareStatement("Select * from student where id=?");
			state.setString(1, id);
			ResultSet rs = state.executeQuery();
			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			con.close();
		}
		return false;

	}

	// chuc nang 1
	public static void nhapDS() throws SQLException {
		Connection con = JDBCConnection.getConnection();

		String c = "";
		do {
//			int size = mapId.size();
			Student sv = new Student();
			do {
				// Nếu mã sinh viên trùng thì nhập lại
				System.out.println("***Nhap thong tin SV:");
				String id;
				do {
					System.out.println("->Nhap ma sinh vien: ");
					id = sc.nextLine();
					sv.setId(id);
					if (id.equalsIgnoreCase("")) {
						/*
						 * Check nếu không có giá trị nhập vào thì nhập lại
						 */
						System.out.println("Đã xảy ra lỗi mời bạn nhập lại");
					}
				} while (id.equalsIgnoreCase(""));

//				size = mapId.size() + 1;
//				mapId.put(sv.getId(), 0);
				if (checkId(sv.getId())) {
					System.out.println("\nMã sinh viên đã tồn tại, mời bạn nhập lại");
				}

			} while (checkId(sv.getId()));
			sv.nhap();// Nhập thông tin còn lại của sinh viên
			sv.setAverageScore((sv.getMaths() + sv.getPhysics() + sv.getChemistry()) / 3);
			PreparedStatement state;
			try {
				// Insert thông tin sinh viên vào DB
				state = con.prepareStatement("insert into student values(?,?,?,?,?,?,?,?,?,?)");
				state.setString(1, sv.getId());
				state.setString(2, sv.getFullName());
				state.setString(3, sv.getClassId());
				state.setString(4, sv.getAddress());
				state.setInt(5, sv.getAge());
				state.setBoolean(6, sv.isGender());
				state.setFloat(7, sv.getMaths());
				state.setFloat(8, sv.getChemistry());
				state.setFloat(9, sv.getPhysics());
				state.setFloat(10, sv.getAverageScore());
				state.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				con.close();
			}

//			listStudent.add(sv);

			System.out.println("Ban co muon nhap tiep khong: Y/N?");
			c = sc.nextLine();
			if (c.equalsIgnoreCase("n")) {
				menu();// c=n thì quay lại menu chính
				break;
			}
			nhapDS();

		} while (c.equalsIgnoreCase("y"));
	}
	//

	// Chuc nang 2: xuat danh sach theo ABC:
	public static void xuatDS() throws SQLException {
		Connection con = JDBCConnection.getConnection();
//		if (listStudent.size() == 0) {
//			System.out.println("Ban chua nhap vao danh sach!");
//		} else {
//			Collections.sort(listStudent, (a, b) -> {
//				return a.getFullName().compareTo(b.getFullName());
//			});
//			System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%-20s%-5s%-5s%-5s%-30s%n", "STT", "Ten", "Tuoi",
//					"Gioi tinh", "Dia chi", "MSSV", "Ma lop", "Toan", "Ly", "Hoa", "Diem trung binh");
//			for (int i = 0; i < listStudent.size(); i++) {
//				System.out.printf("%-5d", (i + 1));
//				listStudent.get(i).xuat();
//			}
//		}

		try {
			Statement state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = state.executeQuery("Select * from student order by fullname asc");// Trả về ResultSet sắp xếp
			if (rs.next()) { // theo tên tăng dần
				System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%-20s%-5s%-5s%-5s%-30s%n", "STT", "Ten", "Tuoi",
						"Gioi tinh", "Dia chi", "MSSV", "Ma lop", "Toan", "Ly", "Hoa", "Diem trung binh");
				rs.previous();
				int i = 0;
				while (rs.next()) {
					Student sv = new Student();
					sv.setId(rs.getString(1));
					sv.setFullName(rs.getString(2));
					sv.setClassId(rs.getString(3));
					sv.setAddress(rs.getString(4));
					sv.setAge(rs.getInt(5));
					sv.setGender(rs.getBoolean(6));
					sv.setMaths(rs.getFloat(7));
					sv.setPhysics(rs.getFloat(8));
					sv.setChemistry(rs.getFloat(9));
					sv.setAverageScore(rs.getFloat(10));
					System.out.printf("%-5d", (i + 1));
					i = i + 1;
					sv.xuat();
				}
			} else {
				System.out.println("Danh sách trống ");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s;
		do {
			System.out.println("Bạn muốn thoát khỏi màn hình hiển thị");
			s = sc.nextLine();
			if (!s.equalsIgnoreCase("y")) {
				System.out.println("Da xay ra loi! Nhap lai!");
			} else {
				menu();
			}
		} while (!s.equalsIgnoreCase("y"));
		con.close();

	}
	//

	// Chuc nang 3:
	public static void hienThiTheoHocLuc() throws SQLException {
		Connection con = JDBCConnection.getConnection();
		try {
			Statement state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ResultSet rs = null;
			
			boolean check=false;
			do {
				
				System.out.println("1.Hiển thị theo học lực Giỏi");
				System.out.println("2.Hiển thị theo học lực Khá");
				System.out.println("3.Hiển thị theo học lực TB");
				System.out.println("4.Thoát khỏi menu chức năng");
				int key=Integer.parseInt(sc.nextLine());
				switch (key) {
				case 1:
					rs = state.executeQuery("Select * from student where avgscore>=8.0 order by fullname asc ");// Trả về ResultSet sắp xếp
					if(rs.next()) {																					
						System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%-20s%-5s%-5s%-5s%-30s%n", "STT", "Ten", "Tuoi",
								"Gioi tinh", "Dia chi", "MSSV", "Ma lop", "Toan", "Ly", "Hoa", "Diem trung binh");
						rs.previous();
						int i = 0;
						while (rs.next()) {
							Student sv = new Student();
							sv.setId(rs.getString(1));
							sv.setFullName(rs.getString(2));
							sv.setClassId(rs.getString(3));
							sv.setAddress(rs.getString(4));
							sv.setAge(rs.getInt(5));
							sv.setGender(rs.getBoolean(6));
							sv.setMaths(rs.getFloat(7));
							sv.setPhysics(rs.getFloat(8));
							sv.setChemistry(rs.getFloat(9));
							sv.setAverageScore(rs.getFloat(10));
							System.out.printf("%-5d", (i + 1));
							i = i + 1;
							sv.xuat();
							}
					}
					else {
						System.out.println("Danh sách trống ");
					}
					check=false;
					break;
				case 2:
					rs = state.executeQuery("Select * from student where avgscore>=6.5 and avgscore<8.0 order by fullname asc ");// Trả về ResultSet sắp xếp
					
					if(rs.next()) {																					
						System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%-20s%-5s%-5s%-5s%-30s%n", "STT", "Ten", "Tuoi",
								"Gioi tinh", "Dia chi", "MSSV", "Ma lop", "Toan", "Ly", "Hoa", "Diem trung binh");
						rs.previous();
						int i = 0;
						while (rs.next()) {
							Student sv = new Student();
							sv.setId(rs.getString(1));
							sv.setFullName(rs.getString(2));
							sv.setClassId(rs.getString(3));
							sv.setAddress(rs.getString(4));
							sv.setAge(rs.getInt(5));
							sv.setGender(rs.getBoolean(6));
							sv.setMaths(rs.getFloat(7));
							sv.setPhysics(rs.getFloat(8));
							sv.setChemistry(rs.getFloat(9));
							sv.setAverageScore(rs.getFloat(10));
							System.out.printf("%-5d", (i + 1));
							i = i + 1;
							sv.xuat();
							}
					}
					else {
						System.out.println("Danh sách trống ");
					}
					check=false;
					break;
				case 3:
					rs = state.executeQuery("Select * from student where avgscore<6.5 order by fullname asc ");// Trả về ResultSet sắp xếp
					if(rs.next()) {																					
						System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%-20s%-5s%-5s%-5s%-30s%n", "STT", "Ten", "Tuoi",
								"Gioi tinh", "Dia chi", "MSSV", "Ma lop", "Toan", "Ly", "Hoa", "Diem trung binh");
						rs.previous();
						int i = 0;
						while (rs.next()) {
							Student sv = new Student();
							sv.setId(rs.getString(1));
							sv.setFullName(rs.getString(2));
							sv.setClassId(rs.getString(3));
							sv.setAddress(rs.getString(4));
							sv.setAge(rs.getInt(5));
							sv.setGender(rs.getBoolean(6));
							sv.setMaths(rs.getFloat(7));
							sv.setPhysics(rs.getFloat(8));
							sv.setChemistry(rs.getFloat(9));
							sv.setAverageScore(rs.getFloat(10));
							System.out.printf("%-5d", (i + 1));
							i = i + 1;
							sv.xuat();
							}
					}
					else {
						System.out.println("Danh sách trống ");
					}
					check=false;
					break;
				case 4:
					check =true;
					menu();
					break;
				default:
					System.out.println("Không có chức năng này, mời bạn chọn lại");
					check=false;
					break;
				}
			}
			while(!check);
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			con.close();
		}
		
		
	}
	//

	// Chuc nang 4:
	public static String getLastName(String name) {
		int lastSpace=name.lastIndexOf(" ");
		String lastName=name.substring(lastSpace+1);
		return lastName;
		
	}
	public static void timTheoTen() throws SQLException {
		Connection con = JDBCConnection.getConnection();
		boolean check1 = true;
		String lastName="";
		do {
		do {
			try {
				System.out.println("Mời bạn nhập tên cần tìm");
				String name=sc.nextLine().trim();
				lastName=getLastName(name);
				; // check exception, co thi catch, ko thi break khoi while;
				check1 = false;

				// break;
			} catch (Exception e) { // neu ham parseInt k the convert string sang int
				// TODO: handle exception
				System.out.println("!!!Da xay ra loi. Nhap lai!");

			}
		} while (check1);
		
		try {
			Statement state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = state.executeQuery("Select * from student");// Trả về ResultSet sắp xếp
			List<Student> listStudent = new ArrayList<Student>();
			int count = 0;
			Student sv = new Student();
			while (rs.next()) {
				if(lastName.equalsIgnoreCase(rs.getString(2))) {
					
					sv.setId(rs.getString(1));
					sv.setFullName(rs.getString(2));
					sv.setClassId(rs.getString(3));
					sv.setAddress(rs.getString(4));
					sv.setAge(rs.getInt(5));
					sv.setGender(rs.getBoolean(6));
					sv.setMaths(rs.getFloat(7));
					sv.setPhysics(rs.getFloat(8));
					sv.setChemistry(rs.getFloat(9));
					sv.setAverageScore(rs.getFloat(10));
					listStudent.add(sv);
//				System.out.printf("%-5d", (i + 1));
//				i = i + 1;
//				sv.xuat();
				}
			}
			if(listStudent.size()==0) {
				System.out.println("Không tồn tại sinh viên");
				System.out.println("Bạn có muộn tiếp tục thực hiện chức năng");
			}else {
				System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%-20s%-5s%-5s%-5s%-30s%n", "STT", "Ten", "Tuoi",
						"Gioi tinh", "Dia chi", "MSSV", "Ma lop", "Toan", "Ly", "Hoa", "Diem trung binh");
				for (int i = 0; i < listStudent.size(); i++) {
					System.out.printf("%-5d", (i + 1));
					count = count + 1;
					sv.xuat();
				}
				System.out.println("Bạn có muộn tiếp tục thực hiện chức năng");

				
			}
			
			
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}while(sc.nextLine().equalsIgnoreCase("y"));
		con.close();
		menu();
	}
	//

	// Chuc nang 5:
	public static void hienThiNam() throws SQLException {
		Connection con = JDBCConnection.getConnection();
		try {
			Statement state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = state.executeQuery("Select * from student where gender=1");// Trả về ResultSet sắp xếp
			if (rs.next()) { // theo tên tăng dần
				System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%-20s%-5s%-5s%-5s%-30s%n", "STT", "Ten", "Tuoi",
						"Gioi tinh", "Dia chi", "MSSV", "Ma lop", "Toan", "Ly", "Hoa", "Diem trung binh");
				rs.previous();
				int i = 0;
				while (rs.next()) {
					Student sv = new Student();
					sv.setId(rs.getString(1));
					sv.setFullName(rs.getString(2));
					sv.setClassId(rs.getString(3));
					sv.setAddress(rs.getString(4));
					sv.setAge(rs.getInt(5));
					sv.setGender(rs.getBoolean(6));
					sv.setMaths(rs.getFloat(7));
					sv.setPhysics(rs.getFloat(8));
					sv.setChemistry(rs.getFloat(9));
					sv.setAverageScore(rs.getFloat(10));
					System.out.printf("%-5d", (i + 1));
					i = i + 1;
					sv.xuat();
				}
			} else {
				System.out.println("Danh sách trống ");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s;
		do {
			System.out.println("Bạn muốn thoát khỏi màn hình hiển thị");
			s = sc.nextLine();
			if (!s.equalsIgnoreCase("y")) {
				System.out.println("Da xay ra loi! Nhap lai!");
			} else {
				menu();
			}
		} while (!s.equalsIgnoreCase("y"));
		con.close();
		
	}

	// Chuc nang 6:
	public static void hienThiNu() throws SQLException {
		Connection con = JDBCConnection.getConnection();
		try {
			Statement state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = state.executeQuery("Select * from student where gender=0");// Trả về ResultSet sắp xếp
			if (rs.next()) { // theo tên tăng dần
				System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%-20s%-5s%-5s%-5s%-30s%n", "STT", "Ten", "Tuoi",
						"Gioi tinh", "Dia chi", "MSSV", "Ma lop", "Toan", "Ly", "Hoa", "Diem trung binh");
				rs.previous();
				int i = 0;
				while (rs.next()) {
					Student sv = new Student();
					sv.setId(rs.getString(1));
					sv.setFullName(rs.getString(2));
					sv.setClassId(rs.getString(3));
					sv.setAddress(rs.getString(4));
					sv.setAge(rs.getInt(5));
					sv.setGender(rs.getBoolean(6));
					sv.setMaths(rs.getFloat(7));
					sv.setPhysics(rs.getFloat(8));
					sv.setChemistry(rs.getFloat(9));
					sv.setAverageScore(rs.getFloat(10));
					System.out.printf("%-5d", (i + 1));
					i = i + 1;
					sv.xuat();
				}
			} else {
				System.out.println("Danh sách trống ");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s;
		do {
			System.out.println("Bạn muốn thoát khỏi màn hình hiển thị");
			s = sc.nextLine();
			if (!s.equalsIgnoreCase("y")) {
				System.out.println("Da xay ra loi! Nhap lai!");
			} else {
				menu();
			}
		} while (!s.equalsIgnoreCase("y"));
		con.close();
		
	}
	// Chuc nang 7:
	public static String change() throws SQLException {
//		 Phương thức này dùng để thay đổi thông tin sinh viên
		String id = "";
		Connection con = JDBCConnection.getConnection();
		do {
			// Check mã sinh viên có tồn tại không
			System.out.println("Nhập mã sv cần đổi: ");
			id = sc.nextLine();
			if (!checkId(id)) {
				System.out.println("Sinh viên không tồn tại");
				System.out.println("Bạn có muốn thay đổi tiếp Y/N");
				String checkYN = sc.nextLine();
				if (checkYN.equalsIgnoreCase("n")) {
					// Nếu không tồn tại thì có muốn thực hiện tiếp chức năng hay không
					return checkYN;
				}
			}
		} while (!checkId(id));
		Boolean check = true;
		do {
			// Menu của chức năng thay đổi thông tin sinh viên
			System.out.println("Thông tin bạn cần đổi: ");
			System.out.println("1.Tuổi");
			System.out.println("2.Tên");
			System.out.println("3.Địa chỉ");
			System.out.println("4.Lớp");
			System.out.println("5.Giới tính");
			System.out.println("6.Toán");
			System.out.println("7.Lý");
			System.out.println("8.Hóa");
			System.out.println("9.Thoat");
			int key = Integer.parseInt(sc.nextLine());
			int age = 0;
			String name = "";
			String address = "";
			String classId = "";
			String gender;
			float math = 0;
			float physics = 0;
			float chemistry = 0;
			PreparedStatement state;

			switch (key) {
			case 1: {

				boolean check1 = true;
				do {
					try {
						System.out.println("->Nhap tuoi:");
						age = Integer.parseInt(sc.nextLine());
						; // check exception, co thi catch, ko thi break khoi while;
						check1 = false;

						// break;
					} catch (Exception e) { // neu ham parseInt k the convert string sang int
						// TODO: handle exception
						System.out.println("!!!Da xay ra loi. Nhap lai!");

					}
				} while (check1);
				state = con.prepareStatement("UPDATE student " + "SET age = ? " + "WHERE id = ?");
				state.setInt(1, age);
				state.setString(2, id);
				if (state.execute()) {
					System.out.println("Thay đổi thành công");
				}

				check = false;
				break;
			}
			case 2: {
				do {
					System.out.println("Nhập tên: ");
					name = sc.nextLine();
					if (name.equalsIgnoreCase("")) {
						/*
						 * Check nếu không có giá trị nhập vào thì nhập lại
						 */
						System.out.println("Đã xảy ra lỗi mời bạn nhập lại");
					}
				} while (name.equalsIgnoreCase(""));
				state = con.prepareStatement("UPDATE student " + "SET fullname = ? " + "WHERE id = ?");
				state.setString(1, name);
				state.setString(2, id);
				if (state.execute()) {
					System.out.println("Thay đổi thành công");
				}
				check = false;

				break;
			}
			case 3: {
				do {
					System.out.println("Nhập địa chỉ: ");
					address = sc.nextLine();
					if (address.equalsIgnoreCase("")) {
						/*
						 * Check nếu không có giá trị nhập vào thì nhập lại
						 */
						System.out.println("Đã xảy ra lỗi mời bạn nhập lại");
					}
				} while (address.equalsIgnoreCase(""));
				state = con.prepareStatement("UPDATE student " + "SET address = ? " + "WHERE id = ?");
				state.setString(1, address);
				state.setString(2, id);
				if (state.execute()) {
					System.out.println("Thay đổi thành công");
				}
				check = false;
				break;
			}
			case 4: {
				do {
					System.out.println("Nhập lớp: ");
					classId = sc.nextLine();
					if (classId.equalsIgnoreCase("")) {
						/*
						 * Check nếu không có giá trị nhập vào thì nhập lại
						 */
						System.out.println("Đã xảy ra lỗi mời bạn nhập lại");
					}
				} while (classId.equalsIgnoreCase(""));
				state = con.prepareStatement("UPDATE student " + "SET classid = ? " + "WHERE id = ?");
				state.setString(1, classId);
				state.setString(2, id);
				if (state.execute()) {
					System.out.println("Thay đổi thành công");
				}
				check = false;
				break;
			}
			case 5: {
				do {
					System.out.println("Nhập giới tính: ");
					gender = sc.nextLine();
					if (!gender.equalsIgnoreCase("nam") && !gender.equalsIgnoreCase("nu")) {
						/*
						 * Check nếu không có giá trị nhập vào thì nhập lại
						 */
						System.out.println("Đã xảy ra lỗi mời bạn nhập lại");
					}
				} while (!gender.equalsIgnoreCase("nam") && !gender.equalsIgnoreCase("nu"));
				boolean bogender = false;
				if (gender.equalsIgnoreCase("nam")) {
					bogender = true;
				}
				state = con.prepareStatement("UPDATE student " + "SET gender = ? " + "WHERE id = ?");
				state.setBoolean(1, bogender);
				state.setString(2, id);
				if (state.execute()) {
					System.out.println("Thay đổi thành công");
				}
				check = false;

				break;
			}
			case 6: {

				boolean check1 = true;
				do {
					try {
						System.out.println("->Nhap diem toan:");
						math = Float.parseFloat(sc.nextLine()); // check exception, co thi catch, ko thi break khoi
																// while;
						check1 = false;

						// break;
					} catch (Exception e) { // neu ham parseInt k the convert string sang int
						// TODO: handle exception
						System.out.println("!!!Da xay ra loi. Nhap lai!");

					}
				} while (check1);
				state = con.prepareStatement("UPDATE student " + "SET math = ? " + "WHERE id = ?");
				state.setFloat(1, math);
				state.setString(2, id);
				if (state.execute()) {
					System.out.println("Thay đổi thành công");
				}
				check = false;

				break;
			}
			case 7: {

				boolean check1 = true;
				do {
					try {
						System.out.println("->Nhap diem ly:");
						physics = Float.parseFloat(sc.nextLine()); // check exception, co thi catch, ko thi break khoi
																	// while;
						check1 = false;

						// break;
					} catch (Exception e) { // neu ham parseInt k the convert string sang int
						// TODO: handle exception
						System.out.println("!!!Da xay ra loi. Nhap lai!");

					}
				} while (check1);
				state = con.prepareStatement("UPDATE student " + "SET physics = ? " + "WHERE id = ?");
				state.setFloat(1, physics);
				state.setString(2, id);
				if (state.execute()) {
					System.out.println("Thay đổi thành công");
				}
				check = false;

				break;
			}
			case 8: {
				boolean check1 = true;
				do {
					try {
						System.out.println("->Nhap diem hoa:");
						chemistry = Float.parseFloat(sc.nextLine()); // check exception, co thi catch, ko thi break khoi
						// while;
						check1 = false;

						// break;
					} catch (Exception e) { // neu ham parseInt k the convert string sang int
						// TODO: handle exception
						System.out.println("!!!Da xay ra loi. Nhap lai!");

					}
				} while (check1);
				state = con.prepareStatement("UPDATE student " + "SET chemistry = ? " + "WHERE id = ?");
				state.setFloat(1, chemistry);
				state.setString(2, id);
				if (state.execute()) {
					System.out.println("Thay đổi thành công");
				}
				check = false;

				break;
			}
			case 9:
				check = true;
				break;

			default:
				System.out.println("Khong co chuc nang nay!");
				check = false;
				break;
			}
		} while (!check);
		System.out.println("Bạn có muốn dùng tiếp chức năng này");
		return sc.nextLine();
	}

// Chuc nang 8
	public static String deleteStudent() throws SQLException {
		Connection con = JDBCConnection.getConnection();
		String id = "";
		do {
			System.out.println("Nhập mã sv cần xóa: ");
			id = sc.nextLine();
			if (!checkId(id)) {
				System.out.println("Sinh viên không tồn tại");
				System.out.println("Bạn có muốn xóa tiếp Y/N");
				String checkYN = sc.nextLine();
				if (checkYN.equalsIgnoreCase("n")) {
					return checkYN;
				}
			}

		} while (!checkId(id));
//		for (int i = 0; i < listStudent.size(); i++) {
//			if (listStudent.get(i).getId().equals(id)) {
//				listStudent.remove(i);
//				mapId.remove(id);
//			}
//		}
		PreparedStatement state = con.prepareStatement("DELETE FROM student " + " WHERE id=?");
		state.setString(1, id);
		if (!state.execute()) {
			System.out.println("Xóa thành công");

		} else
			System.out.println("Xóa không thành công");
		con.close();
		System.out.println("Bạn có muốn xóa tiếp Y/N");
		return sc.nextLine();

	}

	// menu chuong trinh
	public static void menu() throws SQLException {
		String check = "y";
		do {

			System.out.println("**********************************************");
			System.out.println("**    	CHUONG TRINH QUAN LY SINH VIEN    	");
			System.out.println();
			System.out.println("**************MENU CHUONG TRINH***************");
			System.out.println("**	1. Nhap thong tin sinh vien.			");
			System.out.println("**	2. Hien thi danh sach sinh vien.		");
			System.out.println("**	3. Hien thi sinh vien theo hoc luc.		");
			System.out.println("** 	4. Tim sinh vien theo ten.				");
			System.out.println("**	5. Hien thi thong tin nam.			");
			System.out.println("**	6. Hien thi thong tin nu.			");
			System.out.println("**	7. Thay đổi thông tin sinh viên.			");
			System.out.println("**	8. Xóa thông tin sinh viên.			");
			System.out.println("** 	9. Thoat chuong trinh.					");
			System.out.println("**********************************************");
			System.out.println("** 	Nhap lua chon cua ban:					");

			int key = sc.nextInt();
			sc.nextLine();

			switch (key) {
			case 1:
				System.out.println("Ban da chon nhap thong tin sinh vien:");
				nhapDS();

				break;
			case 2:
				System.out.println("Ban da chon hien thi danh sach sinh vien:");
				xuatDS();

				break;
			case 3:
				System.out.println("Ban da chon hien thi sinh vien theo hoc luc:");
				hienThiTheoHocLuc();

				break;
			case 4:
				System.out.println("Ban da chon tim kiem sinh vien theo ten:");
				timTheoTen();

				break;
			case 5:
				System.out.println("Ban da chon hien thi thong tin nam:");
				hienThiNam();
				break;
			case 6:
				System.out.println("Ban da chon hien thi thong tin nu:");
				hienThiNu();
				break;
			case 7:
				Connection con = JDBCConnection.getConnection();
				Statement state = con.createStatement();

				String choice = "";
				System.out.println("Bạn có muốn thay đổi thông tin??");
				ResultSet rs = state.executeQuery("select * from student");

				if (sc.nextLine().equalsIgnoreCase("y")) {

					if (!rs.next()) {
						System.out.println("Danh sách ko có sinh viên nào");
						menu();
					} else {
						do {
							choice = change();

						} while (choice.equalsIgnoreCase("y"));
						if (choice.equalsIgnoreCase("n")) {
							menu();
						}
					}
				} else {
					menu();
				}

				break;
			case 8:
				Connection con1 = JDBCConnection.getConnection();
				Statement state1 = con1.createStatement();
				System.out.println("Xóa thông tin sinh viên");
				String choice2 = "";
				System.out.println("Bạn có muốn xoa thông tin??");
				ResultSet rs1 = state1.executeQuery("select * from student");
				if (sc.nextLine().equalsIgnoreCase("y")) {
					if (!rs1.next()) {
						System.out.println("Danh sách ko có sinh viên nào");
						menu();
					} else {
						do {
							choice2 = deleteStudent();
							if (!rs1.next()) {
								System.out.println("Danh sách không có sinh viên nào");
								menu();
								break;
							}
						} while (choice2.equalsIgnoreCase("y"));
						if (choice2.equalsIgnoreCase("n")) {
							menu();
						}
					}
				} else {
					menu();
				}

				break;
			case 9:
				System.out.println("Ban da chon thoat chuong trinh:");
				String c = "";
				do {
					System.out.println("Ban co muon thoat chuong trinh: Y/N?");
					c = sc.nextLine();
					if (c.equalsIgnoreCase("y")) {
						clearScreen();
						return; // thoat khoi ham
					} else if (c.equalsIgnoreCase("n")) {
						break; // break vong do-while
					} else {
						System.out.println("!!!Da xay ra loi. Nhap lai!");
					}
				} while (c.equalsIgnoreCase("y") != true && c.equalsIgnoreCase("n") != true); // c!= y va c!=n
				// sc.nextLine();// tam dung chuong trinh
				check = "n";
				break; // break case 5
			default:
				System.out.println("Khong co chuc nang nay!");
				// sc.nextLine();// tam dung chuong trinh
				break;
			}
		} while (check.equalsIgnoreCase("n"));
	}
	//

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

		menu();

	}

}

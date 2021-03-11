package pk;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Student {

	// attributes;
	private String fullName;
	private String id;
	private String classId;
	private Integer age;
	private String address;
	private Boolean gender; // male = true; female = false;
	private Float maths;
	private Float physics;
	private Float chemistry;
	private Float averageScore;

//	public static Map<String, Integer> mapId = new TreeMap<String, Integer>();
	// size cua map = so luong key;
	//

	// getter- setter
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public float getMaths() {
		return maths;
	}

	public void setMaths(float maths) {
		this.maths = maths;
	}

	public float getPhysics() {
		return physics;
	}

	public void setPhysics(float physics) {
		this.physics = physics;
	}

	public float getChemistry() {
		return chemistry;
	}

	public void setChemistry(float chemistry) {
		this.chemistry = chemistry;
	}

	public void setAverageScore(float averageScore) {
		this.averageScore = averageScore;
	}

	public float getAverageScore() {
		return averageScore;
	}
	//

	// constructor:
	public Student() {
		super();
	}
	//

	// methods:
	public void nhap() {

		Scanner sc = new Scanner(System.in);
		boolean c = true;
		//
//		do {
//			System.out.println("->Nhap MSSV:");
//			String s = sc.nextLine();
//			int size = mapId.size();
//			mapId.put(s, 0); //
//			if (mapId.size() > size) { // mssv ko bi trung, nen duoc add vao map;
//				setId(s);
//				c = false;
//				// break;
//			} else {
//				System.out.println("!!!Ma so sinh vien da ton tai! Nhap lai!");
//			}
//		} while (c);

		//
		String name;
		do {
			System.out.println("->Nhap ten:");
			name = sc.nextLine();
			setFullName(name);
			//
			if (name.equalsIgnoreCase("")) {
				System.out.println("Đã xảy ra lỗi mời bạn nhập lại");
			}
		} while (name.equalsIgnoreCase(""));
		String classid;
		do {
			System.out.println("->Nhap ma lop");
			classid = sc.nextLine();
			
			setClassId(classid);
			//
			if (classid.equalsIgnoreCase("")) {
				System.out.println("Đã xảy ra lỗi mời bạn nhập lại");
			}
		} while (classid.equalsIgnoreCase(""));
		String add;
		do {
			System.out.println("->Nhap địa chỉ");
			add = sc.nextLine();
			
			setAddress(add);
			//
			if (add.equalsIgnoreCase("")) {
				System.out.println("Đã xảy ra lỗi mời bạn nhập lại");
			}
		} while (add.equalsIgnoreCase(""));
		
		
		
		
		//
		// kiem tra input tuoi
		c = true;
		while (c) {
			try {
				System.out.println("->Nhap tuoi:");
				int d = Integer.parseInt(sc.nextLine()); // check exception, co thi catch, ko thi break khoi while;
				setAge(d);
				c = false;
				// break;
			} catch (Exception e) { // neu ham parseInt k the convert string sang int
				// TODO: handle exception
				System.out.println("!!!Da xay ra loi. Nhap lai!");
			}
		}
		//
		
		//
		// kiem tra input gioi tinh
		String s = "";
		do {
			System.out.println("->Nhap gioi tinh:");
			s = sc.nextLine();
			if (s.equalsIgnoreCase("nam")) {
				setGender(true);
			} else if (s.equalsIgnoreCase("nu")) {
				setGender(false);
			} else {
				System.out.println("!!!Da xay ra loi. Nhap lai!");
			}
		} while (!s.equalsIgnoreCase("nam") && !s.equalsIgnoreCase("nu")); // ca 2 deu sai
		//
		// kiem tra input diem toan
		while (true) {
			try {
				System.out.println("->Nhap diem toan:");
				float f = Float.parseFloat(sc.nextLine()); // check exception, co thi catch, ko thi break khoi while;
				setMaths(f);
				break;
			} catch (Exception e) { // neu ham parseInt k the convert string sang float
				// TODO: handle exception
				System.out.println("!!!Da xay ra loi. Nhap lai!");
			}
		}
		//
		// kiem tra input diem ly
		while (true) {
			try {
				System.out.println("->Nhap diem ly:");
				float f = Float.parseFloat(sc.nextLine()); // check exception, co thi catch, ko thi break khoi while;
				setPhysics(f);
				break;
			} catch (Exception e) { // neu ham parseInt k the convert string sang float
				// TODO: handle exception
				System.out.println("!!!Da xay ra loi. Nhap lai!");
			}
		}
		//
		// kiem tra input diem hoa
		while (true) {
			try {
				System.out.println("->Nhap diem hoa:");
				float f = Float.parseFloat(sc.nextLine()); // check exception, co thi catch, ko thi break khoi while;
				setChemistry(f);
				break;
			} catch (Exception e) { // neu ham parseInt k the convert string sang float
				// TODO: handle exception
				System.out.println("!!!Da xay ra loi. Nhap lai!");
			}
		}
	}

	//
	public void xuat() {
		String strGender = "";
		if (isGender() == true) {
			strGender = "nam";
		} else {
			strGender = "nu";
		}
		System.out.printf("%-20s%-20d%-20s%-20s%-20s%-20s%-5.1f%-5.1f%-5.1f%-30.1f", getFullName(), getAge(), strGender,
				getAddress(), getId(), getClassId(), getMaths(), getPhysics(), getChemistry(), getAverageScore());
		System.out.println();
	}
}

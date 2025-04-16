package lib;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;



public class Employee {

	public enum EmployeeGrade {
	grade_1,
	grade_2,
	grade_3
}
	public enum Gender {
		MALE,
		FEMALE
	}
private static final int grade_1_salary = 3000000;
private static final int grade_2_salary = 5000000;
private static final int grade_3_salary = 7000000;
private static final double foreigner_salary = 1.5;

	private String employeeId;
	private String firstName;
	private String lastName;
	private String idNumber;
	private String address;
	
	private LocalDate joinDate;
    private int monthWorkingInYear;
	
	private boolean isForeigner;
	private Gender gender; 
	
	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;
	
	private Spouse spouse;
    private List<Child> children;
	
	public Employee(String employeeId, String firstName, String lastName, String idNumber, String address,
                    LocalDate joinDate, boolean isForeigner, Gender gender) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.address = address;
        this.joinDate = joinDate;
        this.isForeigner = isForeigner;
        this.gender = gender;

        children = new LinkedList<>();
    }
	
	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3: 7.000.000 per bulan)
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 */
	
	public void setMonthlySalary(int grade) {	
		int newSalary;

		switch (grade) {
			case grade_1:
				newSalary = grade_1_salary;
				break;
			case grade_2:
				newSalary = grade_2_salary;
				break;
			case grade_3:
				newSalary = grade_3_salary;
				break;
			default:
				throw new IllegalArgumentException("employee grade tidak diketahui: " + grade);
		}

		if (isForeigner) {
			newSalary = (int) (newSalary * foreigner_salary);
		}

		this.monthlySalary = newSalary;
		}
	
		public void setAnnualDeductible(int deductible) {
			this.annualDeductible = deductible;
		}
	
		public void setAdditionalIncome(int income) {
			this.otherMonthlyIncome = income;
		}
	
		public void setSpouse(String name, String idNumber) {
			this.spouse = new Spouse(name, idNumber);
		}
	
		public void addChild(String name, String idNumber) {
			children.add(new Child(name, idNumber));
		}
	
	
	private int calculateMonthWork() {
    LocalDate currentDate = LocalDate.now();
    if (currentDate.getYear() == joinDate.getYear()) {
        return currentDate.getMonthValue() - joinDate.getMonthValue();
    }
    return 12;
}
	
	
	public int getAnnualIncomeTax() {
		//Menghitung berapa lama pegawai bekerja dalam setahun ini, jika pegawai sudah bekerja dari tahun sebelumnya maka otomatis dianggap 12 bulan.
    monthWorkingInYear = calculateMonthWork();
    boolean hasNoSpouse = (spouse == null);
    return TaxFunction.calculateTax(
        monthlySalary, otherMonthlyIncome,
        monthWorkingInYear, annualDeductible,
        hasNoSpouse, children.size()
    );
}


	public static class Person {
        protected String name;
        protected String idNumber;

        public Person(String name, String idNumber) {
            this.name = name;
            this.idNumber = idNumber;
        }
    }

    public static class Spouse extends Person {
        public Spouse(String name, String idNumber) {
            super(name, idNumber);
        }
    }

    public static class Child extends Person {
        public Child(String name, String idNumber) {
            super(name, idNumber);
        }
    }
}

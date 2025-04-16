package lib;

import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;



public class Employee {

	public enum EmployeeGrade {
    grade_1(3000000),
    grade_2(5000000),
    grade_3(7000000);

    private final int baseSalary;

    EmployeeGrade(int baseSalary) {
        this.baseSalary = baseSalary;
    }

    public int getBaseSalary() {
        return baseSalary;
    }
}
	public enum Gender {
		MALE,
		FEMALE
	}
private static final double foreigner_salary = 1.5;

	private String employeeId;
	private String firstName;
	private String lastName;
	private String idNumber;
	private String address;
	
	private LocalDate joinDate;
    int monthWorkedThisYear = calculateMonthWork();
	
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
	
	
	public void setMonthlySalary(EmployeeGrade grade) {
    int newSalary = grade.getBaseSalary();
    if (isForeigner) {
        newSalary *= foreigner_salary;
    }
    this.monthlySalary = newSalary;
}
	
		public void setAnnualDeductible(int deductible) {
			this.annualDeductible = deductible;
		}
	
		public void setAdditionalIncome(int income) {
			this.otherMonthlyIncome = income;
		}
	
		public void setSpouse(Person spouse) {
		this.spouse = new Spouse(spouse.name, spouse.idNumber);
		}

		public void addChild(Person child) {
			children.add(new Child(child.name, child.idNumber));
		}

	
	
	private int calculateMonthWork() {
    LocalDate currentDate = LocalDate.now();
    if (currentDate.getYear() == joinDate.getYear()) {
        return currentDate.getMonthValue() - joinDate.getMonthValue();
    }
    return 12;
}
	
	
	public int getAnnualIncomeTax() {
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

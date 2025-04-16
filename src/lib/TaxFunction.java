package lib;

public class TaxFunction {

	
	/**
	 * Fungsi untuk menghitung jumlah pajak penghasilan pegawai yang harus dibayarkan setahun.
	 * 
	 * Pajak dihitung sebagai 5% dari penghasilan bersih tahunan (gaji dan pemasukan bulanan lainnya dikalikan jumlah bulan bekerja dikurangi pemotongan) dikurangi penghasilan tidak kena pajak.
	 * 
	 * Jika pegawai belum menikah dan belum punya anak maka penghasilan tidak kena pajaknya adalah Rp 54.000.000.
	 * Jika pegawai sudah menikah maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000.
	 * Jika pegawai sudah memiliki anak maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000 per anak sampai anak ketiga.
	 * 
	 */
	
    private static final double tax_rate = 0.05;
    private static final int non_tax_income = 54000000;
    private static final int deduction_marriage = 4500000;
    private static final int deduction_child = 1500000;
    private static final int max_children_for_deduction= 3;
	
	
		public static int calculateTax(FinancialDetails financialDetails, FamilyStatus familyStatus, int numberOfMonthWorking) {
			validateMonthWorking(numberOfMonthWorking);

			int numberOfChildren = limitChildrenForDeduction(familyStatus.getNumberOfChildren());
			int nonTaxableIncome = calculateNonTaxableIncome(familyStatus.isMarried(), numberOfChildren);
			int totalAnnualIncome = calculateTotalAnnualIncome(financialDetails, numberOfMonthWorking);
			int taxableIncome = totalAnnualIncome - financialDetails.getDeductible() - nonTaxableIncome;

			return calculateFinalTax(taxableIncome);
		}

		private static void validateMonthWorking(int numberOfMonthWorking) {
			if (numberOfMonthWorking > 12) {
				System.err.println("More than 12 months working per year");
			}
		}

		private static int limitChildrenForDeduction(int numberOfChildren) {
			return Math.min(numberOfChildren, max_children_for_deduction);
		}

		private static int calculateNonTaxableIncome(boolean isMarried, int numberOfChildren) {
			int nonTaxable = non_tax_income;
			if (isMarried) {
				nonTaxable += deduction_marriage;
			}
			nonTaxable += numberOfChildren * deduction_child;
			return nonTaxable;
		}

		private static int calculateTotalAnnualIncome(FinancialDetails financialDetails, int monthsWorked) {
			return (financialDetails.getMonthlySalary() + financialDetails.getOtherMonthlyIncome()) * monthsWorked;
		}

		private static int calculateFinalTax(int taxableIncome) {
			if (taxableIncome <= 0) {
				return 0;
			}
			return (int) Math.round(tax_rate * taxableIncome);
		}
    }
			 
	
	
		public class FinancialDetails {
		private final int monthlySalary;
		private final int otherMonthlyIncome;
		private final int deductible;

		public FinancialDetails(int monthlySalary, int otherMonthlyIncome, int deductible) {
			this.monthlySalary = monthlySalary;
			this.otherMonthlyIncome = otherMonthlyIncome;
			this.deductible = deductible;
		}

		public int getMonthlySalary() {
			return monthlySalary;
		}

		public int getOtherMonthlyIncome() {
			return otherMonthlyIncome;
		}

		public int getDeductible() {
			return deductible;
		}
	}
		public class FamilyStatus {
		private final boolean isMarried;
		private final int numberOfChildren;

		public FamilyStatus(boolean isMarried, int numberOfChildren) {
			this.isMarried = isMarried;
			this.numberOfChildren = numberOfChildren;
		}

		public boolean isMarried() {
			return isMarried;
		}

		public int getNumberOfChildren() {
			return numberOfChildren;
		}
	}
}

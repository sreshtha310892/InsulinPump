package bin;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class InsulinGlucagonCalculation {
	
	public static BigDecimal weight = new BigDecimal(70.00);
		
	//Calculation of total dosage
	public static BigDecimal dose(BigDecimal sugar, BigDecimal bloodsugarLevel){
		BigDecimal requirement = weight.multiply(new BigDecimal (0.55));
		BigDecimal carbohydrateCoverageRatio = (new BigDecimal(500.00).divide(requirement,2,RoundingMode.HALF_EVEN));
		BigDecimal insulinSensitivity = (new BigDecimal(1800.00).divide(requirement,2,RoundingMode.HALF_EVEN));
		BigDecimal insulinDose = sugar.divide(carbohydrateCoverageRatio,2,RoundingMode.HALF_EVEN).abs();;
		BigDecimal difference = (bloodsugarLevel.subtract(BloodSugar.initialValue)).abs();
		BigDecimal correctionDose = difference.divide(insulinSensitivity,2,RoundingMode.HALF_EVEN);
		BigDecimal totalDose = insulinDose.add(correctionDose);
		return totalDose;
	}
	
}

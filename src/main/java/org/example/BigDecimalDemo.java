package org.example;

import java.math.BigDecimal;
import java.util.*;

public class BigDecimalDemo {
    BigDecimal total;
    BigDecimal other;
    BigDecimal packageFee;
    BigDecimal shipFee;

    private static final List<String> PACKAGE_BENEFIT = Arrays.asList("packageBenefitCode1", "packageBenefitCode2");
    private static final List<String> SHIP_BENEFIT = Arrays.asList("shipFeeBenefitCode1", "shipFeeBenefitCode2");

    public void cal(Map<String, String> params, List<FeeDto> feeList) {
        total = BigDecimal.ZERO;
        other = BigDecimal.ZERO;

        BigDecimal defaultValue = BigDecimal.ZERO;

        boolean hasPackageBenefit = PACKAGE_BENEFIT.contains(params.get("packageBenefitCode"));
        boolean hasShipBenefit = SHIP_BENEFIT.contains(params.get("shipFeeBenefitCode"));

        boolean isAddPackageFee = false;
        boolean isAddShipFee = false;

        for (FeeDto fee : feeList) {
            BigDecimal feeFare = Optional.ofNullable(fee.fare).orElse(defaultValue);
            if (fee.type.equals("package")) {
                isAddPackageFee = true;
                if (null == packageFee) {
                    packageFee = feeFare;
                }
                if (!hasPackageBenefit) {
                    total = total.add(packageFee);
                }
            } else if (fee.type.equals("ship")) {
                isAddShipFee = true;
                if (null == shipFee) {
                    shipFee = feeFare;
                }
                if (!hasShipBenefit) {
                    total = total.add(shipFee);
                }
            } else {
                other = other.add(feeFare);
            }
        }
        if (!isAddPackageFee && !hasPackageBenefit && packageFee != null) {
            total = total.add(packageFee);
        }
        if (!isAddShipFee && !hasShipBenefit && shipFee != null) {
            total = total.add(shipFee);
        }
        total = total.add(other);
    }

    public static void main(String[] args) {
        BigDecimalDemo demo = new BigDecimalDemo();
        demo.cal(new HashMap<>(), new ArrayList<>());
    }
}

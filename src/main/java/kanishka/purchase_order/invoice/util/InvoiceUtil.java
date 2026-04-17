package kanishka.purchase_order.invoice.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class InvoiceUtil {

    public String formatINR(BigDecimal val) {
        if (val == null) return "₹ 0.00";

        val = val.setScale(2, RoundingMode.HALF_UP);

        String[] parts = val.toString().split("\\.");
        String integerPart = parts[0];
        String decimalPart = parts[1];

        boolean isNegative = integerPart.startsWith("-");
        if (isNegative) {
            integerPart = integerPart.substring(1);
        }

        int len = integerPart.length();

        if (len <= 3) {
            return (isNegative ? "-₹ " : "₹ ") + integerPart + "." + decimalPart;
        }

        String lastThree = integerPart.substring(len - 3);
        String remaining = integerPart.substring(0, len - 3);

        StringBuilder formatted = new StringBuilder();

        while (remaining.length() > 2) {
            formatted.insert(0, "," + remaining.substring(remaining.length() - 2));
            remaining = remaining.substring(0, remaining.length() - 2);
        }

        formatted.insert(0, remaining);

        formatted.append(",").append(lastThree);

        return (isNegative ? "-₹ " : "₹ ") + formatted + "." + decimalPart;
    }


    public String convertToWords(BigDecimal amount) {
        if (amount == null) return "Zero Rupees Only";

        amount = amount.setScale(2, java.math.RoundingMode.HALF_UP);

        long rupees = amount.longValue();
        int paise = amount.remainder(BigDecimal.ONE)
                .movePointRight(2)
                .intValue();

        StringBuilder result = new StringBuilder();

        if (rupees == 0) {
            result.append("Zero Rupees");
        } else {
            result.append(convert(rupees)).append(" Rupees");
        }

        if (paise > 0) {
            result.append(" and ")
                    .append(convert(paise))
                    .append(" Paise");
        }

        result.append(" Only");

        return result.toString().replaceAll("\\s+", " ").trim();
    }

    private String convert(long n) {
        String[] units = {
                "", "One", "Two", "Three", "Four", "Five", "Six",
                "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve",
                "Thirteen", "Fourteen", "Fifteen", "Sixteen",
                "Seventeen", "Eighteen", "Nineteen"
        };

        String[] tens = {
                "", "", "Twenty", "Thirty", "Forty", "Fifty",
                "Sixty", "Seventy", "Eighty", "Ninety"
        };

        if (n < 20) return units[(int) n];

        if (n < 100)
            return tens[(int) n / 10] + " " + units[(int) n % 10];

        if (n < 1000)
            return units[(int) n / 100] + " Hundred " + convert(n % 100);

        if (n < 100000)
            return convert(n / 1000) + " Thousand " + convert(n % 1000);

        if (n < 10000000)
            return convert(n / 100000) + " Lakh " + convert(n % 100000);

        return convert(n / 10000000) + " Crore " + convert(n % 10000000);
    }
}
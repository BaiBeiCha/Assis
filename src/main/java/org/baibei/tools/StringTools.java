package org.baibei.tools;

public class StringTools {

    public static String append(String[] strings, int from, int to) {
        StringBuffer sb = new StringBuffer();

        for (int i = from; i < to; i++) {
            sb.append(strings[i]).append(" ");
        }

        return sb.toString().trim();
    }

    public static String append(String[] strings) {
        return append(strings, 0, strings.length);
    }

    public static String JsonToStringValue(String input) {
        return input.replace("\"", "")
                .replace("{", "")
                .replace("}", "")
                .split(":")[1]
                .trim();
    }

    public static String appendLetters(String[] strings, int from, int to) {
        StringBuffer sb = new StringBuffer();

        for (int i = from; i < to; i++) {
            if (strings[i].length() == 1) {
                sb.append(strings[i]);
            } else {
                return sb.toString();
            }
        }

        return sb.toString().trim();
    }

    public static String appendInOne(String[] strings, int from, int to) {
        StringBuffer sb = new StringBuffer();

        for (int i = from; i < to; i++) {
            sb.append(strings[i]);
        }

        return sb.toString();
    }

    public static String replaceNums(String string) {
        return string.replaceAll("owe", "0")
                .replaceAll("ten", "10")
                .replaceAll("eleven", "11")
                .replaceAll("twelve", "12")
                .replaceAll("thirteen", "13")
                .replaceAll("fourteen", "14")
                .replaceAll("fifteen", "15")
                .replaceAll("sixteen", "16")
                .replaceAll("seventeen", "17")
                .replaceAll("eighteen", "18")
                .replaceAll("nineteen", "19")
                .replaceAll("twenty", "20")
                .replaceAll("thirty", "30")
                .replaceAll("fourty", "40")
                .replaceAll("fifty", "50")
                .replaceAll("sixty", "60")
                .replaceAll("seventy", "70")
                .replaceAll("eighty", "80")
                .replaceAll("ninety", "90")
                .replaceAll("one", "1")
                .replaceAll("two", "2")
                .replaceAll("three", "3")
                .replaceAll("four", "4")
                .replaceAll("five", "5")
                .replaceAll("six", "6")
                .replaceAll("seven", "7")
                .replaceAll("eight", "8")
                .replaceAll("nine", "9");
    }
}

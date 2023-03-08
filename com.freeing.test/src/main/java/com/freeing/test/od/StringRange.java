package com.freeing.test.od;

/**
 * 范围
 *
 * @author yanggy
 */
public class StringRange {

//    public static String getAndDelFromRange(String ranges, int value) {
//        String[] splitArr = ranges.split(",");
//        int index = -1;
//        int i = 0;
//        for (String split : splitArr) {
//            String[] item = split.split("-");
//            if (item.length == 1) {
//                if (value == Integer.parseInt(item[0])) {
//                    index = i++;
//                    break;
//                }
//            } else {
//                if (value >= Integer.parseInt(item[0]) && value <= Integer.parseInt(item[1])) {
//                    index = i++;
//                    break;
//                }
//            }
//        }
//
//        if (index == -1) {
//            return "异常了";
//        }
//        StringBuilder builder = new StringBuilder();
//        for (int j = 0; j < splitArr.length; j++) {
//            if (j != index) {
//                builder.append(splitArr[j]).append(",");
//            } else {
//                String[] split = splitArr[j].split("-");
//                if (split.length == 1) {
//                    continue;
//                } else {
//                    builder.append(split[0]).append("-").append(value - 1);
//                }
//            }
//        }
//    }
}

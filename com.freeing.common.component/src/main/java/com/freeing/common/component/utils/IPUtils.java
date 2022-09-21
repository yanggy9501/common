package com.freeing.common.component.utils;

import com.freeing.common.component.constants.NumConstants;
import com.freeing.common.component.constants.StrPool;
import sun.net.util.IPAddressUtil;

import java.math.BigInteger;

/**
 * IP 工具类
 *
 * @author yanggy
 */
public class IPUtils {
    /**
     * 判断一字符串是否为IP地址
     *
     * @param ipStr IP地址
     * @return boolean
     */
    public static boolean isIpAddress(String ipStr) {
        return IPAddressUtil.isIPv4LiteralAddress(ipStr) || IPAddressUtil.isIPv6LiteralAddress(ipStr);
    }

    public static IpVersion getIpVersion(String ipStr) {
        if (IPAddressUtil.isIPv4LiteralAddress(ipStr)) {
            return IpVersion.IPV4;
        } else if (IPAddressUtil.isIPv6LiteralAddress(ipStr)) {
            return IpVersion.IPV6;
        } else {
            return IpVersion.NULL;
        }
    }

    /***
     * 获取下一个连续 IP
     *
     * @param ipStr IP 字符串
     * @return IP
     */
    public static String nextIpAddress(String ipStr) {
        return nextIpAddressOfStep(ipStr, 1);
    }

    /***
     * 获取下一个指定步长的 IP 地址
     *
     * @param ipStr IP 字符串
     * @param step 步长
     * @return IP
     */
    public static String nextIpAddressOfStep(String ipStr, int step) {
        BigInteger bigInteger = ipAddressToBigInteger(ipStr);
        BigInteger next = bigInteger.add(BigInteger.valueOf(step));
        return bigIntegerToIpAddress(next, getIpVersion(ipStr));
    }

    /**
     * 将 IP 字符串转换为 BigInteger
     *
     * @param ipStr IP 字符串
     * @return IP 字符串的 BigInteger 表示
     */
    public static BigInteger ipAddressToBigInteger(String ipStr) {
        checkIpAddress(ipStr);
        BigInteger ipBigInteger = new BigInteger(StrPool.ZERO);
        // 判断是否是 ipv4, 如果不是则可能是 ipv6，ipv6 在standardizeIpv6Address 方法中会做检查
        boolean iPv4AddressFlag = IPAddressUtil.isIPv4LiteralAddress(ipStr);
        // ip 地址每小段的二进制位长度
        int segmentLength = iPv4AddressFlag ?
            NumConstants.IPV4_SEGMENT_BIT_LENGTH : NumConstants.IPV6_SEGMENT_BIT_LENGTH;
        // ip 地址段的进制表示
        int ipRadix = iPv4AddressFlag ? NumConstants.RADIX_10 : NumConstants.RADIX_16;
        // ip 地址段的分隔符
        String separator = iPv4AddressFlag ? StrPool.BACK_SLASH_DOT : StrPool.COLON;

        String[] ipSegmentArr = iPv4AddressFlag ?
            ipStr.split(separator) : standardizeIpv6Address(ipStr).split(separator);
        for (String ipSegment : ipSegmentArr) {
            ipBigInteger = ipBigInteger
                .shiftLeft(segmentLength)
                .or(new BigInteger(String.valueOf(Integer.parseInt(ipSegment, ipRadix))));
        }
        return ipBigInteger;
    }

    /**
     * 将简写的 ipv6 地址展开为标准的 ipv6 地址
     *
     * @param ipv6Address ipv6
     * @return ipv6 标准格式字符串
     */
    public static String standardizeIpv6Address(String ipv6Address) {
        checkIpv6Address(ipv6Address);
        if (!ipv6Address.contains(StrPool.DOUBLE_COLON)) {
            return ipv6Address;
        }
        String[] ipv6SegmentArr = ipv6Address.split(StrPool.DOUBLE_COLON);

        StringBuilder standardIpv6 = new StringBuilder();

        // "a::1:1" 或 "a::"
        String leftSegment = ipv6SegmentArr[0];
        int leftSegmentCnt = StringUtils.countMatches(leftSegment, StrPool.COLON) + 1;
        int rightSegmentCnt = 0;
        String rightSegment = StrPool.EMPTY;
        if (ipv6SegmentArr.length > 1) {
            rightSegment = ipv6SegmentArr[1];
            rightSegmentCnt = StringUtils.countMatches(rightSegment, StrPool.COLON) + 1;
        }

        standardIpv6.append(leftSegment);

        int padLength = NumConstants.IPV6_TOTAL_SEGMENT - leftSegmentCnt - rightSegmentCnt ;
        for (int i = padLength; i > 0 ; i--) {
            standardIpv6.append(StrPool.COLON).append(StrPool.ZERO);
        }

        if (StringUtils.isNotEmpty(rightSegment)) {
            standardIpv6.append(StrPool.COLON).append(rightSegment);
        }
        return standardIpv6.toString();
    }

    /**
     * 将 BigInteger 转换为 IP 字符串
     *
     * @param bigInteger BigInteger
     * @param ipVersion IP 版本
     * @return IP 字符串
     */
    public static String bigIntegerToIpAddress(BigInteger bigInteger, IpVersion ipVersion) {
        int radix;
        String separator;
        int totalSegment;
        if (ipVersion.equals(IpVersion.IPV4)) {
            radix = NumConstants.RADIX_2;
            totalSegment = NumConstants.NUM_4;
            separator = StrPool.DOT;
        } else if (ipVersion.equals(IpVersion.IPV6)){
            radix = NumConstants.RADIX_16;
            totalSegment = NumConstants.NUM_8;
            separator = StrPool.COLON;
        } else {
            throw new IllegalArgumentException("IP version is error.");
        }
        // ipv4 二进制，ipv6 十六进制
        String ipOfRadix = bigInteger.toString(radix);

        // ipv4 不足 【32】位2进制，或 ipv6 【32】位16进制，要在前面补0，如 127 -> 0111 1111 前置 0 不存在，需要补
        if (ipOfRadix.length() <= NumConstants.NUM_32) {
            ipOfRadix = StringUtils.leftPad(ipOfRadix, NumConstants.NUM_32 , StrPool.ZERO);
        } else {
            throw new IllegalArgumentException(ipOfRadix + " of BigInteger can not convert IP address.");
        }
        String[] ipSegmentArr = new String[totalSegment];
        for (int i = 0; i < totalSegment; i++) {
            // ipv4 32位2进制 4 段，每段 8个二进制；ipv6 32位16进制 8 段，每段 4 个16进制，totalSegment
            if (ipVersion.equals(IpVersion.IPV4)) {
                String ipSegment = ipOfRadix.substring(i << 3, (i + 1)  << 3);
                ipSegmentArr[i] = String.valueOf(Integer.parseInt(ipSegment, radix));

            } else {
                String ipSegment = ipOfRadix.substring(i << 2, (i + 1) << 2);
                ipSegmentArr[i] = Integer.toHexString(Integer.parseInt(ipSegment, radix));
            }
        }
        return String.join(separator, ipSegmentArr);
    }

    public static void checkIpv6Address(String ipv6Address) {
        if (!IPAddressUtil.isIPv6LiteralAddress(ipv6Address)) {
            throw new IllegalArgumentException(ipv6Address + " is not a ipv6 address.");
        }
    }

    public static void checkIpv4Address(String ipv4Address) {
        if (!IPAddressUtil.isIPv4LiteralAddress(ipv4Address)) {
            throw new IllegalArgumentException(ipv4Address + " is not a ipv4 address.");
        }
    }

    public static void checkIpAddress(String ipAddress) {
        if (!isIpAddress(ipAddress)) {
            throw new IllegalArgumentException(ipAddress + " is not a ip address.");
        }
    }

    /**
     * IP 版本
     */
    public enum IpVersion{
        IPV4,
        IPV6,
        NULL
    }
}

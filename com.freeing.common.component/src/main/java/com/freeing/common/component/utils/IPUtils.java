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

    /**
     * 将 IP 字符串转换为 BigInteger
     *
     * @param ipStr IP 字符串
     * @return IP 字符串的 BigInteger 表示
     */
    public static BigInteger ipAddressToBigInteger(String ipStr) {
        BigInteger ipBigInteger = new BigInteger(StrPool.ZERO);
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
            try {
                ipBigInteger = ipBigInteger
                    .shiftLeft(segmentLength)
                    .or(new BigInteger(String.valueOf(Integer.parseInt(ipSegment, ipRadix))));
            } catch (NumberFormatException ignored) {
                throw new IllegalArgumentException(ipStr + " is not a ip address.");
            }
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
        if (!IPAddressUtil.isIPv6LiteralAddress(ipv6Address)) {
            throw new IllegalArgumentException(ipv6Address + " is not a ipv6 address.");
        }
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
     * @return IP 字符串
     */
    public static String bigIntegerToIPAddress(BigInteger bigInteger) {
        return null;
    }
}

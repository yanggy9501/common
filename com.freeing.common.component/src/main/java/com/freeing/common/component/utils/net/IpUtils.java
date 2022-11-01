package com.freeing.common.component.utils.net;

import com.freeing.common.component.constants.NumConstants;
import com.freeing.common.component.constants.StrPool;
import com.freeing.common.component.utils.StringUtils;

import java.math.BigInteger;
import java.util.Objects;

/**
 * IP 工具类
 *
 * @author yanggy
 */
public class IpUtils {
    /**
     * 判断一字符串是否为IP地址
     *
     * @param ipStr IP地址
     * @return boolean
     */
    public static boolean isIpAddress(String ipStr) {
        return isIpv4(ipStr) || isIpv6(ipStr);
    }

    /**
     * 获取 IP 版本
     *
     * @param ipStr IP地址
     * @return  Ip Version
     */
    public static IpVersion getIpVersion(String ipStr) {
        if (isIpv4(ipStr)) {
            return IpVersion.IPV4;
        } else if (isIpv6(ipStr)) {
            return IpVersion.IPV6;
        } else {
            return IpVersion.NULL;
        }
    }

    public static boolean isIpv4(String ipStr) {
        Objects.requireNonNull(ipStr, "Illegal ipv4 address.");
        String[] ipSegmentArr = ipStr.split(StrPool.BACK_SLASH_DOT);
        if (ipSegmentArr.length != NumConstants.NUM_4) {
            return false;
        }
        for (String segment : ipSegmentArr) {
            if (segment.length() > 3 || segment.length() == 0) {
                return false;
            }
            if (segment.length() > 1 && segment.charAt(0) == '0') {
                return false;
            }
            for (int j = 0; j < segment.length(); j++) {
                char ch = segment.charAt(j);
                if (!(ch >= '0' && ch <= '9')) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isIpv6(String ipStr) {
        Objects.requireNonNull(ipStr, "IPv6 address is null.");
        String[] bigIpv6SegmentArr = ipStr.split(StrPool.DOUBLE_COLON);
        if (bigIpv6SegmentArr.length > NumConstants.NUM_2) {
            return false;
        }
        int count = 0;
        for (String bigIpv6Segment : bigIpv6SegmentArr) {
            String[] peerSegment = bigIpv6Segment.split(StrPool.COLON);
            count += peerSegment.length;
            if (count > 8) {
                return false;
            }
            for (String segment : peerSegment) {
                if (segment.length() > 4 || segment.length() == 0) {
                    return false;
                }
                if (segment.length() > 1 && segment.charAt(0) == '0') {
                    return false;
                }
                for (int j = 0; j < segment.length(); j++) {
                    char ch = segment.charAt(j);
                    if (ch >= '0' && ch <= '9') {
                        continue;
                    }
                    if (ch  >= 'a' && ch <= 'f') {
                        continue;
                    }
                    if (ch >= 'A' && ch <= 'F') {
                        continue;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /***
     * 获取下一个连续 IP
     *
     * @param ipAddress IP
     * @return IP
     */
    public static String nextIpAddress(IpAddress ipAddress) {
        return nextIpAddressOfStep(ipAddress, 1);
    }

    /***
     * 获取下一个指定步长的 IP 地址
     *
     * @param ipAddress IP
     * @param step 步长
     * @return IP
     */
    public static String nextIpAddressOfStep(IpAddress ipAddress, int step) {
        BigInteger bigInteger = ipAddress.getIpBigInteger();
        BigInteger next = bigInteger.add(BigInteger.valueOf(step));
        return bigIntegerToIpAddress(next, ipAddress.getVersion());
    }

    /**
     * 检查两个 IP 地址是否冲突。
     * 输入格式；ipStr=127.0.0.0/20 或者 a::/120
     *
     * @param ipA IP
     * @param ipB IP
     * @return true: 则两个 IP 地址冲突
     */
    public static boolean checkIpConflict(IpAddress ipA, IpAddress ipB) {

        IpVersion ipVersionA = ipA.getVersion();
        int markbitA = ipA.getMark();

        IpVersion ipVersionB = ipB.getVersion();
        int markbitB = ipB.getMark();

        // IP 版本不一样一定不冲突
        if (ipVersionA != ipVersionB) {
            return false;
        }

        int bitLength = ipVersionA == IpVersion.IPV4 ?
            NumConstants.IPV4_BIT_LENGTH :  NumConstants.IPV6_BIT_LENGTH;

        BigInteger bigIntA = ipA.getIpBigInteger();
        BigInteger bigIntB = ipB.getIpBigInteger();

        // 根据二进制比较是否冲突
        return StringUtils
            .leftPad(bigIntA.toString(NumConstants.RADIX_2), bitLength, StrPool.ZERO)
            .startsWith(StringUtils
                .leftPad(bigIntB.toString(NumConstants.RADIX_2), bitLength, StrPool.ZERO)
                .substring(0, Math.min(markbitA, markbitB))
            );
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
            throw new IllegalArgumentException("Error IP version.");
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
}

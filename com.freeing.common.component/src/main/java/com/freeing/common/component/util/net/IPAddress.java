package com.freeing.common.component.util.net;

import com.freeing.common.component.constant.NumConstants;
import com.freeing.common.component.constant.StrPool;
import com.freeing.common.component.util.NumberUtils;
import com.freeing.common.component.util.StringUtils;

import java.math.BigInteger;

/**
 * IP
 *
 * @author yanggy
 */
public class IPAddress {
    /**
     * IP 地址
     */
    private String ipAddress;

    /**
     * IP 的十或十六进制表示
     */
    private  BigInteger ipBigInteger;

    /**
     * IP 掩码位
     */
    private int mark;

    /**
     * IP 版本
     */
    private IPVersion version;

    /**
     * 构建对象
     *
     * @param ip ip 如：127.0.0.1\24 或 127.0.0.1；a::1:1\124 或 a:: 若无掩码则默认 24 或 124
     * @return
     */
    public static IPAddress build(String ip) {
        return new IPAddress(ip);
    }

    /**
     * 构建对象
     *
     * @param ipBigInteger ip 的十进制表示
     * @param mark 掩码
     * @param version ip 版本
     * @return
     */
    public static IPAddress build(BigInteger ipBigInteger, int mark, IPVersion version) {
        return new IPAddress(ipBigInteger, mark, version);
    }

    /**
     * 构造方法
     *
     * @param ip 如：127.0.0.1\24 或 127.0.0.1；a::1:1\124 或 a:: 若无掩码则默认 24 或 124
     */
    private IPAddress(String ip) {
        parse(ip);
    }

    private IPAddress(BigInteger ipBigInteger, int mark, IPVersion version) {
        this.ipBigInteger = ipBigInteger;
        this.mark = mark;
        this.version = version;
        ipAddress = IPUtils.bigIntegerToIpAddress(ipBigInteger, version);
    }

    private void parse(String ip) {
        // IP 格式: 127.0.0.1；127.0.0.1\24
        String[] ipAndMark = ip.split(StrPool.BACK_SLASH);
        if (ipAndMark.length > NumConstants.NUM_2) {
            throw new IllegalArgumentException("Illegal ip: " + ip);
        }
        doParseIpAddress(ipAndMark[0]);
        if (version == IPVersion.IPV4) {
            mark = ipAndMark.length == 2 ? NumberUtils.parseInt(ipAndMark[1], 24) : 24;
        } else {
            mark = ipAndMark.length == 2 ? NumberUtils.parseInt(ipAndMark[1], 124) : 124;
        }
    }

    private void doParseIpAddress(String ipStr) {
        if (IPUtils.isIpv4(ipStr)) {
            this.version = IPVersion.IPV4;
            this.ipAddress = ipStr;
        } else if (IPUtils.isIpv6(ipStr)) {
            this.version = IPVersion.IPV6;
            standardizeIpv6Address(ipStr);
        } else {
            throw new IllegalArgumentException("Illegal ip '" + ipStr + "'");
        }
        this.ipBigInteger = ipAddressToBigInteger();
    }

    /**
     * 将 IP 字符串转换为 BigInteger
     *
     * @return IP 字符串的 BigInteger 表示
     */
    private BigInteger ipAddressToBigInteger() {
        BigInteger ipBigInteger = new BigInteger(StrPool.ZERO);
        // ip 地址每小段的二进制位长度
        boolean ipv4Flag = this.version == IPVersion.IPV4;
        int segmentLength = ipv4Flag ?
            NumConstants.IPV4_SEGMENT_BIT_LENGTH : NumConstants.IPV6_SEGMENT_BIT_LENGTH;
        // ip 地址段的进制表示
        int ipRadix = ipv4Flag ? NumConstants.RADIX_10 : NumConstants.RADIX_16;
        // ip 地址段的分隔符
        String separator = ipv4Flag ? StrPool.BACK_SLASH_DOT : StrPool.COLON;

        String[] ipSegmentArr = this.ipAddress.split(separator);
        for (String ipSegment : ipSegmentArr) {
            ipBigInteger =
                ipBigInteger
                .shiftLeft(segmentLength)
                .or(new BigInteger(String.valueOf(Integer.parseInt(ipSegment, ipRadix))));
        }
        return ipBigInteger;
    }

    /**
     * 将简写的 ipv6 地址展开为标准的 ipv6 地址
     *
     * @param ipv6Address ipv6
     */
    private void standardizeIpv6Address(String ipv6Address) {
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

        this.ipAddress = standardIpv6.toString();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public BigInteger getIpBigInteger() {
        return ipBigInteger;
    }

    public int getMark() {
        return mark;
    }

    public IPVersion getVersion() {
        return version;
    }
}

package com.freeing.common.component.utils.net;

import com.freeing.common.component.constants.NumConstants;
import com.freeing.common.component.constants.StrPool;
import com.freeing.common.component.utils.NumberUtils;
import com.freeing.common.component.utils.StringUtils;

import java.math.BigInteger;

/**
 * IP
 *
 * @author yanggy
 */
public class IpAddress {
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
    private IpVersion version;

    public IpAddress(String ip) {
        parse(ip);
    }

    public IpAddress(BigInteger ipBigInteger, int mark, IpVersion version) {
        this.ipBigInteger = ipBigInteger;
        this.mark = mark;
        this.version = version;
        ipAddress = IpUtils.bigIntegerToIpAddress(ipBigInteger, version);
    }

    private void parse(String ip) {
        // IP 格式: 127.0.0.1；127.0.0.1/24
        String[] ipAndMark = ip.split(StrPool.BACK_SLASH);
        if (ipAndMark.length > NumConstants.NUM_2) {
            return;
        }
        doParseIpAddress(ipAndMark[0]);
        if (version == IpVersion.IPV4) {
            mark = ipAndMark.length == 2 ? NumberUtils.parseInt(ipAndMark[1], 24) : 24;
        } else {
            mark = ipAndMark.length == 2 ? NumberUtils.parseInt(ipAndMark[1], 124) : 124;
        }
    }

    private void doParseIpAddress(String ipStr) {
        if (IpUtils.isIpv4(ipStr)) {
            this.version = IpVersion.IPV4;
            this.ipAddress = ipStr;
        } else if (IpUtils.isIpv6(ipStr)) {
            this.version = IpVersion.IPV6;
            standardizeIpv6Address(ipStr);
        } else {
            throw new IllegalArgumentException("Error IP address: " + ipStr);
        }
        this.ipBigInteger = ipAddressToBigInteger(ipStr);

    }

    /**
     * 将 IP 字符串转换为 BigInteger
     *
     * @param ipStr IP 字符串
     * @return IP 字符串的 BigInteger 表示
     */
    private BigInteger ipAddressToBigInteger(String ipStr) {
        BigInteger ipBigInteger = new BigInteger(StrPool.ZERO);
        // ip 地址每小段的二进制位长度
        boolean ipv4Flag = this.version == IpVersion.IPV4;
        int segmentLength = ipv4Flag ?
            NumConstants.IPV4_SEGMENT_BIT_LENGTH : NumConstants.IPV6_SEGMENT_BIT_LENGTH;
        // ip 地址段的进制表示
        int ipRadix = ipv4Flag? NumConstants.RADIX_10 : NumConstants.RADIX_16;
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

    public IpVersion getVersion() {
        return version;
    }

    public void setVersion(IpVersion version) {
        this.version = version;
    }
}

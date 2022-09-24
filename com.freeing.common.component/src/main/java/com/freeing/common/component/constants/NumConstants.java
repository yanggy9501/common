package com.freeing.common.component.constants;

/**
 * Number 常量
 *
 * @author yanggy
 */
public interface NumConstants {
    /**
     * 整数
     */
    int NUM_4 = 4;
    int NUM_8 = 8;
    int NUM_32 = 32;

    /**
     * 集合默认长度
     */
    int MAP_DEFAULT_SIZE = 16;
    int SET_DEFAULT_SIZE = 16;

    /**
     * buffer 默认大小
     */
    int BUFFER_DEFAULT_SIZE = 8192;

    /**
     * N 进制基数
     */
     int RADIX_2 = 2;
     int RADIX_10 = 10;
     int RADIX_16 = 16;

    /* ***************************** IP *****************************/

    /**
     * IPV4 bit 位长度
     */
     int IPV4_BIT_LENGTH = 32;

    /**
     * IPV4 每段 bit 位长度
     */
     int IPV4_SEGMENT_BIT_LENGTH = 8;

    /**
     * IPV6 bit 位长度
     */
     int IPV6_BIT_LENGTH = 128;

    /**
     * IPV6 每段 bit 位长度
     */
     int IPV6_SEGMENT_BIT_LENGTH = 16;

    /**
     * IPV6 总的分段数
     */
    int IPV6_TOTAL_SEGMENT = 8;
}

package com.nnnn.dddd.controller.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * VO tương tác giữa frontend và backend
 *
 * @author vantrang
 */
@Data
public class ResultMessage<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Cờ thành công
     */
    private boolean success;

    /**
     * Thông báo
     */
    private String message;

    /**
     * Mã trả về
     */
    private Integer code;

    /**
     * Dấu thời gian (epoch millis)
     */
    private long timestamp = System.currentTimeMillis();

    /**
     * Dấu thời gian dạng chuỗi định dạng dễ đọc
     */
    private String timestampFormatted = formatTimestamp(timestamp);

    private static String formatTimestamp(long epochMillis) {
        // Customize your timezone here if needed
        return Instant.ofEpochMilli(epochMillis)
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * Đối tượng kết quả
     */
    private T result;
}


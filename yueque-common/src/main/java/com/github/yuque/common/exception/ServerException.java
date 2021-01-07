package com.github.yuque.common.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: yuque
 * @description: 服务异常
 * @author: zhangchaozhen
 * @create: 2021-01-07 15:28
 **/
@JsonIgnoreProperties({"logger"})
public class ServerException extends BaseException {
    private static final Logger log = LoggerFactory.getLogger(ServerException.class);
    @JsonProperty("message")
    private final String message;
    @JsonProperty("cause")
    private final String causes;
    @JsonProperty("stackTrace")
    private final List<StackTraceElement> stackTraceList;

    public ServerException(String bizCode, Exception ex) {
        super(bizCode);
        this.message = ex.getMessage();
        this.causes = ex.getClass().getName();
        this.stackTraceList = Arrays.stream(ex.getStackTrace()).filter((i) -> {
            return i.getClassName().startsWith("com.github.yuque.albert.") || i.getClassName().startsWith("com.github.yuque.");
        }).collect(Collectors.toList());
        log.error(this.causes, ex);
    }

    public ServerException(String bizCode, String message) {
        super(bizCode);
        this.causes = null;
        this.stackTraceList = null;
        this.message = message;
        log.error(message);
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public String getCauses() {
        return this.causes;
    }

    public List<StackTraceElement> getStackTraceList() {
        return this.stackTraceList;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ServerException)) {
            return false;
        } else {
            ServerException other = (ServerException)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$message = this.getMessage();
                    Object other$message = other.getMessage();
                    if (this$message == null) {
                        if (other$message == null) {
                            break label47;
                        }
                    } else if (this$message.equals(other$message)) {
                        break label47;
                    }

                    return false;
                }

                Object this$causes = this.getCauses();
                Object other$causes = other.getCauses();
                if (this$causes == null) {
                    if (other$causes != null) {
                        return false;
                    }
                } else if (!this$causes.equals(other$causes)) {
                    return false;
                }

                Object this$stackTraceList = this.getStackTraceList();
                Object other$stackTraceList = other.getStackTraceList();
                if (this$stackTraceList == null) {
                    if (other$stackTraceList != null) {
                        return false;
                    }
                } else if (!this$stackTraceList.equals(other$stackTraceList)) {
                    return false;
                }

                return true;
            }
        }
    }

    @Override
    protected boolean canEqual(final Object other) {
        return other instanceof ServerException;
    }

    @Override
    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $causes = this.getCauses();
        result = result * 59 + ($causes == null ? 43 : $causes.hashCode());
        Object $stackTraceList = this.getStackTraceList();
        result = result * 59 + ($stackTraceList == null ? 43 : $stackTraceList.hashCode());
        return result;
    }
}
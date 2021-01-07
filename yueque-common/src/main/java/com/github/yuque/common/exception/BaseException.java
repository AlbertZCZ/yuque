package com.github.yuque.common.exception;

/**
 * @program: yuque
 * @description: 基础异常
 * @author: zhangchaozhen
 * @create: 2021-01-07 15:26
 **/
public abstract class BaseException extends RuntimeException {
    private final String bizCode;

    BaseException(String bizCode) {
        super(bizCode);
        this.bizCode = bizCode;
    }

    public String getBizCode() {
        return this.bizCode;
    }

    @Override
    public String toString() {
        return "BaseException(bizCode=" + this.getBizCode() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BaseException)) {
            return false;
        } else {
            BaseException other = (BaseException) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$bizCode = this.getBizCode();
                Object other$bizCode = other.getBizCode();
                if (this$bizCode == null) {
                    if (other$bizCode != null) {
                        return false;
                    }
                } else if (!this$bizCode.equals(other$bizCode)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BaseException;
    }

    @Override
    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $bizCode = this.getBizCode();
        result = result * 59 + ($bizCode == null ? 43 : $bizCode.hashCode());
        return result;
    }
}

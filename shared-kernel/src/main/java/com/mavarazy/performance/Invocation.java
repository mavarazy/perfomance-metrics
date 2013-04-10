package com.mavarazy.performance;

import java.lang.reflect.Method;

public class Invocation {

    final private String className;
    final private String methodName;

    private Invocation(String className, String methodName) {
        assert className != null : "Class name must not be null";
        assert methodName != null : "Method name must not be null";
        this.className = className;
        this.methodName = methodName;
    }

    final static public Invocation valueOf(Method method) {
        return new Invocation(method.getDeclaringClass().getSimpleName(), method.getName());
    }

    final static public Invocation valueOf(String className, String methodName) {
        return new Invocation(className, methodName);
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((className == null) ? 0 : className.hashCode());
        result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Invocation other = (Invocation) obj;
        if (className == null) {
            if (other.className != null) {
                return false;
            }
        } else if (!className.equals(other.className)) {
            return false;
        }
        if (methodName == null) {
            if (other.methodName != null) {
                return false;
            }
        } else if (!methodName.equals(other.methodName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Invocation [" + className + "." + methodName + "]";
    }
}

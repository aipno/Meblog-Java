package cn.iswxl.meblog.jwt.aspect;

import cn.iswxl.meblog.common.context.UserContext;
import cn.iswxl.meblog.common.domain.mapper.UserMapper;
import cn.iswxl.meblog.common.enums.LogicalEnum;
import cn.iswxl.meblog.common.enums.ResponseCodeEnum;
import cn.iswxl.meblog.common.exception.BizException;
import cn.iswxl.meblog.jwt.annotation.RequiresPermission;
import cn.iswxl.meblog.jwt.annotation.RequiresRoles;
import cn.iswxl.meblog.jwt.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Aspect
@Component
@Slf4j
public class PermissionAspect {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserMapper userMapper;

    /**
     * 权限注解的切点
     */
    @Pointcut("@annotation(cn.iswxl.meblog.jwt.annotation.RequiresPermission)")
    public void permissionPointcut() {
    }

    /**
     * 角色注解的切点
     */
    @Pointcut("@annotation(cn.iswxl.meblog.jwt.annotation.RequiresRoles)")
    public void rolePointcut() {
    }

    /**
     * 权限验证通知
     */
    @Around("@annotation(cn.iswxl.meblog.jwt.annotation.RequiresPermission)") // 简化 Pointcut 写法
    public Object checkPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = joinPoint.getTarget().getClass();

        // 1. 获取用户信息 (使用我们自定义的 Context)
        Long userId = UserContext.getUserId();
        if (userId == null) {
            throw new BizException(ResponseCodeEnum.UNAUTHORIZED);
        }

        // 2. 解析权限
        RequiresPermission methodAnno = method.getAnnotation(RequiresPermission.class);
        RequiresPermission classAnno = targetClass.getAnnotation(RequiresPermission.class);

        // 获取类级别的基础权限 (例如 "admin:article")
        String basePerm = (classAnno != null && classAnno.value().length > 0)
                ? classAnno.value()[0] : "";

        // 组合最终权限列表
        List<String> finalPermissions = new ArrayList<>();
        if (methodAnno != null) {
            for (String p : methodAnno.value()) {
                // 如果方法权限包含冒号(如 "root:admin")，视为绝对路径，不拼接
                // 否则拼接：basePerm + ":" + p
                if (p.contains(":") || StringUtils.isBlank(basePerm)) {
                    finalPermissions.add(p);
                } else {
                    finalPermissions.add(basePerm + ":" + p);
                }
            }
        }

        // 3. 执行校验
        // 注意：这里需要把 logical 参数也传进去，通常以方法上的定义为准
        LogicalEnum logical = (methodAnno != null) ? methodAnno.logical() : LogicalEnum.AND;

        boolean hasPermission = checkPermissions(userId, finalPermissions.toArray(new String[0]), logical);
        if (!hasPermission) {
            throw new BizException(ResponseCodeEnum.FORBIDDEN);
        }

        return joinPoint.proceed();
    }

    /**
     * 角色验证通知
     */
    @Around("rolePointcut()")
    public Object checkRole(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        RequiresRoles requiresRoles = method.getAnnotation(RequiresRoles.class);
        if (requiresRoles != null) {
            Long userId = UserContext.getUserId();
            if (userId == null) {
                throw new BizException(ResponseCodeEnum.UNAUTHORIZED);
            }

            String[] roles = requiresRoles.value();
            LogicalEnum logical = requiresRoles.logical();

            boolean hasRole = checkRoles(userId, roles, logical);
            if (!hasRole) {
                throw new BizException(ResponseCodeEnum.FORBIDDEN);
            }
        }

        return joinPoint.proceed();
    }

    /**
     * 验证权限
     */
    private boolean checkPermissions(Long userId, String[] permissions, LogicalEnum logical) {
        if (permissions.length == 0) {
            return true;
        }

        Set<String> userPermissions = permissionService.getUserPermissions(userId);

        if (logical == LogicalEnum.AND) {
            // 必须拥有所有权限
            for (String permission : permissions) {
                if (!userPermissions.contains(permission)) {
                    return false;
                }
            }
            return true;
        } else {
            // 拥有任意一个权限即可
            for (String permission : permissions) {
                if (userPermissions.contains(permission)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 验证角色
     */
    private boolean checkRoles(Long userId, String[] roles, LogicalEnum logical) {
        String userRoles = permissionService.getUserRole(userId);

        if (logical == LogicalEnum.AND) {
            for (String role : roles) {
                if (!userRoles.contains(role)) {
                    return false;
                }
            }
            return true;
        } else {
            for (String role : roles) {
                if (userRoles.contains(role)) {
                    return true;
                }
            }
            return false;
        }
    }
}
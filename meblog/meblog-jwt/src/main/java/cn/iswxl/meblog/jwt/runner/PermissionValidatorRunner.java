package cn.iswxl.meblog.jwt.runner;

import cn.iswxl.meblog.common.domain.dos.PermissionDO;
import cn.iswxl.meblog.common.domain.mapper.PermissionMapper;
import cn.iswxl.meblog.jwt.annotation.RequiresPermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 启动时校验权限配置完整性
 */
@Component
@Slf4j
public class PermissionValidatorRunner implements CommandLineRunner {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public void run(String... args) throws Exception {
        log.info("开始校验权限配置完整性...");

        // 1. 扫描代码中定义的所有权限
        Set<String> codePermissions = new HashSet<>();
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();

        map.forEach((info, handlerMethod) -> {
            // 获取类上的注解
            RequiresPermission classAnno = handlerMethod.getBeanType().getAnnotation(RequiresPermission.class);
            String basePerm = (classAnno != null && classAnno.value().length > 0) ? classAnno.value()[0] : "";

            // 获取方法上的注解
            RequiresPermission methodAnno = handlerMethod.getMethodAnnotation(RequiresPermission.class);

            if (methodAnno != null) {
                for (String p : methodAnno.value()) {
                    String fullPerm = (p.contains(":") || basePerm.isEmpty()) ? p : basePerm + ":" + p;
                    codePermissions.add(fullPerm);
                }
            }
        });

        // 2. 查询数据库中存在的所有权限
        // 假设 PermissionDO 有 permCode 字段
        List<PermissionDO> dbPermissionList = permissionMapper.selectList(null);
        Set<String> dbPermissions = dbPermissionList.stream()
                .map(PermissionDO::getPermCode)
                .collect(Collectors.toSet());

        // 3. 对比差异
        List<String> missingPermissions = new ArrayList<>();
        for (String codePerm : codePermissions) {
            if (!dbPermissions.contains(codePerm)) {
                missingPermissions.add(codePerm);
            }
        }

        // 4. 报警或报错
        if (!missingPermissions.isEmpty()) {
            log.error("======================================================");
            log.error("【严重警告】检测到代码中定义了数据库中不存在的权限码！");
            log.error("请立即在数据库 t_permission 表中添加以下权限：");
            missingPermissions.forEach(p -> log.error("- {}", p));
            log.error("======================================================");
            // 如果你想严格一点，可以直接抛出异常阻止项目启动
            // throw new RuntimeException("权限配置不一致，启动终止");
        } else {
            log.info("权限校验通过，数据库与代码一致。");
        }
    }
}
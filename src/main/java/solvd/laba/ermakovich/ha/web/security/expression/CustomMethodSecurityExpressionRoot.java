package solvd.laba.ermakovich.ha.web.security.expression;

import lombok.Setter;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import solvd.laba.ermakovich.ha.domain.UserInfo;
import solvd.laba.ermakovich.ha.domain.UserRole;
import solvd.laba.ermakovich.ha.service.UserInfoService;

@Setter
public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private UserInfoService userService;
    private Object filterObject;
    private Object returnObject;
    private Object target;
    private Authentication authentication;

    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
        this.authentication = authentication;
    }


    public boolean hasPatientAccess(Long id) {
        UserInfo userInfo = userService.findByEmail(authentication.getName());
        UserInfo user =  this.userService.findById(id);
        if (user.getRole().equals(UserRole.ADMIN)) {
            return true;
        }
        return id.equals(userInfo.getId());
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public Object getThis() {
        return target;
    }
}

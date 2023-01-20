package solvd.laba.ermakovich.ha.web.security.expression;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import solvd.laba.ermakovich.ha.domain.Appointment;
import solvd.laba.ermakovich.ha.domain.Review;
import solvd.laba.ermakovich.ha.domain.UserRole;
import solvd.laba.ermakovich.ha.service.AppointmentService;
import solvd.laba.ermakovich.ha.service.ReviewService;
import solvd.laba.ermakovich.ha.web.security.jwt.JwtUserDetails;

@Setter
public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot  implements MethodSecurityExpressionOperations {

    private HttpServletRequest request;
    private Object filterObject;
    private Object returnObject;
    private Object target;
    private Authentication authentication;
    private AppointmentService appointmentService;
    private ReviewService reviewService;

    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
        this.authentication = authentication;
    }

    public boolean hasAccess(Long id) {
        JwtUserDetails jwtUser = (JwtUserDetails) authentication.getPrincipal();
        return id.equals(jwtUser.getId()) || hasAdminRole(jwtUser);
    }

    public boolean hasAccessForDelApp(Long appId) {
        JwtUserDetails jwtUser = (JwtUserDetails) authentication.getPrincipal();
        Appointment appointment = appointmentService.retrieveById(appId);
        return appointment.getPatientCard()
                .getId()
                .equals(jwtUser.getId()) || hasAdminRole(jwtUser);
    }

    public boolean hasAccessForReview(Long reviewId) {
        JwtUserDetails jwtUser = (JwtUserDetails) authentication.getPrincipal();
        Review review = reviewService.retrieveById(reviewId);
        return review.getPatient()
                .getId()
                .equals(jwtUser.getId()) || hasAdminRole(jwtUser);
    }

    private boolean hasAdminRole(JwtUserDetails jwtUser) {
        var adminAuthority = new SimpleGrantedAuthority(UserRole.ADMIN.getAuthority());
        return jwtUser.getAuthorities()
                .contains(adminAuthority);
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
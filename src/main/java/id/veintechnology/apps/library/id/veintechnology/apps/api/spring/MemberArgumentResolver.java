package id.veintechnology.apps.library.id.veintechnology.apps.api.spring;

import id.veintechnology.apps.library.id.veintechnology.apps.api.AuthenticatedMember;
import id.veintechnology.apps.library.id.veintechnology.apps.dao.Member;
import id.veintechnology.apps.library.id.veintechnology.apps.security.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;




@Component
public class MemberArgumentResolver implements HandlerMethodArgumentResolver{

    private final ObjectFactory<Member> userObjectFactory;

    @Autowired
    public MemberArgumentResolver(@AuthenticatedMember ObjectFactory<Member> userObjectFactory) {
        this.userObjectFactory = userObjectFactory;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(AuthenticatedMember.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return userObjectFactory.getObject();
    }
}

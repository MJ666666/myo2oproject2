package com.lmj.o2o.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * ClassName: ResourceServerConfiguration
 * Description:
 * date: 2020/4/3 21:05
 *
 * @author MJ
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {

        //session创建策略：
        //ALWAYS 总是创建HttpSession
        //IF_REQUIRED Spring Security只会在需要时创建一个HttpSession
        //NEVER  Spring Security不会创建HttpSession，但如果它已经存在，将可以使用HttpSession
        //STATELESS SpringSecurity永远不会创建HttpSession，它不会使用HttpSession来获取SecurityContext
        http.exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers("/area/**").hasAuthority("Owner")
                .and().authorizeRequests().antMatchers("/award/**").hasAuthority("Owner")
                .and().authorizeRequests().antMatchers("/image/**").hasAuthority("Owner")
                .and().authorizeRequests().antMatchers("/personInfo/**").hasAuthority("Owner")
                .and().authorizeRequests().antMatchers("/productCategory/**").hasAuthority("Owner")
                .and().authorizeRequests().antMatchers("/product/**").hasAuthority("Owner")
                .and().authorizeRequests().antMatchers("/qrcode").hasAuthority("Owner")
                .and().authorizeRequests().antMatchers("/shopAuth/**").hasAuthority("Owner")
                .and().authorizeRequests().antMatchers("/shopCategory/**").hasAuthority("Owner")
                .and().authorizeRequests().antMatchers("/shop/**").hasAuthority("Owner")
                .and().authorizeRequests().antMatchers("/userAward/**").hasAuthority("Owner")
                .and().authorizeRequests().antMatchers("/useProduct/**").hasAuthority("Owner")
                .and().authorizeRequests().antMatchers("/userShop/**").hasAuthority("Owner")
                .and().authorizeRequests().antMatchers("/useProduct/**").hasAuthority("Owner")
                .and().authorizeRequests().antMatchers("/wechat/**").hasAuthority("Owner")


        ;

    }

}

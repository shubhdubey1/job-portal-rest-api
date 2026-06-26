package com.springcourse.springbootrest.Service;


import com.springcourse.springbootrest.Model.User;
import com.springcourse.springbootrest.Model.UserPrincipal;
import com.springcourse.springbootrest.Repository.UserRepo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

   private static final Logger LOGGER = LoggerFactory.getLogger(MyUserDetailsService.class);
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repo.findByUsername(username);
        if(user==null){
            LOGGER.info("404 NF");
           throw new UsernameNotFoundException("404 NF");
        }

        return new UserPrincipal(user);
    }
}

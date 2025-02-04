//package com.example.ngbackend.Security;
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//@Component
//public class JwtTokenFilter extends OncePerRequestFilter {
//
////    private final JwtUtil jwtUtil;
////    private final UserDetailsService userDetailsService;
////
////    public JwtTokenFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
////        this.jwtUtil = jwtUtil;
////        this.userDetailsService = userDetailsService;
////    }
////
////    private String getTokenFromRequest(HttpServletRequest request) {
////        String bearerToken = request.getHeader("Authorization");
////        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
////            return bearerToken.substring(7);
////        }
////        return null;
////    }
////
////    @Override
////    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
////            throws jakarta.servlet.ServletException, IOException {
////        String token = getTokenFromRequest(request);
////        if (token != null && jwtUtil.validateToken(token)) {
////            String username = jwtUtil.getEmail(token);
////            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
////            UsernamePasswordAuthenticationToken auth =
////                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
////            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
////            SecurityContextHolder.getContext().setAuthentication(auth);
////        }
////        filterChain.doFilter(request, response);
////    }
//}

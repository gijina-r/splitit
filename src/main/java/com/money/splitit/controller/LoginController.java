package com.money.splitit.controller;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String index() {
        return "index"; // Public landing page
    }

    @GetMapping("/home")
    public Mono<String> home(@AuthenticationPrincipal Mono<OidcUser> oidcUserMono, Model model) {
        return oidcUserMono.map(user -> {
            String idToken = user.getIdToken().getTokenValue();  // <-- Google ID token (JWT)
            String email = user.getEmail();
            String name = user.getFullName();

            model.addAttribute("idToken", idToken);
            model.addAttribute("email", email);
            model.addAttribute("name", name);

            System.out.println("Google ID Token: " + idToken);
            return "home";
        }).switchIfEmpty(Mono.just("home"));
    }
    /*@GetMapping("/home")
    public String homeRedirect(Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            System.out.println("Enteredd the google login");
            model.addAttribute("name", principal.getAttribute("name"));
            model.addAttribute("email", principal.getAttribute("email"));
            System.out.println("Enteredd the google login"+principal.getAttribute("name"));
            System.out.println("Enteredd the google login"+principal.getAttribute("email"));
        }
        return "home"; // Logged-in home page
    }*/
}

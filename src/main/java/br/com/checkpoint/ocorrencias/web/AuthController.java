package br.com.checkpoint.ocorrencias.web;
import br.com.checkpoint.ocorrencias.dto.ApiDtos.*; import br.com.checkpoint.ocorrencias.repository.UsuarioRepository; import br.com.checkpoint.ocorrencias.security.JwtService;
import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final UsuarioRepository users; private final PasswordEncoder encoder; private final JwtService jwt;
 public AuthController(UsuarioRepository u,PasswordEncoder e,JwtService j){users=u;encoder=e;jwt=j;}
 @PostMapping("/login") public LoginResponse login(@Valid @RequestBody LoginRequest r){var u=users.findByLogin(r.login()).filter(x->x.isAtivo()&&encoder.matches(r.senha(),x.getSenha())).orElseThrow(()->new IllegalArgumentException("Login ou senha inválidos"));return new LoginResponse(jwt.gerar(u),"Bearer",u.getId(),u.getNome(),u.getLogin(),u.getPerfil());}
 @GetMapping("/me") public UsuarioView me(org.springframework.security.core.Authentication a){var u=(br.com.checkpoint.ocorrencias.domain.Usuario)a.getPrincipal();return new UsuarioView(u.getId(),u.getNome(),u.getLogin(),u.getPerfil(),u.isAtivo());}
}

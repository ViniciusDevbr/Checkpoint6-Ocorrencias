package br.com.checkpoint.ocorrencias.security;
import br.com.checkpoint.ocorrencias.domain.Usuario;
import io.jsonwebtoken.Jwts; import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service;
import javax.crypto.SecretKey; import java.nio.charset.StandardCharsets; import java.util.Date;
@Service
public class JwtService {
 private final SecretKey key; private final long expiration;
 public JwtService(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.expiration-ms}") long expiration){this.key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));this.expiration=expiration;}
 public String gerar(Usuario u){long now=System.currentTimeMillis();return Jwts.builder().subject(u.getLogin()).claim("perfil",u.getPerfil().name()).claim("id",u.getId()).issuedAt(new Date(now)).expiration(new Date(now+expiration)).signWith(key).compact();}
 public String extrairLogin(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();}
}

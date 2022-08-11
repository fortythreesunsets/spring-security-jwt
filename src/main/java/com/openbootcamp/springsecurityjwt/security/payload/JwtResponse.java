package com.openbootcamp.springsecurityjwt.security.payload;

/**
 * Respuestas y clases utilizadas para login, registrarse, recibir una respuesta.
 * Devuelve un token (objeto JSON)
 */
public class JwtResponse {

    private String token;

    public JwtResponse() {}

    public JwtResponse(String token) { this.token = token; }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

}

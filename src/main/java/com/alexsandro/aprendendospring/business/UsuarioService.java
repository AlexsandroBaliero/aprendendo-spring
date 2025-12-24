package com.alexsandro.aprendendospring.business;

import com.alexsandro.aprendendospring.infrastructure.entity.Usuario;
import com.alexsandro.aprendendospring.infrastructure.excepitions.ConflictExcepition;
import com.alexsandro.aprendendospring.infrastructure.excepitions.ResourceNotFoundException;
import com.alexsandro.aprendendospring.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario salvaUsuario(Usuario usuario) {

        try {
            emailExiste(usuario.getEmail());
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            return usuarioRepository.save(usuario);
        } catch (ConflictExcepition e) {
            throw new ConflictExcepition("Email já cadastrado", e.getCause());

        }

    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictExcepition("Email já cadastrado" + email);
            }
        } catch (ConflictExcepition e) {
            throw new ConflictExcepition("Email já cadastrado" + e.getCause());
        }

    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);

    }

    public Usuario buscarUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado" + email));
    }

    public void deletaUsuarioPorEmail (String email){
        usuarioRepository.deleteByEmail(email);
    }

}





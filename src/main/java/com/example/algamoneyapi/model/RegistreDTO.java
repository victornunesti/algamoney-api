package com.example.algamoneyapi.model;

import java.util.List;

public record RegistreDTO(String nome, String email, String senha, List<Permissao> permissoes) {

}

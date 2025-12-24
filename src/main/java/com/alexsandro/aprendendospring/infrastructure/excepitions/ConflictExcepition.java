package com.alexsandro.aprendendospring.infrastructure.excepitions;

public class ConflictExcepition extends RuntimeException{

    public ConflictExcepition(String mensagem){
        super (mensagem);
    }

    public ConflictExcepition(String mensagem, Throwable throwable){
        super(mensagem);
    }
}

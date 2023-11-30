package com.crio.starter.service;

import java.util.List;

public class MemeConstraintViolationException extends RuntimeException{

    public MemeConstraintViolationException(List<String> of) {}

}
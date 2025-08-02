package org.quantdev.var.dto;

public class VarResponse {
    private final double var;

    public VarResponse(double var) {
        this.var = var;
    }

    public double getVar() {
        return var;
    }
}
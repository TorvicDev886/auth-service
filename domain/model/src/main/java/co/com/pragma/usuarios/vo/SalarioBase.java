package co.com.pragma.usuarios.vo;

import java.math.BigDecimal;
import co.com.pragma.shared.DomainException;

public final class SalarioBase {
    private static final BigDecimal MIN = new BigDecimal("0");
    private static final BigDecimal MAX = new BigDecimal("15000000");

    private final BigDecimal value;

    private SalarioBase(BigDecimal value) { this.value = value; }

    public static SalarioBase of(BigDecimal value) {
        if (value == null) throw new DomainException("salario_base requerido");
        if (value.compareTo(MIN) < 0 || value.compareTo(MAX) > 0)
            throw new DomainException("salario_base fuera de rango (0..15,000,000)");
        return new SalarioBase(value);
    }

    public BigDecimal value(){ return value; }
    @Override public String toString(){ return value.toPlainString(); }
}

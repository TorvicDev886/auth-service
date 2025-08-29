package co.com.pragma.usuarios.vo;

import co.com.pragma.shared.DomainException;

import java.util.Objects;
import java.util.regex.Pattern;


public final class Email {
    private static final Pattern RX = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
            Pattern.CASE_INSENSITIVE);

    private final String value;

    private Email(String value) { this.value = value; }

    public static Email of(String value) {
        if (value == null || value.isBlank()) throw new DomainException("correo_electronico requerido");
        if (!RX.matcher(value).matches()) throw new DomainException("correo_electronico inválido");
        return new Email(value.trim());
    }

    public String value() { return value; }

    @Override public boolean equals(Object o){ return o instanceof Email e && value.equalsIgnoreCase(e.value); }
    @Override public int hashCode(){ return Objects.hash(value.toLowerCase()); }
    @Override public String toString(){ return value; }
}

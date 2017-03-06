package de.techdev.trackr.domain.employee;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table( uniqueConstraints = { @UniqueConstraint(columnNames = {"type", "employee_id"}) } )
public class Settings {

    public enum SettingsType {
        LOCALE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SettingsType type;

    @NotEmpty
    private String value;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public String getValue() {
        // TODO lombok is not really compatible with the Kotlin compiler, as long as we have separate classes we need this getter.
        return value;
    }
}

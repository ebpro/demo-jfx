package fr.univtln.bruno.demos.demojfx.model;

import lombok.*;

@AllArgsConstructor(staticName = "of")
@ToString
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Person {
    @EqualsAndHashCode.Include
    private long id;
    private String name;
    private String firstname;
}

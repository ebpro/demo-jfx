package fr.univtln.bruno.demos.demojfx.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;


@AllArgsConstructor(staticName = "of")
@Getter
@ToString
public class Page<E> {
    private final List<E> content;
}

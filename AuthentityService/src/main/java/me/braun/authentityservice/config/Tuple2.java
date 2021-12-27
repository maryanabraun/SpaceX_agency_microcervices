package me.braun.authentityservice.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Tuple2<A, B> {
    private A first;
    private B second;
}
